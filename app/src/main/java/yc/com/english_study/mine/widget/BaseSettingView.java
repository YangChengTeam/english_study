package yc.com.english_study.mine.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import yc.com.base.BaseView;
import yc.com.english_study.R;

/**
 * Created by wanglin  on 2019/4/23 14:28.
 */
public class BaseSettingView extends BaseView {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_centet_tile)
    TextView tvCentetTile;
    private String title;
    private Drawable mDrawable;

    public BaseSettingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseSettingView);
        try {
            title = ta.getString(R.styleable.BaseSettingView_settingTitle);
            mDrawable = ta.getDrawable(R.styleable.BaseSettingView_settingIcon);

            if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
            if (mDrawable != null) ivIcon.setImageDrawable(mDrawable);


        } finally {
            ta.recycle();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_setting_view;
    }

    public void setCentTitle(CharSequence charSequence) {
        tvCentetTile.setText(charSequence);
    }
}
