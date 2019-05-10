package yc.com.english_study.mine.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.kk.utils.ScreenUtil;

import java.util.List;

import yc.com.base.BaseActivity;
import yc.com.english_study.R;
import yc.com.english_study.category.utils.ItemDecorationHelper;
import yc.com.english_study.databinding.ActivityOrderBinding;
import yc.com.english_study.mine.adapter.OrderAdapter;
import yc.com.english_study.mine.contract.OrderContract;
import yc.com.english_study.mine.presenter.OrderPresenter;
import yc.com.english_study.pay.alipay.OrderInfo;

/**
 * Created by wanglin  on 2019/4/25 15:11.
 */
public class OrderActivity extends BaseActivity<OrderPresenter, ActivityOrderBinding> implements OrderContract.View {

    private OrderAdapter orderAdapter;

    private int page = 1;
    private int pageSize = 20;

    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void init() {
        mPresenter = new OrderPresenter(this, this);
        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.setTitle(getString(R.string.my_order));
        mDataBinding.mainToolbar.init(this);
        mDataBinding.mainToolbar.setRightContainerVisible(false);
        getData(false);
        initAdapter();
        initListener();

    }


    private void initListener() {
        mDataBinding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.app_selected_color));
        mDataBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = mDataBinding.recyclerView.getChildAt(0);

                if (view.getTop() < 0) {

                    mDataBinding.recyclerView.setPadding(mDataBinding.recyclerView.getPaddingLeft(), 0, mDataBinding.recyclerView.getPaddingRight(), 0);

                } else {

                    mDataBinding.recyclerView.setPadding(mDataBinding.recyclerView.getPaddingLeft(), ScreenUtil.dip2px(OrderActivity.this, 46f), mDataBinding.recyclerView.getPaddingRight(), 0);
                }

            }
        });

        mDataBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(true);
            }
        });
        orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, mDataBinding.recyclerView);
    }

    private void getData(boolean isRefresh) {
        mPresenter.getOrderInfoList(page, pageSize, isRefresh);
    }

    private void initAdapter() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(null);
        mDataBinding.recyclerView.setAdapter(orderAdapter);
        mDataBinding.recyclerView.addItemDecoration(new ItemDecorationHelper(this, 10));
    }

    @Override
    public void showOrderInfos(List<OrderInfo> orderInfos) {
        if (page == 1) {
            orderAdapter.setNewData(orderInfos);
        } else {
            orderAdapter.addData(orderInfos);
        }
        if (orderInfos.size() == pageSize) {
            page++;
            orderAdapter.loadMoreComplete();
        } else {
            orderAdapter.loadMoreEnd();
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
        mDataBinding.stateView.showLoading(mDataBinding.rlContainer);
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNoData() {
        mDataBinding.stateView.showNoData(mDataBinding.rlContainer);
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNoNet() {
        mDataBinding.stateView.showNoNet(mDataBinding.rlContainer, HttpConfig.NET_ERROR, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(false);
            }
        });
        if (mDataBinding.swipeRefreshLayout.isRefreshing()) {
            mDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
