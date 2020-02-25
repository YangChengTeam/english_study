package yc.com.english_study.study.listener;

/**
 * Created by wanglin  on 2018/11/1 15:48.
 */
public interface OnAVManagerListener {

    void playMusic(String musicUrl, boolean isOnce, int playStep);

    void playMusic(String musicUrl);

    void stopMusic();


    void startRecordAndSynthesis(String musicPath,String word, boolean isWord);

    void stopRecord();

    boolean isRecording();

    void playRecordFile(String musicPath);

    void playAssetFile(String assetFilePath,boolean isOnce, int step);

    boolean isPlaying();//是否在播放

    void destroy();

}
