package com.wallet.bo.wallets.pojo;

import com.wallet.bo.wallets.Utils.TextUtils;

import java.math.BigDecimal;

/**
 * author:ggband
 * date:2017/8/18 16:34
 * email:ggband520@163.com
 * desc:借款费用
 */

public class MoneyCalculate {


    /**
     * day : 14
     * money : 10000
     * creditaduit : 396
     * interest : 22
     * administrativefee : 132
     * refundmoney : 10550
     * <p>
     * creditaduit : 信审费用。
     * interest：利息
     * administrativefee：账号管理费
     * refundmoney：到期应还
     * ace
     */
    private BigDecimal em = new BigDecimal(0);
    private String day;
    private String money;
    private BigDecimal creditaduit;
    private BigDecimal interest;
    private BigDecimal administrativefee;
    private BigDecimal refundmoney;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public BigDecimal getCreditaduit() {
        return creditaduit == null ? em : creditaduit;
    }

    public void setCreditaduit(BigDecimal creditaduit) {
        this.creditaduit = creditaduit;
    }

    public BigDecimal getInterest() {
        return interest == null ? em : interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getAdministrativefee() {
        return administrativefee == null ? em : administrativefee;
    }

    public void setAdministrativefee(BigDecimal administrativefee) {
        this.administrativefee = administrativefee;
    }

    public BigDecimal getRefundmoney() {
        return refundmoney == null ? em : refundmoney;
    }

    public void setRefundmoney(BigDecimal refundmoney) {
        this.refundmoney = refundmoney;
    }

    @Override
    public String toString() {
        return "MoneyCalculate{" +
                "day='" + day + '\'' +
                ", money='" + money + '\'' +
                ", creditaduit=" + creditaduit +
                ", interest=" + interest +
                ", administrativefee=" + administrativefee +
                ", refundmoney=" + refundmoney +
                '}';
    }
}
