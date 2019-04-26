package yc.com.english_study.study.activity;

import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityPreviewPictureBinding;

/**
 * Created by wanglin  on 2018/11/5 16:03.
 */
public class PreviewActivity extends BaseActivity<BasePresenter, ActivityPreviewPictureBinding> {


    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_picture;
    }

    @Override
    public void init() {
        String img = getIntent().getStringExtra("img");

        Glide.with(this).load(img).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(mDataBinding.zoomImageView);
        mDataBinding.zoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
