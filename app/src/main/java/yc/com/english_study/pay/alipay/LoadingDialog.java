package yc.com.english_study.pay.alipay;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import yc.com.english_study.R;
import yc.com.rthttplibrary.view.BaseLoadingView;


/**
 * Created by zhangkai on 2017/2/21.
 */

public class LoadingDialog extends Dialog implements BaseLoadingView {

    ImageView ivCircle;
    TextView tvMsg;

    public LoadingDialog(Context context) {

        super(context, R.style.customDialog);
        View view = LayoutInflater.from(context).inflate(
                getLayoutID(), null);
        this.setContentView(view);

        this.setCancelable(true);
        ivCircle = (ImageView) view.findViewById(R.id.iv_circle);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
    }

    public void show(String msg) {
        super.show();
        ivCircle.startAnimation(AnimationUtil.rotaAnimation());
        tvMsg.setText(msg);
    }


    @Override
    protected void onStop() {
        super.onStop();


    }

    public int getLayoutID() {
        return R.layout.dialog_loading;
    }

    @Override
    public void showLoading() {
        ivCircle.startAnimation(AnimationUtil.rotaAnimation());
        super.show();
    }

    @Override
    public void hideLoading() {
        ivCircle.clearAnimation();
        dismiss();
    }

    public void setText(String msg) {
        tvMsg.setText(msg);
    }
}
