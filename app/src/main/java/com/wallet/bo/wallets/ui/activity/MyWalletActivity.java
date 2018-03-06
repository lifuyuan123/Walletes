package com.wallet.bo.wallets.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.WalletLoanlogItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.LoanDetail;
import com.wallet.bo.wallets.pojo.LoanLog;
import com.wallet.bo.wallets.pojo.LoanRepayBean;
import com.wallet.bo.wallets.pojo.LoanStatus;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.WalletLoanLog;
import com.wallet.bo.wallets.ui.weiget.CommonDialog;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.WaveView;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PtrDefaultHandlerWithLoadMore;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PullLayout;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:我的钱包（积分足够）
 */

public class MyWalletActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.rv_loanLog)
    RecyclerView rvLoanLog;
    CommAdapter commAdapter;

    List<LoanLog> loanLogList = new ArrayList<>();
    @BindView(R.id.basic_pl)
    PullLayout basicPl;
    @BindView(R.id.bt_goloan)
    Button btGoloan;
    @BindView(R.id.bt_gorepayment)
    Button btGorepayment;
    private String repaymentNumber;
    TextView tvLoanAccount;
    TextView tvTotalAccount;
    TextView tvRepayAccount;
    ImageView imLoanEmpty;
    LinearLayout linnoLoanLog;
    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    WaveView waveView;
    int page = 1;
    View viewFooter;
    private DefaultLoadingLayout loadingLayout;

    private boolean isCanloan=false;


    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initView() {
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, basicPl);
        EventBus.getDefault().register(this);

        easeTitlebar.getRightTitleView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        easeTitlebar.getRightImage().setVisibility(View.GONE);
        basicPl.setMode(PtrFrameLayout.Mode.BOTH);
        basicPl.setPtrHandler(new PtrDefaultHandlerWithLoadMore() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                initProduct();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                initProduct();
            }
        });


    }



    private void isloan() {
        httpLoader.loanrepa(MyApplication.getLogin().getUserid(), new ApiBaseResponseCallback<LoanRepayBean>() {
            @Override
            public void onSuccessful(LoanRepayBean loanRepayBean) {
                Log.e("loanrepa", loanRepayBean.toString());
                if (loanRepayBean.getBorrowState() == 0) {//借款
                     isCanloan=true;
                } else if (loanRepayBean.getBorrowState() == 1) {//还款
                    isCanloan=false;
                }

            }

            @Override
            public void onFailure(String msg) {
                if (msg != null)
                    Log.e("loanrepaonFailure", msg.toString());
            }

            @Override
            public void onFinish() {
                Log.e("loanrepaonFinish", "完成" + "   " + MyApplication.getLogin().getUserid());
            }
        });
    }

    @Override
    protected void setUpView() {
        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                basicPl.post(new Runnable() {
                    @Override
                    public void run() {
                        basicPl.autoRefresh(true);
                    }
                });
            }
        });
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvLoanLog.setLayoutManager(new LinearLayoutManager(context));
        commAdapter = new CommAdapter<LoanLog>(loanLogList, -1, R.layout.wallet_loanlog_item) {
            @Override
            protected void bindViewItemData(final ViewDataBinding binding, int position, final LoanLog loanLog, ViewHolder holder) {
                final WalletLoanlogItemBinding loanLogItem = (WalletLoanlogItemBinding) binding;
                final LoanStatus loanStatus = LoanStatus.getLoanStatus(loanLog.getOrder_status());
                loanLogItem.tvRepayMon.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(loanLog.getRefundmoney())));
                loanLogItem.tvLoanTime.setText(loanLog.getLoan());
                loanLogItem.tvFanTime.setText(loanLog.getTime());
                loanLogItem.tvTimeName.setText(loanStatus.getTimeName());
                loanLogItem.tvRepayTime.setText(loanLog.getRepayment_time());
                loanLogItem.tvLoanMon.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(loanLog.getMoney())));
                loanLogItem.tvNumber.setText(loanLog.getNumber());
                loanLogItem.btStatus.setText(loanStatus.getBtName());
                loanLogItem.cvBtStatus.setClickable(loanLog.getMake_loans_state());
                loanLogItem.cvBtStatus.setCardBackgroundColor(loanStatus.getStatusColorRes());
                loanLogItem.imStatus.setBackgroundResource(loanStatus.getStatusBgRes());
                loanLogItem.cvBtStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("loanLog",loanLog.toString());
                        if (AntiShake.check(view.getId())) {    //判断是否多次点击
                            return;
                        }
