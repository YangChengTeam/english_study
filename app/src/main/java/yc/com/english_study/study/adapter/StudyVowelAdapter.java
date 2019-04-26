package yc.com.english_study.study.adapter;

import java.util.List;

import yc.com.english_study.BR;
import yc.com.english_study.R;
import yc.com.english_study.databinding.StudyVowelItemBinding;
import yc.com.english_study.index.utils.BaseBindingAdapter;
import yc.com.english_study.index.utils.BaseBindingHolder;
import yc.com.english_study.study.model.domain.WordInfo;

/**
 * Created by wanglin  on 2018/11/1 09:12.
 */
public class StudyVowelAdapter extends BaseBindingAdapter<WordInfo, StudyVowelItemBinding, BaseBindingHolder<StudyVowelItemBinding>> {
    public StudyVowelAdapter(List<WordInfo> data) {
        super(R.layout.study_vowel_item, data);
    }

    @Override
    protected void convert(BaseBindingHolder<StudyVowelItemBinding> helper, WordInfo item) {
        helper.getBinding().setVariable(BR.wordInfo, item);
        helper.getBinding().executePendingBindings();
    }


}
