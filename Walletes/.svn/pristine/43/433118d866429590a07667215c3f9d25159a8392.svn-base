package com.wallet.bo.wallets.pojo;

import com.wallet.bo.wallets.Utils.TextUtils;

import java.io.Serializable;

/**
 * author:ggband
 * data:2017/9/11 001116:17
 * email:ggband520@163.com
 * desc:还款详情
 */

public class RepayDetail implements Serializable{


    /**
     * make_loans_time : 1504195200  放款时间
     * account : 6214850285682073  银行卡号
     * phone : 15390089473  手机号
     * loan : 7  借款天数
     * money : 0.09  借款金额
     * number : XMQB10000000000000019925 订单编号
     * ApiBorrowInfo : 0.009 利息
     * LateFee : 0.0054 逾期费用
     * daysOverdue : (逾期3天)逾期时长
     * bankname:银行卡名字
     */

    private String make_loans_time;
    private String account;
    private String phone;
    private String loan;
    private String money;
    private String number;
    private double ApiBorrowInfo;
    private double LateFee;
    private String daysOverdue;
    private String bankname;


    public String getRepayTotal() {
        return String.valueOf(TextUtils.decimalTwo(Float.parseFloat(money)  + (float) LateFee));
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getMake_loans_time() {
        return make_loans_time;
    }

    public void setMake_loans_time(String make_loans_time) {
        this.make_loans_time = make_loans_time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getMoney() {
        return TextUtils.decimalTwo(Float.parseFloat(money));
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getApiBorrowInfo() {
        return TextUtils.decimalTwo((float) ApiBorrowInfo);
    }

    public void setApiBorrowInfo(double ApiBorrowInfo) {
        this.ApiBorrowInfo = ApiBorrowInfo;
    }

    public String getLateFee() {
        return TextUtils.decimalTwo((float) LateFee);
    }

    public void setLateFee(double LateFee) {
        this.LateFee = LateFee;
    }

    public String getDaysOverdue() {
        return daysOverdue;
    }

    public void setDaysOverdue(String daysOverdue) {
        this.daysOverdue = daysOverdue;
    }
}
