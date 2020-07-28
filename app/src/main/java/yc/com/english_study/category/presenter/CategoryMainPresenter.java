package yc.com.english_study.category.presenter;

import android.content.Context;

import java.util.List;

import yc.com.base.BasePresenter;
import yc.com.english_study.base.observer.BaseCommonObserver;
import yc.com.english_study.category.contract.CategoryMainContract;
import yc.com.english_study.category.model.domain.WeiKeCategory;
import yc.com.english_study.category.model.domain.WeiKeCategoryWrapper;
import yc.com.english_study.category.model.engine.CategoryMainEngine;

/**
 * Created by wanglin  on 2018/10/25 14:09.
 */
public class CategoryMainPresenter extends BasePresenter<CategoryMainEngine, CategoryMainContract.View> implements CategoryMainContract.Presenter {
    public CategoryMainPresenter(Context context, CategoryMainContract.View view) {
        super(context, view);
        mEngine = new CategoryMainEngine(context);
    }

    @Override
    public void loadData(boolean isForceUI, boolean isLoadingUI) {

    }

    @Override
    public void getCategoryInfos(final int page, int pageSize, String pid, boolean isRefresh) {
        if (page == 1 && !isRefresh)
            mView.showLoading();

        mEngine.getCategoryInfos(page, pageSize, pid).subscribe(new BaseCommonObserver<WeiKeCategoryWrapper>(mContext) {
            @Override
            public void onSuccess(WeiKeCategoryWrapper weiKeCategoryWrapper, String message) {

                if (weiKeCategoryWrapper != null) {
                    mView.hide();
                    List<WeiKeCategory> weiKeCategoryList = weiKeCategoryWrapper.getList();
                    mView.showWeiKeCategoryInfos(weiKeCategoryList);

                } else {
                    if (page == 1)
                        mView.showNoData();
                }

            }

            @Override
            public void onFailure(int code, String errorMsg) {
                if (page == 1)
                    mView.showNoNet();
            }

            @Override
            public void onRequestComplete() {

            }
        });

    }

    @Override
    public void getWeiKeList(final int page, int page_size, String pid, boolean isRefresh) {
        if (page == 1 && !isRefresh)
            mView.showLoading();

        mEngine.getWeiKeList(page, page_size, pid).subscribe(new BaseCommonObserver<WeiKeCategoryWrapper>(mContext) {
            @Override
            public void onSuccess(WeiKeCategoryWrapper weiKeCategoryWrapper, String message) {

                if (weiKeCategoryWrapper != null) {
                    mView.hide();
                    List<WeiKeCategory> weiKeCategoryList = weiKeCategoryWrapper.getList();
                    mView.showWeiKeCategoryInfos(weiKeCategoryList);

                } else {
                    if (page == 1)
                        mView.showNoData();
                }

            }

            @Override
            public void onFailure(int code, String errorMsg) {
                if (page == 1)
                    mView.showNoNet();
            }

            @Override
            public void onRequestComplete() {

            }
        });

    }
}
