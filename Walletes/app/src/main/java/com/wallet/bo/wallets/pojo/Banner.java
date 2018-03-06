package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * date:2017/7/20 12:28
 * email:ggband520@163.com
 * desc:
 */

public class Banner {


    /**
     * title : 现金白卡
     * img :
     * url : null
     */

    private String title;
    private String img;
    private String url;

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
        return "Banner{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
