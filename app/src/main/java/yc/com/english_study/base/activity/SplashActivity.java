package yc.com.english_study.base.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.Config;
import yc.com.english_study.databinding.ActivitySplashBinding;
import yc.com.english_study.index.utils.UserInfoHelper;
import yc.com.tencent_adv.AdvDispatchManager;

import yc.com.tencent_adv.AdvType;
import yc.com.tencent_adv.OnAdvStateListener;
import yc.com.toutiao_adv.TTAdDispatchManager;
import yc.com.toutiao_adv.TTAdType;

/**
 * Created by wanglin  on 2018/10/24 11:17.
 */
public class SplashActivity extends BaseActivity<BasePresenter, ActivitySplashBinding> implements OnAdvStateListener, yc.com.toutiao_adv.OnAdvStateListener {


    private Handler mHandler = new Handler();
    private long Time = 1000;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
//        LogUtil.msg("tag:  " + Build.BRAND);

        switchMain(Time);

//        if (isAssignPhone() || UserInfoHelper.isPhonogramOrPhonicsVip()) {
//            mDataBinding.skipView.setVisibility(View.GONE);
//            switchMain(Time);
//        } else {
////            AdvDispatchManager.getManager().init(this, AdvType.SPLASH, mDataBinding.splashContainer, mDataBinding.skipView, Config.TENCENT_ADV_ID, Config.SPLASH_ADV_ID, this);
//
//            TTAdDispatchManager.getManager().init(this, TTAdType.SPLASH, mDataBinding.splashContainer, Config.TOUTIAO_SPLASH_ID, 0, null, 0, null, 0, this);
//
//
//        }

    }


    private void switchMain(long delayTime) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, delayTime);

    }


    @Override
    public boolean isStatusBarMateria() {
        return true;
    }


    @Override
    public void onShow() {
        mDataBinding.ivSplash.setVisibility(View.GONE);
        mDataBinding.skipView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(long delayTime) {
        switchMain(delayTime);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onNativeExpressDismiss(NativeExpressADView view) {

    }

    @Override
    public void onNativeExpressShow(Map<NativeExpressADView, Integer> mDatas) {

    }

    @Override
    protected void onResume() {
        super.onResume();

//        AdvDispatchManager.getManager().onResume();

        TTAdDispatchManager.getManager().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        AdvDispatchManager.getManager().onPause();
        TTAdDispatchManager.getManager().onStop();
    }

    //防止用户返回键退出 APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!isAssignPhone()) {
            AdvDispatchManager.getManager().onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    //是否是指定机型
    private boolean isAssignPhone() {
        return TextUtils.equals("xiaomi", Build.BRAND.toLowerCase()) || TextUtils.equals("huawei", Build.BRAND.toLowerCase()) || TextUtils.equals("honor", Build.BRAND.toLowerCase());
    }

    @Override
    public void loadSuccess() {
        switchMain(0);
    }

    @Override
    public void loadFailed() {
        switchMain(0);
    }

    @Override
    public void clickAD() {
        switchMain(0);
    }


    @Override
    public void onTTNativeExpressed(List<TTNativeExpressAd> ads) {

    }


}
