package yc.com.english_study.mine.presenter;

import android.content.Context;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BasePresenter;
import yc.com.english_study.mine.contract.OrderContract;
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
}
