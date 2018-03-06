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
    String msg;
    int isjump;//0 是没有认证     1 可以立即借款      2 芝麻积分小于判断积分

    private String  prompt;//提示
    private String  describe;//描述

    public int getIsjump() {
        return isjump;
    }

    public void setIsjump(int isjump) {
        this.isjump = isjump;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "JudmentLoanState{" +
                "must_grade=" + must_grade +
                ", no_must_grade=" + no_must_grade +
                ", total=" + total +
                ", isLoan=" + isLoan +
                ", msg='" + msg + '\'' +
                ", isjump=" + isjump +
                ", prompt='" + prompt + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
