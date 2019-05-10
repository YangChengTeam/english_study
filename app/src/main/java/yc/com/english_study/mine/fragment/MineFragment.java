package yc.com.english_study.mine.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.jakewharton.rxbinding.view.RxView;
import com.kk.utils.ToastUtil;
import com.vondear.rxtools.view.RxTextViewVertical;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.FragmentMineBinding;
import yc.com.english_study.index.utils.UserInfoHelper;
import yc.com.english_study.mine.activity.OrderActivity;
import yc.com.english_study.mine.activity.PayActivity;
import yc.com.english_study.mine.activity.PhoneticActivity;
import yc.com.english_study.mine.activity.VipEquitiesActivity;

/**
 * Created by wanglin  on 2019/4/23 11:13.
 */
public class MineFragment extends BaseFragment<BasePresenter, FragmentMineBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init() {
        mDataBinding.mainToolbar.hideLeftIcon();
        mDataBinding.mainToolbar.setTvRightTitleAndIcon(getString(R.string.phonetic_introduce), R.mipmap.index_phogetic_introduce);
        mDataBinding.mainToolbar.init(((BaseActivity) getActivity()), PhoneticActivity.class);

        initListener();
    }

    private void initListener() {
        RxView.clicks(mDataBinding.baseSettingViewDredge).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), PayActivity.class));
            }
        });
        RxView.clicks(mDataBinding.baseSettingViewEquities).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), VipEquitiesActivity.class));
            }
        });

        RxView.clicks(mDataBinding.baseSettingViewOrder).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        RxView.clicks(mDataBinding.baseSettingViewShare).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ShareFragment shareFragment = new ShareFragment();
                shareFragment.show(getChildFragmentManager(), "");
            }
        });
        RxView.clicks(mDataBinding.baseSettingViewService).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                if (UserInfoHelper.getContactInfo() != null) {
                    cm.setPrimaryClip(ClipData.newPlainText("weixin", UserInfoHelper.getContactInfo().getWeixin()));
                    gotoWeixin();
                }
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
