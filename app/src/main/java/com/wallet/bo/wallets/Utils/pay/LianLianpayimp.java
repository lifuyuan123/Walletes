package com.wallet.bo.wallets.Utils.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.http.HttpLoader;
import com.wallet.bo.wallets.lianlianpay.AuthActivity;
import com.wallet.bo.wallets.lianlianpay.BaseHelper;
import com.wallet.bo.wallets.lianlianpay.Constants;
import com.wallet.bo.wallets.lianlianpay.MobileSecurePayer;
import com.wallet.bo.wallets.lianlianpay.PayOrder;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.PayResultBean;
import com.wallet.bo.wallets.pojo.RepayDetail;
import com.wallet.bo.wallets.pojo.RepaySuccess;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.RepaymentSuccessActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * author:pxb
 * date:2017/8/31 22:22
 * email:ggband520@163.com
 * des:LianLian 银联支付
 */
public class LianLianpayimp implements IPay {

    Context context;
    Login login;
    HttpLoader httpLoader;
    private RepayDetail repayDetail;
    private int type=1;  //1:还款   2：购买vip

    public LianLianpayimp(Context context, RepayDetail repayDetail,int type) {
        this.context = context;
        this.repayDetail = repayDetail;
        this.type=type;
        login = MyApplication.getLogin();
        httpLoader = HttpLoader.getInstance();
    }
    public LianLianpayimp(Context context, RepayDetail repayDetail) {
        this.context = context;
        this.repayDetail = repayDetail;
        login = MyApplication.getLogin();
        httpLoader = HttpLoader.getInstance();
    }

    private Handler mHandler = createHandler();

    private Handler createHandler() {
        return new Handler() {
            public void handleMessage(Message msg) {
                String strRet = (String) msg.obj;
                switch (msg.what) {
                    case Constants.RQF_PAY: {
                        JSONObject objContent = BaseHelper.string2JSON(strRet);
                        String retCode = objContent.optString("ret_code");
                        String retMsg = objContent.optString("ret_msg");

                        Log.i(Config.LOGTAG, objContent.toString());

                        // 成功
                        if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
//                            BaseHelper.showDialog((Activity) context, "提示",
//                                    "支付成功，交易状态码：" + retCode + " 返回报文:" + strRet,
//                                    android.R.drawable.ic_dialog_alert);
                            //1:还款   2：购买vip
                            Intent intent = new Intent(context, RepaymentSuccessActivity.class);
                            RepaySuccess repaySuccess = new RepaySuccess();
                            repaySuccess.setRepayType(repayDetail.getBankname() + "(" + TextUtils.sub4end(repayDetail.getAccount()) + ")");
                            repaySuccess.setRepayMoney(objContent.optString("money_order"));
                            repaySuccess.setType(type);
                            if(type==1){
                                //总金额  借款加逾期
                                repaySuccess.setOrderMoney(repayDetail.getRepayTotal());
                            }else if (type==2){}
                            Log.e("paySuccessful"," 返回报文 "+strRet);
                            intent.putExtra("repaySuccess", repaySuccess);
                            context.startActivity(intent);
                            ((Activity) context).finish();

                        } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {

//                                BaseHelper.showDialog((Activity) context, "提示",
//                                        objContent.optString("ret_msg") + "交易状态码："
//                                                + retCode + " 返回报文:" + strRet,
//                                        android.R.drawable.ic_dialog_alert);
                            }
                            Toast.makeText(context, objContent.optString("ret_msg"), Toast.LENGTH_SHORT).show();

                        } else {
                            // TODO 失败
                            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
//                            BaseHelper.showDialog((Activity) context, "提示", retMsg
//                                            + "，交易状态码:" + retCode + " 返回报文:" + strRet,
//                                    android.R.drawable.ic_dialog_alert);

                            Log.e("log",retMsg + "，交易状态码:" + retCode + " 返回报文:" + strRet);
                        }
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

    }


    @Override
    public void pay() {

        switch (type){
            case 1://还款

                Map<String, Object> stringStringMap = new HashMap<>();
                stringStringMap.put("number", repayDetail.getNumber());
                stringStringMap.put("uid", login.getUserid());
                httpLoader.addLianLianSign(stringStringMap, new ApiBaseResponseCallback<PayOrder>() {
                    @Override
                    public void onSuccessful(PayOrder s) {
                        Log.e("passorderonSuccessful",s.toString());
                        //   order.setSign(s);
                        String content4Pay = BaseHelper.toJSONString(s);
                        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
                        Log.i(AuthActivity.class.getSimpleName() + "提交参数：", content4Pay);

                        MobileSecurePayer msp = new MobileSecurePayer();
                        boolean bRet = msp.payAuth(content4Pay, mHandler,
                                Constants.RQF_PAY, (Activity) context, false);

                        Log.i(AuthActivity.class.getSimpleName(), String.valueOf(bRet));
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (msg!=null){
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            Log.e("passorderonFailure",msg.toString());
                        }

                    }

                    @Override
                    public void onFinish() {

                    }
                });

                break;

            case 2://购买vip

                Map<String,String> map=new HashMap<>();
                map.put("userid",MyApplication.getLogin().getUserid());
//                map.put("account",repayDetail.getAccount());//
                map.put("money",repayDetail.getMoney());//Math.random()
                map.put("RequestType","cardPay");
                httpLoader.AndroidOrIosUserCharge(map, new ApiBaseResponseCallback<PayOrder>() {
                    @Override
                    public void onSuccessful(PayOrder payResultBean) {
                        if (payResultBean==null)
                            return;
                        Log.e("payonSuccessful",payResultBean.toString());
                        String content4Pay = BaseHelper.toJSONString(payResultBean);
                        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
                        Log.i("payonSuccessful_", "提交参数："+ content4Pay);
                        MobileSecurePayer msp = new MobileSecurePayer();
                        //认证支付
                        boolean bRet= msp.payAuth(content4Pay, mHandler,
                                Constants.RQF_PAY, (Activity) context, false);
                        Log.i("payonSuccessful_", String.valueOf(bRet));
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (msg!=null){
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            Log.e("payonFailure",msg.toString());
                        }

                    }

                    @Override
                    public void onFinish() {
                        Log.e("payonFinish","onFinish");
                    }
                });

                break;
        }


    }

}

