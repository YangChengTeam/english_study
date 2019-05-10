package yc.com.english_study.mine.presenter;

import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BasePresenter;
import yc.com.english_study.mine.contract.OrderContract;
import yc.com.english_study.mine.model.bean.OrderInfoWrapper;
import yc.com.english_study.mine.model.engine.OrderEngine;
import yc.com.english_study.pay.alipay.OrderInfo;

/**
 * Created by wanglin  on 2019/4/25 16:42.
 */
public class OrderPresenter extends BasePresenter<OrderEngine, OrderContract.View> implements OrderContract.Presenter {
    public OrderPresenter(Context context, OrderContract.View view) {
        super(context, view);
        mEngine = new OrderEngine(context);
    }

    @Override
    public void loadData(boolean isForceUI, boolean isLoadingUI) {
        if (!isForceUI) return;
        getOrderInfos();
//        getOrderInfoList();
    }

    @Override
    public void getOrderInfos() {
        Subscription subscription = mEngine.getOrderInfos().subscribe(new Subscriber<List<OrderInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<OrderInfo> orderInfos) {
                mView.showOrderInfos(orderInfos);
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getOrderInfoList(final int page, int page_size, boolean isRefresh) {
        if (page == 1 && !isRefresh)
            mView.showLoading();
        Subscription subscription = mEngine.getOrderInfoList(page, page_size).subscribe(new Subscriber<ResultInfo<OrderInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (page == 1)
                    mView.showNoNet();
            }

            @Override
            public void onNext(ResultInfo<OrderInfoWrapper> infoWrapperResultInfo) {
                if (infoWrapperResultInfo != null) {
                    if (infoWrapperResultInfo.code == HttpConfig.STATUS_OK && infoWrapperResultInfo.data != null ) {
                        mView.hide();
                        mView.showOrderInfos(infoWrapperResultInfo.data.getList());
                    } else {
                        if (page == 1)
                            mView.showNoData();
                    }
                } else {
                    if (page == 1)
                        mView.showNoNet();
                }
            }
        });
        mSubscriptions.add(subscription);
    }


}
