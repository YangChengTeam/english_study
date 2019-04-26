package yc.com.english_study.index.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import yc.com.base.BaseActivity;
import yc.com.english_study.base.widget.BaseToolBar;
import yc.com.english_study.study.widget.MediaPlayerView;

/**
 * Created by wanglin  on 2019/4/20 10:49.
 */
public class ViewAttrBinding {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter(value = {"app:imageUrl", "app:placeHolder", "app:error"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, int holderDrawable, int errorDrawable) {
        BitmapTypeRequest<String> request = Glide.with(imageView.getContext())
                .load(url).asBitmap();
        if (holderDrawable != 0) {
            request.placeholder(holderDrawable);
        }
        if (errorDrawable != 0) {
            request.error(errorDrawable);
        }
        request.diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(true).into(imageView);
    }

    @BindingAdapter({"app:mediaPath"})
    public static void setPath(MediaPlayerView mediaPlayerView, String path) {
        mediaPlayerView.setPath(path);
    }

    @BindingAdapter({"app:webViewUrl"})
    public static void loadWebView(WebView webView, String path) {
        webView.loadUrl(path);
    }

    @BindingAdapter(value = {"app:toolBarTitle", "app:toolBarBack", "app:toolBarShowRight", "app:toolBarIsAct"}, requireAll = false)
    public static void setToolBar(BaseToolBar toolBar, String title, boolean isShowNavigation, boolean showRight, boolean isAct) {
        if (isAct) {
            if (!TextUtils.isEmpty(title)) toolBar.setTitle(title);
            if (isShowNavigation) {
                toolBar.showNavigationIcon();
                toolBar.init((BaseActivity) toolBar.getContext());
            }
            toolBar.setRightContainerVisible(showRight);
        }
    }

    @BindingAdapter(value = {"app:images"})
    public static void setBannerImage(Banner banner, List<Integer> images) {

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);

        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


}
