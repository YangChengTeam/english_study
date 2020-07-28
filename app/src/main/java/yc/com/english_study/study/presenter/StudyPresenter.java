package yc.com.english_study.study.presenter;

import android.content.Context;

import yc.com.base.BasePresenter;
import yc.com.english_study.base.observer.BaseCommonObserver;
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

        mEngine.getStudyPages().subscribe(new BaseCommonObserver<StudyPages>(mContext) {
            @Override
            public void onSuccess(StudyPages data, String message) {

                if (data != null) {
                    mView.hide();
//                        getStudyDetail(0, stringResultInfo.data.count);
                    mView.showStudyPages(data.count);
                } else {
                    mView.showNoData();
                }

            }

            @Override
            public void onFailure(int code, String errorMsg) {
                mView.showNoNet();
            }

            @Override
            public void onRequestComplete() {

            }
        });
//        Subscription subscription = mEngine.getStudyPages().subscribe(new Subscriber<ResultInfo<StudyPages>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.showNoNet();
//            }
//
//            @Override
//            public void onNext(ResultInfo<StudyPages> stringResultInfo) {
//
//
//            }
//        });
//        mSubscriptions.add(subscription);
    }

    @Override
    public void getStudyDetail(final int page) {
        mView.showLoading();

        mEngine.getStudyDetail(page).subscribe(new BaseCommonObserver<StudyInfoWrapper>(mContext) {
            @Override
            public void onSuccess(StudyInfoWrapper data, String message) {

                if (data != null) {
                    mView.hide();

                    mView.showStudyInfo(data);

                } else {
                    mView.showNoData();
                }


            }

            @Override
            public void onFailure(int code, String errorMsg) {
                mView.showNoNet();
            }

            @Override
            public void onRequestComplete() {

            }
        });

//        Subscription subscription = mEngine.getStudyDetail(page).subscribe(new Subscriber<ResultInfo<StudyInfoWrapper>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.showNoNet();
//            }
//
//            @Override
//            public void onNext(ResultInfo<StudyInfoWrapper> studyInfoWrapperResultInfo) {
//
//
//            }
//        });
//        mSubscriptions.add(subscription);

    }

    @Override
    public void getPhoneticPages() {

        mEngine.getPhoneticPages().subscribe(new BaseCommonObserver<StudyPages>(mContext) {
            @Override
            public void onSuccess(StudyPages data, String message) {
                if (data != null) {
                    mView.showPhoneticPages(data.count);
                }
            }

            @Override
            public void onFailure(int code, String errorMsg) {

            }

            @Override
            public void onRequestComplete() {

            }
        });
//        Subscription subscription = mEngine.getPhoneticPages().subscribe(new Subscriber<ResultInfo<StudyPages>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onNext(ResultInfo<StudyPages> studyPagesResultInfo) {
//
//            }
//        });
//        mSubscriptions.add(subscription);
    }

    @Override
    public void getPhoneticDetail(int page) {
        mView.showLoading();
        mEngine.getPhoneticDetail(page).subscribe(new BaseCommonObserver<StudyPhoneticInfoWrapper>(mContext) {
            @Override
            public void onSuccess(StudyPhoneticInfoWrapper data, String message) {

                if (data != null) {
                    mView.hide();
                    mView.showPhoneticInfo(data.getInfo());
                } else {
                    mView.showNoData();
                }

            }

            @Override
            public void onFailure(int code, String errorMsg) {
                mView.showNoNet();
            }

            @Override
            public void onRequestComplete() {

            }
        });

//        Subscription subscription = mEngine.getPhoneticDetail(page).subscribe(new Subscriber<ResultInfo<StudyPhoneticInfoWrapper>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.showNoNet();
//            }
//
//            @Override
//            public void onNext(ResultInfo<StudyPhoneticInfoWrapper> studyInfoWrapperResultInfo) {
//
//            }
//        });
//        mSubscriptions.add(subscription);
    }
}
