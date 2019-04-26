package yc.com.english_study.category.adapter;

import java.util.List;

import yc.com.english_study.BR;
import yc.com.english_study.R;
import yc.com.english_study.category.model.domain.WeiKeCategory;
import yc.com.english_study.databinding.WeikeInfoItemBinding;
import yc.com.english_study.index.utils.BaseBindingAdapter;
import yc.com.english_study.index.utils.BaseBindingHolder;

/**
 * Created by zhangkai on 2017/8/30.
 */

public class WeiKeInfoItemAdapter extends BaseBindingAdapter<WeiKeCategory, WeikeInfoItemBinding, BaseBindingHolder<WeikeInfoItemBinding>> {
    private String mType;

    public WeiKeInfoItemAdapter(List<WeiKeCategory> data, String type) {
        super(R.layout.weike_info_item, data);
        this.mType = type;
    }

    @Override
    protected void convert(BaseBindingHolder<WeikeInfoItemBinding> helper, WeiKeCategory item) {
        helper.getBinding().setVariable(BR.weiKeCategory, item);
        helper.getBinding().executePendingBindings();
    }
}
