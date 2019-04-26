package yc.com.english_study.index.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.jakewharton.rxbinding.view.RxView;
import com.kk.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.FragmentPhoneticBinding;

/**
 * Created by wanglin  on 2018/10/24 17:21.
 */
public class PhoneticFragment extends BaseFragment<BasePresenter, FragmentPhoneticBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_phonetic;
    }

    @Override
    public void init() {

        GuideFragment guideFragment = new GuideFragment();
        guideFragment.show(getChildFragmentManager(), "");


        mDataBinding.mainToolbar.init(((BaseActivity) getActivity()), null);

        RxView.clicks(mDataBinding.ivVip).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                MobclickAgent.onEvent(getActivity(), "VIP-click", "会员权益");

                VipEquitiesFragment vipEquitiesFragment = new VipEquitiesFragment();
                vipEquitiesFragment.show(getFragmentManager(), "");

            }
        });

        RxView.clicks(mDataBinding.tvServiceWechat).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("weixin", mDataBinding.tvServiceWechat.getText().toString().trim()));
                gotoWeixin();
            }
        });
    }


    private void gotoWeixin() {

        try {
            ToastUtil.toast2(getActivity(), "复制成功，正在前往微信");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            ToastUtil.toast2(getActivity(), "检查到您手机没有安装微信，请安装后使用该功能");
        }

    }


}
