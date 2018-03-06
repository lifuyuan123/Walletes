package com.wallet.bo.wallets.ui.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.bo.wallets.MainActivity;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AddSpaceTextWatcher;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.databinding.FragmentAccountLoginBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.ResetLoginPsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * author:ggband
 * date:2017/8/8 9:05
 * email:ggband520@163.com
 * desc:用户名登陆
 */

public class AccountLoginFragment extends BaseFragment implements View.OnClickListener{
    
    private FragmentAccountLoginBinding accountLoginBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accountLoginBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_account_login,container,false);
        return accountLoginBinding.getRoot();
    }

    @Override
    public void initView() {
        accountLoginBinding.btHelp.setOnClickListener(this);
        accountLoginBinding.etName.setInputType(InputType.TYPE_CLASS_NUMBER);
        AddSpaceTextWatcher addSpaceTextWatcher = new AddSpaceTextWatcher(accountLoginBinding.etName, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);

    }

    @Override
    public void setViewUp() {
    }

    //帮助
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.bt_help:
                Intent intent = new Intent(activity, ResetLoginPsActivity.class);
                startActivity(intent);
                break;


        }
    }

    //登陆
    private void login() {
        if (mainDialog!=null)
        mainDialog.show();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("phone", TextUtils.repaceTrim(accountLoginBinding.etName.getText().toString().trim()));
        stringStringMap.put("password", accountLoginBinding.etPs.getText().toString().trim());

        httpLoader.login(stringStringMap, new ApiBaseResponseCallback<Login>() {
            @Override
            public void onSuccessful(final Login login) {
                MyApplication.setLogin(login);
                Log.e("login",login.toString());
                EventBus.getDefault().post(new LoginOut(false));
                activity.setResult(102);
                JPushInterface.setAlias(context, login.getUserid(), new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.i("ggband", "phone:" + login.getUserid() + " i:" + i + " s:" + s);
                    }
                });
                if (activity.getIntent().getBooleanExtra("pro", false)) {
                    activity.finish();
                } else if (activity.getIntent().getBooleanExtra("credit", false)) {
                    EventBus.getDefault().post(new Navagation(2));
                    activity.finish();
                } else if (activity.getIntent().getBooleanExtra("toWallet", false)) {
//                    EventBus.getDefault().post(new WalletLoanLog());
                    activity.finish();
                } else if (activity.getIntent().getBooleanExtra("resetPw", false)) {
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                } else{
                    activity.finish();
//                    startActivity();
                }

            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                if (mainDialog!=null)
                mainDialog.dismiss();
            }
        });

    }

    public void loging() {
        login();
    }


}
