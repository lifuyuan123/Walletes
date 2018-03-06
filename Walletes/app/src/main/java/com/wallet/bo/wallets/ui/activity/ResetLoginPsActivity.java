package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.LogUtils;
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
 * author:ggband
 * date:2017/7/28 15:54
 * email:ggband520@163.com
 * desc:重置登录密码
 */

public class ResetLoginPsActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_cp)
    EditText etCp;
    @BindView(R.id.bt_cp)
    TextView btCp;
    @BindView(R.id.et_ps)
    EditText etPs;
    @BindView(R.id.et_cps)
    EditText etCps;
    @BindView(R.id.bt_play)
    Button btPlay;

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
        return R.layout.activity_reset_loginpw;
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

        etTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    return;
                String phoneText = etTel.getText().toString().trim();
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("phone", etTel.getText().toString().trim());
                httpLoader.commonExecute(URL.PHONEEXIT, stringStringMap, new ApiBaseResponseCallback<Object>() {
                    @Override
                    public void onSuccessful(Object o) {
                        isRegister = true;
                    }

                    @Override
                    public void onFailure(String msg) {
                        isRegister = false;
                    }

                    @Override
                    public void onFinish() {

                    }
                });

            }
        });

    }

    @OnClick({R.id.bt_cp, R.id.bt_play})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_cp:

                if (runningThree)
                    return;
                if (isRegister) {
                    toastShow("手机号不存在");
                    return;
                }
                String phoneText = etTel.getText().toString().trim();
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                downTimer.start();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("phone", phoneText);
                httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {
                    @Override
                    public void onSuccessful(Verify verify) {
                        ResetLoginPsActivity.this.verify = verify;
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
                break;

            case R.id.bt_play:

                String pw = etPs.getText().toString().trim();
                String pws = etCps.getText().toString().trim();
                if (isRegister) {
                    toastShow("手机号不存在");
                    return;
                }
                if (TextUtils.isEmpty(pw) || TextUtils.isEmpty(pws)) {
                    toastShow("密码不能为空");
                    return;
                }
                if (!pw.equals(pws)) {
                    toastShow("两次密码不一致");
                    return;
                }

                String cp = etCp.getText().toString().trim();
                if (verify == null || !String.valueOf(verify.getVerify()).equals(cp)) {
                    toastShow("验证码错误");
                    return;
                }

                mainDialog.show();
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("userid", verify.getUids());
                stringStringMap.put("phone", etTel.getText().toString().trim());
                stringStringMap.put("password", etPs.getText().toString().trim());
                httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        toastShow("重置成功");
                        activity.startActivity(new Intent(activity, LogingActivity.class));
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
        }
    }

    @Override
    protected void onDestroy() {
        downTimer.cancel();
        super.onDestroy();
    }
}
