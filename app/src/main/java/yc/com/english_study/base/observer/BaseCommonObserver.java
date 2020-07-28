package yc.com.english_study.base.observer;

import android.content.Context;

import yc.com.english_study.pay.alipay.LoadingDialog;
import yc.com.rthttplibrary.converter.BaseObserver;


/**
 * Created by suns  on 2020/7/25 10:35.
 */
public abstract class BaseCommonObserver<T> extends BaseObserver<T, LoadingDialog> {

    public BaseCommonObserver(LoadingDialog view) {
        super(view);
    }

    public BaseCommonObserver(Context context) {
        this(context, "加载中...");
    }

    public BaseCommonObserver(Context context, String mess) {
        super(null);
        LoadingDialog loadDialog = new LoadingDialog(context);
        loadDialog.setText(mess);
        this.view = loadDialog;
    }

    @Override
    protected boolean isShow() {
        return false;
    }
}
