package yc.com.english_study.category.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.kk.utils.ScreenUtil;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;
import java.util.Map;

import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;
import yc.com.english_study.R;
import yc.com.english_study.base.activity.WebActivity;
import yc.com.english_study.base.constant.Config;
import yc.com.english_study.category.activity.WeikeUnitActivity;
import yc.com.english_study.category.adapter.CategoryMainAdapter;
import yc.com.english_study.category.contract.CategoryMainContract;
import yc.com.english_study.category.model.domain.WeiKeCategory;
import yc.com.english_study.category.presenter.CategoryMainPresenter;
import yc.com.english_study.category.utils.ItemDecorationHelper;
import yc.com.english_study.databinding.FragmentCategoryBinding;
import yc.com.tencent_adv.AdvDispatchManager;
import yc.com.tencent_adv.AdvType;
import yc.com.tencent_adv.OnAdvStateListener;


/**
 * Created by wanglin  on 2018/10/24 17:21.
 */
public class CategoryFragment extends BaseFragment<CategoryMainPresenter, FragmentCategoryBinding> implements CategoryMainContract.View, OnAdvStateListener {


    private CategoryMainAdapter categoryMainAdapter;
    private int page = 1;

    private int PAGE_SIZE = 20;
    private RecyclerView.ItemDecoration itemDecoration;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void init() {

        mPresenter = new CategoryMainPresenter(getActivity(), this);

        mDataBinding.setIsAct(false);
        mDataBinding.mainToolbar.init((BaseActivity) getActivity(), WebActivity.class);
        mDataBinding.mainToolbar.setTvRightTitleAndIcon(getString(R.string.diandu), R.mipmap.diandu);

        if (!(TextUtils.equals("Xiaomi", Build.BRAND) || TextUtils.equals("xiaomi", Build.BRAND)))
            AdvDispatchManager.getManager().init(getActivity(), AdvType.BANNER, mDataBinding.bottomContainer, null, Config.TENCENT_ADV_ID, Config.BANNER_TOP_ADV_ID, this);
        getData(false);
        mDataBinding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDataBinding.categoryRecyclerView.setHasFixedSize(true);

        categoryMainAdapter = new CategoryMainAdapter(null);

        mDataBinding.categoryRecyclerView.setAdapter(categoryMainAdapter);

        itemDecoration = new ItemDecorationHelper(getActivity(), 6, 6);

        mDataBinding.categoryRecyclerView.addItemDecoration(itemDecoration);


        mDataBinding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.app_selected_color));
        mDataBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(true);
            }
        });

        categoryMainAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, mDataBinding.categoryRecyclerView);


        categoryMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WeikeUnitActivity.class);
                intent.putExtra("category_id", categoryMainAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });

        mDataBinding.categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = recyclerView.getChildAt(0);

                if (view.getTop() < 0) {
                    mDataBinding.categoryRecyclerView.setPadding(ScreenUtil.dip2px(getActivity(), 6), 0, 0, 0);
                } else {
                    mDataBinding.categoryRecyclerView.setPadding(ScreenUtil.dip2px(getActivity(), 6), ScreenUtil.dip2px(getActivity(), 6), 0, 0);
                }
            }
        });


    }


    @Override
    public void showWeiKeCategoryInfos(List<WeiKeCategory> weiKeCategoryList) {
        if (page == 1) {
            categoryMainAdapter.setNewData(weiKeCategoryList);
        } else {
            categoryMainAdapter.addData(weiKeCategoryList);
        }

        if (weiKeCategoryList.size() == PAGE_SIZE) {
            page++;
            categoryMainAdapter.loadMoreComplete();
        } else {
            categoryMainAdapter.loadMoreEnd();
        }
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hide() {
        mDataBinding.stateView.hide();
    }

    @Override
    public void showLoading() {
        mDataBinding.stateView.showLoading(mDataBinding.categoryRecyclerView);
    }

    @Override
    public void showNoData() {
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
        mDataBinding.stateView.showNoData(mDataBinding.categoryRecyclerView);

    }

    @Override
    public void showNoNet() {
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
        mDataBinding.stateView.showNoNet(mDataBinding.categoryRecyclerView, HttpConfig.NET_ERROR, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(false);
            }
        });
    }

    private void getData(boolean isRefresh) {
        mPresenter.getCategoryInfos(page, PAGE_SIZE, "0", isRefresh);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (itemDecoration != null) {
            mDataBinding.categoryRecyclerView.removeItemDecoration(itemDecoration);
        }

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDismiss(long delayTime) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onNativeExpressDismiss(NativeExpressADView view) {

    }

    @Override
    public void onNativeExpressShow(Map<NativeExpressADView, Integer> mDatas) {

    }
}
