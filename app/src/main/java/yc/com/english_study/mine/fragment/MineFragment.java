package yc.com.english_study.mine.fragment;

import android.content.Intent;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.FragmentMineBinding;
import yc.com.english_study.mine.activity.OrderActivity;
import yc.com.english_study.mine.activity.PayActivity;
import yc.com.english_study.mine.activity.VipEquitiesActivity;

/**
 * Created by wanglin  on 2019/4/23 11:13.
 */
public class MineFragment extends BaseFragment<BasePresenter, FragmentMineBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init() {
        mDataBinding.mainToolbar.hideLeftIcon();
        mDataBinding.mainToolbar.setTvRightTitleAndIcon(getString(R.string.phonetic_introduce), R.mipmap.index_phogetic_introduce);
//        mDataBinding.mainToolbar.init(((BaseActivity) getActivity()),);
        initListener();
    }

    private void initListener() {
        RxView.clicks(mDataBinding.baseSettingViewDredge).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), PayActivity.class));
            }
        });
        RxView.clicks(mDataBinding.baseSettingViewEquities).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), VipEquitiesActivity.class));
            }
        });

        RxView.clicks(mDataBinding.baseSettingViewOrder).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });


    }
}
