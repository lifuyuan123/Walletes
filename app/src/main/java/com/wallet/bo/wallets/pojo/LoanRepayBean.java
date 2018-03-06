package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/10/24 002411:58
 * email:ggband520@163.com
 * desc:
 */

public class LoanRepayBean {

    private Integer BorrowState;//0/1判断是借款（0）还是还款（1）
    private String  OrderExpirationDate;//产品有效期
    private String loan;//借款期限
    private String  make_loans_time;//贷款时间
    private String money;//还款金额
    private String number;//借款单号
    private String img;
    private int state;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Integer getBorrowState() {
        return BorrowState;
    }

    public void setBorrowState(Integer borrowState) {
        BorrowState = borrowState;
    }

    public String getOrderExpirationDate() {
        return OrderExpirationDate;
    }

    public void setOrderExpirationDate(String orderExpirationDate) {
        OrderExpirationDate = orderExpirationDate;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getMake_loans_time() {
        return make_loans_time;
    }

    public void setMake_loans_time(String  make_loans_time) {
        this.make_loans_time = make_loans_time;
    }

    public String getMoney() {
        return money;
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

    @Override
    public String toString() {
        return "LoanRepayBean{" +
                "BorrowState=" + BorrowState +
                ", OrderExpirationDate='" + OrderExpirationDate + '\'' +
                ", loan='" + loan + '\'' +
                ", make_loans_time='" + make_loans_time + '\'' +
                ", money='" + money + '\'' +
                ", number='" + number + '\'' +
                ", img='" + img + '\'' +
                ", state=" + state +
                '}';
    }
}
