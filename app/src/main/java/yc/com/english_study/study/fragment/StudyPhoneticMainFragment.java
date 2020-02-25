package yc.com.english_study.study.fragment;

import android.os.Bundle;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.kk.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import rx.functions.Action1;
import yc.com.base.BaseFragment;
import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.SpConstant;
import yc.com.english_study.databinding.FragmentStudyBinding;
import yc.com.english_study.index.utils.UserInfoHelper;
import yc.com.english_study.mine.fragment.ShareFragment;
import yc.com.english_study.study.adapter.StudyForeignMainAdapter;
import yc.com.english_study.study.contract.StudyContract;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPhoneticInfo;
import yc.com.english_study.study.presenter.StudyPresenter;
import yc.com.english_study.study.utils.AVMediaManager;

/**
 * Created by wanglin  on 2018/10/24 17:21.
 */
public class StudyPhoneticMainFragment extends BaseFragment<StudyPresenter, FragmentStudyBinding> implements StudyContract.View {


    private List<Fragment> fragments = new ArrayList<>();
    private int currentPos = 0;
    private int totalPages;//总页码


    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }

    @Override
    public void init() {
        mPresenter = new StudyPresenter(getActivity(), this);
//
        initListener();
//        UIUtils.getInstance(getActivity()).measureViewLoction(mDataBinding.llTopTint);
        totalPages = SPUtils.getInstance().getInt(SpConstant.PHONETIC_PAGES, 0);
        if (totalPages == 0) {
            mPresenter.getPhoneticPages();
        } else {
            initViewPager(totalPages);
        }

    }


    private void initListener() {
        RxView.clicks(mDataBinding.ivShowVowel).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            StudyVowelFragment studyVowelFragment = new StudyVowelFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            studyVowelFragment.setArguments(bundle);
            studyVowelFragment.setOnClickListener(pos -> {
                if (pos < totalPages) {
                    mDataBinding.studyViewPager.setCurrentItem(pos);
                    currentPos = pos;
                }

                if (pos == 0) {
                    mDataBinding.ivPre.setImageResource(R.mipmap.study_pre_normal);
                } else if (pos == totalPages - 1) {
                    mDataBinding.ivNext.setImageResource(R.mipmap.study_next_normal_);
                }
            });

            studyVowelFragment.show(getChildFragmentManager(), "");
        });

        RxView.clicks(mDataBinding.ivNext).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //todo 下一页

                currentPos++;
                if (currentPos < totalPages) {
                    if (isCanNext(currentPos)) {
                        next(currentPos);
                    } else {
                        currentPos--;
                        showPayDialog();
                    }
                } else {
                    currentPos--;
                    ToastUtil.toast2(getActivity(), "已经是最后一页了");
                }

//                LogUtil.msg("currentPos: next--> " + currentPos);


            }
        });
        RxView.clicks(mDataBinding.ivPre).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                // TODO: 2018/11/2 上一页
                if (currentPos > 0) {
                    currentPos--;
                    pre(currentPos);
                } else {
                    mDataBinding.ivPre.setImageResource(R.mipmap.study_pre_normal);
                    ToastUtil.toast2(getActivity(), "已经是第一页了");
                }
//                LogUtil.msg("currentPos: pre--> " + currentPos);
            }
        });


    }


    private void next(int pos) {//下一页

        mDataBinding.studyViewPager.setCurrentItem(pos);

        mDataBinding.ivPre.setImageResource(R.mipmap.study_pre_selected);
        if (pos == totalPages - 1) {
            mDataBinding.ivNext.setImageResource(R.mipmap.study_next_normal_);
        }

    }

    private void pre(int pos) {//上一页

        mDataBinding.studyViewPager.setCurrentItem(pos);
        mDataBinding.ivNext.setImageResource(R.mipmap.study_next_selected);
        if (pos == 0) {
            mDataBinding.ivPre.setImageResource(R.mipmap.study_pre_normal);
        }
    }


    @Override
    public void showStudyPages(Integer data) {

    }

    private void initViewPager(Integer data) {
//        LogUtil.msg("data:  " + data);
        for (int i = 0; i < data; i++) {
            StudyPhoneticFragment studyMainFragment = new StudyPhoneticFragment();
            studyMainFragment.setPos(i);

            fragments.add(studyMainFragment);
        }

        StudyForeignMainAdapter mainAdapter = new StudyForeignMainAdapter(getChildFragmentManager(), fragments);
        mDataBinding.studyViewPager.setAdapter(mainAdapter);
//        mDataBinding.studyViewPager.setOffscreenPageLimit(data - 1);
        mDataBinding.studyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                XinQuVideoPlayer.releaseAllVideos();
                AVMediaManager.getInstance().releaseAudioManager();

//                LogUtil.msg("currentPos: scroll-->" + currentPos + "--position-->" + position);
                if (isCanNext(position)) {
                    if (currentPos > position) {
                        pre(position);
                    } else {
                        next(position);
                    }
                    currentPos = position;
                } else {
                    mDataBinding.studyViewPager.setCurrentItem(currentPos);
                    showPayDialog();
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void showStudyInfo(StudyInfoWrapper data) {

    }

    @Override
    public void showPhoneticPages(int count) {
        totalPages = count;
        initViewPager(count);
    }

    @Override
    public void showPhoneticInfo(StudyPhoneticInfo info) {

    }


    private boolean isCanNext(int pos) {
        boolean isNext = false;
        if (UserInfoHelper.isShareSuccess() || pos < 9) {
            isNext = true;
        }
        return isNext;

    }

    private void showPayDialog() {

        ShareFragment shareFragment = new ShareFragment();
        shareFragment.show(getFragmentManager(), "");
    }

    @Override
    public void hide() {
        mDataBinding.stateView.hide();

    }

    @Override
    public void showLoading() {
        mDataBinding.stateView.showLoading(mDataBinding.container);

    }

    @Override
    public void showNoData() {
        mDataBinding.stateView.showNoData(mDataBinding.container);
    }

    @Override
    public void showNoNet() {
        mDataBinding.stateView.showNoNet(mDataBinding.container, HttpConfig.NET_ERROR, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPhoneticPages();
            }
        });

    }

    @Override
    public void fetchData() {
        super.fetchData();

    }


}
