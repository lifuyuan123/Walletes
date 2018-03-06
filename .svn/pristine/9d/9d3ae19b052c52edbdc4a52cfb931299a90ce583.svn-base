package com.wallet.bo.wallets.Utils.pay;

import android.content.Context;

import com.wallet.bo.wallets.pojo.RepayDetail;

/**
 * author:ggband
 * date:2017/8/31 15:36
 * email:ggband520@163.com
 * desc:支付帮助
 */

public class PayHelper implements IPay {


    public static final int ALIPAY = 0;//支付宝支付
    public static final int WECHANTPAY = 1;//微信支付
    public static final int LIANLIANPAY = 2;//银联支付

    private Context context;
    private ProxyPay proxyPay;
    private RepayDetail repayDetail;

    public PayHelper(Context context) {
        this.context = context;
    }


    private IPay iPay;

    public PayHelper setPayType(int payType, RepayDetail repayDetail) {
        this.repayDetail = repayDetail;

        switch (payType) {
            case ALIPAY:
                iPay = new Alipayimp(context);
                break;

            case WECHANTPAY:
                iPay = new WeChatpayimp(context);
                break;

            case LIANLIANPAY:
                iPay = new LianLianpayimp(context, repayDetail);
                break;
        }
        proxyPay = new ProxyPay(iPay, context);

        return this;

    }

    @Override
    public void pay() {
        if (iPay == null)
            return;
        proxyPay.pay();
    }

}
