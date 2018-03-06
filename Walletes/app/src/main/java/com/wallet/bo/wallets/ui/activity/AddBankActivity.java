package com.wallet.bo.wallets.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AddSpaceTextWatcher;
import com.wallet.bo.wallets.Utils.GsonUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.lianlianpay.AuthActivity;
import com.wallet.bo.wallets.lianlianpay.BaseHelper;
import com.wallet.bo.wallets.lianlianpay.Constants;
import com.wallet.bo.wallets.lianlianpay.MobileSecurePayer;
import com.wallet.bo.wallets.lianlianpay.PayOrder;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:添加银行卡
 */

public class AddBankActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_card)
    EditText etCard;
    @BindView(R.id.tv_cardType)
    TextView tvCardType;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.bt_open)
    TextView btOpen;
    @BindView(R.id.ll_alert)
    LinearLayout llAlert;
    @BindView(R.id.bt_goto)
    Button btGoto;
    private Login login;
    private int MY_SCAN_REQUEST_CODE = 100;


    @Override
    protected int getContentView() {
        return R.layout.activity_addbank;
    }

    @Override
    protected void initView() {

        AddSpaceTextWatcher[] asEditTexts = new AddSpaceTextWatcher[2];
        asEditTexts[1] = new AddSpaceTextWatcher(etCard, 48);
        asEditTexts[1].setSpaceType(AddSpaceTextWatcher.SpaceType.bankCardNumberType);


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
        if (MyApplication.isOpen()) {
            llAlert.setVisibility(View.VISIBLE);
            Login login = MyApplication.getLogin();
            tvName.setText(login.getName());
            tvCard.setText(TextUtils.dosubtext424(login.getCard()));
        } else {
            btOpen.setVisibility(View.VISIBLE);
        }


    }

    @OnClick({R.id.bt_goto, R.id.bt_open, R.id.bt_toScan})
    public void onClick(View view) {

        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {

            case R.id.bt_toScan://扫描银行卡号
                toScanBank();
                break;
            case R.id.bt_goto:
//                String tel = TextUtils.repaceTrim(etTel.getText().toString().trim());
//                String card = TextUtils.repaceTrim(etCard.getText().toString().trim());
//                if (!AccountValidatorUtil.isMobile(tel)) {
//                    toastShow("手机号格式不正确");
//                    return;
//                }
                //       Intent intent = new Intent(this, AddBankMesckActivity.class);
//                Card addBank = new Card();
//                addBank.setAccount(card);
//                addBank.setPhone(tel);
//                addBank.setBankname("招商银行");
//                intent.putExtra("bank", addBank);
//                startActivity(intent);
//                finish();
                requestAddBank();
                break;

            case R.id.bt_open:
                Intent intentLoan = new Intent(new Intent(activity, OpenLoanActivity.class));
                intentLoan.addFlags(1);
                activity.startActivityForResult(intentLoan, Activity.RESULT_FIRST_USER);
                break;

        }


    }

    private void toScanBank() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);
        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }


    private void requestAddBank() {
        String card = TextUtils.repaceTrim(etCard.getText().toString().trim());
        Log.i("ggband", "card:" + card);
        Map<String, String> stringStringMap = new HashMap<String, String>();
        stringStringMap.put("uid", login.getUserid());
        stringStringMap.put("account", card);

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

                        // 成功
                        if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
//                                objContent.put(RequestHelpr.KEYKEY, RequestHelpr.getInstance().getKey());
//                                Map<String, String> stringMap = new HashMap<>();
//                                stringMap.put("sign", new String(Base64.encodeBase64(objContent.toString().getBytes())));
//                                stringMap.put(RequestHelpr.APPIDKEY, RequestHelpr.APPID);
                            Map<String, String> stringMap = GsonUtils.GsonToMaps(objContent.toString());

                            httpLoader.returnBank(stringMap, new ApiBaseResponseCallback<Object>() {
                                @Override
                                public void onSuccessful(Object o) {
                                    toastShow("添加成功");
                                    EventBus.getDefault().post(new Card());//添加成功返回LoanActivity
                                    activity.startActivity(new Intent(activity, UpdateTransactionpasswordActivity.class));
                                    activity.finish();

                                }

                                @Override
                                public void onFinish() {

                                }

                                @Override
                                public void onFailure(String msg) {
                                    toastShow(msg);
                                }
                            });
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

                            //     strRet = "{\"no_agree\":\"2017090139530692\",\"oid_partner\" : \"201708090000781882\",\"result_sign\" : \"SUCCESS\", \"ret_code\" :\"0000\",\"ret_msg\" :\"交易成功\",\"sign\" : \"iGmVERse6g1KU4MEQ+riV4DfsAED7ybl8PzM9aD/iIpsevA8SkxuVnvKpQeyNa6t18rWFqLIBU9JHYeIS6bXHNXPNWgdootS33+ginRYHzBEDocEZcEtgjhBRj1OVmc6+IT9C0v/yM8j3xL206Y6xDclk6hMa0O20Lop21qNyoA=\",\"sign_type\" :\"RSA\",\"user_id\": \"60064697\"}";
                        }
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                etCard.setText(TextUtils.addTrim4(scanResult.cardNumber));
                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
                //    etCard.setText(resultDisplayStr);
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
        }
    }
}
