package com.wallet.bo.wallets.ui.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.LogUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.lianlianpay.AuthActivity;
import com.wallet.bo.wallets.lianlianpay.BaseHelper;
import com.wallet.bo.wallets.lianlianpay.Constants;
import com.wallet.bo.wallets.lianlianpay.MobileSecurePayer;
import com.wallet.bo.wallets.lianlianpay.PayOrder;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.Verify;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:添加银行卡
 */

public class AddBankMesckActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.bt_cp)
    TextView btCp;
    @BindView(R.id.tv_alert)
    TextView tvAlert;
    @BindView(R.id.et_cp)
    EditText etCp;
    private Card bank;

    private Verify verify;


    private boolean runningThree;

    private Login login;


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

                        // 成功
                        if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
                            // TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉

                            BaseHelper.showDialog(activity, "提示",
                                    "签约成功，交易状态码：" + retCode + " 返回报文:" + strRet,
                                    android.R.drawable.ic_dialog_alert);
                            break;

                        } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {
                                BaseHelper.showDialog(activity, "提示",
                                        objContent.optString("ret_msg") + "交易状态码："
                                                + retCode + " 返回报文:" + strRet,
                                        android.R.drawable.ic_dialog_alert);
                            }

                        } else {
                            // TODO 失败
                            BaseHelper.showDialog(activity, "提示", retMsg
                                            + "，交易状态码:" + retCode + " 返回报文:" + strRet,
                                    android.R.drawable.ic_dialog_alert);
                        }
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

    }


    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            btCp.setText((l / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            runningThree = false;
            verify = null;
            btCp.setText("重新发送");
            tvAlert.setText("验证码已失效，请重新获取");
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_addbank_mesck;
    }

    @Override
    protected void initView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {
        login = MyApplication.getLogin();
        bank = (Card) getIntent().getSerializableExtra("bank");
        sendSem();
    }


    @OnClick({R.id.bt_goto, R.id.bt_cp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_goto:
                String cp = etCp.getText().toString().trim();
                if (verify == null || !String.valueOf(verify.getVerify()).equals(cp)) {
                    toastShow("验证码错误");
                    return;
                }
                Map<String, String> stringStringMap = new HashMap<String, String>();
                stringStringMap.put("uid", login.getUserid());
                stringStringMap.put("account", bank.getAccount());

                httpLoader.addBankLianLianSign(stringStringMap, new ApiBaseResponseCallback<PayOrder>() {
                    @Override
                    public void onSuccessful(PayOrder order) {

                        String content4Pay = BaseHelper.toJSONString(order);
                        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
                        Log.i(AuthActivity.class.getSimpleName() + "给连连：", content4Pay);

                        MobileSecurePayer msp = new MobileSecurePayer();
                        boolean bRet = msp.paySign(content4Pay, mHandler,
                                Constants.RQF_PAY, activity, false);


                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });


//                mainDialog.show();
//                Map<String, String> stringStringMap = new HashMap<>();
//                stringStringMap.put("phone", bank.getPhone());
//                stringStringMap.put("uid", login.getUserid());
//                stringStringMap.put("account", bank.getAccount());
//                stringStringMap.put("cardname", bank.getBankname());
//                httpLoader.addBank(stringStringMap, new ApiBaseResponseCallback<String>() {
//                    @Override
//                    public void onSuccessful(String s) {
//                        LogUtils.i(Config.LOGTAG, s);
//                        activity.startActivity(new Intent(activity, UpdateTransactionpasswordActivity.class));
//                        activity.finish();
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        toastShow(msg);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        mainDialog.dismiss();
//                    }
//                });
//
////

                break;

            case R.id.bt_cp:
                if (!runningThree) {
                    sendSem();
                }
                break;
        }


    }

    private void sendSem() {
        if (bank == null || bank.getPhone() == null)
            return;
        tvAlert.setText(String.format(getResources().getString(R.string.addbank_tel), TextUtils.dosubtext(bank.getPhone())));
        downTimer.start();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("phone", bank.getPhone());
        httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {
            @Override
            public void onSuccessful(Verify verify) {
                AddBankMesckActivity.this.verify = verify;
                LogUtils.i(Config.LOGTAG, verify.toString());
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }

}
