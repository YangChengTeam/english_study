package yc.com.english_study.index.utils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.databinding.ViewDataBinding;


/**
 * Created by wanglin  on 2019/4/22 13:27.
 */
public class BaseBindingHolder<VM extends ViewDataBinding> extends BaseViewHolder {
    private VM mDataBinding;


    public BaseBindingHolder(VM dataBinding) {
        super(dataBinding.getRoot());
//        dataBinding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.mDataBinding = dataBinding;
    }

    public BaseViewHolder setBindingAdapter(BaseQuickAdapter adapter) {
        super.setAdapter(adapter);
        return this;
    }


    public ViewDataBinding getBinding() {
        return mDataBinding;
    }
}
