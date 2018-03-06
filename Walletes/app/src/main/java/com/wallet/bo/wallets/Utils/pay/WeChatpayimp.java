package com.wallet.bo.wallets.Utils.pay;

import android.content.Context;

import com.wallet.bo.wallets.pojo.RepayDetail;
import com.wallet.bo.wallets.wxapi.WechatPay;

/**
 * author:pxb
 * date:2017/8/31 22:22
 * email:ggband520@163.com
 * des:微信支付
 */
public class WeChatpayimp implements IPay {

    private Context context;
    private WechatPay wechatPay;

    public WeChatpayimp(Context context) {
        this.context = context;
        wechatPay = new WechatPay(context);
    }

    @Override
    public void pay() {
        wechatPay.weChantPay(null);
    }

}
