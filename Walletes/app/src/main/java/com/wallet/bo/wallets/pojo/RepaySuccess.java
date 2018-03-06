package com.wallet.bo.wallets.pojo;

import java.io.Serializable;

/**
 * author:pxb
 * date:2017/9/12 22:58
 * email:ggband520@163.com
 * des:还款成功
 */
public class RepaySuccess implements Serializable{

    private String orderMoney;//订单金额

    private String repayMoney;//还款金额

    private String repayType;//还款方式


    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(String repayMoney) {
        this.repayMoney = repayMoney;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }
}
