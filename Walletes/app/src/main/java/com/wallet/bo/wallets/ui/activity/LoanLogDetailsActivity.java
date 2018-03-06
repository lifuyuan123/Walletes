package com.wallet.bo.wallets.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.LoanDetail;
import com.wallet.bo.wallets.pojo.LoanLog;
import com.wallet.bo.wallets.pojo.LoanStatus;
import com.wallet.bo.wallets.ui.weiget.BounceScrollView;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/19 14:26
 * email:ggband520@163.com
 * desc:借款记录详情
 */

public class LoanLogDetailsActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_loanTime)
    TextView tvLoanTime;
    @BindView(R.id.bankNo)
    TextView bankNo;
    @BindView(R.id.tv_loanLimit)
    TextView tvLoanLimit;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.repayTime)
    TextView repayTime;
    @BindView(R.id.xsf)
    TextView xsf;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_accountfei)
    TextView tvAccountfei;
    @BindView(R.id.tv_rapayMoeny)
    TextView tvRapayMoeny;
    @BindView(R.id.tv_mon)
    TextView tvMon;
    @BindView(R.id.parent)
    BounceScrollView parent;
    @BindView(R.id.bt_status)
    TextView btStatus;
    @BindView(R.id.ll_bt)
    LinearLayout llBt;
    @BindView(R.id.tv_timeName)
    TextView tvTimeName;
    @BindView(R.id.tv_factMon)
    TextView tvFactMon;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_dayMon)
    TextView tvDayMon;
    private DefaultLoadingLayout loadingLayout;
    LoanStatus loanStatus;
    LoanDetail loanDetail;
    private LoanLog loanLog;


    @Override
    protected int getContentView() {
        return R.layout.activity_loanlog_details;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, parent);
        loanLog = (LoanLog) getIntent().getSerializableExtra("loanLog");
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {
        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                initLoanLog();
            }
        });
        initLoanLog();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initLoanLog() {
        loadingLayout.onLoading();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("number", loanLog.getNumber());
        stringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.loanLogDetail(stringMap, new ApiBaseResponseCallback<LoanDetail>() {
            @Override
            public void onSuccessful(LoanDetail loanDetails) {
                setData(loanDetails);
            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals(Config.NONETWORK)) {
                    loadingLayout.onNonet();
                } else {
                    loadingLayout.onError();
                }
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                loadingLayout.onDone();
            }
        });
    }

    private void setData(LoanDetail loanDetail) {
        if (loanDetail == null)
            return;
        loanStatus = LoanStatus.getLoanStatus(loanDetail.getS());
        tvNumber.setText(loanDetail.getNumber());
        tvLoanTime.setText(loanDetail.getTime());
        tvMon.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(loanDetail.getMoney())));
        bankNo.setText(loanDetail.getBankname() + "\n" + "(" + loanDetail.getAccount() + ")");
        tvLoanLimit.setText(String.format(getResources().getString(R.string.day), String.valueOf(loanDetail.getInfo().getDay())));
        phone.setText(loanDetail.getPhone());
        if (loanDetail.getDay() == 0)
            tvDay.setText("当前订单未逾期");
        else
            tvDay.setText(String.format(getResources().getString(R.string.day), String.valueOf(loanDetail.getDay())));
        if (loanDetail.getOverdueFormula() == 0)
            tvDayMon.setText("未产生逾期费用");
        else
            tvDayMon.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(loanDetail.getOverdueFormula())));


        repayTime.setText(loanDetail.getRepayment_time());
        xsf.setText(String.format(getResources().getString(R.string.rmb), loanDetail.getInfo().getCreditaduit()));
        tvRate.setText(String.format(getResources().getString(R.string.rmb), loanDetail.getInfo().getInterest()));
        tvAccountfei.setText(String.format(getResources().getString(R.string.rmb), loanDetail.getInfo().getAdministrativefee()));
        tvRapayMoeny.setText(String.format(getResources().getString(R.string.rmb), loanDetail.getRefundmoney()));
        tvFactMon.setText(String.format(getResources().getString(R.string.rmb), loanDetail.getInfo().getRefundmoney()));
        tvTimeName.setText(loanStatus.getTimeName());
        LoanLogDetailsActivity.this.loanDetail = loanDetail;
        btStatus.setText(loanStatus.getBtName());
        llBt.setBackgroundColor(loanStatus.getStatusColorRes());

    }

    @OnClick({R.id.ll_bt})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.ll_bt:
                if (!loanStatus.isClick())
                    return;
                if (loanDetail.getS() == 1) {//取消
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确定取消订单号为" + loanDetail.getNumber() + "的订单吗?").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Map<String, String> stringMap = new HashMap<String, String>();
                            stringMap.put("number", loanDetail.getNumber());
                            stringMap.put("uid", MyApplication.getLogin().getUserid());
                            mainDialog.show();
                            httpLoader.cancelOrder(stringMap, new ApiBaseResponseCallback<LoanDetail>() {
                                @Override
                                public void onFailure(String msg) {
                                    toastShow(msg);
                                }

                                @Override
                                public void onSuccessful(LoanDetail loanDetail) {
                                    setData(loanDetail);
                                    loanLog.setOrder_status(loanDetail.getS());
                                    loanLog.setRepayment_time(loanDetail.getRepayment_time());
                                    EventBus.getDefault().post(loanLog);
                                    toastShow("取消成功");
                                }

                                @Override
                                public void onFinish() {
                                    mainDialog.dismiss();
                                }
                            });
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                } else {//还款
                    Intent intent = new Intent(activity, RepaymentActivity.class);
                    intent.putExtra("number", loanDetail.getNumber());
                    startActivity(intent);
                }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(LoanLog loanLog) {
        if (loanLog.isRefresh())
            initLoanLog();
    }
}
