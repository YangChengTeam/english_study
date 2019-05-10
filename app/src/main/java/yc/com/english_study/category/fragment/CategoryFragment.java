package yc.com.english_study.category.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.base.activity.MainActivity;
import yc.com.english_study.base.activity.WebActivity;
import yc.com.english_study.base.constant.Config;
import yc.com.english_study.category.adapter.CategoryPagerAdapter;
import yc.com.english_study.databinding.FragmentCategoryBinding;
import yc.com.english_study.mine.activity.PhoneticActivity;
import yc.com.tencent_adv.AdvDispatchManager;
import yc.com.tencent_adv.AdvType;
import yc.com.tencent_adv.OnAdvStateListener;


/**
 * Created by wanglin  on 2018/10/24 17:21.
 */
public class CategoryFragment extends BaseFragment<BasePresenter, FragmentCategoryBinding> implements OnAdvStateListener {


    private List<Fragment> fragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void init() {


        mDataBinding.setIsAct(false);
        mDataBinding.mainToolbar.init((BaseActivity) getActivity(), PhoneticActivity.class);
        mDataBinding.mainToolbar.setTvRightTitleAndIcon(getString(R.string.phonetic_introduce), R.mipmap.index_phogetic_introduce);

//        if (!(TextUtils.equals("Xiaomi", Build.BRAND) || TextUtils.equals("xiaomi", Build.BRAND)))
//            AdvDispatchManager.getManager().init(getActivity(), AdvType.BANNER, mDataBinding.bottomContainer, null, Config.TENCENT_ADV_ID, Config.BANNER_TOP_ADV_ID, this);

        fragmentList = new ArrayList<>();
        for (int i = 8; i < 11; i++) {
            WeikeUnitFragment weikeUnitFragment = new WeikeUnitFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type_id", i + "");
            weikeUnitFragment.setArguments(bundle);
            fragmentList.add(weikeUnitFragment);
        }
        CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(getChildFragmentManager(), getActivity(), fragmentList);


        mDataBinding.categoryViewPager.setAdapter(categoryPagerAdapter);
        mDataBinding.categoryViewPager.setOffscreenPageLimit(2);
        mDataBinding.categoryTabLayout.setupWithViewPager(mDataBinding.categoryViewPager);

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
