package com.wallet.bo.wallets.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.BankItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PtrDefaultHandlerWithLoadMore;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PullLayout;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:我的银行卡
 */

public class MyBankActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.rv_bank)
    RecyclerView rvLoanLog;
    @BindView(R.id.basic_pl)
    PullLayout basicPl;
    private List<Card> cardList;
    private CommAdapter<Card> commAdapter;
    int bankSelectID = 0;//默认银行卡
    boolean isShowBankSelect;

    private DefaultLoadingLayout loadingLayout;
    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private View footerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_bank;
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void initView() {

        basicPl.post(new Runnable() {
            @Override
            public void run() {
                basicPl.autoRefresh(true);
            }
        });
        basicPl.setMode(PtrFrameLayout.Mode.REFRESH);
        rvLoanLog.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        basicPl.setPtrHandler(new PtrDefaultHandlerWithLoadMore() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                init();
            }
        });
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, basicPl);
        isShowBankSelect = getIntent().getBooleanExtra("isShowBankSelect", false);
        cardList = new ArrayList<>();

        commAdapter = new CommAdapter<Card>(cardList, -1, R.layout.bank_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, Card card, ViewHolder holder) {
                BankItemBinding bankItemBinding = (BankItemBinding) binding;
                bankItemBinding.tvCardType.setText(card.getBankname() + "-储蓄卡");
                bankItemBinding.tvCardNumberS.setText(TextUtils.sub4start(card.getAccount()));
                bankItemBinding.tvCardNumber.setText(TextUtils.sub4end(card.getAccount()));
                bankItemBinding.tvIphone.setText("手机尾号" + TextUtils.sub4end(MyApplication.getLogin().getPhone()));
                if (isShowBankSelect)
                    if (position == bankSelectID)
                        bankItemBinding.ivBankSelect.setVisibility(View.VISIBLE);
                    else
                        bankItemBinding.ivBankSelect.setVisibility(View.GONE);
            }
        };

        {
            { //添加头

                footerView = LayoutInflater.from(context).inflate(R.layout.view_bank_footer, null);
                footerView.findViewById(R.id.bt_addBank).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AntiShake.check(view.getId())) {    //判断是否多次点击
                            return;
                        }
                        if (cardList.size() > 0){
                            toastShow("暂不能添加多张银行卡");
                            return;
                        }
                        if(MyApplication.isOpen()){// Y->添加银行卡   N->填写基本信息
                            startActivity(new Intent(activity, AddBankActivity.class));
                        }else {
                            Intent intentLoan = new Intent(new Intent(activity, OpenLoanActivity.class));
                            intentLoan.addFlags(1);
                            activity.startActivityForResult(intentLoan, Activity.RESULT_FIRST_USER);
                        }

                    }
                });
                mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commAdapter);

                mHeaderAndFooterWrapper.addFootView(footerView);

                rvLoanLog.setAdapter(mHeaderAndFooterWrapper);
            }
        }
        commAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {
                { //单选
                    if (isShowBankSelect) {
                        bankSelectID = (int) position;
                        commAdapter.notifyDataSetChanged();
                        Intent intent = getIntent();
                        intent.putExtra("bank", cardList.get((int) position).getAccount());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            }
        });


    }

    @Override
    protected void setUpView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    }

//    @OnClick({R.id.bt_addBank})
//    public void onClick(View view) {
//
//        switch (view.getId()) {
//
//            case R.id.bt_addBank:
//                startActivity(new Intent(activity, AddBankActivity.class));
//                break;
//
//        }
//
//    }

    private void init() {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.getCard(stringStringMap, new ApiBaseResponseCallback<Card>() {
            @Override
            public void onSuccessful(Card card) {
                if (card == null) {
                    //   btAddBank.setVisibility(View.VISIBLE);
                    return;
                }
                cardList.clear();
                // btAddBank.setVisibility(View.GONE);
                cardList.add(0, card);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals(Config.NONETWORK)) {
                    loadingLayout.onNonet();
                    return;
                } else if (msg.equals("查询失败")) {
                    return;
                }else if(msg.equals("您还未添加银行卡，请先添加银行卡。")){
                    footerView.setVisibility(View.VISIBLE);
                }
                else {
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
}
