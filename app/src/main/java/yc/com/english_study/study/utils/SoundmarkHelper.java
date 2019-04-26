package yc.com.english_study.study.utils;

import com.alibaba.fastjson.JSON;
import com.kk.utils.LogUtil;

import java.util.List;

import yc.com.blankj.utilcode.util.SPUtils;
import yc.com.english_study.base.constant.SpConstant;
import yc.com.english_study.study.model.domain.WordInfo;

/**
 * Created by wanglin  on 2018/11/9 11:45.
 */
public class SoundmarkHelper {

    private static List<WordInfo> wordInfos;

    public static List<WordInfo> getWordInfos() {
        if (wordInfos != null) {
            return wordInfos;
        }
        try {
            String str = SPUtils.getInstance().getString(SpConstant.SOUND_INFO);

            wordInfos = JSON.parseArray(str, WordInfo.class);
        } catch (Exception e) {
            LogUtil.msg("parse json error->" + e.getMessage());
        }

        return wordInfos;
    }

    public static void setWordInfos(List<WordInfo> wordInfos) {
        SoundmarkHelper.wordInfos = wordInfos;
        try {
            String str = JSON.toJSONString(wordInfos);
            SPUtils.getInstance().put(SpConstant.SOUND_INFO, str);

        } catch (Exception e) {
            LogUtil.msg("to json error-> " + e.getMessage());
        }
    }
}
