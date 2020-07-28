package yc.com.english_study.study.fragment;

import android.app.Activity;

import com.google.android.material.tabs.TabLayout;

import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.SpConstant;
import yc.com.english_study.databinding.FragmentNewStudyBinding;
import yc.com.english_study.index.fragment.GuideFragment;
import yc.com.english_study.mine.activity.PhoneticActivity;
import yc.com.english_study.study.adapter.StudyMainAdapter;
import yc.com.rthttplibrary.util.LogUtil;
import yc.com.tencent_adv.OnAdvStateListener;

/**
 * Created by wanglin  on 2019/4/28 18:49.
 */
public class StudyMainFragment extends BaseFragment<BasePresenter, FragmentNewStudyBinding> implements OnAdvStateListener {
    private List<Fragment> fragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_study;
    }

    @Override
    public void init() {

        boolean isShowFirst = SPUtils.getInstance().getBoolean(SpConstant.IS_SHOW_FIRST, true);
        if (isShowFirst) {
            GuideFragment guideFragment = new GuideFragment();
            guideFragment.show(getChildFragmentManager(), "");
            SPUtils.getInstance().put(SpConstant.IS_SHOW_FIRST, false);
        }

        Activity ac = getActivity();
        if (ac instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) ac;
            mDataBinding.mainToolbar.init(baseActivity, PhoneticActivity.class);
        }
        mDataBinding.mainToolbar.setTvRightTitleAndIcon(getString(R.string.phonetic_introduce), R.mipmap.index_phogetic_introduce);

        fragmentList = new ArrayList<>();
        fragmentList.add(new StudyPhoneticMainFragment());
        fragmentList.add(new StudyForeignMainFragment());

        StudyMainAdapter studyMainAdapter = new StudyMainAdapter(getFragmentManager(), ac, fragmentList);
        mDataBinding.studyViewPager.setAdapter(studyMainAdapter);
        mDataBinding.studyViewPager.setOffscreenPageLimit(1);
        mDataBinding.studyTabLayout.setupWithViewPager(mDataBinding.studyViewPager);

//        initListener();
//        if (!(TextUtils.equals("Xiaomi", Build.BRAND) || TextUtils.equals("xiaomi", Build.BRAND)))
//            AdvDispatchManager.getManager().init(getActivity(), AdvType.BANNER, mDataBinding.bottomContainer, null, Config.TENCENT_ADV_ID, Config.BANNER_BOTTOM_ADV_ID, this);
    }

    private void initListener() {
        mDataBinding.studyTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                LogUtil.msg("position: " + position);
                switchFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchFragment(int position) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        for (Fragment fragment : fragmentList) {
//            if (fragment != null && fragment.isAdded()) {
//                ft.hide(fragment);
//            }
//        }
        Fragment fragment = fragmentList.get(position);
//        if (!fragment.isAdded()) {
//            ft.add(R.id.content_container, fragment);
//        } else {
//            ft.show(fragment);
//        }
        ft.replace(R.id.content_container, fragment).commit();
//        ft.commit();
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDismiss(long delayTime) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onNativeExpressDismiss(NativeExpressADView view) {

    }

    @Override
    public void onNativeExpressShow(Map<NativeExpressADView, Integer> mDatas) {

    }
}
