package com.wallet.bo.wallets.apshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.BaseReq;
import com.alipay.share.sdk.openapi.BaseResp;
import com.alipay.share.sdk.openapi.IAPAPIEventHandler;
import com.alipay.share.sdk.openapi.IAPApi;
import com.wallet.bo.wallets.R;

import cn.sharesdk.alipay.utils.AlipayHandlerActivity;

import static com.wallet.bo.wallets.Utils.pay.alipay.AlipayPay.APPID;

public class ShareEntryActivity extends AlipayHandlerActivity implements IAPAPIEventHandler {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建工具对象实例，此处的APPID为上文提到的，申请应用生效后，在应用详情页中可以查到的支付宝应用唯一标识
        IAPApi api = APAPIFactory.createZFBApi(getApplicationContext(), APPID, false);
        Intent intent = getIntent();
//通过调用工具实例提供的handleIntent方法，绑定消息处理对象实例，
        api.handleIntent(intent, this);

        Log.i("ggband", "发送");

    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //打印相应返回消息结果码
        int result;

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                result = R.string.errcode_send_fail;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

}
