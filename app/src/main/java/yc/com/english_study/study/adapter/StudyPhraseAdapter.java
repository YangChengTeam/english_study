package yc.com.english_study.study.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import yc.com.english_study.BR;
import yc.com.english_study.R;
import yc.com.english_study.databinding.StudyPhraseItemBinding;
import yc.com.english_study.index.utils.BaseBindingAdapter;
import yc.com.english_study.index.utils.BaseBindingHolder;
import yc.com.english_study.study.model.domain.PhraseInfo;

/**
 * Created by wanglin  on 2018/10/31 14:28.
 */
public class StudyPhraseAdapter extends BaseBindingAdapter<PhraseInfo, StudyPhraseItemBinding,BaseBindingHolder<StudyPhraseItemBinding>> {
    private SparseArray<ImageView> sparseArray;
    private SparseArray<RelativeLayout> layoutSparseArray;

    public StudyPhraseAdapter(List<PhraseInfo> data) {
        super(R.layout.study_phrase_item, data);
        sparseArray = new SparseArray<>();
        layoutSparseArray = new SparseArray<>();
    }

    @Override
    protected void convert(BaseBindingHolder<StudyPhraseItemBinding> helper, PhraseInfo item) {


        helper.getBinding().setVariable(BR.phraseInfo,item);
        helper.getBinding().executePendingBindings();

        helper.addOnClickListener(R.id.ll_play)
                .addOnClickListener(R.id.ll_record)
                .addOnClickListener(R.id.ll_record_playback);

        int adapterPosition = helper.getAdapterPosition();
        sparseArray.put(adapterPosition, (ImageView) helper.getView(R.id.iv_loading));
        layoutSparseArray.put(adapterPosition, (RelativeLayout) helper.getView(R.id.rl_action_container));
        if (adapterPosition == 0) {
            helper.setVisible(R.id.rl_action_container, true);
        }
    }


    public void startAnimation(int position) {
        resetDrawable();
        if (sparseArray != null) {
            ImageView imageView = sparseArray.get(position);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            imageView.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
    }


    public void resetDrawable() {
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                ImageView imageView = sparseArray.get(i);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                animationDrawable.stop();
                imageView.setVisibility(View.GONE);
            }
        }
    }

    private void hideActionContainer() {
        if (layoutSparseArray != null) {
            for (int i = 0; i < layoutSparseArray.size(); i++) {
                layoutSparseArray.get(i).setVisibility(View.GONE);
            }
        }
    }

    public void showActionContainer(int position) {
        hideActionContainer();
        if (layoutSparseArray != null) {
            layoutSparseArray.get(position).setVisibility(View.VISIBLE);
        }
    }
}
