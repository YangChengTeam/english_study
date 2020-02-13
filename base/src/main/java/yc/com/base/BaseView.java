package yc.com.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.vondear.rxtools.RxLogTool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import butterknife.ButterKnife;

/**
 * Created by wanglin  on 2018/2/27 11:45.
 */

public abstract class BaseView<VM extends ViewDataBinding> extends FrameLayout implements IView {

    protected Context mContext;

    protected VM mDataBinding;

    public BaseView(@NonNull Context context) {
        super(context);
        this.mContext = context;
//        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, false);
        LayoutInflater.from(context).inflate(getLayoutId(), this);

        try {
            ButterKnife.bind(this);
        } catch (Exception e) {
            RxLogTool.e("初始化失败-->" + e.getMessage());
        }
        init();

    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

//        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, false);
        LayoutInflater.from(context).inflate(getLayoutId(), this);

        try {
            ButterKnife.bind(this);
        } catch (Exception e) {
            RxLogTool.e("初始化失败-->" + e.getMessage());
        }
        init();
    }


    @Override
    public void init() {

    }
}
