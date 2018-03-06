package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.AddSpaceTextWatcher;
import com.wallet.bo.wallets.Utils.CodeUtils;
import com.wallet.bo.wallets.Utils.LogUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.pojo.Verify;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:pxb
 * date:2017/7/17 20:14
 * email:ggband520@163.com
 * des:注册页面
 */
public class RegisterActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_imcp)
    EditText etImcp;
    @BindView(R.id.im_verify)
    ImageView imVerify;
    @BindView(R.id.et_cp)
    EditText etCp;
    @BindView(R.id.bt_cp)
    TextView btCp;
    @BindView(R.id.et_ps)
    EditText etPs;
    @BindView(R.id.bt_help)
    TextView btHelp;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.cb_isAgree)
    CheckBox cbIsAgree;
    private boolean isRegister;

    private Verify verify;


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
            verify = null;
            btCp.setText("重新发送");
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
        AddSpaceTextWatcher addSpaceTextWatcher = new AddSpaceTextWatcher(etName, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    return;
                String phoneText = TextUtils.repaceTrim(etName.getText().toString().trim());
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("phone", phoneText);
                httpLoader.commonExecute(URL.PHONEEXIT, stringStringMap, new ApiBaseResponseCallback<Object>() {
                    @Override
                    public void onSuccessful(Object o) {
                        isRegister = true;
                    }

                    @Override
                    public void onFailure(String msg) {
                        isRegister = false;
                        toastShow(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });

            }
        });

    }

    @OnClick({R.id.bt_register, R.id.im_verify, R.id.bt_cp, R.id.bt_help, R.id.bt_agree})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_register:

                if (!cbIsAgree.isChecked())
                    return;

                String pw = etPs.getText().toString().trim();
                if (!isRegister) {
                    toastShow("手机号不可用");
                    return;
                }
                if (android.text.TextUtils.isEmpty(pw)) {
                    toastShow("密码不能为空");
                    return;
                }
                if (!AccountValidatorUtil.isLoginPw(pw)) {
                    Snackbar.make(easeTitlebar, "密码必须字母、数字组合，长度为6-12位", Snackbar.LENGTH_LONG).setAction("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                    return;
                }
                String cp = etCp.getText().toString().trim();
                if (verify == null || !String.valueOf(verify.getVerify()).equals(cp)) {
                    toastShow("验证码错误");
                    return;
                }

                mainDialog.show();
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("phone", TextUtils.repaceTrim(etName.getText().toString().trim()));
                stringStringMap.put("password", etPs.getText().toString().trim());
                httpLoader.commonExecute(URL.REGISTER, stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        toastShow("注册成功");
                        activity.finish();

                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                    }

                    @Override
                    public void onFinish() {
                        mainDialog.dismiss();
                    }
                });

                break;

            case R.id.im_verify:
                imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());

                break;

            case R.id.bt_agree:
                Intent intent = new Intent(activity, CommonClientWebActivity.class);
                intent.putExtra(CommonWebActivity.TITLE, "注册协议");
                intent.putExtra(CommonWebActivity.URL, URL.AGREENMENT);
                startActivity(intent);

                break;

            case R.id.bt_cp:
                if (runningThree)
                    return;
                if (!isRegister) {
                    toastShow("手机号不可用");
                    return;
                }
                String phoneText = TextUtils.repaceTrim(etName.getText().toString().trim());
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
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("phone", phoneText);
                httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {
                    @Override
                    public void onSuccessful(Verify verify) {
                        RegisterActivity.this.verify = verify;
                        Log.e("verify", verify.toString());
                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
                break;


            case R.id.bt_help:
                startActivity(new Intent(activity, TelActivity.class));

                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }
}
