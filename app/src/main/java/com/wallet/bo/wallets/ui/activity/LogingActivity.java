package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.adapter.LoginTypeViewPagerAdapter;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.SwapViewpager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/8/8 9:10
 * email:ggband520@163.com
 * desc:登陆
 */

public class LogingActivity extends BaseSwipeActivity {
    @BindView(R.id.tl_loginType)
    TabLayout tlLoginType;
    @BindView(R.id.vp_loginType)
    SwapViewpager vpLoginType;
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    private LoginTypeViewPagerAdapter loginTypeViewPagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_loging;
    }

    @Override
    protected void initView() {
        if (getIntent().getBooleanExtra("resetPw", false))
            easeTitlebar.getLeftImage().setVisibility(View.GONE);
        else
            easeTitlebar.getLeftImage().setVisibility(View.VISIBLE);
        easeTitlebar.getRightImage().setVisibility(View.GONE);
        easeTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, RegisterActivity.class));

            }
        });
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //控制radiobutton下标
                setResult(102);
                finish();
            }
        });

        loginTypeViewPagerAdapter = new LoginTypeViewPagerAdapter(getSupportFragmentManager());
        vpLoginType.setAdapter(loginTypeViewPagerAdapter);
        tlLoginType.setupWithViewPager(vpLoginType);

    }

    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                if (tlLoginType.getSelectedTabPosition() == 0)
                    loginTypeViewPagerAdapter.getAccountLoginFragment().loging();
                else
                    loginTypeViewPagerAdapter.getMesLoginFragment().loging();
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            //控制radiobutton下标
            setResult(102);
            finish();
        }
        return true;
    }
}
