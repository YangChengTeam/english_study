package yc.com.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.kk.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.vondear.rxtools.RxLogTool;

import butterknife.ButterKnife;

/**
 * Created by wanglin  on 2018/3/6 10:52.
 */

public abstract class BaseFragment<P extends BasePresenter, VM extends ViewDataBinding> extends Fragment implements IView {


    protected P mPresenter;

    protected VM mDataBinding;

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    private static final String TAG = "BaseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        RxBus.get().register(this);


        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        init();

        return mDataBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter.subscribe();
        }
        isViewInitiated = true;
//        Log.e(TAG, "onActivityCreated: ");
        prepareFetchData();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter.unsubscribe();
        }
        RxBus.get().unregister(this);
        Runtime.getRuntime().gc();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
//        LogUtil.msg("TAG ： " + getClass().getName() + "  isVisibleToUser   " + isVisibleToUser + "  isDataInitiated  " + isDataInitiated + "  isViewInitiated  " + isViewInitiated);
        prepareFetchData();
//        Log.e(TAG, "setUserVisibleHint: ");
    }

    public void fetchData() {
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

}
