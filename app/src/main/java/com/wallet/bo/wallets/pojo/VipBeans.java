package com.wallet.bo.wallets.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * author:ggband
 * data:2018/1/11 001115:16
 * email:ggband520@163.com
 * desc:  vip列表
 */

public class VipBeans implements Serializable{


    /**
     * menber : [{"money":-199,"name":"白银","mem_money":99,"canupgrade":0,"user_grade":3,"refund_explain":"","grade":2,"txt_upgrade":"可借款","txt_interestfree":"","txt_refund":"","img":"http://dai.moxtx.com/Public/puload/201801/1515826048.png","img_logo":"http://dai.moxtx.com/Public/puload/201801/1515825954.png"},{"money":0,"name":"黄金","mem_money":298,"canupgrade":0,"user_grade":3,"refund_explain":"","grade":3,"txt_upgrade":"可借款","txt_interestfree":"可免息","txt_refund":"","img":"http://dai.moxtx.com/Public/puload/201801/1515826024.png","img_logo":"http://dai.moxtx.com/Public/puload/201801/1515825976.png"},{"money":299,"name":"钻石","mem_money":597,"canupgrade":1,"user_grade":3,"refund_explain":"您已满足钻石会员在会员期限内有限借款笔数累计8笔且无任何逾期，您可申请退款!","grade":4,"txt_upgrade":"可借款","txt_interestfree":"可免息","txt_refund":"10笔未逾期可退款","img":"http://dai.moxtx.com/Public/puload/201801/1515826000.png","img_logo":"http://dai.moxtx.com/Public/puload/201801/1515825985.png"}]
     * describe : 商家扩大出口就啊是采集卡说不出卡家上次阿贾克斯才把 u 次啊开始吃啊 u 私家车卡是 v 次 u 啊健身卡擦拭崔阿贾克斯错币啦 u 是擦还是从百家上次。
     */

    private String describe;
    private List<MenberBean> menber;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<MenberBean> getMenber() {
        return menber;
    }

    public void setMenber(List<MenberBean> menber) {
        this.menber = menber;
    }

    public static class MenberBean implements Serializable{
        /**
         * money : -199
         * name : 白银
         * mem_money : 99
         * canupgrade : 0
         * user_grade : 3
         * refund_explain :
         * grade : 2
         * txt_upgrade : 可借款
         * txt_interestfree :
         * txt_refund :
         * img : http://dai.moxtx.com/Public/puload/201801/1515826048.png
         * img_logo : http://dai.moxtx.com/Public/puload/201801/1515825954.png
         */

        private int money;
        private String name;
        private int mem_money;
        private int canupgrade;
        private int user_grade;
        private String refund_explain;
        private int grade;
        private String txt_upgrade;
        private String txt_interestfree;
        private String txt_refund;
        private String img;
        private String img_logo;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMem_money() {
            return mem_money;
        }

        public void setMem_money(int mem_money) {
            this.mem_money = mem_money;
        }

        public int getCanupgrade() {
            return canupgrade;
        }

        public void setCanupgrade(int canupgrade) {
            this.canupgrade = canupgrade;
        }

        public int getUser_grade() {
            return user_grade;
        }

        public void setUser_grade(int user_grade) {
            this.user_grade = user_grade;
        }

        public String getRefund_explain() {
            return refund_explain;
        }

        public void setRefund_explain(String refund_explain) {
            this.refund_explain = refund_explain;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getTxt_upgrade() {
            return txt_upgrade;
        }

        public void setTxt_upgrade(String txt_upgrade) {
            this.txt_upgrade = txt_upgrade;
        }

        public String getTxt_interestfree() {
            return txt_interestfree;
        }

        public void setTxt_interestfree(String txt_interestfree) {
            this.txt_interestfree = txt_interestfree;
        }

        public String getTxt_refund() {
            return txt_refund;
        }

        public void setTxt_refund(String txt_refund) {
            this.txt_refund = txt_refund;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg_logo() {
            return img_logo;
        }

        public void setImg_logo(String img_logo) {
            this.img_logo = img_logo;
        }

        @Override
        public String toString() {
            return "MenberBean{" +
                    "money=" + money +
                    ", name='" + name + '\'' +
                    ", mem_money=" + mem_money +
                    ", canupgrade=" + canupgrade +
                    ", user_grade=" + user_grade +
                    ", refund_explain='" + refund_explain + '\'' +
                    ", grade=" + grade +
                    ", txt_upgrade='" + txt_upgrade + '\'' +
                    ", txt_interestfree='" + txt_interestfree + '\'' +
                    ", txt_refund='" + txt_refund + '\'' +
                    ", img='" + img + '\'' +
                    ", img_logo='" + img_logo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VipBeans{" +
                "describe='" + describe + '\'' +
                ", menber=" + menber +
                '}';
    }
}
