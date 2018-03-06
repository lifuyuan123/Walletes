package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * date:2017/8/23 9:49
 * email:ggband520@163.com
 * desc:征信
 */

public class Credit {
    /**
     * img : http://dai.moxtx.com//Public/puload/201708/1503556215.png
     * url : /Wap/Credit/operator
     * name : 京东
     * id : 1
     * standby : JD
     * type : 1
     * i : 0i;//认证状态 1：已认证 0：未认证 2：已认证未通过
     * tables : 1
     * ApiUrl :
     * ss 必须认证的功能
     */

    private String img;
    private String url;
    private String name;
    private String id;
    private String standby;
    private String type;
    private int i;
    private int tables;
    private String ApiUrl;
    private String ss;


    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getApiUrl() {
        return ApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        ApiUrl = apiUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }



}
