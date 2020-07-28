package yc.com.english_study.mine.model.engine;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yc.com.english_study.base.model.engine.BaseEngine;
import yc.com.rthttplibrary.bean.ResultInfo;

/**
 * Created by wanglin  on 2019/5/10 10:32.
 */
public class ShareEngine extends BaseEngine {
    public ShareEngine(Context context) {
        super(context);
    }

    public Observable<ResultInfo<String>> share() {

        return request.share().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
