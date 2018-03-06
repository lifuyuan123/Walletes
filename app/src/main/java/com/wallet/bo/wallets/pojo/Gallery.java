package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/9/12 001213:43
 * email:ggband520@163.com
 * desc:
 */

public class Gallery {

    /**
     * title : 链接2
     * img : http://dai.moxtx.com//Public/puload/201709/1505120793.png
     * url : www.baidu.com
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
        return "Gallery{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
