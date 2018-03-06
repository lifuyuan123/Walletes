package com.wallet.bo.wallets.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.ui.weiget.CleanableEditText;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 15:54
 * email:ggband520@163.com
 * desc:修改邮箱
 */

public class UpdateEmailActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_email)
    CleanableEditText etEmail;

    @Override
    protected int getContentView() {
        return R.layout.activity_update_email;
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

    @OnClick({R.id.bt_play})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.bt_play:
                final String email = etEmail.getText().toString().trim();

               if( !AccountValidatorUtil.isEmail(email)){
                   toastShow("邮箱格式有误");
                   return;
               }

                mainDialog.show();
                Map<String, String> stringStringMap = new HashMap<>();
                final Login login = MyApplication.getLogin();
                stringStringMap.put("mail", email);
                stringStringMap.put("userid", MyApplication.getLogin().getUserid());
                httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        login.setMail(email);
                        MyApplication.setLogin(login);
                        toastShow("更改成功");
                        finish();
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


                break;
        }

    }


}
