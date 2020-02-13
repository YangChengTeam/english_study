package yc.com.english_study.index.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.viewpager.widget.ViewPager;
import rx.functions.Action1;
import yc.com.base.BaseDialogFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.FragmentGuideBinding;

/**
 * Created by wanglin  on 2019/4/26 08:40.
 */
public class GuideFragment extends BaseDialogFragment<BasePresenter, FragmentGuideBinding> {
    private List<Integer> images;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void init() {
        images = new ArrayList<>();
        images.add(R.mipmap.guide1);
        images.add(R.mipmap.guide2);
        images.add(R.mipmap.guide3);
//        mDataBinding.setLifecycleOwner(BR.images, images);
        mDataBinding.setImages(images);
        initListener();

    }

    private void initListener() {

        mDataBinding.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDataBinding.ivStartBtn.setVisibility(position == images.size() - 1 ? View.VISIBLE : View.GONE);
//                mDataBinding.banner.setViewPagerIsScroll(position != images.size() - 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RxView.clicks(mDataBinding.ivStartBtn).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                dismiss();
            }
        });
    }

    @Override
    protected float getWidth() {
        return 0.9f;
    }

    @Override
    public int getAnimationId() {
        return 0;
    }

    @Override
    public int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
