package yc.com.english_study.study.model.domain;

import androidx.databinding.BaseObservable;

/**
 * Created by wanglin  on 2019/4/29 10:44.
 */
public class StudyPhoneticInfo extends BaseObservable {


    /**
     * id : 3
     * letters : Ee
     * letters_mp3 : http://tic.upkao.com/Upload/20190426/5cc2c9f5871b6.mp3
     * pronunciation : /e/
     * pronunciation_mp3 : http://tic.upkao.com/Upload/20190426/5cc2c9ff56273.mp3
     * word1 : egg
     * word1_pron :  /eg/
     * word2 : leg
     * word2_pron : /leg/
     * word1_pic : http://tic.upkao.com/Upload/20190429/5cc65a295704a.jpg
     * word2_pic : http://tic.upkao.com/Upload/20190429/5cc65a2d3bdfe.jpg
     * word1_mp3 : http://tic.upkao.com/Upload/20190426/5cc2c9e54fe6d.mp3
     * word2_mp3 : http://tic.upkao.com/Upload/20190426/5cc2c9eb936e4.mp3
     * memory : http://tic.upkao.com/Upload/20190426/5cc2ca150c396.jpg
     * sentence : http://tic.upkao.com/Upload/20190429/5cc663635c28b.jpg
     * sentence_pic : http://tic.upkao.com/Upload/20190429/5cc6636a087e5.jpg
     * tips : 不是所有的字母Ee在单词中都发/e/，Ee可以发/i:/，如she；也可以发/ɪ/或/ə/，如eleven /ɪˈlevn/，open /ˈəʊpən/等。
     */

    private String id;
    private String letters;
    private String letters_mp3;
    private String pronunciation;
    private String pronunciation_mp3;
    private String word1;
    private String word1_pron;
    private String word2;
    private String word2_pron;
    private String word1_pic;
    private String word2_pic;
    private String word1_mp3;
    private String word2_mp3;
    private String memory;
    private String sentence;
    private String sentence_pic;
    private String tips;

    private String fa;
    private String word1_hanzi;
    private String word2_hanzi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getLetters_mp3() {
        return letters_mp3;
    }

    public void setLetters_mp3(String letters_mp3) {
        this.letters_mp3 = letters_mp3;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getPronunciation_mp3() {
        return pronunciation_mp3;
    }

    public void setPronunciation_mp3(String pronunciation_mp3) {
        this.pronunciation_mp3 = pronunciation_mp3;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord1_pron() {
        return word1_pron;
    }

    public void setWord1_pron(String word1_pron) {
        this.word1_pron = word1_pron;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getWord2_pron() {
        return word2_pron;
    }

    public void setWord2_pron(String word2_pron) {
        this.word2_pron = word2_pron;
    }

    public String getWord1_pic() {
        return word1_pic;
    }

    public void setWord1_pic(String word1_pic) {
        this.word1_pic = word1_pic;
    }

    public String getWord2_pic() {
        return word2_pic;
    }

    public void setWord2_pic(String word2_pic) {
        this.word2_pic = word2_pic;
    }

    public String getWord1_mp3() {
        return word1_mp3;
    }

    public void setWord1_mp3(String word1_mp3) {
        this.word1_mp3 = word1_mp3;
    }

    public String getWord2_mp3() {
        return word2_mp3;
    }

    public void setWord2_mp3(String word2_mp3) {
        this.word2_mp3 = word2_mp3;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence_pic() {
        return sentence_pic;
    }

    public void setSentence_pic(String sentence_pic) {
        this.sentence_pic = sentence_pic;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getWord1_hanzi() {
        return word1_hanzi;
    }

    public void setWord1_hanzi(String word1_hanzi) {
        this.word1_hanzi = word1_hanzi;
    }

    public String getWord2_hanzi() {
        return word2_hanzi;
    }

    public void setWord2_hanzi(String word2_hanzi) {
        this.word2_hanzi = word2_hanzi;
    }
}
