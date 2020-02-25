package yc.com.english_study.study.fragment;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.kk.securityhttp.net.contains.HttpConfig;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseFragment;
import yc.com.english_study.R;
import yc.com.english_study.databinding.StudyPhoneticItemBinding;
import yc.com.english_study.study.contract.StudyContract;
import yc.com.english_study.study.listener.OnAVManagerListener;
import yc.com.english_study.study.listener.OnUIPracticeControllerListener;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPhoneticInfo;
import yc.com.english_study.study.presenter.StudyPresenter;
import yc.com.english_study.study.utils.PpAudioManager;

/**
 * Created by wanglin  on 2019/4/28 14:13.
 */
public class StudyPhoneticFragment extends BaseFragment<StudyPresenter, StudyPhoneticItemBinding> implements StudyContract.View, OnUIPracticeControllerListener {
    private int pos;
    private OnAVManagerListener manager;
    private StudyPhoneticInfo mInfo;
    private ImageView currentView;

    @Override
    public int getLayoutId() {
        return R.layout.study_phonetic_item;
    }

    @Override
    public void init() {
        mPresenter = new StudyPresenter(getActivity(), this);

        initListener();
    }


    private void initListener() {
        RxView.clicks(mDataBinding.ivPhoneticWord).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            if (mInfo != null) {
                manager.playMusic(mInfo.getLetters_mp3());
                currentView = mDataBinding.ivPhoneticWord;
            }
        });

        RxView.clicks(mDataBinding.ivPhoneticPronunciation).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            if (mInfo != null) {
                manager.playMusic(mInfo.getPronunciation_mp3());
                currentView = mDataBinding.ivPhoneticPronunciation;
            }
        });

        RxView.clicks(mDataBinding.rlPracticeWordLeft).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            if (mInfo != null) {
                manager.playMusic(mInfo.getWord1_mp3());
                currentView = mDataBinding.ivPracticeWordLeft;
            }
        });
        RxView.clicks(mDataBinding.rlPracticeWordRight).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            if (mInfo != null) {
                manager.playMusic(mInfo.getWord2_mp3());
                currentView = mDataBinding.ivPracticeWordRight;
            }
        });
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public void showStudyPages(Integer data) {

    }

    @Override
    public void showStudyInfo(StudyInfoWrapper data) {

    }

    @Override
    public void showPhoneticPages(int count) {

    }

    @Override
    public void showPhoneticInfo(StudyPhoneticInfo info) {
        mInfo = info;
        mDataBinding.setStudyPhoneticInfo(info);

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
        mDataBinding.stateView.showNoNet(mDataBinding.nestedScrollView, HttpConfig.NET_ERROR, v -> mPresenter.getPhoneticDetail(pos));
    }

    @Override
    public void fetchData() {
        super.fetchData();

        mPresenter.getPhoneticDetail(pos);
        manager = new PpAudioManager(getActivity(), this);
    }

    @Override
    public void playBeforeUpdateUI() {
        if (currentView == mDataBinding.ivPhoneticWord || currentView == mDataBinding.ivPhoneticPronunciation) {
            Glide.with(getActivity()).load(R.mipmap.big_trumpet_stop).asGif().into(currentView);
        } else if (currentView == mDataBinding.ivPracticeWordLeft || currentView == mDataBinding.ivPracticeWordRight) {
            Glide.with(getActivity()).load(R.mipmap.word_practice_trumpet_stop).asGif().into(currentView);
        }
    }

    @Override
    public void playAfterUpdateUI() {
        if (currentView != null)
            Glide.clear(currentView);

        mDataBinding.ivPhoneticWord.setImageResource(R.mipmap.big_trumpet);
        mDataBinding.ivPhoneticPronunciation.setImageResource(R.mipmap.big_trumpet);
        mDataBinding.ivPracticeWordLeft.setImageResource(R.mipmap.word_practice_trumpet);
        mDataBinding.ivPracticeWordRight.setImageResource(R.mipmap.word_practice_trumpet);
    }

    @Override
    public void playPracticeBeforeUpdateUI(int progress) {

    }

    @Override
    public void playPracticeFirstUpdateUI() {

    }

    @Override
    public void playPracticeSecondUpdateUI() {

    }

    @Override
    public void recordUpdateUI() {

    }

    @Override
    public void playPracticeAfterUpdateUI() {

    }

    @Override
    public void updateProgressBar(int percent) {

    }

    @Override
    public void playPracticeThirdUpdateUI() {

    }
}
