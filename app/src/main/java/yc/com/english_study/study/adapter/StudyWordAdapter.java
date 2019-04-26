package yc.com.english_study.study.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import yc.com.english_study.R;
import yc.com.english_study.databinding.StudyWordItemBinding;
import yc.com.english_study.index.utils.BaseBindingAdapter;
import yc.com.english_study.index.utils.BaseBindingHolder;
import yc.com.english_study.study.model.domain.WordInfo;
import yc.com.english_study.BR;

/**
 * Created by wanglin  on 2018/10/31 11:37.
 */
public class StudyWordAdapter extends BaseBindingAdapter<WordInfo, StudyWordItemBinding, BaseBindingHolder<StudyWordItemBinding>> {

    private SparseArray<ImageView> sparseArray;

    private SparseArray<RelativeLayout> layoutSparseArray;

    public StudyWordAdapter(List<WordInfo> data) {
        super(R.layout.study_word_item, data);
        sparseArray = new SparseArray<>();
        layoutSparseArray = new SparseArray<>();
    }

    @Override
    protected void convert(BaseBindingHolder<StudyWordItemBinding> helper, WordInfo item) {

        helper.getBinding().setVariable(BR.wordInfo, item);
        helper.getBinding().executePendingBindings();

        helper.addOnClickListener(R.id.ll_play)
                .addOnClickListener(R.id.ll_record)
                .addOnClickListener(R.id.ll_record_playback);

        ImageView imageView = helper.getView(R.id.iv_loading);
        RelativeLayout relativeLayout = helper.getView(R.id.rl_action_container);

        int position = helper.getAdapterPosition();
        sparseArray.put(position, imageView);
        layoutSparseArray.put(position, relativeLayout);

        if (position == 0) {
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
