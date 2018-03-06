package com.wallet.bo.wallets.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/24 11:15
 * email:ggband520@163.com
 * desc:反馈意见
 */

public class FeedbackActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_feed)
    EditText etFeed;


    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
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

    }

    @OnClick({R.id.bt_goto})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        mainDialog.show();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("userid", MyApplication.getLogin().getUserid());
        stringStringMap.put("v", etFeed.getText().toString().trim());
        stringStringMap.put("source", "android");
        httpLoader.feedback(stringStringMap, new ApiBaseResponseCallback<Object>() {

            @Override
            public void onSuccessful(Object o) {
                toastShow("反馈成功");
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();
            }
        });
//http://dai.moxtx.com/index.php/Home/Feedback/Feedback

        //$_POST = array("appid"=>"10202820","sign"=>base64_encode('{"key":"SioXHEFuMVUPuJKLuHqZTlzWWFlfRQhe","userid":"25205078","v":"1111111","source":"android"}'));


    }


}
