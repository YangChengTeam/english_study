package yc.com.english_study.category.activity;

import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.jakewharton.rxbinding.view.RxView;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.xinqu.videoplayer.XinQuVideoPlayer;
import com.xinqu.videoplayer.XinQuVideoPlayerStandard;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.blankj.utilcode.util.LogUtils;
import yc.com.blankj.utilcode.util.NetworkUtils;
import yc.com.blankj.utilcode.util.SizeUtils;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.BusAction;
import yc.com.english_study.base.fragment.BasePayFragment;
import yc.com.english_study.category.contract.WeiKeDetailContract;
import yc.com.english_study.category.model.domain.CourseInfo;
import yc.com.english_study.category.presenter.WeiKeDetailPresenter;
import yc.com.english_study.databinding.CommonActivityWeikeDetailBinding;
import yc.com.english_study.index.model.domain.UserInfo;
import yc.com.english_study.index.utils.UserInfoHelper;

/**
 * Created by wanglin  on 2017/9/6 08:32.
 */

public class WeiKeDetailActivity extends BaseActivity<WeiKeDetailPresenter, CommonActivityWeikeDetailBinding> implements WeiKeDetailContract.View {

    private static final String TAG = "NewsDetailActivity";


    private String id;

    private long startTime;

    private CourseInfo currentCourseInfo;

