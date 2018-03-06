package com.wallet.bo.wallets.pojo;

import java.util.List;

/**
 * author:pxb
 * date:2017/9/10 22:11
 * email:ggband520@163.com
 * des:钱包借款记录
 */
public class WalletLoanLog {


    /**
     * repaymentNumber :"XMQB10000000000000019923" //还款订单号
     * reimbursement : 0//待还款的金额
     * mayBorrowMoney : 500//可以借款的金额
     * ApiBorrowList : [{"number":"XMQB10000000000004","money":500,"time":"2017年09月08日 15:27","loan":7,"s":1,"refundmoney":0,"make_loans_time":"1970年01月01日 08:00","ss":0,"statename":"等待审核"}]
     */

    private String reimbursement;
    private String  mayBorrowMoney;
    private String repaymentNumber;

    private List<LoanLog> ApiBorrowList;

    public String getRepaymentNumber() {
        return repaymentNumber;
    }

    public void setRepaymentNumber(String repaymentNumber) {
        this.repaymentNumber = repaymentNumber;
    }

    public String getTotalMoner() {
        return mayBorrowMoney;//Float.parseFloat(reimbursement)+
    }


    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getMayBorrowMoney() {
        return mayBorrowMoney;
    }

    public void setMayBorrowMoney(String mayBorrowMoney) {
        this.mayBorrowMoney = mayBorrowMoney;
    }

    public List<LoanLog> getApiBorrowList() {
        return ApiBorrowList;
    }

    public void setApiBorrowList(List<LoanLog> apiBorrowList) {
        ApiBorrowList = apiBorrowList;
    }

    @Override
    public String toString() {
        return "WalletLoanLog{" +
                "reimbursement='" + reimbursement + '\'' +
                ", mayBorrowMoney=" + mayBorrowMoney +
                ", repaymentNumber='" + repaymentNumber + '\'' +
                ", ApiBorrowList=" + ApiBorrowList +
                '}';
    }


}
