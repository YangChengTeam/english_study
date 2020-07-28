package yc.com.english_study.base.presenter;

import android.content.Context;
import android.text.TextUtils;

import yc.com.base.BasePresenter;
import yc.com.blankj.utilcode.util.RegexUtils;
import yc.com.english_study.base.contract.BasePhoneContract;
import yc.com.english_study.base.model.engine.BasePhoneEngine;
import yc.com.english_study.base.observer.BaseCommonObserver;
import yc.com.rthttplibrary.util.ToastUtil;

/**
 * Created by wanglin  on 2018/11/1 15:00.
 */
public class BasePhonePresenter extends BasePresenter<BasePhoneEngine, BasePhoneContract.View> implements BasePhoneContract.Presenter {
    public BasePhonePresenter(Context context, BasePhoneContract.View view) {
        super(context, view);
        mEngine = new BasePhoneEngine(context);
    }

    @Override
    public void loadData(boolean isForceUI, boolean isLoadingUI) {

    }

    public void uploadPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast(mContext, "手机号码不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtil.toast(mContext, "请输入正确的手机号码");
            return;
        }
        mView.showLoadingDialog("正在上传手机号,请稍候...");

        mEngine.uploadPhone(phone).subscribe(new BaseCommonObserver<String>(mContext) {
            @Override
            public void onSuccess(String data, String message) {
                mView.dismissDialog();
                mView.showUploadSuccess();

            }

            @Override
            public void onFailure(int code, String errorMsg) {
                mView.dismissDialog();
            }

            @Override
            public void onRequestComplete() {

            }
        });


    }
}
