package com.wallet.bo.wallets.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * author:ggband
 * data:2017/9/18 001813:33
 * email:ggband520@163.com
 * desc:入栈 出栈
 */

public abstract class AbstractTemplateActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) this.getApplication();
        application.getActivityManager().pushActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication application = (MyApplication) getApplication();
        application.getActivityManager().popActivity(this);
    }
}
