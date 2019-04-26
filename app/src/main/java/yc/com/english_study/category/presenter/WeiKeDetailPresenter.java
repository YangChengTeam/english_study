package yc.com.english_study.category.presenter;

import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BasePresenter;
import yc.com.english_study.category.contract.WeiKeDetailContract;
import yc.com.english_study.category.model.domain.CourseInfo;
import yc.com.english_study.category.model.domain.WeiKeCategoryWrapper;
import yc.com.english_study.category.model.engine.WeiKeDetailEngine;

/**
 * Created by zhangkai on 2017/8/30.
 */

public class WeiKeDetailPresenter extends BasePresenter<WeiKeDetailEngine, WeiKeDetailContract.View> implements WeiKeDetailContract.Presenter {

    private final String WEIKE_INFO = "weike_info";
    private final String SPOKEN_INFO = "spoken_info";

    public WeiKeDetailPresenter(Context context, WeiKeDetailContract.View iView) {
        super(context, iView);
        mEngine = new WeiKeDetailEngine(context);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {

    }

    @Override
    public void getWeikeCategoryInfo(String id) {
        mView.showLoading();
        Subscription subscription = mEngine.getWeikeCategoryInfo(id, 1, 20).subscribe(new Subscriber<ResultInfo<CourseInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo<CourseInfo> courseInfoResultInfo) {
                if (courseInfoResultInfo!=null){
                    if (courseInfoResultInfo.code == HttpConfig.STATUS_OK && courseInfoResultInfo.data != null){
                        mView.hide();
                        CourseInfo courseInfo = courseInfoResultInfo.data;
                        mView.showWeikeInfo(courseInfo);
                    }else {
                        mView.showNoData();
                    }

                }else {
                    mView.showNoNet();
                }
            }
        });
        mSubscriptions.add(subscription);
    }


}
