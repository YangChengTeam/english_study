package yc.com.english_study.mine.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.bugly.beta.Beta;

import java.util.concurrent.TimeUnit;


import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.base.FileUtils;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.BusAction;
import yc.com.english_study.base.utils.DataCleanManagerUtils;
import yc.com.english_study.databinding.ActivitySettingBinding;
import yc.com.english_study.index.model.domain.UserInfo;
import yc.com.english_study.index.utils.UserInfoHelper;

/**
 * Created by suns  on 2020/4/15 17:58.
 */
public class SettingActivity extends BaseActivity<BasePresenter, ActivitySettingBinding> {
    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.init(this);
        mDataBinding.mainToolbar.setTitle("系统设置");
        mDataBinding.mainToolbar.setRightContainerVisible(false);

        mDataBinding.tvVersionCode.setText(getCode());
        try {
            mDataBinding.tvCache.setText(DataCleanManagerUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(UserInfoHelper.getUid())) {
            mDataBinding.tvLogout.setVisibility(View.GONE);
        } else {
            mDataBinding.tvLogout.setVisibility(View.VISIBLE);
        }

        initListener();

    }

    private void initListener() {
        RxView.clicks(mDataBinding.rlVersionCode).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Beta.checkUpgrade(true, false);
            }
        });

        RxView.clicks(mDataBinding.rlCache).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                DataCleanManagerUtils.clearAllCache(SettingActivity.this);
                mDataBinding.tvCache.setText("0M");
            }
        });

        RxView.clicks(mDataBinding.tvLogout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                UserInfoHelper.logout();
                mDataBinding.tvLogout.setVisibility(View.GONE);
                RxBus.get().post(BusAction.LOGIN, new UserInfo());
            }
        });
    }


    private String getCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }
}
