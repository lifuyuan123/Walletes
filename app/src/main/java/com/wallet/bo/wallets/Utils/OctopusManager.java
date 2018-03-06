package com.wallet.bo.wallets.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import cn.fraudmetrix.octopus.aspirit.R.color;
import cn.fraudmetrix.octopus.aspirit.R.mipmap;
import cn.fraudmetrix.octopus.aspirit.R.string;
//import cn.fraudmetrix.octopus.aspirit.activity.WebViewActivity;
import cn.fraudmetrix.octopus.aspirit.net.OctopusIntentService;
//import cn.fraudmetrix.octopus.aspirit.utils.Constants;
import cn.fraudmetrix.octopus.aspirit.utils.DataManager;
import cn.fraudmetrix.octopus.aspirit.utils.OctoTrustManger;

/**
 * author:ggband
 * date:2017/9/5 16:39
 * email:ggband520@163.com
 * desc:魔盒（征信）
 */


public class OctopusManager {
    private final String VERSION = "1.1.0";
    private String partnerCode;
    private String partnerKey;
    private String currentUrlHead = "https://api.shujumohe.com/";
    private static OctopusManager octopusManager;
    private int primaryColorResId;
    private int navImgResId;
    private int titleColorResId;
    private int titleSize;
    private int titleGravity;

    public OctopusManager() {
        this.primaryColorResId = color.color_white;
        this.navImgResId = mipmap.img_navigation;
        this.titleColorResId = color.color_font_grayest;
        this.titleSize = 14;
        this.titleGravity = 17;
    }

    public static synchronized OctopusManager getInstance() {
        if (octopusManager == null) {
            octopusManager = new OctopusManager();
        }

        return octopusManager;
    }

    public void init(String partnerCode, String partnerKey) {
        this.partnerKey = partnerKey;
        this.partnerCode = partnerCode;
    }

    public String getPartnerCode() {
        return this.partnerCode;
    }

    public String getPartnerKey() {
        return this.partnerKey;
    }

    public void getChannel(Activity context, Fragment fragment, String channelCode) {
        if (context != null) {
            if (this.partnerCode != null && !"".equals(this.partnerCode)) {
                if (this.partnerKey != null && !"".equals(this.partnerKey)) {
                    if (channelCode != null && !"".equals(channelCode)) {
                        if (this.currentUrlHead.contains("https:")) {
                            OctoTrustManger.allowAllSSL();
                        }

//                        Constants.OCTOPUS_THREAD_POOL_STATUS = 0;
//                        DataManager.getInstance().setLogInfoBean();
//                        Intent service = new Intent(context, OctopusIntentService.class);
//                        context.startService(service);
//                        Intent intent = null;
//                        intent = new Intent(context, WebViewActivity.class);
//                        intent.putExtra("octopus_intent_data", channelCode);
//                        if (fragment == null)
//                            context.startActivityForResult(intent, Constants.OCTOPUS_ACTIVITY_REQUEST_CODE);
//                        else
//                            fragment.startActivityForResult(intent, Constants.OCTOPUS_ACTIVITY_REQUEST_CODE);
                    } else {
                        this.showMsg(context, context.getString(string.octopus_channelcode_emp));
                    }
                } else {
                    this.showMsg(context, context.getString(string.octopus_partnerkey_emp));
                }
            } else {
                this.showMsg(context, context.getString(string.octopus_partnercode_emp));
            }
        }
    }

    private void showMsg(Context context, String msg) {
//        Toast.makeText(context, msg, 1).show();
    }

    public void setHost(String host) {
        this.currentUrlHead = host;
    }

    public String getCurrentUrlHead() {
        return this.currentUrlHead;
    }

    public String getVersion() {
        return "1.1.0";
    }

    public int getPrimaryColorResId() {
        return this.primaryColorResId;
    }

    public void setPrimaryColorResId(int primaryColorResId) {
        this.primaryColorResId = primaryColorResId;
    }

    public int getNavImgResId() {
        return this.navImgResId;
    }

    public void setNavImgResId(int navImgResId) {
        this.navImgResId = navImgResId;
    }

    public int getTitleColorResId() {
        return this.titleColorResId;
    }

    public void setTitleColorResId(int titleColorResId) {
        this.titleColorResId = titleColorResId;
    }

    public int getTitleSize() {
        return this.titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public int getTitleGravity() {
        return this.titleGravity;
    }

    public void setTitleGravity(int titleGravity) {
        this.titleGravity = titleGravity;
    }
}

