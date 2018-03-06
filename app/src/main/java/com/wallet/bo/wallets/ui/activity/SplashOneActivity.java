package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.wallet.bo.wallets.R;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/7/13.
 */

public class SplashOneActivity extends BaseActivity {


    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_reg)
    Button btReg;


    @Override
    protected int getContentView() {
        return R.layout.activity_splashone;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.bt_login, R.id.bt_reg})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_login:
                startActivity(new Intent(this, LogingActivity.class));
                finish();
                break;
            case R.id.bt_reg:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

        }

    }
}
