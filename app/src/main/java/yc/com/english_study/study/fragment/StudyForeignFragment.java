package yc.com.english_study.study.fragment;

import android.content.Intent;
import android.graphics.RectF;
import android.text.Html;
import android.view.View;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Builder;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding.view.RxView;

import com.xinqu.videoplayer.XinQuVideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import rx.functions.Action1;
import yc.com.base.BaseFragment;
import yc.com.english_study.R;
import yc.com.english_study.base.utils.UIUtils;
import yc.com.english_study.databinding.StudyMainItemBinding;
import yc.com.english_study.study.activity.PreviewActivity;
import yc.com.english_study.study.adapter.StudyApplyAdapter;
import yc.com.english_study.study.contract.StudyContract;
import yc.com.english_study.study.listener.OnAVManagerListener;
import yc.com.english_study.study.listener.OnUIPracticeControllerListener;
import yc.com.english_study.study.model.domain.StudyInfo;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPhoneticInfo;
import yc.com.english_study.study.presenter.StudyPresenter;
import yc.com.english_study.study.utils.PpAudioManager;
import yc.com.english_study.study_1vs1.utils.LPUtils;
import yc.com.rthttplibrary.config.HttpConfig;
import yc.com.rthttplibrary.util.ScreenUtil;

/**
 * Created by wanglin  on 2018/10/26 16:23.
 */
public class StudyForeignFragment extends BaseFragment<StudyPresenter, StudyMainItemBinding> implements StudyContract.View, OnUIPracticeControllerListener {


    private int playStep = 1;//播放步骤


    private List<Fragment> mFragments;
    private int pos;

    private OnAVManagerListener mListener;
    private int[] firstLayoutIds = new int[]{R.layout.study_perception_guide, R.layout.study_study_guide};


    public void setFragments(List<Fragment> fragments) {
        this.mFragments = fragments;
    }

    @Override
    public int getLayoutId() {
        return R.layout.study_main_item;
    }


    @Override
    public void init() {

        mPresenter = new StudyPresenter(getActivity(), this);
        mDataBinding.videoPlayer.widthRatio = 16;
        mDataBinding.videoPlayer.heightRatio = 9;

    }


