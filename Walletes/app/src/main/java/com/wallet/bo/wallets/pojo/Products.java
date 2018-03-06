package com.wallet.bo.wallets.pojo;

import java.io.Serializable;

/**
 * author:ggband
 * date:2017/7/21 16:17
 * email:ggband520@163.com
 * desc:借款产品
 */

public class Products implements Serializable{


    /**
     * id : 14
     * title : 现金借款
     * img : /Public/puload/201707/1500537260.png
     * loantime : 立即到账
     * applycount : 1
     */

    private String id;
    private String title;
    private String img;
    private String loantime;
    private String applycount;
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLoantime() {
        return loantime;
    }

    public void setLoantime(String loantime) {
        this.loantime = loantime;
    }

    public String getApplycount() {
        return applycount;
    }

    public void setApplycount(String applycount) {
        this.applycount = applycount;
    }


    @Override
    public String toString() {
        return "Products{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", loantime='" + loantime + '\'' +
                ", applycount='" + applycount + '\'' +
                '}';
    }
}
