package com.wallet.bo.wallets.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.PackageUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/24 10:41
 * email:ggband520@163.com
 * desc:关于我们
 */

public class WeActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.im_head)
    ImageView imHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;

    @Override
    protected int getContentView() {
        return R.layout.activity_we;
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
        tvUserName.setText(getResources().getString(R.string.app_name) + PackageUtils.getAppVersionName(context));
    }

    @OnClick({R.id.bt_hp, R.id.bt_update, R.id.bt_weChant, R.id.bt_xy})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.bt_hp:
                if (!hasAnyMarketInstalled(context))
                    return;
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.bt_update:
                Beta.checkUpgrade(true, false);

                break;

            case R.id.bt_weChant:

                break;

            case R.id.bt_xy:
                Intent intent1 = new Intent(activity, CommonClientWebActivity.class);
                intent1.putExtra(CommonWebActivity.TITLE, "法律协议");
                intent1.putExtra(CommonWebActivity.URL, URL.AGREENMENT);
                startActivity(intent1);
                break;
        }

    }

    private boolean hasAnyMarketInstalled(Context context) {

        Intent intent = new Intent();

        intent.setData(Uri.parse("market://details?id=android.browser"));

        List list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return 0 != list.size();

    }
}
