package yc.com.english_study.index.model.domain;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import yc.com.english_study.BR;

/**
 * Created by wanglin  on 2018/10/29 09:31.
 */
public class ContactInfo extends BaseObservable {
    /**
     * tel : null
     * qq : null
     */

    private String tel;
    private String qq;
    private String weixin;

    @Bindable
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        notifyPropertyChanged(BR.tel);
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Bindable
    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
        notifyPropertyChanged(BR.weixin);

    }
}
