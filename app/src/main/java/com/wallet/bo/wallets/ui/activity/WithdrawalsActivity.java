package com.wallet.bo.wallets.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.StringUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.http.HttpLoader;
import com.wallet.bo.wallets.pojo.WithdrawalsBean;
import com.wallet.bo.wallets.ui.weiget.CommonDialog;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.MainDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 提现页面
 */
public class WithdrawalsActivity extends BaseSwipeActivity {

    @BindView(R.id.withdrawals_titlebar)
    EaseTitleBar withdrawalsTitlebar;
    @BindView(R.id.tv_withdrawals_number)
    TextView tvWithdrawalsNumber;
    @BindView(R.id.tv_withdrawals_applytime)
    TextView tvWithdrawalsApplytime;
    @BindView(R.id.tv_withdrawals_money)
    TextView tvWithdrawalsMoney;
    @BindView(R.id.tv_withdrawals_bankcard)
    TextView tvWithdrawalsBankcard;
    private String orderNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_withdrawals;
    }

    @Override
    protected void initView() {

        //返回按钮
        withdrawalsTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void setUpView() {

        final Map<String,String> map=new HashMap<>();
        map.put("uid",MyApplication.getLogin().getUserid());
        httpLoader.WithdrawalPageData(map, new ApiBaseResponseCallback<WithdrawalsBean>() {
            @Override
            public void onSuccessful(WithdrawalsBean withdrawalsBean) {
                Log.e("WithdrawalPageData",withdrawalsBean.toString());
                if(withdrawalsBean!=null){
                    orderNumber=withdrawalsBean.getNumber();
                    tvWithdrawalsNumber.setText(orderNumber);
                    tvWithdrawalsApplytime.setText(withdrawalsBean.getTime());
                    tvWithdrawalsMoney.setText(withdrawalsBean.getRefundmoney());
                    tvWithdrawalsBankcard.setText(withdrawalsBean.getBankname()+"("+withdrawalsBean.getAccount()+")");
                }
            }
            @Override
            public void onFailure(String msg) {
                if (msg!=null)
                 toastShow(msg.toString());

         }
            @Override
            public void onFinish() {}
        });

    }

    @OnClick(R.id.bt_withdrawals)
    public void onClick() {


        final CommonDialog dialog=new CommonDialog(this);
        dialog.setTitle("提现");
        dialog.setContent("确定提现？");
        dialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
            @Override
            public void clickCancel() {
                dialog.dismiss();
            }
        });
        dialog.setConfirmClickListener("确定",new CommonDialog.ConfirmClickListener(){
            @Override
            public void clickConfirm() {
                dialog.dismiss();
                 mainDialog.show();
                final Map<String,String> map=new HashMap<>();
                map.put("number",orderNumber);
                map.put("uid",MyApplication.getLogin().getUserid());
                httpLoader.UserWithdrawal(map, new ApiBaseResponseCallback<String>() {
                    @Override
                    public void onSuccessful(String s) {
                        if (s!=null)
                            toastShow(s.toString());
                        mainDialog.dismiss();
                        finish();

                    }
                    @Override
                    public void onFailure(String msg) {
                        if (msg!=null){
                            toastShow(msg.toString());
                        }
                        mainDialog.dismiss();

                    }
                    @Override
                    public void onFinish() {
                        if (mainDialog.isShowing())
                            mainDialog.dismiss();
                    }
                });
            }
        });
        dialog.show();


    }
}
