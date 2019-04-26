package yc.com.english_study.base.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.kk.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.vondear.rxtools.RxPermissionsTool;
import com.xinqu.videoplayer.XinQuVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.base.adapter.MainAdapter;
import yc.com.english_study.base.fragment.ExitFragment;
import yc.com.english_study.base.utils.BottomNavigationViewHelper;
import yc.com.english_study.base.utils.UIUtils;
import yc.com.english_study.category.fragment.CategoryFragment;
import yc.com.english_study.databinding.ActivityMainBinding;
import yc.com.english_study.index.fragment.PhoneticFragment;
import yc.com.english_study.mine.fragment.MineFragment;
import yc.com.english_study.study.fragment.StudyFragment;
import yc.com.english_study.study.utils.AVMediaManager;
import yc.com.english_study.study_1vs1.fragment.Study1vs1Fragment;

public class MainActivity extends BaseActivity<BasePresenter, ActivityMainBinding> {


    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        fragments.add(new PhoneticFragment());
        fragments.add(new StudyFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new MineFragment());
        initNavigation();
        initViewPager();
        applyPermission();
        UIUtils.getInstance(this).measureBottomBarHeight(mDataBinding.navigation);


    }


    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        mDataBinding.viewPager.setOffscreenPageLimit(3);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);

        mDataBinding.viewPager.setAdapter(mainAdapter);

        mDataBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDataBinding.navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化navigation
     */

    private void initNavigation() {
        mDataBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(mDataBinding.navigation);
        mDataBinding.navigation.setItemIconTintList(null);
        mDataBinding.navigation.getMenu().getItem(0).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mDataBinding.viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_study:
                    mDataBinding.viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_category:
                    mDataBinding.viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_1vs1:
                    mDataBinding.viewPager.setCurrentItem(3);
                    MobclickAgent.onEvent(MainActivity.this, "one-to-one-click", "1对1辅导");
                    return true;

            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        if (XinQuVideoPlayer.backPress()) {
            return;
        }
        final ExitFragment exitFragment = new ExitFragment();
        exitFragment.show(getSupportFragmentManager(), "");
        exitFragment.setOnConfirmListener(new ExitFragment.onConfirmListener() {
            @Override
            public void onConfirm() {
                exitFragment.dismiss();
                AVMediaManager.getInstance().releaseAudioManager();
                finish();
                System.exit(0);
            }
        });

    }


    @Override
    public boolean isStatusBarMateria() {
        return true;
    }


    /**
     * Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,
     * Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
     * Manifest.permission.SET_DEBUG_APP,
     * Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS
     */
    private void applyPermission() {
        RxPermissionsTool.
                with(this).
                addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                addPermission(Manifest.permission.RECORD_AUDIO).
                addPermission(Manifest.permission.ACCESS_COARSE_LOCATION).
                addPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                initPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (String permission : permissions) {
                LogUtil.msg("TAG: " + permission);
            }
        }
//        if (!TextUtils.equals("Xiaomi", Build.BOARD)) {
//            AdvDispatchManager.getManager().onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
    }
}
