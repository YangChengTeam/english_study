package yc.com.english_study.pay;

import yc.com.english_study.pay.alipay.OrderInfo;
import yc.com.english_study.pay.alipay.PayInfo;

/**
 * Created by wanglin  on 2018/11/26 17:25.
 */
public interface PayListener {

    void onPayResult(OrderInfo orderInfo);
}
