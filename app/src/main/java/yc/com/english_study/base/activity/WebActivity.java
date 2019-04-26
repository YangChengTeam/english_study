package yc.com.english_study.base.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import yc.com.base.BaseActivity;
import yc.com.base.BasePresenter;
import yc.com.english_study.R;
import yc.com.english_study.databinding.ActivityWebBinding;

/**
 * Created by wanglin  on 2018/11/8 15:03.
 */
public class WebActivity extends BaseActivity<BasePresenter, ActivityWebBinding> {


    private String url = "http://en.upkao.com/";

    @Override
    public boolean isStatusBarMateria() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void init() {


        mDataBinding.commonWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mDataBinding.progressBar.getVisibility() == View.GONE) {
                    mDataBinding.progressBar.setVisibility(View.VISIBLE);
                }

                mDataBinding.progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mDataBinding.progressBar.setVisibility(View.GONE);
                }

            }


        });

        mDataBinding.setUrl(url);


    }


}
