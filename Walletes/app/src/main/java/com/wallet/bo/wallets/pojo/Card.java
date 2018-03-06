package com.wallet.bo.wallets.pojo;

import java.io.Serializable;

/**
 * author:ggband
 * date:2017/8/2 10:32
 * email:ggband520@163.com
 * desc:银行卡
 */

public class Card implements Serializable {

    private String phone;
    private String type;
    private String account;
    private String bankname;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    @Override
    public String toString() {
        return "Card{" +
                "phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", account='" + account + '\'' +
                ", bankname='" + bankname + '\'' +
                '}';
    }
}
