package yc.com.english_study.mine.activity;

import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;


import java.util.concurrent.TimeUnit;

import androidx.core.content.ContextCompat;
import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityModifyPwdBinding;
import yc.com.english_study.mine.contract.LoginContract;
import yc.com.english_study.mine.presenter.LoginPresenter;

/**
 * Created by suns  on 2020/4/15 16:58.
 */
public class ModifyPWdActivity extends BaseActivity<LoginPresenter, ActivityModifyPwdBinding> implements LoginContract.View {
    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    public void init() {
        mPresenter = new LoginPresenter(this, this);
        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.init(this);
        mDataBinding.mainToolbar.setTitle("修改密码");
        TextView tvDone = new TextView(this);
        tvDone.setText("完成");
        tvDone.setTextColor(ContextCompat.getColor(this, R.color.white));

        mDataBinding.mainToolbar.addRightView(tvDone);

        RxView.clicks(tvDone).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            String pwd = mDataBinding.etPwd.getText().toString().trim();
            String newPwd = mDataBinding.etNewPwd.getText().toString().trim();
            mPresenter.modifyPwd(pwd, newPwd);
        });
    }

    @Override
    public void showDisplayCode() {

    }
}
