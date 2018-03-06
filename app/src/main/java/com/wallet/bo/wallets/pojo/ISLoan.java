package com.wallet.bo.wallets.pojo;

/**
 * author:pxb
 * date:2017/10/19 20:59
 * email:ggband520@163.com
 * des:
 */
public class ISLoan {


    /**
     * errorcode : 0
     * byborrow : 1
     * message : 用户基本信息、积分、认证通过
     */

    private int errorcode;
    private int byborrow;
    private String message;
    private String can_borrow;//0:借500   1：500-1000

    public String getCan_borrow() {
        return can_borrow;
    }

    public void setCan_borrow(String can_borrow) {
        this.can_borrow = can_borrow;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getByborrow() {
        return byborrow;
    }

    public void setByborrow(int byborrow) {
        this.byborrow = byborrow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ISLoan{" +
                "errorcode=" + errorcode +
                ", byborrow=" + byborrow +
                ", message='" + message + '\'' +
                ", can_borrow='" + can_borrow + '\'' +
                '}';
    }
}