    private void initView(StudyInfoWrapper studyInfoWrapper) {

        StudyApplyAdapter studyApplyAdapter = new StudyApplyAdapter(getChildFragmentManager(), getActivity(),
                mFragments, studyInfoWrapper.getWords(), studyInfoWrapper.getPhrase(), studyInfoWrapper.getSentence(), pos);
        mDataBinding.viewPager.setAdapter(studyApplyAdapter);
        mDataBinding.viewPager.setCurrentItem(0);
        mDataBinding.viewPager.setOffscreenPageLimit(3);
        mDataBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);


    }


    @Override
    public void showStudyPages(Integer data) {

    }

    private StudyInfo mStudyInfo;

    @Override
    public void showStudyInfo(StudyInfoWrapper data) {
        //显示引导页
//        boolean isShowFirst = SPUtils.getInstance().getBoolean(SpConstant.IS_SHOW_FIRST, true);
//        if (isShowFirst && isVisibleToUser) {
//            views.clear();
//            views.add(mDataBinding.llPerceptionContainer);
//            views.add(mDataBinding.llStudyContainer);
//            startGuide(views, firstLayoutIds);
//
//            SPUtils.getInstance().put(SpConstant.IS_SHOW_FIRST, false);
//        }


        StudyInfo studyInfo = data.getInfo();
        mDataBinding.setStudyInfo(studyInfo);
        mStudyInfo = studyInfo;

        mDataBinding.tvPronounce.setText(Html.fromHtml(LPUtils.getInstance().addPhraseLetterColor(studyInfo.getVoice())));

        initView(data);
        playVideo(studyInfo);
        initListener(studyInfo);
    }

    @Override
    public void showPhoneticPages(int count) {

    }

    @Override
    public void showPhoneticInfo(StudyPhoneticInfo info) {

    }

    private View currentView;

    private void initListener(final StudyInfo studyInfo) {
        RxView.clicks(mDataBinding.llPerceptionVoice).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                currentView = mDataBinding.ivPerceptionVoice;
                mListener.playMusic(studyInfo.getVowel_mp3());
//                startAnimation(tvPerceptionVoice);
            }
        });

        RxView.clicks(mDataBinding.llPerceptionWord).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                currentView = mDataBinding.ivPerceptionWord;
                mListener.playMusic(studyInfo.getMp3());
            }
        });

        //1.播放引导音 2.播放发音 3.播放di声音4.录音并播放5.播放第二段引导音6
        RxView.clicks(mDataBinding.ivPractice).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                if (mListener.isPlaying()) {
                    playPracticeAfterUpdateUI();
                } else {
                    //todo 播放并录音
                    playStep = 1;

                    mListener.playAssetFile("guide_01.mp3",false, playStep);
                    if (mStudyInfo != null) {
                        mDataBinding.tvPracticeSoundmark.setVisibility(View.VISIBLE);
                        mDataBinding.tvPracticeSoundmark.setText(mStudyInfo.getName());
                    }
                }


            }
        });

        RxView.clicks(mDataBinding.rlPronounce).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(getActivity(), PreviewActivity.class);
                if (mStudyInfo != null)
                    intent.putExtra("img", mStudyInfo.getImage());
                startActivity(intent);
            }
        });

        RxView.clicks(mDataBinding.rlEssentials).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(getActivity(), PreviewActivity.class);
                if (mStudyInfo != null) {
                    intent.putExtra("img", mStudyInfo.getMouth_cover());
                }
                startActivity(intent);

            }
        });

    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private List<View> views = new ArrayList<>();

    @Override
    public void fetchData() {


        mPresenter.getStudyDetail(pos);
        mListener = new PpAudioManager(getActivity(), this);

    }

    //设置引导视图
    private void showGuide(final List<View> viewList, int[] layoutIds, final int scrollHeight) {

        Builder builder = getBuilder("guide1", 1, scrollHeight);

        if (viewList != null && viewList.size() > 0) {

            for (int i = 0; i < viewList.size(); i++) {

                builder.addGuidePage(GuidePage.newInstance()
                        .addHighLight(viewList.get(i), HighLight.Shape.RECTANGLE, 16)
                        .setEverywhereCancelable(false)

                        .setLayoutRes(layoutIds[i], R.id.iv_next)

                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {


                            }


                        }));

            }

            builder.show();
        }
    }


    private boolean isPracticeShow = true;
    private boolean isApplyShow = true;

    private Builder getBuilder(String label, final int step, final int scrollHeight) {

        return NewbieGuide.with(StudyForeignFragment.this)
                .setLabel(label)
                .alwaysShow(true)
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                    }

                    @Override
                    public void onRemoved(Controller controller) {

                        if (step == 1 && isPracticeShow) {
                            isPracticeShow = false;
                            mDataBinding.nestedScrollView.scrollTo(0, scrollHeight);
                            int[] location = new int[2];
                            mDataBinding.llPracticeContainer.getLocationOnScreen(location);
                            RectF rect = new RectF();
                            rect.set(location[0] - ScreenUtil.dip2px(getActivity(), 7), location[1], location[0] + mDataBinding.llPerceptionContainer.getRight() - ScreenUtil.dip2px(getActivity(), 10), mDataBinding.llPerceptionContainer.getBottom() + location[1] + ScreenUtil.dip2px(getActivity(), 40));

                            int[] location1 = new int[2];
                            mDataBinding.llEssentialsContainer.getLocationOnScreen(location1);
                            RectF rectF = new RectF();
                            rectF.set(location1[0] - ScreenUtil.dip2px(getActivity(), 7), location1[1] - ScreenUtil.dip2px(getActivity(), 10),
                                    location1[0] + mDataBinding.llEssentialsContainer.getRight() - ScreenUtil.dip2px(getActivity(), 10),
                                    mDataBinding.llEssentialsContainer.getBottom() + location1[1] - ScreenUtil.dip2px(getActivity(), 40));

                            practiceGuide(rect, rectF);
                        } else if (step == 2 && isApplyShow) {
                            isApplyShow = false;
                            mDataBinding.nestedScrollView.scrollBy(0, ScreenUtil.getHeight(getActivity()) - UIUtils.getInstance(getActivity()).getBottomBarHeight());
                            int[] location = new int[2];
                            mDataBinding.llApplyContainer.getLocationOnScreen(location);

                            RectF rectF = new RectF(location[0] - ScreenUtil.dip2px(getActivity(), 7), location[1] - ScreenUtil.dip2px(getActivity(), 10), location[0] + mDataBinding.llApplyContainer.getRight() - ScreenUtil.dip2px(getActivity(), 10), mDataBinding.llApplyContainer.getBottom() + location[1] - ScreenUtil.dip2px(getActivity(), 20));
                            applyGuide(rectF);

                        } else if (step == 3) {
                            //
                            mDataBinding.nestedScrollView.scrollBy(0, -ScreenUtil.getHeight(getActivity()));
                        }
                    }
                });
    }


    private void practiceGuide(final RectF rect, final RectF rectF) {
        mDataBinding.llPracticeContainer.post(new Runnable() {
            @Override
            public void run() {
                Builder builder = getBuilder("guide2", 2, 0);
                builder.addGuidePage(GuidePage.newInstance()
                        .addHighLight(rect, HighLight.Shape.RECTANGLE, 16)
                        .setEverywhereCancelable(false)
                        .setLayoutRes(R.layout.study_practice_guide, R.id.iv_next))
                        .addGuidePage(GuidePage.newInstance()
                                .addHighLight(rectF, HighLight.Shape.RECTANGLE, 16)
                                .setEverywhereCancelable(false)
                                .setLayoutRes(R.layout.study_essentials_guide, R.id.iv_next));


                builder.show();
            }

        });
    }

    private void applyGuide(final RectF rect) {
        mDataBinding.llPracticeContainer.post(new Runnable() {
            @Override
            public void run() {
                Builder builder = getBuilder("guide3", 3, 0);
                builder.addGuidePage(GuidePage.newInstance()
                        .addHighLight(rect, HighLight.Shape.RECTANGLE, 16)
                        .setEverywhereCancelable(false)
                        .setLayoutRes(R.layout.study_apply_guide, R.id.iv_next));


                builder.show();
            }

        });
    }


    private void startGuide(final List<View> views, final int[] layoutIds) {

        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                mDataBinding.llStudyTotalContainer.getLocationOnScreen(location);

                UIUtils instance = UIUtils.getInstance(getActivity());
                final int[] topLocation = instance.getLocation();
                location[1] = location[1] + mDataBinding.llStudyTotalContainer.getBottom() - mDataBinding.llStudyTotalContainer.getTop() - topLocation[1];

                showGuide(views, layoutIds, location[1]);

            }
        }, 1000);
    }


    /**
     * 播放视频
     *
     * @param studyInfo
     */
    private void playVideo(StudyInfo studyInfo) {
        Glide.with(getActivity()).load(studyInfo.getVideo_cover()).thumbnail(0.1f).into(mDataBinding.videoPlayer.thumbImageView);

        mDataBinding.videoPlayer.setUp(studyInfo.getVoice_video(), XinQuVideoPlayer.SCREEN_WINDOW_LIST, false, null == studyInfo.getCn() ? "" : studyInfo.getCn());
    }


    @Override
    public void onPause() {
        super.onPause();
        XinQuVideoPlayer.goOnPlayOnPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataBinding.mediaPlayerView.destroy();
    }


    @Override
    public void playBeforeUpdateUI() {
        if (currentView == mDataBinding.ivPerceptionVoice) {
            Glide.with(getActivity()).load(R.mipmap.small_trumpet_stop).asGif().into(mDataBinding.ivPerceptionVoice);
        } else if (currentView == mDataBinding.ivPerceptionWord) {
            Glide.with(getActivity()).load(R.mipmap.big_trumpet_stop).asGif().into(mDataBinding.ivPerceptionWord);
        }
    }

    @Override
    public void playAfterUpdateUI() {
        Glide.clear(mDataBinding.ivPerceptionVoice);
        Glide.clear(mDataBinding.ivPerceptionWord);
        mDataBinding.ivPerceptionVoice.setImageResource(R.mipmap.small_trumpet);
        mDataBinding.ivPerceptionWord.setImageResource(R.mipmap.big_trumpet);
    }


    @Override
    public void playPracticeBeforeUpdateUI(int progress) {
        mDataBinding.ivPractice.setImageResource(R.mipmap.study_practice_pause);
        mDataBinding.tvNumberProgress.setText(String.format(getString(R.string.practice_progress), progress));
    }

    @Override
    public void playPracticeFirstUpdateUI() {

        playStep = 2;
        if (mStudyInfo == null) return;
        String mp3 = "http://thumb.1010pic.com/dmt/diandu/27/mp3/000002_Unit%201_Lesson%2001.mp3";
        mListener.playMusic(mStudyInfo.getVowel_mp3(), false, playStep);

    }

    @Override
    public void playPracticeSecondUpdateUI() {

        playStep = 3;
        mListener.playAssetFile("user_tape_tips.mp3",false, playStep);
    }

    @Override
    public void recordUpdateUI() {
        mDataBinding.ivTopCarton.setVisibility(View.VISIBLE);
    }

    @Override
    public void playPracticeAfterUpdateUI() {
        mDataBinding.ivPractice.setImageResource(R.mipmap.study_practice_play);
        if (isAdded())
            mDataBinding.tvNumberProgress.setText(String.format(getString(R.string.practice_progress), 0));
        mDataBinding.ivTopCarton.setVisibility(View.GONE);
        mListener.stopMusic();
        mDataBinding.tvPracticeSoundmark.setVisibility(View.GONE);

    }

    @Override
    public void updateProgressBar(int percent) {
        mDataBinding.progressBar.setProgress(percent);
        if (percent != 100) {
            mDataBinding.progressBar.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void playPracticeThirdUpdateUI() {
        playStep = 1;
        mListener.playAssetFile("guide_02.mp3",false, playStep);
        mDataBinding.ivTopCarton.setVisibility(View.GONE);
    }


    @Override
    public void hide() {
        mDataBinding.stateView.hide();
    }

    @Override
    public void showLoading() {
        mDataBinding.stateView.showLoading(mDataBinding.nestedScrollView);
    }

    @Override
    public void showNoData() {
        mDataBinding.stateView.showNoData(mDataBinding.nestedScrollView);

    }

    @Override
    public void showNoNet() {
        mDataBinding.stateView.showNoNet(mDataBinding.nestedScrollView, HttpConfig.NET_ERROR, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getStudyDetail(pos);
            }
        });
    }


}

