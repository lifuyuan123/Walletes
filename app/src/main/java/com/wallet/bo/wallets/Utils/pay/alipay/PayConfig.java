package com.wallet.bo.wallets.Utils.pay.alipay;

/**
 * author:ggband
 * date:2017/8/16 11:13
 * email:ggband520@163.com
 * desc:支付宝配置信息
 */

public class PayConfig {

    /** 商户PID*/
    public static final String PARTNER = "faff";
    /** 商户收款账号*/
    public static final String SELLER = "15390089473";
    /** 商户私钥，pkcs8格式*/
    public static final String RSA_PRIVATE = "fdgsg";
    /** 支付宝公钥*/
    public static final String RSA_PUBLIC = "fasfsaf";

    /** 服务器返回地址*/
    public static final String NOTIFY_URL = "http://domain.merchant.com/payment_notify";
}
