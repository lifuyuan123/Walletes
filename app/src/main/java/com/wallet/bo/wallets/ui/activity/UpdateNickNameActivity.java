package com.wallet.bo.wallets.ui.activity;

import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.ui.weiget.CleanableEditText;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:修改昵称
 */

public class UpdateNickNameActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_nickName)
    CleanableEditText etNickName;
    private Login login;
    private String nNAme;

    @Override
    protected int getContentView() {
        return R.layout.activity_update_nickname;
    }

    @Override
    protected void initView() {

        login = MyApplication.getLogin();
        etNickName.setHint(login.getCname());
        easeTitlebar.getRightImage().setVisibility(View.GONE);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        easeTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nNAme = etNickName.getText().toString().trim();

                if (nNAme.length() < 4) {
                    toastShow("昵称字符数必须在4-20之间");
                    return;
                }
                mainDialog.show();
                Map<String, String> stringStringMap = new HashMap<>();
                final Login login = MyApplication.getLogin();
                stringStringMap.put("userid", MyApplication.getLogin().getUserid());
                stringStringMap.put("cname", nNAme);

                httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        login.setCname(nNAme);
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


            }
        });

    }

    @Override
    protected void setUpView() {


    }


}
