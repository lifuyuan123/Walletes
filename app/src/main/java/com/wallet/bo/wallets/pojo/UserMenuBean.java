package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/10/30 003010:21
 * email:ggband520@163.com
 * desc:
 */

//我的  列表类集合
public class UserMenuBean {

    /**
     * id : 26
     * name : 个人信息
     * imgurl : http://dai.moxtx.com/
     * android_url : 2
     */

    private String id;
    private String name;
    private String imgurl;
    private String android_url;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    @Override
    public String toString() {
        return "UserMenuBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", android_url='" + android_url + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
