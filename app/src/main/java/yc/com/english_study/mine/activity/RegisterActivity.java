package yc.com.english_study.mine.activity;

import android.text.Html;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityRegisterBinding;
import yc.com.english_study.mine.contract.LoginContract;
import yc.com.english_study.mine.fragment.UserPolicyFragment;
import yc.com.english_study.mine.presenter.LoginPresenter;

/**
 * Created by suns  on 2020/4/14 17:02.
 */
public class RegisterActivity extends BaseActivity<LoginPresenter, ActivityRegisterBinding> implements LoginContract.View {

    private boolean isLogin = false;


    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        mPresenter = new LoginPresenter(this, this);
        if (getIntent() != null) {
            this.isLogin = getIntent().getBooleanExtra("isLogin", false);
        }
        mDataBinding.tvPolicy.setText(Html.fromHtml("注册即代表同意<font color='#efc008'>《用户协议》</font>"));
        initListener();
    }

    private void initListener() {
        RxView.clicks(mDataBinding.ivRegister).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String phone = mDataBinding.etPhone.getText().toString().trim();
                String code = mDataBinding.etCode.getText().toString().trim();
                mPresenter.codeLogin(phone, code, isLogin);

            }
        });
        RxView.clicks(mDataBinding.tvGetCode).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String phone = mDataBinding.etPhone.getText().toString().trim();
                mPresenter.sendCode(phone);
            }
        });
        RxView.clicks(mDataBinding.tvPolicy).throttleFirst(200,TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                UserPolicyFragment userPolicyFragment= new UserPolicyFragment();
                userPolicyFragment.show(getSupportFragmentManager(),"");
            }
        });
    }

    @Override
    public void showDisplayCode() {
        showGetCodeDisplay(mDataBinding.tvGetCode, mDataBinding.tvCountDown);
    }
}
