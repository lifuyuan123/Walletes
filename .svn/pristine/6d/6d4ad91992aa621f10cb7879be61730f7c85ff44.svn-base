package com.wallet.bo.wallets.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.ui.ba.SwipeBackActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:赢额度
 */

public class YedActivity extends BaseSwipeActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_yed;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.tv_pf, R.id.tv_share})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_pf:
                if (!hasAnyMarketInstalled(context))
                    return;
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

            case R.id.tv_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/*");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "分享内容");
                startActivity(sendIntent);

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
