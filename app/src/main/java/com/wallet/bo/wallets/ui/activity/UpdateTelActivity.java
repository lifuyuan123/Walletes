package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.LogUtils;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Login;
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
 * desc:修改手机号
 */

public class UpdateTelActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_cp)
    EditText etCp;
    @BindView(R.id.bt_cp)
    TextView btCp;
    private boolean runningThree;
    private Verify verify;

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
        return R.layout.activity_updatetel;
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

    }

    @OnClick({R.id.bt_cp, R.id.bt_play})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_cp:
                if (runningThree) {
                    return;
                }
                String phoneText = etPhone.getText().toString().trim();
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                downTimer.start();
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("phone", phoneText);
                httpLoader.verify(stringStringMap, new ApiBaseResponseCallback<Verify>() {
                    @Override
                    public void onSuccessful(Verify verify) {
                        UpdateTelActivity.this.verify = verify;
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

                String cp = etCp.getText().toString().trim();
                if (verify == null || !String.valueOf(verify.getVerify()).equals(cp)) {
                    toastShow("验证码错误");
                    return;
                }
                mainDialog.show();
                Map<String, String> stringMap = new HashMap<>();
                Login login = MyApplication.getLogin();
                stringMap.put("userid", login.getUserid());
                stringMap.put("phone", etPhone.getText().toString().trim());
                httpLoader.updataUserInfo(stringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        toastShow("修改成功，请重新登录");
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
}
