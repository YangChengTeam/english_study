package yc.com.english_study.study.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.FragmentStudyApplyBinding;
import yc.com.english_study.study.adapter.StudyWordAdapter;
import yc.com.english_study.study.listener.OnAVManagerListener;
import yc.com.english_study.study.listener.OnUIApplyControllerListener;
import yc.com.english_study.study.model.domain.WordInfo;
import yc.com.english_study.study.utils.AVManager;

/**
 * Created by wanglin  on 2018/10/26 14:14.
 */
public class StudyWordFragment extends BaseFragment<BasePresenter, FragmentStudyApplyBinding> implements OnUIApplyControllerListener {


    private StudyWordAdapter studyWordAdapter;

    private static final String TAG = "StudyWordFragment";

    private WordInfo currentInfo;
    private OnAVManagerListener mListener;
    private String parent;
    private ImageView playImg;
    private ImageView recordImg;
    private LinearLayout layoutResult;
    private ImageView ivSpeakResult;
    private TextView tvResultHint;
    private ImageView ivRecordPlayback;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_apply;
    }

    @Override
    public void init() {

        List<WordInfo> wordInfos = getArguments().getParcelableArrayList("studyInfo");
        parent = getArguments().getString("pos");
        if (wordInfos != null && wordInfos.size() > 0) {
            currentInfo = wordInfos.get(0);
        }
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataBinding.recyclerView.setHasFixedSize(true);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        studyWordAdapter = new StudyWordAdapter(wordInfos);
        mDataBinding.recyclerView.setAdapter(studyWordAdapter);
//        ItemDecorationHelper decor = new ItemDecorationHelper(getActivity(), 10, 10);
//        recyclerView.removeItemDecoration(decor);
//        if (isFirst) {
//            recyclerView.addItemDecoration(decor);
//            isFirst = false;
//        }
        initListener();
//        WakeLockUtils.acquireWakeLock(this);

    }


    private void initListener() {
        studyWordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                playImg = (ImageView) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.iv_play);
                layoutResult = (LinearLayout) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.layout_result);

                studyWordAdapter.startAnimation(position);
                studyWordAdapter.showActionContainer(position);

                resetState();
                view.setSelected(true);

                currentInfo = studyWordAdapter.getItem(position);
                startPlay();
            }
        });

        studyWordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                layoutResult = (LinearLayout) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.layout_result);
                currentInfo = studyWordAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.ll_play:
                        // TODO: 2018/11/1 播放
                        playImg = (ImageView) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.iv_play);
                        startPlay();
                        break;
                    case R.id.ll_record:

                        if (currentInfo == null) return false;
                        recordImg = (ImageView) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.iv_record);

                        ivSpeakResult = (ImageView) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.iv_speak_result);
                        tvResultHint = (TextView) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.tv_result_hint);
                        if (mListener.isRecording()) {
                            mListener.stopRecord();
                        } else {
                            mListener.startRecordAndSynthesis(currentInfo.getMp3(),currentInfo.getWord().replaceAll("#", "").trim(), true);
                        }
                        break;
                    case R.id.ll_record_playback://回放
                        if (currentInfo == null) return false;
                        ivRecordPlayback = (ImageView) adapter.getViewByPosition(mDataBinding.recyclerView, position, R.id.iv_record_playback);
                        mListener.playRecordFile(currentInfo.getMp3());
                        break;
                }

                return false;
            }
        });

    }


    private void startPlay() {
        if (currentInfo == null) return;
        mListener.playMusic(currentInfo.getMp3());
    }

    @Override
    public void fetchData() {
        super.fetchData();
        mListener = new AVManager(getActivity(), this, parent + "/word");

    }

    private void resetState() {
        int count = mDataBinding.recyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            mDataBinding.recyclerView.getChildAt(i).setSelected(false);
        }
    }


    @Override
    public void playBeforeUpdateUI() {
        studyWordAdapter.resetDrawable();
        layoutResult.setVisibility(View.GONE);
        playImg.setImageResource(R.mipmap.study_essentials_pause);
    }


    @Override
    public void playAfterUpdateUI() {
        playImg.setImageResource(R.mipmap.study_essentials_play);
    }

    @Override
    public void recordBeforeUpdateUI() {
        layoutResult.setVisibility(View.GONE);
        Glide.with(getActivity()).load(R.mipmap.study_essentials_record_stop).asGif().into(recordImg);
    }

    @Override
    public void recordAfterUpdataUI() {
        Glide.clear(recordImg);
        recordImg.setImageResource(R.mipmap.study_essentials_record_start);
    }

    @Override
    public void playRecordBeforeUpdateUI() {
        ivRecordPlayback.setImageResource(R.mipmap.study_essentials_playback_stop);
    }

    @Override
    public void playRecordAfterUpdateUI() {
        ivRecordPlayback.setImageResource(R.mipmap.study_essentials_playback_start);
    }

    @Override
    public void showEvaluateResult(String percent) {

        int result = (int) Float.parseFloat(percent);
        layoutResult.setVisibility(View.VISIBLE);
        if (result >= 60) {
            ivSpeakResult.setImageResource(R.mipmap.read_item_result_yes);
            tvResultHint.setText(String.format(getString(R.string.evaluating_good_result), result + ""));
        } else {
            ivSpeakResult.setImageResource(R.mipmap.listen_result_no);
            tvResultHint.setText("加油");
        }


    }

    @Override
    public void playErrorUpdateUI() {
        studyWordAdapter.resetDrawable();
    }


}
