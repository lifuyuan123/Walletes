package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.Utils.pay.ItrasPw;
import com.wallet.bo.wallets.databinding.PpTraspwBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.ui.weiget.SecurityPasswordEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/19 14:03
 * email:ggband520@163.com
 * desc:还款输入密码页面
 */

public class RepaymentPwActivity extends BaseSwipeNoStatusActivity {

    @BindView(R.id.spe)
    SecurityPasswordEditText spe;
    Login login;
    private View btForgotpassword;
    private boolean isShowForgotPassword;

    public static ItrasPw itrasPw;

    public static void setItrasPw(ItrasPw itrasPw) {
        RepaymentPwActivity.itrasPw = itrasPw;
    }


    @Override
    protected int getContentView() {
        //  return R.layout.activity_repayment_pw;
        return R.layout.pp_traspw;
    }

    @Override
    protected void initView() {
        btForgotpassword = spe.findViewById(R.id.bt_forgotPassword);
        isShowForgotPassword = getIntent().getBooleanExtra("isShowForgotPassword", false);
        if (isShowForgotPassword)
            btForgotpassword.setVisibility(View.VISIBLE);

        login = MyApplication.getLogin();

        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);

        spe.setOnEditTextListener(new SecurityPasswordEditText.OnEditTextListener() {
            @Override
            public void inputComplete(int state, String password) {
                mainDialog.show();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("uid", MyApplication.getLogin().getUserid());
                stringMap.put("paypwd", password);
                httpLoader.verifyTransactionPassword(stringMap, new ApiBaseResponseCallback<Object>() {
                    @Override
                    public void onSuccessful(Object o) {
                        Log.e("passonSuccessful","onSuccessful");
                        if (itrasPw == null)
                            return;
                        itrasPw.status(true);
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                        Log.e("passonFailure","onFailure");
                        finish();
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

    @OnClick({R.id.bt_back, R.id.bt_forgotPassword})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.bt_forgotPassword:

                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("uid", MyApplication.getLogin().getUserid());
                mainDialog.show();
                httpLoader.getCard(stringStringMap, new ApiBaseResponseCallback<Card>() {
                    @Override
                    public void onSuccessful(Card card) {
                        Intent intent = new Intent(activity, AccountsecutsecurityAuthentictionMesckActivity.class);
                        intent.putExtra("bank", card);
                        activity.startActivity(intent);
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
