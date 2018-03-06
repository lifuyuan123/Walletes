package com.wallet.bo.wallets.ui.weiget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

import com.wallet.bo.wallets.pojo.Config;

/**
 * author:ggband
 * data:2017/9/18 001814:11
 * email:ggband520@163.com
 * desc:功能未开通Dialog
 */

public class TelDialog extends AlertDialog.Builder {
    public TelDialog(@NonNull Context context) {
        super(context);
    }

    public TelDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }


    @Override
    public AlertDialog show() {
        setTitle("使用电话提示").setMessage("确定拨打号码为"+ Config.TEL+"的电话号码吗").setPositiveButton("确定", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.TEL));
                getContext().startActivity(intent);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return super.show();
    }
}
