package com.wallet.bo.wallets.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.DataCleanManager;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.ui.weiget.CommonDialog;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * author:ggband
 * date:2017/7/24 11:15
 * email:ggband520@163.com
 * desc:设置
 */

public class SetActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.tv_zl)
    TextView tvZl;
    @BindView(R.id.tv_aq)
    TextView tvAq;
    @BindView(R.id.tv_chache)
    TextView tvChache;
    @BindView(R.id.sc_pushEnable)
    SwitchCompat scPushEnable;
    private Vibrator vibrator;

    @Override
    protected int getContentView() {
        return R.layout.activity_set;
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
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void setUpView() {
        tvChache.setText(DataCleanManager.getTotalCacheSize(context));
        scPushEnable.setChecked(SharedPreferencesUtil.getInstance().getBoolean(Config.ISPUSH, true));
        scPushEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {//接受消息
                    SharedPreferencesUtil.getInstance().putBoolean(Config.ISPUSH, true);
                } else {
                    SharedPreferencesUtil.getInstance().putBoolean(Config.ISPUSH, false);
                }
                vibrator.vibrate(200);
            }
        });


    }

    @OnClick({R.id.tv_zl, R.id.tv_exit, R.id.tv_aq, R.id.tv_chache, R.id.tv_hp,R.id.tv_vip})
    public void onClick(View view) {

        switch (view.getId()) {
            //个人资料
            case R.id.tv_zl:
                startActivity(new Intent(this, MyInformationActivity.class));
                break;
            //推出
            case R.id.tv_exit:
                final CommonDialog dialog=new CommonDialog(SetActivity.this);
                dialog.setTitle("退出登陆");
                dialog.setContent("确定退出登陆？");
                dialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
                    @Override
                    public void clickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.setConfirmClickListener("确定", new CommonDialog.ConfirmClickListener() {
                    @Override
                    public void clickConfirm() {
                        dialog.dismiss();
                        SharedPreferencesUtil.getInstance().clearAll();
                        SharedPreferencesUtil.getInstance().putBoolean(Config.ISUPDATA, false);
                        SharedPreferencesUtil.getInstance().putInt("isAppStore",0);
                        EventBus.getDefault().post(new LoginOut(true));
                        String userid = MyApplication.getLogin().getUserid();
                        JPushInterface.deleteAlias(SetActivity.this,Integer.valueOf(userid));
                    }
                });
                dialog.show();

                break;
            //账户安全
            case R.id.tv_aq:
                startActivity(new Intent(this, AccountsecurityActivity.class));
                break;

            case R.id.tv_chache:
                DataCleanManager.clearAllCache(context);
                tvChache.setText(DataCleanManager.getTotalCacheSize(context));
                break;

            case R.id.tv_hp:
                if (!hasAnyMarketInstalled(context))
                    return;
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            //会员服务
            case R.id.tv_vip:
                NavigationLogin.navigation2Login(activity, new Intent(activity, VipClubActivity.class));
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        vibrator.cancel();
    }

    private boolean hasAnyMarketInstalled(Context context) {

        Intent intent = new Intent();

        intent.setData(Uri.parse("market://details?id=android.browser"));

        List list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return 0 != list.size();

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
