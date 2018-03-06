package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/11/15 001514:16
 * email:ggband520@163.com
 * desc:提现界面实体类
 */

public class WithdrawalsBean {

    private String number;//订单号
    private String time;//申请时间
    private String refundmoney;//借款money
    private String bankname;//银行名
    private String account;//银行卡号

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRefundmoney() {
        return refundmoney;
    }

    public void setRefundmoney(String refundmoney) {
        this.refundmoney = refundmoney;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "WithdrawalsBean{" +
                "number='" + number + '\'' +
                ", time='" + time + '\'' +
                ", refundmoney='" + refundmoney + '\'' +
                ", bankname='" + bankname + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
