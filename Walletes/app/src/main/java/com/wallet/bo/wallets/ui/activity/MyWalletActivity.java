package com.wallet.bo.wallets.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.WalletLoanlogItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.LoanDetail;
import com.wallet.bo.wallets.pojo.LoanLog;
import com.wallet.bo.wallets.pojo.LoanStatus;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.WalletLoanLog;
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
    private String repaymentNumber;
    TextView tvLoanAccount;
    TextView tvTotalAccount;
    TextView tvRepayAccount;
    ImageView imLoanEmpty;
    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    WaveView waveView;
    int page = 1;
    View viewFooter;
    private DefaultLoadingLayout loadingLayout;


    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initView() {
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, basicPl);
        EventBus.getDefault().register(this);
        basicPl.post(new Runnable() {
            @Override
            public void run() {
                basicPl.autoRefresh(true);
            }
        });
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
                        if (AntiShake.check(view.getId())) {    //判断是否多次点击
                            return;
                        }
//                        if (!loanLog.getMake_loans_state())
//                            return;
                        if (!loanStatus.isClick())
                            return;
                        if (loanLog.getOrder_status() == 1) {//取消
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("确定取消订单号为" + loanLog.getNumber() + "的订单吗?").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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
                                            toastShow("取消成功");
                                            mHeaderAndFooterWrapper.notifyDataSetChanged();
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
            View headerView = LayoutInflater.from(context).inflate(R.layout.view_wallet_header, null);
            tvLoanAccount = (TextView) headerView.findViewById(R.id.tv_loanAccount);
            tvRepayAccount = (TextView) headerView.findViewById(R.id.tv_repayAccount);
            tvTotalAccount = (TextView) headerView.findViewById(R.id.tv_totalAccount);
            imLoanEmpty = (ImageView) headerView.findViewById(R.id.im_noLoanLog);
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
                if (page == 1) {
                    basicPl.setMode(PtrFrameLayout.Mode.BOTH);
                    loanLogList.clear();
                }

                repaymentNumber = walletLoanLog.getRepaymentNumber();
                tvLoanAccount.setText(String.valueOf(walletLoanLog.getMayBorrowMoney()));
                tvRepayAccount.setText(String.valueOf(walletLoanLog.getReimbursement()));
                tvTotalAccount.setText(String.valueOf(walletLoanLog.getTotalMoner()));
                if (walletLoanLog.getApiBorrowList() == null || walletLoanLog.getApiBorrowList().size() == 0) {

                    if (loanLogList.size() == 0) {
                        imLoanEmpty.setVisibility(View.VISIBLE);
                    } else {
                        imLoanEmpty.setVisibility(View.GONE);
                        viewFooter.setVisibility(View.VISIBLE);
                        basicPl.setMode(PtrFrameLayout.Mode.REFRESH);
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
                } else {
                    loadingLayout.onError();
                }
                toastShow(msg);
            }

            @Override
            public void onFinish() {
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

            case R.id.bt_goloan:
                startActivity(new Intent(activity, LoanActivity.class));
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
}