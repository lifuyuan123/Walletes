package com.wallet.bo.wallets.ui.activity;

import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.SecurityPasswordEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:交易密码
 */

public class UpdateTransactionpasswordActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.se_pw)
    SecurityPasswordEditText sePw;


    @Override
    protected int getContentView() {
        return R.layout.activity_update_transactionpassword;
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
        sePw.setOnEditTextListener(new SecurityPasswordEditText.OnEditTextListener() {
            @Override
            public void inputComplete(int state, String password) {
                sendHttp(password);
            }
        });
    }

    @OnClick({})
    public void onClick(View view) {

        switch (view.getId()) {
        }


    }

    private void sendHttp(String paywd) {

        mainDialog.show();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("paypwd", paywd);
        stringStringMap.put("userid", MyApplication.getLogin().getUserid());
        httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

            @Override
            public void onSuccessful(Object o) {
                toastShow("更改成功");
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

    }


}
