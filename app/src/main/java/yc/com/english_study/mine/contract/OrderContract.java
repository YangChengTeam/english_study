package yc.com.english_study.mine.contract;

import java.util.List;

import yc.com.base.IPresenter;
import yc.com.base.IView;
import yc.com.english_study.pay.alipay.OrderInfo;

/**
 * Created by wanglin  on 2019/4/25 16:49.
 */
public interface OrderContract {

    interface View extends IView {
        void showOrderInfos(List<OrderInfo> orderInfos);
    }

    interface Presenter extends IPresenter {
        void getOrderInfos();
    }
}
