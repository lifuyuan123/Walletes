package com.wallet.bo.wallets.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wallet.bo.wallets.pojo.WechatBill;

/**
 * author:ggband
 * date:2017/8/16 15:13
 * email:ggband520@163.com
 * desc:微信支付
 */

public class WechatPay {

    Context context;

    public WechatPay(Context context) {
        this.context = context;
    }

    public void weChantPay(WechatBill wechatBill) {

        if (wechatBill == null)
            return;

        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        PayReq payReq = new PayReq();
        payReq.appId = Constants.APP_ID;
        payReq.partnerId = wechatBill.getPartnerid();
        payReq.prepayId = wechatBill.getPrepayid();
        payReq.packageValue = wechatBill.getPackageValue();
        payReq.nonceStr = wechatBill.getNoncestr();
        payReq.timeStamp = wechatBill.getTimestamp();
        payReq.sign = wechatBill.getSign();

        api.sendReq(payReq);
    }
}
