package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wallet.bo.wallets.MainActivity;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.CodeUtils;
import com.wallet.bo.wallets.Utils.RequestHelpr;
import com.wallet.bo.wallets.http.ApiCommonTypeCallback;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/7/13.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_ps)
    EditText etPs;
    @BindView(R.id.bt_cp)
    TextView btCp;
    @BindView(R.id.et_imcp)
    EditText etImcp;
    @BindView(R.id.et_cp)
    EditText etCp;
    @BindView(R.id.bt_help)
    TextView btHelp;
    @BindView(R.id.ll_messageLogin)
    LinearLayout llMessageLogin;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.rg_loginType)
    RadioGroup rgLoginType;
    @BindView(R.id.im_verify)
    ImageView imVerify;
    private int loginType;//0:账号登陆 1:短信登陆
    private final String verifyUrl = "http://dai.moxtx.com/index.php/Home/Com/verify";

    private boolean runningThree;


    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            btCp.setText((l / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            runningThree = false;
            btCp.setText("重新发送");
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etName.setText("15390089473");
        etPs.setText("123456");
        rgLoginType.check(R.id.rb_account);
        rgLoginType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_account) {
                    loginType = 0;
                    btHelp.setText("忘记密码");
                    etPs.setVisibility(View.VISIBLE);
                    llMessageLogin.setVisibility(View.GONE);
                } else {
                    loginType = 1;
                    btHelp.setText("收不到短信？请联系客服");
                    llMessageLogin.setVisibility(View.VISIBLE);
                    etPs.setVisibility(View.GONE);
                }
            }
        });
        imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());

    }

    @Override
    protected void setUpView() {
        easeTitlebar.getRightImage().setVisibility(View.GONE);
        easeTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, RegisterActivity.class));

            }
        });


    }

    @OnClick({R.id.bt_login, R.id.im_verify, R.id.bt_cp, R.id.bt_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.im_verify:
                imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
                break;

            case R.id.bt_help:
                Intent intent = null;
                if (loginType == 1)
                    intent = new Intent(activity, TelActivity.class);
                else
                    intent = new Intent(activity, ResetLoginPsActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_cp:

                if (runningThree) {

                } else {
                    String phoneText = etName.getText().toString().trim();
                    if (!AccountValidatorUtil.isMobile(phoneText)) {
                        toastShow("手机号格式有误");
                        return;
                    }
                    if (!CodeUtils.getInstance().getCode().equals(etImcp.getText().toString().trim())) {
                        toastShow("验证码错误请重新输入");
                        imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
                        return;
                    }
                    downTimer.start();
                    Map<String, String> stringStringMap = new HashMap<>();
                    stringStringMap.put("phone", phoneText);
                    stringStringMap.put("password", etPs.getText().toString().trim());
                    sendRequest("http://dai.moxtx.com/index.php/Home/User/SendSms", RequestHelpr.getRequestParms(stringStringMap), 1);
                }
                break;
        }


    }


    private void sendRequest(String url, Map<String, String> map, final int type) {
        apiStores.common(url, map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ApiCommonTypeCallback() {

            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    Log.i("ggband", "type：" + type);
                    Log.i("ggband", "responseBody" + responseBody.string());
                    if (type == 0) {
                        startActivity(new Intent(activity, MainActivity.class));
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();

            }
        });
    }


    private void login() {
        showProgressDialog("登录中...");
        if (loginType == 0) {

            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("phone", etName.getText().toString().trim());
            stringStringMap.put("password", etPs.getText().toString().trim());
            sendRequest("http://dai.moxtx.com/index.php/Home/Login/login", RequestHelpr.getRequestParms(stringStringMap), 0);
        } else {

            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("phone", etName.getText().toString().trim());
            sendRequest("http://dai.moxtx.com/index.php/Home/Login/SmsLogin", RequestHelpr.getRequestParms(stringStringMap), 0);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }


}
