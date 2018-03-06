package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/10/26 002615:56
 * email:ggband520@163.com
 * desc:
 */

public class UserInfo {


    /**
     * card : null
     * name : null
     */

    private String card;
    private String name;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "card='" + card + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
