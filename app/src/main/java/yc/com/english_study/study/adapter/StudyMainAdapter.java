package yc.com.english_study.study.adapter;

import android.content.Context;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import yc.com.english_study.R;

/**
 * Created by wanglin  on 2018/10/25 17:12.
 */
public class StudyMainAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String[] mTitles;

    public StudyMainAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
        super(fm);
        this.fragments = fragmentList;
        mTitles = context.getResources().getStringArray(R.array.study_array);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
