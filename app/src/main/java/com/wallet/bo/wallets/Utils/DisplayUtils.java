package com.wallet.bo.wallets.Utils;

import android.content.Context;

/**
 * author:pxb
 * date:2017/7/31 20:11
 * email:ggband520@163.com
 * des:屏幕工具
 */
public class DisplayUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
