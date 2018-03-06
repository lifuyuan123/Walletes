package com.wallet.bo.wallets.Utils.pay;

import android.content.Context;
import android.widget.Toast;

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
    private int type=1;//1  还款   2购买vip

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PayHelper(Context context) {
        this.context = context;
    }


    private IPay iPay;

    public PayHelper setPayType(int payType, RepayDetail repayDetail,int type) {
        this.repayDetail = repayDetail;
        this.type=type;

        switch (payType) {
            case ALIPAY:
                iPay = new Alipayimp(context);
                Toast.makeText(context, "进入支付宝", Toast.LENGTH_SHORT).show();
                break;

            case WECHANTPAY:
                iPay = new WeChatpayimp(context);
                Toast.makeText(context, "进入微信", Toast.LENGTH_SHORT).show();
                break;

            case LIANLIANPAY:
                iPay = new LianLianpayimp(context, repayDetail,type);

                break;
        }
        proxyPay = new ProxyPay(iPay, context,payType);

        return this;

    }

    @Override
    public void pay() {
        if (iPay == null)
            return;
        proxyPay.pay();
    }

}
