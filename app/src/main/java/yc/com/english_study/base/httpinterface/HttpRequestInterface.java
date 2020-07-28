package yc.com.english_study.base.httpinterface;

import android.content.Context;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import yc.com.english_study.base.constant.UrlConfig;
import yc.com.english_study.base.model.domain.GoodInfoWrapper;
import yc.com.english_study.category.model.domain.CourseInfo;
import yc.com.english_study.category.model.domain.WeiKeCategoryWrapper;
import yc.com.english_study.index.model.domain.IndexInfoWrapper;
import yc.com.english_study.index.model.domain.ShareInfo;
import yc.com.english_study.index.model.domain.UserInfo;
import yc.com.english_study.index.model.domain.UserInfoWrapper;
import yc.com.english_study.mine.model.bean.OrderInfoWrapper;
import yc.com.english_study.pay.alipay.OrderInfo;
import yc.com.english_study.study.model.domain.StudyInfoWrapper;
import yc.com.english_study.study.model.domain.StudyPages;
import yc.com.english_study.study.model.domain.StudyPhoneticInfoWrapper;
import yc.com.english_study.study.model.domain.VowelInfoWrapper;
import yc.com.english_study.study_1vs1.model.bean.IndexDialogInfoWrapper;
import yc.com.rthttplibrary.bean.ResultInfo;

/**
 * Created by suns  on 2020/7/28 08:55.
 */
public interface HttpRequestInterface {

    /**
     * 首页index
     */
    @POST("index/init")
    Observable<ResultInfo<IndexInfoWrapper>> getIndexInfo();

    /**
     * 微课主页
     */
    @FormUrlEncoded
    @POST("index/weike_list")
    Observable<ResultInfo<WeiKeCategoryWrapper>> getCategoryInfos(@Field("page") int page, @Field("page_size") int page_size, @Field("pid") String pid);

    /**
     * 微课列表
     */
    @FormUrlEncoded
    @POST("weike/weike_list")
    Observable<ResultInfo<WeiKeCategoryWrapper>> getWeiKeList(@Field("page") int page, @Field("page_size") int page_size, @Field("pid") String pid);


    /**
     * 微课详情
     */
    @FormUrlEncoded
    @POST("index/weike_info")
    Observable<ResultInfo<CourseInfo>> getWeikeCategoryInfo(@Field("id") String id, @Field("page") int page, @Field("page_size") int page_size);

    /**
     * vip信息
     */
    @POST("index/vip_list")
    Observable<ResultInfo<GoodInfoWrapper>> getVipInfoList();

    /**
     * 是否绑定手机号
     */
    @FormUrlEncoded
    @POST("index/is_bind_mobile")
    Observable<ResultInfo<String>> isBindPhone(@Field("user_id") String user_id);

    /**
     * 创建订单
     */
    @FormUrlEncoded
    @POST("orders/pay")
    Observable<ResultInfo<OrderInfo>> createOrder(@Field("goods_num") int goods_num, @Field("payway_name") String payway_name, @Field("money") String money, @Field("goods_id") String goods_id, @Field("app_id") String app_id, @Field("user_id") String user_id);

    /**
     * 学习列表
     */
    @POST("index/vowel_lists")
    Observable<ResultInfo<StudyPages>> getStudyPages();

    /**
     * 学习详情页
     */
    @FormUrlEncoded
    @POST("index/vowel_detail")
    Observable<ResultInfo<StudyInfoWrapper>> getStudyDetail(@Field("page") int page);

    /**
     * 音标入门数目
     */
    @POST("letters/count")
    Observable<ResultInfo<StudyPages>> getPhoneticPages();

    /**
     * 音标详情
     */
    @FormUrlEncoded
    @POST("letters/detail")
    Observable<ResultInfo<StudyPhoneticInfoWrapper>> getPhoneticDetail(@Field("page") int page);

    /**
     * 所有的元音数据
     */
    @POST("index/vowel_all")
    Observable<ResultInfo<VowelInfoWrapper>> getVowelInfos();

    /**
     * 所有入门单词集合
     */
    @POST("letters/all")
    Observable<ResultInfo<VowelInfoWrapper>> getPhoneticWordInfos();

    /**
     * 上传手机号码
     */
    @FormUrlEncoded
    @POST("index/upd_user_info")
    Observable<ResultInfo<String>> uploadPhone(@Field("mobile") String mobile);


    @POST("index/menu_adv")
    Observable<ResultInfo<IndexDialogInfoWrapper>> getIndexMenuInfo();

    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST("orders/orders_list")
    Observable<ResultInfo<OrderInfoWrapper>> getOrderInfoList(@Field("user_id") String user_id, @Field("page") int page, @Field("page_size") int page_size);


    /**
     * 获取分享内容
     */
    @POST("share/info")
    Observable<ResultInfo<ShareInfo>> getShareInfo();


    @POST(UrlConfig.SHARE_REWARD_URL)
    Observable<ResultInfo<String>> share();

    @FormUrlEncoded
    @POST(UrlConfig.PHONE_LOGIN)
    Observable<ResultInfo<UserInfoWrapper>> login(@Field("username") String username, @Field("pwd") String password);

    @FormUrlEncoded
    @POST(UrlConfig.SEND_CODE)
    Observable<ResultInfo<String>> sendCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST(UrlConfig.PHONE_REGISTER)
    Observable<ResultInfo<UserInfoWrapper>> register(@Field("mobile") String mobile, @Field("code") String code, @Field("pwd") String pwd);


    @FormUrlEncoded
    @POST(UrlConfig.MODIFY_PWD)
    Observable<ResultInfo<UserInfo>> modifyPwd(@Field("user_id") String user_id, @Field("pwd") String pwd, @Field("new_pwd") String newPwd);

    @FormUrlEncoded
    @POST(UrlConfig.CODE_LOGIN)
    Observable<ResultInfo<UserInfoWrapper>> codeLogin(@Field("mobile") String mobile, @Field("code") String code);

    @FormUrlEncoded
    @POST(UrlConfig.SET_PWD)
    Observable<ResultInfo<String>> setPwd(@Field("user_id") String user_id, @Field("new_pwd") String pwd);
}
