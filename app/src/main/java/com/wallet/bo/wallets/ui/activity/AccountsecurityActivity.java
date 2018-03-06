package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:账户安全
 */

public class AccountsecurityActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    boolean isHaveBank;//用户是否绑银行卡
    Card card;

    @Override
    protected int getContentView() {
        return R.layout.activity_account_security;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {
        init();


    }

    @OnClick({R.id.bt_loginPw, R.id.bt_transactionPw})
    public void onClick(View view) {

        switch (view.getId()) {
            //登陆密码
            case R.id.bt_loginPw:
                startActivity(new Intent(this, UpdateLoginPsActivity.class));

                break;
             //交易密码
            case R.id.bt_transactionPw:
                Intent intent = null;
                if (isHaveBank) {
                    intent = new Intent(this, AccountsecurityAuthenticationActivity.class);
                    intent.putExtra("card", card);
                } else
                    intent = new Intent(this, AddBankActivity.class);
                startActivity(intent);

                break;
        }


    }


    private void init() {
        mainDialog.show();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.getCard(stringStringMap, new ApiBaseResponseCallback<Card>() {
            @Override
            public void onSuccessful(Card card) {
                isHaveBank = true;
                AccountsecurityActivity.this.card = card;
            }

            @Override
            public void onFailure(String msg) {
                isHaveBank = false;
            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();
            }
        });
    }

    @Subscribe
    public void onEventMainThread(LoginOut loginOut) {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
