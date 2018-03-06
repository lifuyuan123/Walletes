package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.LogUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Verify;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc: 交易密码 验证短信
 */

public class AccountsecutsecurityAuthentictionMesckActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.bt_cp)
    TextView btCp;
    @BindView(R.id.tv_alert)
    TextView tvAlert;
    @BindView(R.id.bt_goto)
    Button btGoto;
    @BindView(R.id.et_cp)
    EditText etCp;
    private Card bank;


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
            tvAlert.setText("验证码已失效，请重新获取");
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_addbank_mesck;
    }

    @Override
    protected void initView() {
        easeTitlebar.setTitle("设置交易密码");
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {
        bank = (Card) getIntent().getSerializableExtra("bank");
        if (bank != null)
            if (bank.getPhone() != null)
                tvAlert.setText(String.format(getResources().getString(R.string.addbank_tel), TextUtils.dosubtext(bank.getPhone())));

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

                startActivity(new Intent(this, UpdateTransactionpasswordActivity.class));
                finish();

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
                AccountsecutsecurityAuthentictionMesckActivity.this.verify = verify;
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
