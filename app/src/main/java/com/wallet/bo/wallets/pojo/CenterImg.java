package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/10/27 002714:47
 * email:ggband520@163.com
 * desc:
 */

//首页  秘籍/帮助中心
public class CenterImg {

    private String title;
    private String img;
    private String url;

    public String getTitles() {
        return title;
    }

    public void setTitles(String titles) {
        this.title = titles;
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
        return "CenterImg{" +
                "titles='" + title + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
