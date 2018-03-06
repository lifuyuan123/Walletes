package com.wallet.bo.wallets.ui.weiget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

/**
 * author:ggband
 * data:2017/9/18 001814:11
 * email:ggband520@163.com
 * desc:功能未开通Dialog
 */

public class DisOpenDialog extends AlertDialog.Builder {
    public DisOpenDialog(@NonNull Context context) {
        super(context);
    }

    public DisOpenDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }


    @Override
    public AlertDialog show() {
        setTitle("提示").setMessage("此功能暂未开通").setPositiveButton("确定", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return super.show();
    }
}
