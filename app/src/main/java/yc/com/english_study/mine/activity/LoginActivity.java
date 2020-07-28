package yc.com.english_study.mine.activity;

import android.content.Intent;
import android.text.Html;

import com.jakewharton.rxbinding.view.RxView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityLoginBinding;
import yc.com.english_study.mine.contract.LoginContract;
import yc.com.english_study.mine.fragment.UserPolicyFragment;
import yc.com.english_study.mine.presenter.LoginPresenter;

/**
 * Created by suns  on 2020/4/14 17:02.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View {
    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        mPresenter = new LoginPresenter(this, this);
        mDataBinding.tvPolicy.setText(Html.fromHtml("登录即代表同意<font color='#efc008'>《用户协议》</font>"));
        mDataBinding.tvCodeLogin.setText(Html.fromHtml("忘记了？<font color='#efc008'>验证码登录</font>"));
        initListener();
    }

    private void initListener() {
        RxView.clicks(mDataBinding.ivLogin).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String phone = mDataBinding.etPhone.getText().toString().trim();
                String pwd = mDataBinding.etPwd.getText().toString().trim();
                mPresenter.login(phone, pwd);

            }
        });
        RxView.clicks(mDataBinding.tvCodeLogin).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("isLogin", true);
                startActivity(intent);
                finish();
            }
        });
        RxView.clicks(mDataBinding.tvRegister).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RxView.clicks(mDataBinding.tvPolicy).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                UserPolicyFragment userPolicyFragment = new UserPolicyFragment();
                userPolicyFragment.show(getSupportFragmentManager(), "");
            }
        });
    }


    @Override
    public void showDisplayCode() {

    }
}
