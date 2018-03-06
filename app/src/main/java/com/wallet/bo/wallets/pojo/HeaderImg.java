package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/10/20 002010:14
 * email:ggband520@163.com
 * desc:
 */

public class HeaderImg {

    private String title;
    private String url;
    private String img;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "HeaderImg{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
