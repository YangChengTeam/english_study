package yc.com.english_study.study.model.engine;

import android.content.Context;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yc.com.english_study.base.model.domain.GoodInfoWrapper;
import yc.com.english_study.base.model.engine.BaseEngine;
import yc.com.english_study.index.utils.UserInfoHelper;
import yc.com.english_study.pay.alipay.OrderInfo;
import yc.com.rthttplibrary.bean.ResultInfo;

/**
 * Created by suns  on 2020/7/28 09:42.
 */
public class VipEngine extends BaseEngine {
    public VipEngine(Context context) {
        super(context);
    }

    public Observable<ResultInfo<GoodInfoWrapper>> getVipInfoList() {
        return request.getVipInfoList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResultInfo<String>> isBindPhone() {
        return request.isBindPhone(UserInfoHelper.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResultInfo<OrderInfo>> createOrder(int goods_num, String payway_name, String money, String goods_id) {

        return request.createOrder(goods_num, payway_name, money, goods_id, String.valueOf(7), UserInfoHelper.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
