package yc.com.english_study.base.constant;

/**
 * Created by wanglin  on 2018/10/25 14:04.
 */
public interface UrlConfig {

    boolean isDebug = true;
    String debug_url = "http://tic.upkao.com/Xmyinbiao/";
    String base_url = "";

    /**
     * 首页index
     */
    String main_index_url = (isDebug ? debug_url : base_url) + "index/init";

    /**
     * 微课主页
     */
    String category_url = (isDebug ? debug_url : base_url) + "index/weike_list";

    /**
     * 微课详情
     */
    String weike_info_url = (isDebug ? debug_url : base_url) + "index/weike_info";


    /**
     * vip信息
     */
    String vip_info_url = (isDebug ? debug_url : base_url) + "index/vip_list";

    /**
     * 创建订单
     */
    String pay_url = (isDebug ? debug_url : base_url) + "orders/pay";

    /**
     * 学习列表
     */
    String study_list_url = (isDebug ? debug_url : base_url) + "index/vowel_lists";

    /**
     * 学习详情页
     */
    String study_detail_url = (isDebug ? debug_url : base_url) + "index/vowel_detail";

    /**
     * 所有的元音数据
     */
    String vowel_all_url = (isDebug ? debug_url : base_url) + "index/vowel_all";

    /**
     * 上传手机号码
     */
    String upd_info_url = (isDebug ? debug_url : base_url) + "index/upd_user_info";

    /**
     * 是否绑定手机号
     */
    String is_bind_mobile_url = (isDebug ? debug_url : base_url) + "index/is_bind_mobile";


    String index_menu_url = (isDebug ? debug_url : base_url) + "index/menu_adv";

    /**
     * 微课列表
     */
    String weike_list_url = (isDebug ? debug_url : base_url) + "weike/weike_list";

    /**
     * 订单列表
     */
    String order_list = (isDebug ? debug_url : base_url) + "orders/orders_list";

    /**
     * 音标入门数目
     */
    String phonetic_count_url = (isDebug ? debug_url : base_url) + "letters/count";

    /**
     * 音标详情
     */
    String phonetic_detail_url = (isDebug ? debug_url : base_url) + "letters/detail";

    /**
     * 所有入门单词集合
     */
    String phonetic_word_all = (isDebug ? debug_url : base_url) + "letters/all";


    /**
     * 获取分享内容
     */
    String share_info_url = (isDebug ? debug_url : base_url) + "share/info";

    /**
     * 分享解锁接口
     */
    String share_reward_url = (isDebug ? debug_url : base_url) + "share/reward";

}
