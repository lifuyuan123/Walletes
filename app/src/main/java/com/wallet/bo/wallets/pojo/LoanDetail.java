package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * date:2017/9/4 10:31
 * email:ggband520@163.com
 * desc://借款详情
 */

public class LoanDetail {


    /**
     * bankname : 招商银行
     * account : 6214*********2073
     * phone : 153****9473
     * make_loans_time : 1987年03月24日 08:20 放款时间
     * refundmoney : 0 应该还款金额
     * loan : 7 借款天数
     * s : 9
     * money : 54  借款金额
     * number : MX643278592   订单编号
     * time : 1972年02月01日 06:40 订单生成时间
     * info 借款信息
     * ss : 0
     * statename : 还款成功
     */

    private String em = "";

    private String bankname;
    private String account;
    private String phone;
    private String make_loans_time;
    private String refundmoney;
    private int loan;
    private int s;
    private String money;
    private String number;
    private String time;
    private int ss;
    private String statename;
    private MoneyCalculate info;
    private int make_loans_state;
    private String repayment_time;
    private int day;//逾期天数

    private int overdueFormula;//逾期金额

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getOverdueFormula() {
        return overdueFormula;
    }

    public void setOverdueFormula(int overdueFormula) {
        this.overdueFormula = overdueFormula;
    }

    public String getRepayment_time() {
        return repayment_time;
    }

    public void setRepayment_time(String repayment_time) {
        this.repayment_time = repayment_time;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public boolean getMake_loans_state() {
        return make_loans_state == 1 ? true : false;
    }

    public void setMake_loans_state(int make_loans_state) {
        this.make_loans_state = make_loans_state;
    }

    public MoneyCalculate getInfo() {
        return info;
    }

    public void setInfo(MoneyCalculate info) {
        this.info = info;
    }

    public String getBankname() {
        return bankname == null ? em : bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccount() {
        return account == null ? em : account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone == null ? em : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMake_loans_time() {
        return make_loans_time == null ? em : make_loans_time;
    }

    public void setMake_loans_time(String make_loans_time) {
        this.make_loans_time = make_loans_time;
    }

    public String getRefundmoney() {
        return refundmoney;
    }

    public void setRefundmoney(String refundmoney) {
        this.refundmoney = refundmoney;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNumber() {
        return number == null ? em : number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time == null ? em : time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }

    public String getStatename() {
        return statename == null ? em : statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }


}
