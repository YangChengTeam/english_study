package yc.com.english_study.category.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import yc.com.base.BaseEngine;
import yc.com.english_study.base.constant.UrlConfig;
import yc.com.english_study.category.model.domain.CourseInfo;
import yc.com.english_study.category.model.domain.WeiKeCategoryWrapper;

/**
 * Created by zhangkai on 2017/9/6.
 */

public class WeiKeDetailEngine extends BaseEngine {


    public WeiKeDetailEngine(Context context) {
        super(context);
    }


    public Observable<ResultInfo<CourseInfo>> getWeikeCategoryInfo(String id, int page, int page_size) {

        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");

        params.put("page_size", page_size + "");
        params.put("id", id);


        return HttpCoreEngin.get(mContext).rxpost(UrlConfig.weike_info_url, new TypeReference<ResultInfo<CourseInfo>>() {
                }.getType(),
                params,
                true,
                true, true);
    }


}
