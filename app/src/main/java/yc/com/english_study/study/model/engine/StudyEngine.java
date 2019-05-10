package yc.com.english_study.study.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;


import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import yc.com.base.BaseEngine;
import yc.com.english_study.base.constant.UrlConfig;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPages;
import yc.com.english_study.study.model.domain.StudyPhoneticInfo;
import yc.com.english_study.study.model.domain.StudyPhoneticInfoWrapper;

/**
 * Created by wanglin  on 2018/10/30 16:36.
 */
public class StudyEngine extends BaseEngine {
    public StudyEngine(Context context) {
        super(context);
    }

    public Observable<ResultInfo<StudyPages>> getStudyPages() {


        return HttpCoreEngin.get(mContext).rxpost(UrlConfig.study_list_url, new TypeReference<ResultInfo<StudyPages>>() {
        }.getType(), null, true, true, true);
    }


    public Observable<ResultInfo<StudyInfoWrapper>> getStudyDetail(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        return HttpCoreEngin.get(mContext).rxpost(UrlConfig.study_detail_url, new TypeReference<ResultInfo<StudyInfoWrapper>>() {
        }.getType(), params, true, true, true);

    }

    public Observable<ResultInfo<StudyPages>> getPhoneticPages() {

        return HttpCoreEngin.get(mContext).rxpost(UrlConfig.phonetic_count_url, new TypeReference<ResultInfo<StudyPages>>() {
        }.getType(), null, true, true, true);
    }


    public Observable<ResultInfo<StudyPhoneticInfoWrapper>> getPhoneticDetail(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        return HttpCoreEngin.get(mContext).rxpost(UrlConfig.phonetic_detail_url, new TypeReference<ResultInfo<StudyPhoneticInfoWrapper>>() {
        }.getType(), params, true, true, true);

    }
}
