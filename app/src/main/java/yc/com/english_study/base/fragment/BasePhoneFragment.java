package yc.com.english_study.base.fragment;

import com.jakewharton.rxbinding.view.RxView;
import com.kk.utils.ScreenUtil;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BaseDialogFragment;
import yc.com.english_study.R;
import yc.com.english_study.base.contract.BasePhoneContract;
import yc.com.english_study.base.presenter.BasePhonePresenter;
import yc.com.english_study.databinding.FragmentBasePhoneBinding;

/**
 * Created by wanglin  on 2018/11/1 11:11.
 */
public class BasePhoneFragment extends BaseDialogFragment<BasePhonePresenter, FragmentBasePhoneBinding> implements BasePhoneContract.View {


    @Override
    protected float getWidth() {
        return 0.8f;
    }

    @Override
    public int getAnimationId() {
        return 0;
    }

    @Override
    public int getHeight() {
        return ScreenUtil.getHeight(getActivity()) * 3 / 10;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_phone;
    }

    @Override
    public void init() {

        mPresenter = new BasePhonePresenter(getActivity(), this);
        RxView.clicks(mDataBinding.ivSubmit).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String phone = mDataBinding.etPhone.getText().toString().trim();
                mPresenter.uploadPhone(phone);

            }
        });

        RxView.clicks(mDataBinding.ivClose).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                dismiss();
            }
        });

    }

    @Override
    public void showLoadingDialog(String mess) {
        ((BaseActivity) getActivity()).showLoadingDialog(mess);
    }

    @Override
    public void dismissDialog() {
        ((BaseActivity) getActivity()).dismissDialog();
    }


    @Override
    public void showUploadSuccess() {
        dismiss();
    }
}
