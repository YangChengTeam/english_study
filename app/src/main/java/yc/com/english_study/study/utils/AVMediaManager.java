package yc.com.english_study.study.utils;

import java.util.ArrayList;
import java.util.List;

import yc.com.english_study.study.listener.OnAVManagerListener;

/**
 * Created by wanglin  on 2018/11/5 10:46.
 */
public class AVMediaManager {

    private static AVMediaManager instance;
    private List<OnAVManagerListener> ppAudioManagers;

    private AVMediaManager() {
        ppAudioManagers = new ArrayList<>();
    }

    public static AVMediaManager getInstance() {
        synchronized (AVMediaManager.class) {
            if (instance == null) {
                synchronized (AVMediaManager.class) {
                    instance = new AVMediaManager();
                }

            }
        }
        return instance;

    }


    public void addAudioManager(OnAVManagerListener ppAudioManager) {
        ppAudioManagers.add(ppAudioManager);
    }


    public void releaseAudioManager() {
        for (OnAVManagerListener ppAudioManager : ppAudioManagers) {
            ppAudioManager.destroy();
        }
    }
}
