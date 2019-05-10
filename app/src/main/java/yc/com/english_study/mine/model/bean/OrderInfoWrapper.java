package yc.com.english_study.mine.model.bean;

import java.util.List;

import yc.com.english_study.pay.alipay.OrderInfo;

/**
 * Created by wanglin  on 2019/4/27 11:46.
 */
public class OrderInfoWrapper {
    private List<OrderInfo> list;

    public List<OrderInfo> getList() {
        return list;
    }

    public void setList(List<OrderInfo> list) {
        this.list = list;
    }
}
