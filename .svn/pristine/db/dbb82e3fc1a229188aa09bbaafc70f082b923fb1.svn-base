package com.wallet.bo.wallets.Utils.pay;

import android.content.Context;
import android.content.Intent;

import com.wallet.bo.wallets.ui.activity.RepaymentPwActivity;

/**
 * author:ggband
 * date:2017/9/1 11:29
 * email:ggband520@163.com
 * desc:支付代理
 */

public class ProxyPay implements IPay, ItrasPw {

    private IPay iPay;//要代理的对象
    private Context context;

    public ProxyPay(IPay iPay, Context context) {
        this.iPay = iPay;
        this.context = context;
    }

    @Override
    public void pay() {
        //   判断交易密码
        RepaymentPwActivity.setItrasPw(this);
        Intent intent = new Intent(context, RepaymentPwActivity.class);
        intent.putExtra("isShowForgotPassword",true);
        context.startActivity(intent);
    }


    @Override
    public void status(boolean status) {
        if (iPay == null)
            return;
        if (status)
            iPay.pay();
    }
}
