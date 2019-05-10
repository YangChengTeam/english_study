package yc.com.english_study.mine.presenter;

import android.content.Context;

import com.hwangjr.rxbus.RxBus;
import com.kk.securityhttp.domain.GoagalInfo;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BasePresenter;
import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.english_study.base.constant.BusAction;
import yc.com.english_study.mine.contract.ShareContract;
import yc.com.english_study.mine.model.engine.ShareEngine;

/**
 * Created by wanglin  on 2019/5/10 10:35.
 */
public class SharePresenter extends BasePresenter<ShareEngine, ShareContract.View> implements ShareContract.Presenter {
    public SharePresenter(Context context, ShareContract.View view) {
        super(context, view);
        mEngine = new ShareEngine(context);
    }

    @Override
    public void loadData(boolean isForceUI, boolean isLoadingUI) {

    }


    public void share() {
        Subscription subscription = mEngine.share().subscribe(new Subscriber<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> stringResultInfo) {
                if (stringResultInfo != null && stringResultInfo.code == HttpConfig.STATUS_OK) {
                    RxBus.get().post(BusAction.SHARE_SUCCESS, "success");
                    SPUtils.getInstance().put(GoagalInfo.get().uuid, true);
                }
            }
        });
        mSubscriptions.add(subscription);
    }
}
