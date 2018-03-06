package com.wallet.bo.wallets.pojo;

import java.io.Serializable;

/**
 * author:ggband
 * data:2018/1/13 001314:06
 * email:ggband520@163.com
 * desc:购买vip返回模型
 */

public class PayResultBean implements Serializable {
        /**
         * risk_item : {"frms_ware_category":"2010","user_info_mercht_userno":"","user_info_bind_phone":"","user_info_dt_register":"20180113144756","user_info_full_name":"","user_info_id_no":"","user_info_identify_type":"1","user_info_identify_state":"1"}
         * card_no : 6212263100010651029
         * busi_partner : 101001
         * money_order : 0.01
         * acct_name : null
         * id_type : 0
         * notify_url :
         * no_order : 1515826076
         * user_id : null
         * id_no : null
         * dt_order : 20180113144756
         * oid_partner : 201708090000781882
         * sign_type : RSA
         * key : 201708090000781882_sahdisa_20170809
         * sign : r1ijJ86xc1BuynBG4vJPgHxmAcHDC18F60qJIbokWuEH78XdnQt6/S1ysLJ7vR5rzYAJYPZF80BrNrSach232cS4WxqkeiGWs+k5cmP5tNF0q2qiLUGJHVpGnQQLKQNppJE+IDB0AwqH3Mxypy0DbTf82/3mlK1VDuvOhLb5sLk=
         */

        private String risk_item;
        private String card_no;
        private String busi_partner;
        private double money_order;
        private Object acct_name;
        private String id_type;
        private String notify_url;
        private int no_order;
        private Object user_id;
        private Object id_no;
        private String dt_order;
        private String oid_partner;
        private String sign_type;
        private String key;
        private String sign;

        public String getRisk_item() {
            return risk_item;
        }

        public void setRisk_item(String risk_item) {
            this.risk_item = risk_item;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getBusi_partner() {
            return busi_partner;
        }

        public void setBusi_partner(String busi_partner) {
            this.busi_partner = busi_partner;
        }

        public double getMoney_order() {
            return money_order;
        }

        public void setMoney_order(double money_order) {
            this.money_order = money_order;
        }

        public Object getAcct_name() {
            return acct_name;
        }

        public void setAcct_name(Object acct_name) {
            this.acct_name = acct_name;
        }

        public String getId_type() {
            return id_type;
        }

        public void setId_type(String id_type) {
            this.id_type = id_type;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public int getNo_order() {
            return no_order;
        }

        public void setNo_order(int no_order) {
            this.no_order = no_order;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public Object getId_no() {
            return id_no;
        }

        public void setId_no(Object id_no) {
            this.id_no = id_no;
        }

        public String getDt_order() {
            return dt_order;
        }

        public void setDt_order(String dt_order) {
            this.dt_order = dt_order;
        }

        public String getOid_partner() {
            return oid_partner;
        }

        public void setOid_partner(String oid_partner) {
            this.oid_partner = oid_partner;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "risk_item='" + risk_item + '\'' +
                    ", card_no='" + card_no + '\'' +
                    ", busi_partner='" + busi_partner + '\'' +
                    ", money_order=" + money_order +
                    ", acct_name=" + acct_name +
                    ", id_type='" + id_type + '\'' +
                    ", notify_url='" + notify_url + '\'' +
                    ", no_order=" + no_order +
                    ", user_id=" + user_id +
                    ", id_no=" + id_no +
                    ", dt_order='" + dt_order + '\'' +
                    ", oid_partner='" + oid_partner + '\'' +
                    ", sign_type='" + sign_type + '\'' +
                    ", key='" + key + '\'' +
                    ", sign='" + sign + '\'' +
                    '}';
        }



}
