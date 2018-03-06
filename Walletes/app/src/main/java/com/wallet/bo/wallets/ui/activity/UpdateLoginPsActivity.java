package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.ActivityManager;
import com.wallet.bo.wallets.Utils.LogUtils;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.pojo.Verify;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 15:54
 * email:ggband520@163.com
 * desc:修改登录密码
 */

public class UpdateLoginPsActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_cp)
    EditText etCp;
    @BindView(R.id.et_ps)
    EditText etPs;
    @BindView(R.id.et_cps)
    EditText etCps;
    @BindView(R.id.bt_cp)
    TextView btCp;

    private Verify verify;

    private boolean runningThree;

    private Login login;

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
        return R.layout.activity_update_loginpw;
    }

    @Override
    protected void initView() {
        login = MyApplication.getLogin();
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.bt_cp, R.id.bt_updata})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_cp:
                if (runningThree)
                    return;
                downTimer.start();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("phone", login.getPhone());
                httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {
                    @Override
                    public void onSuccessful(Verify verify) {
                        UpdateLoginPsActivity.this.verify = verify;
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

            case R.id.bt_updata:
                String pw = etPs.getText().toString().trim();
                String pws = etCps.getText().toString().trim();
                if (TextUtils.isEmpty(pw) || TextUtils.isEmpty(pws)) {
                    toastShow("密码不能为空");
                    return;
                }
                if (!pw.equals(pws)) {
                    toastShow("两次密码不一致");
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
                stringStringMap.put("userid", verify.getUids());
                stringStringMap.put("phone", login.getPhone());
                stringStringMap.put("password", etPs.getText().toString().trim());
                httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        SharedPreferencesUtil.getInstance().putBoolean(Config.ISLOGIN, false);
                        EventBus.getDefault().post(new LoginOut(true));
                        finish();

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
        super.onDestroy();
        downTimer.cancel();
    }
}
