package com.wallet.bo.wallets.pojo;

import java.io.Serializable;

/**
 * author:ggband
 * date:2017/8/21 15:47
 * email:ggband520@163.com
 * desc:订单
 */

public class Order implements Serializable{


    /**
     * phone : 15390089473
     * time : 2017-08-21
     * deadline : 星期一(2017-08-21至2017-08-28)
     * number : XMQB1503301590
     * money : 1000
     */

    private String phone;
    private String time;
    private String deadline;
    private String number;
    private String money;
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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

    @Override
    public String toString() {
        return "Order{" +
                "phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", deadline='" + deadline + '\'' +
                ", number='" + number + '\'' +
                ", money='" + money + '\'' +
                ", card=" + card +
                '}';
    }
}
