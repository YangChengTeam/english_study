package yc.com.english_study.study_1vs1.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;
import yc.com.base.BasePresenter;
import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.blankj.utilcode.util.ToastUtils;
import yc.com.blankj.utilcode.util.UIUitls;
import yc.com.english_study.R;
import yc.com.english_study.base.constant.Config;
import yc.com.english_study.base.constant.SpConstant;
import yc.com.english_study.base.httpinterface.HttpRequestInterface;
import yc.com.english_study.base.observer.BaseCommonObserver;
import yc.com.english_study.databinding.FragmentStudy1vs1Binding;
import yc.com.english_study.index.utils.UserInfoHelper;
import yc.com.english_study.mine.activity.PhoneticActivity;
import yc.com.english_study.pay.alipay.IAliPay1Impl;
import yc.com.english_study.pay.alipay.IPayCallback;
import yc.com.english_study.pay.alipay.IWXPay1Impl;
import yc.com.english_study.pay.alipay.LoadingDialog;
import yc.com.english_study.pay.alipay.OrderInfo;
import yc.com.english_study.study_1vs1.model.bean.SlideInfo;
import yc.com.rthttplibrary.request.RetrofitHttpRequest;
import yc.com.rthttplibrary.util.ToastUtil;

/**
 * Created by wanglin  on 2018/10/24 17:21.
 */
public class Study1vs1Fragment extends BaseFragment<BasePresenter, FragmentStudy1vs1Binding> {


    private String url = "http://m.upkao.com/ybzs.html";


    private Handler mHandler;
    private IAliPay1Impl iAliPay;
    private IWXPay1Impl iwxPay;
    private LoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study1vs1;
    }

    @Override
    public void init() {

        SlideInfo slideInfo = JSON.parseObject(SPUtils.getInstance().getString(SpConstant.INDEX_MENU_STATICS), SlideInfo.class);
        if (null != slideInfo) {
            url = slideInfo.getUrl();
        }

//        LogUtil.msg("url: init   "+url);
        mHandler = new Handler();
        mDataBinding.mainToolbar.init((BaseActivity) getActivity(), PhoneticActivity.class);
        mDataBinding.mainToolbar.setTvRightTitleAndIcon(getString(R.string.phonetic_introduce), R.mipmap.index_phogetic_introduce);
        iAliPay = new IAliPay1Impl(getActivity());
        iwxPay = new IWXPay1Impl(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        mDataBinding.webView.addJavascriptInterface(new JavascriptInterface(), "study");
        mDataBinding.setUrl(url);

        mDataBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                super.onProgressChanged(view, newProgress);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mDataBinding.progressBar.getVisibility() == View.GONE) {
                            mDataBinding.progressBar.setVisibility(View.VISIBLE);
                        }
                        mDataBinding.progressBar.setProgress(newProgress);
                        mDataBinding.webView.postInvalidate();
                        if (newProgress == 100) {
                            mDataBinding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }


    public class JavascriptInterface {

        @android.webkit.JavascriptInterface
        public void startQQChat(String qq) {
            try {
                String url3521 = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url3521)));
            } catch (Exception e) {
                ToastUtils.showShort("你的手机还未安装qq,请先安装");
            }

        }

        @android.webkit.JavascriptInterface
        public void pay(final String title, final String money, String paywayname, String id) {


            if (TextUtils.isEmpty(paywayname)) {
                paywayname = "alipay";
            }


            showLoading();
            final String finalPaywayname = paywayname;

            RetrofitHttpRequest.get(getActivity()).create(HttpRequestInterface.class)
                    .createOrder(1, paywayname, money, id, String.valueOf(7), UserInfoHelper.getUid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseCommonObserver<OrderInfo>(getActivity()) {
                        @Override
                        public void onSuccess(OrderInfo orderInfo, String message) {

                            if (orderInfo != null) {
                                orderInfo.setMoney(Float.parseFloat(money));
                                orderInfo.setName(title);
                                if (finalPaywayname.equals("alipay")) {
                                    iAliPay.pay(orderInfo, payCallBack);
                                } else {
                                    iwxPay.pay(orderInfo, payCallBack);
                                }

                            }
                        }

                        @Override
                        public void onFailure(int code, String errorMsg) {
                            ToastUtil.toast(getActivity(), errorMsg);
                        }

                        @Override
                        public void onRequestComplete() {

                        }
                    });


        }

        private IPayCallback payCallBack = new IPayCallback() {

            @Override
            public void onSuccess(OrderInfo orderInfo) {
                UserInfoHelper.saveVip(Config.PHONOGRAMORPHONICS_VIP + "");
                dismissPayDialog();

            }

            @Override
            public void onFailure(OrderInfo orderInfo) {
                dismissPayDialog();
            }
        };

        private void showLoading() {
            if (loadingDialog != null) {
                UIUitls.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.show("创建订单中，请稍候...");

                    }
                });
            }

        }

        private void dismissLoading() {
            UIUitls.post(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismiss();
                }
            });
        }

        private void dismissPayDialog() {

            UIUitls.post(new Runnable() {
                @Override
                public void run() {
                    mDataBinding.webView.loadUrl("javascript:hidePay()");
                }
            });
        }


    }


}
