package yc.com.english_study.category.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import yc.com.base.BaseFragment;
import yc.com.english_study.R;
import yc.com.english_study.category.activity.WeiKeDetailActivity;
import yc.com.english_study.category.adapter.WeiKeInfoItemAdapter;
import yc.com.english_study.category.contract.CategoryMainContract;
import yc.com.english_study.category.model.domain.WeiKeCategory;
import yc.com.english_study.category.presenter.CategoryMainPresenter;
import yc.com.english_study.category.utils.ItemDecorationHelper;
import yc.com.english_study.databinding.FragmentWeikeUnitBinding;
import yc.com.rthttplibrary.util.ScreenUtil;

/**
 * 微课单元列表
 */

public class WeikeUnitFragment extends BaseFragment<CategoryMainPresenter, FragmentWeikeUnitBinding> implements CategoryMainContract.View {


    private WeiKeInfoItemAdapter mWeiKeInfoItemAdapter;

    private String type = "";

    private String pid = "0";

    private int page = 1;

    private int pageSize = 20;

    @Override
    public void init() {

        mDataBinding.setIsAct(true);

        mPresenter = new CategoryMainPresenter(getActivity(), this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            pid = bundle.getString("type_id");
        }


        mDataBinding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mWeiKeInfoItemAdapter = new WeiKeInfoItemAdapter(null, type);
        mDataBinding.categoryRecyclerView.setAdapter(mWeiKeInfoItemAdapter);
        mDataBinding.categoryRecyclerView.addItemDecoration(new ItemDecorationHelper(getActivity(), 6, 6));
        mDataBinding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.app_selected_color));
        mDataBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });


        mWeiKeInfoItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WeiKeDetailActivity.class);

                intent.putExtra("pid", mWeiKeInfoItemAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });

        mWeiKeInfoItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, mDataBinding.categoryRecyclerView);

        mDataBinding.categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = mDataBinding.categoryRecyclerView.getChildAt(0);
                if (view.getTop() < 0) {
                    mDataBinding.categoryRecyclerView.setPadding(ScreenUtil.dip2px(getActivity(), 6), 0, 0, 0);
                } else {
                    mDataBinding.categoryRecyclerView.setPadding(ScreenUtil.dip2px(getActivity(), 6), ScreenUtil.dip2px(getActivity(), 6), 0, 0);
                }
            }
        });

        getData(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weike_unit;
    }


    @Override
    public void showNoNet() {

        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
        mDataBinding.stateView.showNoNet(mDataBinding.categoryRecyclerView, "网络不给力", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(false);
            }
        });
    }

    @Override
    public void showNoData() {
        mDataBinding.stateView.showNoData(mDataBinding.categoryRecyclerView);
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showLoading() {
        mDataBinding.stateView.showLoading(mDataBinding.categoryRecyclerView);
    }


    private void getData(boolean isRefresh) {
//        mPresenter.getCategoryInfos(page, pageSize, pid, isRefresh);
        mPresenter.getWeiKeList(page, pageSize, pid, isRefresh);
    }

    @Override
    public void showWeiKeCategoryInfos(List<WeiKeCategory> weiKeCategoryList) {
        if (page == 1) {

            mWeiKeInfoItemAdapter.setNewData(weiKeCategoryList);
        } else {
            mWeiKeInfoItemAdapter.addData(weiKeCategoryList);
        }
        if (pageSize == weiKeCategoryList.size()) {
            page++;
            mWeiKeInfoItemAdapter.loadMoreComplete();
        } else {
            mWeiKeInfoItemAdapter.loadMoreEnd();
        }

        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hide() {
        mDataBinding.stateView.hide();
    }


}
