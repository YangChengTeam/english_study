package yc.com.english_study.study.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import yc.com.base.BaseView;
import yc.com.english_study.R;
import yc.com.english_study.databinding.LayoutCollapsibleViewBinding;

/**
 * Created by wanglin  on 2018/11/12 08:46.
 * 可折叠布局
 */
public class CollapsibleView extends BaseView<LayoutCollapsibleViewBinding> {
    @BindView(R.id.tv_essentials_word)
    TextView tvEssentialsWord;
    @BindView(R.id.tv_essentials_word_soundmark)
    TextView tvEssentialsWordSoundmark;
    @BindView(R.id.tv_essentials_word_desp)
    TextView tvEssentialsWordDesp;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.ll_play)
    LinearLayout llPlay;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.iv_record_playback)
    ImageView ivRecordPlayback;
    @BindView(R.id.ll_record_playback)
    LinearLayout llRecordPlayback;
    @BindView(R.id.iv_speak_result)
    ImageView ivSpeakResult;
    @BindView(R.id.tv_result_hint)
    TextView tvResultHint;
    @BindView(R.id.layout_result)
    LinearLayout layoutResult;

    public CollapsibleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_collapsible_view;
    }
}
