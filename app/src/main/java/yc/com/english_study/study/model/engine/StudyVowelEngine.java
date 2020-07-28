package yc.com.english_study.study.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import yc.com.english_study.base.constant.UrlConfig;
import yc.com.english_study.base.model.engine.BaseEngine;
import yc.com.english_study.study.model.domain.VowelInfoWrapper;
import yc.com.rthttplibrary.bean.ResultInfo;

/**
 * Created by wanglin  on 2018/11/1 09:33.
 */
public class StudyVowelEngine extends BaseEngine {
    public StudyVowelEngine(Context context) {
        super(context);
    }

    public Observable<ResultInfo<VowelInfoWrapper>> getVowelInfos() {


        return request.getVowelInfos().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ResultInfo<VowelInfoWrapper>> getPhoneticWordInfos() {

        return request.getPhoneticWordInfos().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
