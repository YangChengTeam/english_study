package yc.com.english_study.study.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by wanglin  on 2019/4/29 09:47.
 */
public class StudyViewPager extends ViewPager {
    public StudyViewPager(Context context) {
        super(context);
    }

    public StudyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
