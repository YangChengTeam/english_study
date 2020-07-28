package yc.com.english_study.base.constant;

/**
 * Created by wanglin  on 2018/10/25 14:04.
 */
public interface UrlConfig {

    boolean isDebug = true;
    String debug_url = "http://tic.upkao.com/Xmyinbiao/";
    String base_url = "";


    /**
     * 分享解锁接口
     */
    String SHARE_REWARD_URL = "share/reward";
    /**
     * 手机号密码登录
     */

    String PHONE_LOGIN = "user/login";
    /**
     * 发送验证码
     */

    String SEND_CODE = "user/send_code";
    /**
     * 手机号注册
     */

    String PHONE_REGISTER = "user/reg";
    /**
     * 修改密码
     */

    String MODIFY_PWD = "user/upd_pwd";


    /**
     * 验证码登录
     */
    String CODE_LOGIN = "user/code_login";

    /**
     * 设置密码
     */
    String SET_PWD = "user/set_pwd";

}
