package yc.com.english_study.study.utils;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.Map;

import yc.com.english_study.pay.HttpUtils;
import yc.com.english_study.pay.PayListener;
import yc.com.english_study.pay.XMLUtil;
import yc.com.english_study.pay.alipay.OrderInfo;
import yc.com.english_study.pay.alipay.PayInfo;
import yc.com.rthttplibrary.util.LogUtil;

/**
 * Created by wanglin  on 2018/11/5 16:20.
 */
public class EngineUtils {

    private static String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

//    public static Observable<ResultInfo<OrderInfo>> createOrder(Context context, int goods_num, String payway_name, String money, String goods_id) {
//        Map<String, String> params = new HashMap<>();
//        params.put("user_id", UserInfoHelper.getUid());
//        params.put("goods_num", goods_num + "");
//        params.put("payway_name", payway_name);
//        params.put("app_id", String.valueOf(7));
//        params.put("money", money);
//        params.put("goods_id", goods_id);
////        params.put("goods_list", JSON.toJSONString(goods_list));
//        return HttpCoreEngin.get(context).rxpost(UrlConfig.pay_url, new TypeReference<ResultInfo<OrderInfo>>() {
//        }.getType(), params, true, true, true);
//
//    }

    public static void createOrder(final String name, final String money, final PayListener payListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {


//                    String result = HttpUtils.submitPostData(payUrl, xml, "utf-8");

                String result = HttpUtils.submitPostData(payUrl, HttpUtils.init(HttpUtils.wechatParamMap("wx39c1238171bf9430", name, money)), "utf-8");


                LogUtil.msg("result: " + result);

                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setName(name);
                orderInfo.setMoney(Float.parseFloat(money));
                PayInfo payInfo = new PayInfo();
                try {
                    Map<String, String> map = XMLUtil.doXMLParse(result);
                    payInfo.setAppid(map.get("appid"));
                    payInfo.setMch_id(map.get("mch_id"));
                    payInfo.setNonce_str(map.get("nonce_str"));
                    payInfo.setSign(map.get("sign"));
                    payInfo.setResult_code(map.get("result_code"));
                    payInfo.setPrepay_id(map.get("prepay_id"));
                    payInfo.setTrade_type(map.get("trade_type"));
                    payInfo.setTimestamp(System.currentTimeMillis() + "");
                    orderInfo.setPayInfo(payInfo);
                    payListener.onPayResult(orderInfo);

                    LogUtil.msg(map.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }

    //用Pull方式解析XML
    private static PayInfo parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            //设置输入的内容
            xmlPullParser.setInput(new StringReader(xmlData));
            //获取当前解析事件，返回的是数字
            int eventType = xmlPullParser.getEventType();
            //保存内容
            String appid = "";
            String mch_id = "";
            String nonce_str = "";
            String sign = "";
            String result_code = "";
            String prepay_id = "";
            String trade_type = "";

            PayInfo payInfo = new PayInfo();
            while (eventType != (XmlPullParser.END_DOCUMENT)) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析XML
                    case XmlPullParser.START_TAG: {
                        //nextText()用于获取结点内的具体内容
                        if ("appid".equals(nodeName)) {
                            appid = xmlPullParser.nextText();
                            payInfo.setAppid(appid);
                        } else if ("mch_id".equals(nodeName)) {
                            mch_id = xmlPullParser.nextText();
                            payInfo.setMch_id(mch_id);
                        } else if ("nonce_str".equals(nodeName)) {
                            nonce_str = xmlPullParser.nextText();
                            payInfo.setNonce_str(nonce_str);
                        } else if ("sign".equals(nodeName)) {
                            sign = xmlPullParser.nextText();
                            payInfo.setSign(sign);
                        } else if ("result_code".equals(nodeName)) {
                            result_code = xmlPullParser.nextText();
                            payInfo.setResult_code(result_code);
                        } else if ("prepay_id".equals(nodeName)) {
                            prepay_id = xmlPullParser.nextText();
                            payInfo.setPrepay_id(prepay_id);
                        } else if ("trade_type".equals(nodeName)) {
                            trade_type = xmlPullParser.nextText();
                            payInfo.setTrade_type(trade_type);
                        }
                    }
                    break;
                    //结束解析
                    case XmlPullParser.END_TAG: {
                        if ("xml".equals(nodeName)) {
                            Log.d("TAG", "parseXMLWithPull: id is " + appid);
                            Log.d("TAG", "parseXMLWithPull: name is " + mch_id);
                            Log.d("TAG", "parseXMLWithPull: version is " + nonce_str);
                        }
                    }
                    break;
                    default:
                        break;
                }
                //下一个
                eventType = xmlPullParser.next();
                return payInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
