package yc.com.english_study.category.adapter;

import java.util.List;

import yc.com.english_study.R;
import yc.com.english_study.category.model.domain.WeiKeCategory;
import yc.com.english_study.databinding.WeikecategoryItemBinding;
import yc.com.english_study.index.utils.BaseBindingAdapter;
import yc.com.english_study.index.utils.BaseBindingHolder;
import yc.com.english_study.BR;

/**
 * Created by wanglin  on 2018/10/25 11:31.
 */
public class CategoryMainAdapter extends BaseBindingAdapter<WeiKeCategory, WeikecategoryItemBinding, BaseBindingHolder<WeikecategoryItemBinding>> {
    public CategoryMainAdapter(List<WeiKeCategory> data) {
        super(R.layout.weikecategory_item, data);
    }

    @Override
    protected void convert(BaseBindingHolder<WeikecategoryItemBinding> helper, WeiKeCategory item) {

        helper.getBinding().setVariable(BR.weiKeCategory, item);
        helper.getBinding().executePendingBindings();
    }


}
