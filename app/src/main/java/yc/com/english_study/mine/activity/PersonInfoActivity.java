package yc.com.english_study.mine.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityPersonInfoBinding;
import yc.com.english_study.index.model.domain.UserInfo;
import yc.com.english_study.index.utils.UserInfoHelper;

/**
 * Created by suns  on 2020/4/15 16:13.
 */
public class PersonInfoActivity extends BaseActivity<BasePresenter, ActivityPersonInfoBinding> {
    private boolean isNoPwd;

    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    public void init() {
        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.init(this);
        mDataBinding.mainToolbar.setRightContainerVisible(false);
        mDataBinding.mainToolbar.setTitle("个人信息");
        UserInfo userInfo = UserInfoHelper.getUserInfo();
        if (userInfo != null) {
            String phone = userInfo.getMobile();
            if (!TextUtils.isEmpty(phone)) {
                mDataBinding.tvPhone.setText(phone.replace(phone.substring(3, 7), "****"));
            }
            if (TextUtils.isEmpty(userInfo.getPwd())) {
                isNoPwd = true;
            }
        }

        mDataBinding.tvPwd.setText(isNoPwd ? "设置密码" : "修改密码");
        initListener();
    }

    private void initListener() {
        RxView.clicks(mDataBinding.rlPwd).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isNoPwd) {
                    Intent intent = new Intent(PersonInfoActivity.this, SetPwdActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PersonInfoActivity.this, ModifyPWdActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