//                        if (!loanLog.getMake_loans_state())
//                            return;
                        if (!loanStatus.isClick())
                            return;

//                        loanLog.getOrder_status()==1
                        if (loanLog.getS().equals("1")) {//取消

                            final CommonDialog dialog=new CommonDialog(MyWalletActivity.this);
                            dialog.setTitle("提示");
                            dialog.setContent("确定取消订单号为" + loanLog.getNumber() + "的订单吗?");
                            dialog.setConfirmClickListener("确定", new CommonDialog.ConfirmClickListener() {
                                @Override
                                public void clickConfirm() {
                                    dialog.dismiss();
                                    Map<String, String> stringMap = new HashMap<String, String>();
                                    stringMap.put("number", loanLog.getNumber());
                                    stringMap.put("uid", MyApplication.getLogin().getUserid());
                                    mainDialog.show();
                                    httpLoader.cancelOrder(stringMap, new ApiBaseResponseCallback<LoanDetail>() {
                                        @Override
                                        public void onFailure(String msg) {
                                            toastShow(msg);
                                        }

                                        @Override
                                        public void onSuccessful(LoanDetail loanDetail) {
                                            loanLog.setOrder_status(loanDetail.getS());
                                            loanLog.setRepayment_time(loanDetail.getRepayment_time());
                                            loanLog.setRefundmoney(loanDetail.getRefundmoney());
                                            toastShow("取消成功");
                                            mHeaderAndFooterWrapper.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFinish() {
                                            mainDialog.dismiss();
                                        }
                                    });
                                }
                            });
                            dialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
                                @Override
                                public void clickCancel() {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();



                        //loanLog.getOrder_status()==2
                        } else if(loanLog.getS().equals("2")){
                            startActivity(new Intent(MyWalletActivity.this,WithdrawalsActivity.class));
                        } else {//还款
                            Log.i("ggband", loanStatus.getBtName() + "number:" + loanLog.getNumber());
                            Intent intent = new Intent(activity, RepaymentActivity.class);
                            intent.putExtra("number", loanLog.getNumber());
                            startActivity(intent);
                        }

                    }
                });
            }
        };

        { //添加头

            viewFooter = LayoutInflater.from(context).inflate(R.layout.view_list_nomore_footer, null);
            viewFooter.setVisibility(View.GONE);
            View headerView = LayoutInflater.from(context).inflate(R.layout.view_wallet_header, null);
            tvLoanAccount = (TextView) headerView.findViewById(R.id.tv_loanAccount);
            tvRepayAccount = (TextView) headerView.findViewById(R.id.tv_repayAccount);
            tvTotalAccount = (TextView) headerView.findViewById(R.id.tv_totalAccount);
            imLoanEmpty = (ImageView) headerView.findViewById(R.id.im_noLoanLog);
            linnoLoanLog= (LinearLayout) headerView.findViewById(R.id.lin_noLoanLog);
            waveView = (WaveView) headerView.findViewById(R.id.waveline);
            waveView.waveAnim();
            mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commAdapter);

            mHeaderAndFooterWrapper.addHeaderView(headerView);
            mHeaderAndFooterWrapper.addFootView(viewFooter);

            rvLoanLog.setAdapter(mHeaderAndFooterWrapper);
        }
        commAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {
                if (AntiShake.check(item.getId())) {    //判断是否多次点击
                    return;
                }
                Intent intent = new Intent(activity, LoanLogDetailsActivity.class);
                LoanLog loanLog = loanLogList.get((int) position - 1);
                loanLog.setId((int) position - 1);
                intent.putExtra("loanLog", loanLog);
                startActivity(intent);

            }
        });

    }


    private void initProduct() {
        final Login login = MyApplication.getLogin();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("uid", login.getUserid());
        stringMap.put("page", String.valueOf(page));

        httpLoader.walletLoanLog(stringMap, new ApiBaseResponseCallback<WalletLoanLog>() {
            @Override
            public void onSuccessful(WalletLoanLog walletLoanLog) {

                Log.e("walletLoanLog", walletLoanLog.toString());

                if (page == 1) {
                    if (basicPl!=null)
                    basicPl.setMode(PtrFrameLayout.Mode.BOTH);
                    loanLogList.clear();
                }

                repaymentNumber = walletLoanLog.getRepaymentNumber();
                tvLoanAccount.setText(String.valueOf(walletLoanLog.getMayBorrowMoney()));
                tvRepayAccount.setText(String.valueOf(walletLoanLog.getReimbursement()));
                tvTotalAccount.setText(String.valueOf(walletLoanLog.getTotalMoner()));


                if (walletLoanLog.getApiBorrowList() == null || walletLoanLog.getApiBorrowList().size() == 0) {

                    if (loanLogList.size() == 0) {
//                        imLoanEmpty.setVisibility(View.VISIBLE);
                        linnoLoanLog.setVisibility(View.VISIBLE);
                        viewFooter.setVisibility(View.GONE);

                    } else {
//                        imLoanEmpty.setVisibility(View.GONE);
                        linnoLoanLog.setVisibility(View.GONE);
                        if(basicPl!=null)
                        basicPl.setMode(PtrFrameLayout.Mode.REFRESH);
                        viewFooter.setVisibility(View.VISIBLE);
                        toastShow("已经没有啦");
                    }

                    return;
                }
                for (LoanLog loanLog : walletLoanLog.getApiBorrowList()) {
                    loanLogList.add(loanLog);
                }
                viewFooter.setVisibility(View.GONE);
                page++;
                mHeaderAndFooterWrapper.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals(Config.NONETWORK)) {
                    loadingLayout.onNonet();
                    viewFooter.setVisibility(View.GONE);
                } else {
                    loadingLayout.onError();
                    viewFooter.setVisibility(View.GONE);
                }
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                if (basicPl!=null)
                basicPl.refreshComplete();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        waveView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isloan();//判断是否借款
        basicPl.post(new Runnable() {
            @Override
            public void run() {
                basicPl.autoRefresh(true);
            }
        });
    }


    @OnClick({R.id.bt_goloan, R.id.bt_gorepayment})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }

        switch (view.getId()) {

            case R.id.bt_gorepayment:
                if (repaymentNumber == null || repaymentNumber.isEmpty()) {
                    toastShow("你没有待还款订单");
                    return;
                }
                Intent intent = new Intent(activity, RepaymentActivity.class);
                intent.putExtra("number", repaymentNumber);
                startActivity(intent);
                break;

            case R.id.bt_goloan://借款
                if (!isCanloan){
                    toastShow("当前还有未还款项");
                    return;
                }

                Intent intent1 = new Intent(new Intent(activity, LoanActivity.class));
                String canborrow="";
                if(tvLoanAccount.getText().equals("500")){
                    canborrow="0";
                }
                intent1.putExtra("canborrow", canborrow);
                startActivity(intent1);
                break;
        }

    }

    @Subscribe
    public void onEventMainThread(LoanLog loanLog) {
        if (loanLog.isRefresh()) {
            //还款重新获取数据
            page = 1;
            initProduct();
        } else {
            //取消局部刷新
            loanLogList.get(loanLog.getId()).setOrder_status(loanLog.getOrder_status());
            loanLogList.get(loanLog.getId()).setRepayment_time(loanLog.getRepayment_time());
            mHeaderAndFooterWrapper.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
