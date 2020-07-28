package yc.com.english_study.study.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.jakewharton.rxbinding.view.RxView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.functions.Action1;
import yc.com.base.BaseDialogFragment;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.BusAction;
import yc.com.english_study.category.utils.ItemDecorationHelper;
import yc.com.english_study.databinding.FragmentStudyVowelBinding;
import yc.com.english_study.index.model.domain.UserInfo;
import yc.com.english_study.index.utils.UserInfoHelper;
import yc.com.english_study.mine.activity.PayActivity;
import yc.com.english_study.mine.fragment.ShareFragment;
import yc.com.english_study.study.adapter.StudyVowelAdapter;
import yc.com.english_study.study.contract.StudyVowelContract;
import yc.com.english_study.study.model.domain.WordInfo;
import yc.com.english_study.study.presenter.StudyVowelPresenter;
import yc.com.rthttplibrary.util.ScreenUtil;

/**
 * Created by wanglin  on 2018/11/1 09:01.
 */
public class StudyVowelFragment extends BaseDialogFragment<StudyVowelPresenter, FragmentStudyVowelBinding> implements StudyVowelContract.View {


    private List<StudyVowelAdapter> vowelAdapterList;
    private int type;//1.音标入门 2.外教资深音标


    @Override
    protected float getWidth() {
        return 0.8f;
    }

    @Override
    public int getAnimationId() {
        return 0;
    }

    @Override
    public int getHeight() {
        return ScreenUtil.getHeight(getActivity()) * 7 / 10;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_vowel;

    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 1);
        }

        mPresenter = new StudyVowelPresenter(getActivity(), this);

        if (type == 1) {
            mPresenter.getPhoneticWordInfos();
        } else if (type == 2) {
            mPresenter.getVowelInfos();
        }

        initListener();


    }


    private void initListener() {
        RxView.clicks(mDataBinding.ivVowelClose).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                dismiss();
            }
        });
    }


    @Override
    public void shoVowelNewInfos(List<List<WordInfo>> wordInfoList) {
        if (wordInfoList != null) {
            vowelAdapterList = new ArrayList<>();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (List<WordInfo> wordInfos : wordInfoList) {
                RecyclerView recyclerView = new RecyclerView(getActivity());
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                final StudyVowelAdapter studyVowelAdapter = new StudyVowelAdapter(wordInfos);
                recyclerView.setAdapter(studyVowelAdapter);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.addItemDecoration(new ItemDecorationHelper(getActivity(), 10));
                vowelAdapterList.add(studyVowelAdapter);

                studyVowelAdapter.setOnItemClickListener((adapter, view, position) -> {
                    WordInfo wordInfo = studyVowelAdapter.getItem(position);
                    if (type == 1) {
                        //音标入门
                        if (UserInfoHelper.isShareSuccess() || UserInfoHelper.isPhonogramOrPhonicsVip() || wordInfo.getIs_vip() == 0) {
                            if (clickListener != null) {
                                clickListener.onClick(wordInfo.getPage());
                                dismiss();
                            }
                        } else {
                            ShareFragment shareFragment = new ShareFragment();
                            shareFragment.show(getChildFragmentManager(), "");
                        }

                    } else if (type == 2) {//资深外教
                        if (UserInfoHelper.isPhonogramVip() || wordInfo.getIs_vip() == 0) {
                            if (clickListener != null) {
                                clickListener.onClick(wordInfo.getPage());
                                dismiss();
                            }
                        } else {
                            if (UserInfoHelper.isLogin(getActivity()))
                                startActivity(new Intent(getActivity(), PayActivity.class));

                        }
                    }


                });

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.vowel_header_view, recyclerView, false);
                TextView tvTitle = view.findViewById(R.id.tv_title);
                tvTitle.setText(wordInfos.get(0).getType_text());
                studyVowelAdapter.addHeaderView(view);

                mDataBinding.llContainer.addView(recyclerView, layoutParams);
            }
        }
    }

    private onClickListener clickListener;

    public void setOnClickListener(onClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public interface onClickListener {
        void onClick(int pos);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.PAY_SUCCESS)
            }
    )
    public void paySuccess(String info) {
        if (vowelAdapterList != null) {
            for (StudyVowelAdapter studyVowelAdapter : vowelAdapterList) {
                studyVowelAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.SHARE_SUCCESS)
            }
    )
    public void shareSuccess(String info) {

        mPresenter.getPhoneticWordInfos();
//        if (vowelAdapterList != null) {
//            for (StudyVowelAdapter studyVowelAdapter : vowelAdapterList) {
//                studyVowelAdapter.notifyDataSetChanged();
//            }
//        }
    }
}
