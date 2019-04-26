package yc.com.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hwangjr.rxbus.RxBus;
import com.umeng.analytics.MobclickAgent;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.RxLogTool;

import butterknife.ButterKnife;

/**
 * Created by wanglin  on 2018/3/6 11:14.
 */

public abstract class BaseDialogFragment<P extends BasePresenter, VM extends ViewDataBinding> extends DialogFragment implements IView {

    protected P mPresenter;
    protected VM mDataBinding;

    protected BaseLoadingView loadingView;
    protected BaseActivity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
        Window window = getDialog().getWindow();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

//        if (rootView == null) {

//            rootView = inflater.inflate(getLayoutId(), container, false);
        loadingView = new BaseLoadingView(getActivity());
//            window.setLayout((int) (RxDeviceTool.getScreenWidth(getActivity()) * getWidth()), getHeight());//这2行,和上面的一样,注意顺序就行;
        window.setWindowAnimations(getAnimationId());
//        }
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        init();


        return mDataBinding.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (BaseActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = (int) (RxDeviceTool.getScreenWidth(getActivity()) * getWidth());
            layoutParams.height = getHeight();
            window.setAttributes(layoutParams);
        }

    }

    protected abstract float getWidth();

    public abstract int getAnimationId();

    public abstract int getHeight();


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (EmptyUtils.isNotEmpty(mPresenter))
            mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EmptyUtils.isNotEmpty(mPresenter))
            mPresenter.unsubscribe();
        RxBus.get().unregister(this);
        Runtime.getRuntime().gc();
    }
}
