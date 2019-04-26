package yc.com.english_study.mine.activity;

import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityVipEquitiesBinding;

/**
 * Created by wanglin  on 2019/4/25 14:49.
 */
public class VipEquitiesActivity extends BaseActivity<BasePresenter, ActivityVipEquitiesBinding> {
    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip_equities;
    }

    @Override
    public void init() {
        mDataBinding.mainToolbar.showNavigationIcon();
        mDataBinding.mainToolbar.setTitle(getString(R.string.vip_equities));
        mDataBinding.mainToolbar.setRightContainerVisible(false);
        mDataBinding.mainToolbar.init(this);
    }
}
