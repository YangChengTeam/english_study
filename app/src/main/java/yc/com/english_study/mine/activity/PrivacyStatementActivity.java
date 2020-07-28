package yc.com.english_study.mine.activity;

import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.base.EnglishStudyApp;
import yc.com.english_study.base.utils.AssetsyUtil;
import yc.com.english_study.databinding.ActivityPrivacyStatementBinding;

public class PrivacyStatementActivity extends BaseActivity<BasePresenter, ActivityPrivacyStatementBinding> {


    @Override
    public boolean isStatusBarMateria() {
        return true;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy_statement;
    }

    @Override
    public void init() {
        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.setTitle("隐私声明");
        mDataBinding.mainToolbar.init(this);
        mDataBinding.mainToolbar.setRightContainerVisible(false);
        mDataBinding.tvPrivacy.setText(EnglishStudyApp.privacyPolicy);
    }
}
