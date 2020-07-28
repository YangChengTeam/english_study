package yc.com.english_study.study.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;



import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import yc.com.english_study.base.constant.UrlConfig;
import yc.com.english_study.base.model.engine.BaseEngine;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPages;
import yc.com.english_study.study.model.domain.StudyPhoneticInfoWrapper;
import yc.com.rthttplibrary.bean.ResultInfo;

/**
 * Created by wanglin  on 2018/10/30 16:36.
 */
public class StudyEngine extends BaseEngine {
    public StudyEngine(Context context) {
        super(context);
    }

    public Observable<ResultInfo<StudyPages>> getStudyPages() {



        return request.getStudyPages().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ResultInfo<StudyInfoWrapper>> getStudyDetail(int page) {


        return request.getStudyDetail(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<ResultInfo<StudyPages>> getPhoneticPages() {

        return request.getPhoneticPages().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ResultInfo<StudyPhoneticInfoWrapper>> getPhoneticDetail(int page) {


        return request.getPhoneticDetail(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
}
