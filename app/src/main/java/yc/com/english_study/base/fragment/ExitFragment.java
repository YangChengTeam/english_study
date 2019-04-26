package yc.com.english_study.base.fragment;

import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseDialogFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.FragmentExitBinding;

/**
 * Created by wanglin  on 2018/3/16 18:53.
 */

public class ExitFragment extends BaseDialogFragment<BasePresenter, FragmentExitBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_exit;
    }

    @Override
    public void init() {
        mDataBinding.tvTint.setText(String.format(getString(R.string.is_exit), getString(R.string.app_name)));
        RxView.clicks(mDataBinding.tvConfirm).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
            }
        });

        RxView.clicks(mDataBinding.tvCancel).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                dismiss();
            }
        });
    }


    @Override
    protected float getWidth() {
        return 0.8f;
    }

    @Override
    public int getAnimationId() {
        return 0;
    }

    @Override
    public int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }


    public interface onConfirmListener {
        void onConfirm();
    }

    public onConfirmListener onConfirmListener;

    public void setOnConfirmListener(ExitFragment.onConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
}