    private UserInfo userInfo;
    private SensorManager mSensorManager;
    private XinQuVideoPlayer.XinQuAutoFullscreenListener mSensorEventListener;

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_weike_detail;
    }

    @Override
    public void init() {
        mPresenter = new WeiKeDetailPresenter(this, this);

        mDataBinding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        startTime = System.currentTimeMillis();


        if (getIntent() != null) {
            id = getIntent().getStringExtra("pid");
        }
        userInfo = UserInfoHelper.getUserInfo();
        mPresenter.getWeikeCategoryInfo(id);
        initListener();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensorEventListener = new XinQuVideoPlayer.XinQuAutoFullscreenListener();
        mDataBinding.mJCVideoPlayer.widthRatio = 16;
        mDataBinding.mJCVideoPlayer.heightRatio = 9;


    }


    @Override
    public void showLoading() {
        mDataBinding.stateView.showLoading(mDataBinding.llRootView);
    }

    @Override
    public void showNoData() {
        mDataBinding.stateView.showNoData(mDataBinding.llRootView);
    }

    @Override
    public void showNoNet() {
        mDataBinding.stateView.showNoNet(mDataBinding.llRootView, HttpConfig.NET_ERROR, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getWeikeCategoryInfo(id);
            }
        });
    }

    @Override
    public void hide() {
        mDataBinding.stateView.hide();
    }

    @Override
    public void showWeikeInfo(CourseInfo courseInfo) {

        initData(courseInfo);
    }


    private void initData(CourseInfo courseInfo) {

        if (courseInfo != null) {
            currentCourseInfo = courseInfo;

            mDataBinding.setCourseInfo(courseInfo);

            playVideo(courseInfo);
            initWebView(courseInfo);

        }
    }

    @Override
    public boolean isStatusBarMateria() {
        return false;
    }


    private void initListener() {
//        mToolbar.setOnItemClickLisener(new BaseToolBar.OnItemClickLisener() {
//            @Override
//            public void onClick() {
//                SharePopupWindow sharePopupWindow = new SharePopupWindow(NewsWeiKeDetailActivity.this);
//                sharePopupWindow.show(llRootView);
//            }
//        });

        RxView.clicks(mDataBinding.layoutBuyNow).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (currentCourseInfo != null) {
                    if (UserInfoHelper.getUserInfo() != null) {
                        currentCourseInfo.setUserId(UserInfoHelper.getUserInfo().getUid());
                        showBuyDialog();
                    }
                }
            }
        });

        RxView.clicks(mDataBinding.ivBack).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        RxView.clicks(mDataBinding.tvShare).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //分享
            }
        });
    }


    private void initWebView(final CourseInfo data) {

        final WebSettings webSettings = mDataBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//        webView.addJavascriptInterface(new JavascriptInterface(), "HTML");

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存 //优先使用缓存:
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setBlockNetworkImage(true);//设置是否加载网络图片 true 为不加载 false 为加载

//        String body = data.getInfo().getBody();
        mDataBinding.webView.loadDataWithBaseURL(null, data.getDesp(), "text/html", "utf-8", null);

        mDataBinding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:window.HTML.getContentHeight(document.getElementsByTagName('html')[0].scrollHeight);");

                mDataBinding.llRootView.setVisibility(View.VISIBLE);

                webSettings.setBlockNetworkImage(false);

                LogUtils.e("startTime-->" + (System.currentTimeMillis() - startTime));

                view.loadUrl("javascript:(function(){"
                        + "var imgs=document.getElementsByTagName(\"img\");"
                        + "for(var i=0;i<imgs.length;i++) " + "{"
                        + "  imgs[i].onclick=function() " + "{ "
                        + "    window.HTML.openImg(this.src); "
                        + "   }  " + "}" + "}())");

            }
        });
    }


    /**
     * 播放视频
     *
     * @param courseInfo
     */
    private void playVideo(CourseInfo courseInfo) {
        Glide.with(this).load(courseInfo.getImg()).thumbnail(0.1f).into(mDataBinding.mJCVideoPlayer.thumbImageView);

        mDataBinding.mJCVideoPlayer.setUp(courseInfo.getUrl(), XinQuVideoPlayer.SCREEN_WINDOW_LIST, false, null == courseInfo.getTitle() ? "" : courseInfo.getTitle());

        mDataBinding.mJCVideoPlayer.backButton.setVisibility(View.GONE);
        mDataBinding.mJCVideoPlayer.tinyBackImageView.setVisibility(View.GONE);


        if (judgeVip()) {
            if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_WIFI)
                mDataBinding.mJCVideoPlayer.startVideo();
            else {
                click();
            }
        } else {
            click();
        }


    }


    private boolean judgeVip() {
        boolean isPlay = false;

        if (UserInfoHelper.isPhonicsVip() || currentCourseInfo.getIs_vip() == 0) {
            isPlay = true;
        }

        if (isPlay) {
            mDataBinding.layoutBuyNow.setVisibility(View.GONE);
        } else {
            mDataBinding.layoutBuyNow.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mDataBinding.webView.getLayoutParams();
            layoutParams.bottomMargin = SizeUtils.dp2px(45);
            mDataBinding.webView.setLayoutParams(layoutParams);
        }

        return isPlay;
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.PAY_SUCCESS)
            }
    )
    public void paySuccess(String info) {
        judgeVip();
    }

    private void click() {
        RxView.clicks(mDataBinding.mJCVideoPlayer.startButton).throttleFirst(1000, TimeUnit.MICROSECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startOrBuy();
            }
        });
    }


    private void startOrBuy() {
        if (userInfo != null) {
            if (judgeVip()) {
                mDataBinding.mJCVideoPlayer.startVideo();
            } else {
                showBuyDialog();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();

        XinQuVideoPlayerStandard.releaseAllVideos();

        mSensorManager.unregisterListener(mSensorEventListener);
        XinQuVideoPlayerStandard.clearSavedProgress(this, null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDataBinding.webView != null && mDataBinding.llRootView != null) {
            mDataBinding.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mDataBinding.webView.clearHistory();

            mDataBinding.llRootView.removeView(mDataBinding.webView);
            mDataBinding.webView.destroy();
        }
    }


    @Override
    public void onBackPressed() {
        if (XinQuVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    //显示支付弹窗

    private void showBuyDialog() {
        BasePayFragment basePayFragment = new BasePayFragment();
        basePayFragment.show(getSupportFragmentManager(), "");
    }


}
