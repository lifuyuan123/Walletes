package com.wallet.bo.wallets.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.pojo.LoanLog;
import com.wallet.bo.wallets.pojo.RepaySuccess;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/19 14:03
 * email:ggband520@163.com
 * desc:还款失败/成功页面
 */

public class RepaymentSuccessActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_orderMoney)
    TextView tvOrderMoney;
    @BindView(R.id.tv_payMoney)
    TextView tvPayMoney;

    private RepaySuccess repaySuccess;


    @Override
    protected int getContentView() {
        return R.layout.activity_repayment_success;
    }

    @Override
    protected void initView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackToLoanLog();
                finish();
            }
        });
        repaySuccess = (RepaySuccess) getIntent().getSerializableExtra("repaySuccess");

    }

    @Override
    protected void setUpView() {
        if (repaySuccess == null)
            return;
        tvOrderMoney.setText(repaySuccess.getOrderMoney());
        tvPayMoney.setText(repaySuccess.getRepayMoney());
        tvType.setText(repaySuccess.getRepayType());


    }

    @OnClick({R.id.bt_goto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_goto:
                callBackToLoanLog();
                finish();
                break;
        }

    }

    private void callBackToLoanLog() {
        LoanLog loanLog = new LoanLog();
        loanLog.setRefresh(true);
        EventBus.getDefault().post(loanLog);
    }

}


