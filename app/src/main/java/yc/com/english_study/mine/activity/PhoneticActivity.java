package yc.com.english_study.mine.activity;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.jakewharton.rxbinding.view.RxView;

import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.SpConstant;
import yc.com.english_study.databinding.FragmentPhoneticBinding;
import yc.com.english_study.index.fragment.GuideFragment;
import yc.com.english_study.index.fragment.VipEquitiesFragment;
import yc.com.rthttplibrary.util.ToastUtil;

/**
 * Created by wanglin  on 2018/10/24 17:21.
 */
public class PhoneticActivity extends BaseActivity<BasePresenter, FragmentPhoneticBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_phonetic;
    }

    @Override
    public void init() {

        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.init(this, null);
        mDataBinding.mainToolbar.setRightContainerVisible(false);


        RxView.clicks(mDataBinding.ivVip).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                MobclickAgent.onEvent(PhoneticActivity.this, "VIP-click", "会员权益");

                VipEquitiesFragment vipEquitiesFragment = new VipEquitiesFragment();
                vipEquitiesFragment.show(getSupportFragmentManager(), "");

            }
        });

        RxView.clicks(mDataBinding.tvServiceWechat).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("weixin", mDataBinding.tvServiceWechat.getText().toString().trim()));
                gotoWeixin();
            }
        });
    }


    private void gotoWeixin() {

        try {
            ToastUtil.toast(this, "复制成功，正在前往微信");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            ToastUtil.toast(this, "检查到您手机没有安装微信，请安装后使用该功能");
        }

    }


    @Override
    public boolean isStatusBarMateria() {
        return true;
    }
}
