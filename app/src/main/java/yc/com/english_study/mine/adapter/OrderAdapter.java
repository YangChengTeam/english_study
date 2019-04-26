package yc.com.english_study.mine.adapter;

import java.util.List;

import yc.com.english_study.BR;
import yc.com.english_study.R;
import yc.com.english_study.databinding.OrderItemInfoBinding;
import yc.com.english_study.index.utils.BaseBindingAdapter;
import yc.com.english_study.index.utils.BaseBindingHolder;
import yc.com.english_study.pay.alipay.OrderInfo;

/**
 * Created by wanglin  on 2019/4/25 15:23.
 */
public class OrderAdapter extends BaseBindingAdapter<OrderInfo, OrderItemInfoBinding, BaseBindingHolder<OrderItemInfoBinding>> {


    public OrderAdapter(List<OrderInfo> data) {
        super(R.layout.order_item_info, data);
    }

    @Override
    protected void convert(BaseBindingHolder<OrderItemInfoBinding> helper, OrderInfo item) {

        helper.getBinding().setVariable(BR.orderInfo,item);
        helper.getBinding().executePendingBindings();
    }


}
