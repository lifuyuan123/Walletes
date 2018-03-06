package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/10/27 002714:49
 * email:ggband520@163.com
 * desc:
 */

public class ButImag {


    /**
     * title : button_img
     * img : http://dai.moxtx.com/
     * url :
     */

    private String title;
    private String img;
    private String url;
    private int state;
    private  String text1;
    private String text2;
    private String text3;


    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ButImag{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", state=" + state +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3='" + text3 + '\'' +
                '}';
    }
}
