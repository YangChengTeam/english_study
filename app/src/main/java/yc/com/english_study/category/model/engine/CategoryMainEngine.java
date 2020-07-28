package yc.com.english_study.category.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import yc.com.english_study.base.constant.UrlConfig;
import yc.com.english_study.base.model.engine.BaseEngine;
import yc.com.english_study.category.model.domain.WeiKeCategoryWrapper;
import yc.com.rthttplibrary.bean.ResultInfo;

/**
 * Created by wanglin  on 2018/10/25 14:01.
 */
public class CategoryMainEngine extends BaseEngine {
    public CategoryMainEngine(Context context) {
        super(context);
    }


    public Observable<ResultInfo<WeiKeCategoryWrapper>> getCategoryInfos(int page, int page_size, String pid) {


        return request.getCategoryInfos(page, page_size, pid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ResultInfo<WeiKeCategoryWrapper>> getWeiKeList(int page, int page_size, String pid) {


        return request.getWeiKeList(page, page_size, pid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
