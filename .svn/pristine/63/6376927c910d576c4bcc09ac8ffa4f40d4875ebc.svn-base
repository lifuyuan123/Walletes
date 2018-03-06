package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/9/8 000813:57
 * email:ggband520@163.com
 * desc:积分
 */

public class JudmentLoanState {
    private final static int limitLine = 600;//借款分数底线 600
    int must_grade;//必须达到借款分数底线才能借款
    int no_must_grade;//选填分数
    int total;//总分
    boolean isLoan;//是否可借款


    public int getMust_grade() {
        return must_grade;
    }

    public void setMust_grade(int must_grade) {
        this.must_grade = must_grade;
    }

    public int getNo_must_grade() {
        return no_must_grade;
    }

    public void setNo_must_grade(int no_must_grade) {
        this.no_must_grade = no_must_grade;
    }

    public int getTotal() {
        return must_grade + no_must_grade;
    }


    public boolean isLoan() {
        return must_grade >= limitLine ? true : false;
    }

}
