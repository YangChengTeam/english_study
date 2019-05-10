package yc.com.english_study.study.presenter;

import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BasePresenter;
import yc.com.english_study.study.contract.StudyContract;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPages;
import yc.com.english_study.study.model.domain.StudyPhoneticInfoWrapper;
import yc.com.english_study.study.model.engine.StudyEngine;

/**
 * Created by wanglin  on 2018/10/30 16:40.
 */
public class StudyPresenter extends BasePresenter<StudyEngine, StudyContract.View> implements StudyContract.Presenter {
    public StudyPresenter(Context context, StudyContract.View view) {
        super(context, view);
        mEngine = new StudyEngine(context);
    }

    @Override
    public void loadData(boolean isForceUI, boolean isLoadingUI) {
        if (!isForceUI) return;


    }

    @Override
    public void getStudyPages() {
//        mView.showLoading();
        Subscription subscription = mEngine.getStudyPages().subscribe(new Subscriber<ResultInfo<StudyPages>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(ResultInfo<StudyPages> stringResultInfo) {
                if (stringResultInfo != null) {
                    if (stringResultInfo.code == HttpConfig.STATUS_OK && stringResultInfo.data != null) {
                        mView.hide();
//                        getStudyDetail(0, stringResultInfo.data.count);
                        mView.showStudyPages(stringResultInfo.data.count);
                    } else {
                        mView.showNoData();
                    }
                } else {
                    mView.showNoNet();
                }

            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getStudyDetail(final int page) {
        mView.showLoading();
        Subscription subscription = mEngine.getStudyDetail(page).subscribe(new Subscriber<ResultInfo<StudyInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(ResultInfo<StudyInfoWrapper> studyInfoWrapperResultInfo) {
                if (studyInfoWrapperResultInfo != null) {
                    if (studyInfoWrapperResultInfo.code == HttpConfig.STATUS_OK && studyInfoWrapperResultInfo.data != null) {
                        mView.hide();

                        mView.showStudyInfo(studyInfoWrapperResultInfo.data);

                    } else {
                        mView.showNoData();
                    }

                } else {
                    mView.showNoNet();
                }

            }
        });
        mSubscriptions.add(subscription);

    }

    @Override
    public void getPhoneticPages() {

        Subscription subscription = mEngine.getPhoneticPages().subscribe(new Subscriber<ResultInfo<StudyPages>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultInfo<StudyPages> studyPagesResultInfo) {
                if (studyPagesResultInfo != null && studyPagesResultInfo.code == HttpConfig.STATUS_OK && studyPagesResultInfo.data != null) {
                    mView.showPhoneticPages(studyPagesResultInfo.data.count);
                }
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getPhoneticDetail(int page) {
        mView.showLoading();
        Subscription subscription = mEngine.getPhoneticDetail(page).subscribe(new Subscriber<ResultInfo<StudyPhoneticInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(ResultInfo<StudyPhoneticInfoWrapper> studyInfoWrapperResultInfo) {
                if (studyInfoWrapperResultInfo != null) {
                    if (studyInfoWrapperResultInfo.code == HttpConfig.STATUS_OK && studyInfoWrapperResultInfo.data != null) {
                        mView.hide();
                        mView.showPhoneticInfo(studyInfoWrapperResultInfo.data.getInfo());
                    } else {
                        mView.showNoData();
                    }
                } else {
                    mView.showNoNet();
                }
            }
        });
        mSubscriptions.add(subscription);
    }
}
