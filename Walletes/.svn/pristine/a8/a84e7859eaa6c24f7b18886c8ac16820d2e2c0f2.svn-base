package com.wallet.bo.wallets.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.ui.activity.LogingActivity;

/**
 * author:ggband
 * data:2017/9/13 001317:24
 * email:ggband520@163.com
 * desc:没登陆去登陆
 */

public class NavigationLogin {

    public static void navigation2Login(Activity activity, Class<? extends Activity> aClass) {
        boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
        if (!isLogin) {
            activity.startActivity(new Intent(activity, LogingActivity.class));
           // activity.finish();
        } else
            activity.startActivity(new Intent(activity, aClass));
    }



    public static void navigation2Login(Activity activity, Intent intent) {
        boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
        if (!isLogin) {
            ComponentName componentName = new ComponentName(activity, LogingActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            activity.startActivity(intent);
        } else
            activity.startActivity(intent);
    }

    public static void navigation2LoginForResult(Activity activity, Class<? extends Activity> aClass, int requestCode) {
        boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
        if (!isLogin) {
            activity.startActivity(new Intent(activity, LogingActivity.class));
            activity.finish();
        } else
            activity.startActivityForResult(new Intent(activity, aClass), requestCode);
    }

}
