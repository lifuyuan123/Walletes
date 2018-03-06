package com.wallet.bo.wallets.pojo;

import java.io.Serializable;

/**
 * author:ggband
 * date:2017/9/4 9:16
 * email:ggband520@163.com
 * desc:借款记录
 */

public class LoanLog implements Serializable {

    /**
     * number : XMQB1503306749 借款订单编号
     * money : 0.01  申请金额
     * time : 1503306744  借款时间
     * loan : 14 借款期限
     * s : 9  数字状态 0:审核失败， 1：等待审核，2：审核成功未提现，3：审核成功已提现，4：已到期未还款，7：待提现，8：还款失败，9：还款成功，default:方法订单
     * refundmoney : 613.9192 应该还款金额
     * make_loans_time : null  放款时间
     * statename : 还款成功  借款状态
     * make_loans_state//1显示还款  1：还款金额  0：借款 按钮是否可点击（还款）
     * order_status ：借款状态  （0审核失败、1等待审核、2审核成功未提现、3审核成已放款、4已到期未还款、5以申请付款待回复、7待提现、8还款失败、9还款成功、10 已取消）
     */

    private String number;
    private String money;
    private String time;
    private String loan;
    private String s;
    private String refundmoney;
    private String make_loans_time;
    private String statename;
    private int order_status;
    private int make_loans_state;
    private int id;
    private String repayment_time;
    private boolean isRefresh;//是否刷新


    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public String getRepayment_time() {
        return repayment_time;
    }

    public void setRepayment_time(String repayment_time) {
        this.repayment_time = repayment_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMake_loans_state(int make_loans_state) {
        this.make_loans_state = make_loans_state;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getRefundmoney() {
        return refundmoney;
    }

    public void setRefundmoney(String refundmoney) {
        this.refundmoney = refundmoney;
    }

    public String getMake_loans_time() {
        return make_loans_time;
    }

    public void setMake_loans_time(String make_loans_time) {
        this.make_loans_time = make_loans_time;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public boolean getMake_loans_state() {
        return make_loans_state == 1 ? true : false;
    }

}
