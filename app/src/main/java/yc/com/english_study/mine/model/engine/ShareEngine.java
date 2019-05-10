package yc.com.english_study.mine.model.engine;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;

import rx.Observable;
import yc.com.base.BaseEngine;
import yc.com.english_study.base.constant.UrlConfig;

/**
 * Created by wanglin  on 2019/5/10 10:32.
 */
public class ShareEngine extends BaseEngine {
    public ShareEngine(Context context) {
        super(context);
    }

    public Observable<ResultInfo<String>> share() {
        return HttpCoreEngin.get(mContext).rxpost(UrlConfig.share_reward_url, new TypeReference<ResultInfo<String>>() {
        }.getType(), null, true, true, true);
    }
}
