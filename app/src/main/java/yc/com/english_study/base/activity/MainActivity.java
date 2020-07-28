package yc.com.english_study.base.activity;

import android.content.Intent;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.umeng.analytics.MobclickAgent;
import com.xinqu.videoplayer.XinQuVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.english_study.R;
import yc.com.english_study.base.adapter.MainAdapter;
import yc.com.english_study.base.constant.SpConstant;
import yc.com.english_study.base.fragment.ExitFragment;
import yc.com.english_study.base.utils.UIUtils;
import yc.com.english_study.category.fragment.CategoryFragment;
import yc.com.english_study.databinding.ActivityMainBinding;
import yc.com.english_study.index.fragment.IndexNoticeDialogFragment;
import yc.com.english_study.mine.fragment.MineFragment;
import yc.com.english_study.study.fragment.IndexDialogFragment;
import yc.com.english_study.study.fragment.StudyMainFragment;
import yc.com.english_study.study.utils.AVMediaManager;
import yc.com.english_study.study_1vs1.fragment.Study1vs1Fragment;
import yc.com.rthttplibrary.util.LogUtil;

public class MainActivity extends BaseActivity<BasePresenter, ActivityMainBinding> {


    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {


        fragments.add(new StudyMainFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new Study1vs1Fragment());
        fragments.add(new MineFragment());
        initNavigation();
        initViewPager();

        UIUtils.getInstance(this).measureBottomBarHeight(mDataBinding.navigation);

        if (!SPUtils.getInstance().getBoolean(SpConstant.INDEX_DIALOG)) {
            IndexDialogFragment indexDialogFragment = new IndexDialogFragment();
//            IndexNoticeDialogFragment indexNoticeDialogFragment= new IndexNoticeDialogFragment();
//            indexNoticeDialogFragment.show(getSupportFragmentManager(), "");
            indexDialogFragment.show(getSupportFragmentManager(), "");
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int position = intent.getIntExtra("position", 0);
            mDataBinding.viewPager.setCurrentItem(position);
        }
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
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
//            BottomNavigationViewHelper.disableShiftMode(mDataBinding.navigation);
//        } else {
//            mDataBinding.navigation.setLabelVisibilityMode(1);
//        }
        mDataBinding.navigation.setItemIconTintList(null);
        mDataBinding.navigation.getMenu().getItem(0).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_study:
                mDataBinding.viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_category:
                mDataBinding.viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_discover:
                mDataBinding.viewPager.setCurrentItem(2);
                MobclickAgent.onEvent(MainActivity.this, "one-to-one-click", "1对1辅导");
                return true;
            case R.id.navigation_mine:
                mDataBinding.viewPager.setCurrentItem(3);
                return true;

        }
        return false;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (String permission : permissions) {
                LogUtil.msg("TAG: " + permission);
            }
        }
    }
}
