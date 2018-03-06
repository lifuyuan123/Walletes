package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.RequestHelpr;
import com.wallet.bo.wallets.http.ApiCommonTypeCallback;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author:ggband
 * date:2017/7/26 9:59
 * email:ggband520@163.com
 * desc:我的帮助中心
 */

public class HelpActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;

    @Override
    protected int getContentView() {
        return R.layout.activity_myhelp;
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

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("newstype", "还款问题|申请问题");
        sendRequest("http://dai.moxtx.com/index.php/Home/News/GetApiNews", RequestHelpr.getRequestParms(stringStringMap), 0);

    }

    @OnClick({R.id.bt_loanwen, R.id.bt_cs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_loanwen:
                startActivity(new Intent(this, CommonproblemActivity.class));
                break;

            case R.id.bt_cs:

                break;
        }


    }


    private void sendRequest(String url, Map<String, String> map, final int type) {
        apiStores.common(url, map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ApiCommonTypeCallback() {

            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    Log.i("ggband", "type：" + type);
                    Log.i("ggband", "responseBody" + responseBody.string());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();

            }
        });
    }
}
