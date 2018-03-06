package com.wallet.bo.wallets.Utils.pay;

import android.content.Context;

import com.wallet.bo.wallets.Utils.pay.alipay.AlipayPay;
import com.wallet.bo.wallets.pojo.RepayDetail;

/**
 * author:pxb
 * date:2017/8/31 22:22
 * email:ggband520@163.com
 * des:支付宝支付
 */
public class Alipayimp implements IPay {

    Context context;

    public Alipayimp(Context context) {
        this.context = context;
    }

    @Override
    public void pay() {
        AlipayPay alipayPay = new AlipayPay(context, "23734638");
        alipayPay.payV2();
    }

}
