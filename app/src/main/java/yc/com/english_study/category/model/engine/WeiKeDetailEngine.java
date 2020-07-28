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
import yc.com.english_study.category.model.domain.CourseInfo;
import yc.com.rthttplibrary.bean.ResultInfo;


public class WeiKeDetailEngine extends BaseEngine {


    public WeiKeDetailEngine(Context context) {
        super(context);
    }


    public Observable<ResultInfo<CourseInfo>> getWeikeCategoryInfo(String id, int page, int page_size) {


        return request.getWeikeCategoryInfo(id, page, page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
