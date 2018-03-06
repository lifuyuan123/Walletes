package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.DisplayUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.Utils.pay.PayHelper;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.RepayDetail;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.ui.weiget.BounceZoomScrollView;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.SecurityPasswordEditText;
import com.wallet.bo.wallets.ui.weiget.TakePhotoPopWin;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/19 14:03
 * email:ggband520@163.com
 * desc:还款页面
 */

public class RepaymentActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;

    @BindView(R.id.bt_gorepayment)
    Button btGorepayment;
    @BindView(R.id.rg_banks)
    RadioGroup rgBanks;
    @BindView(R.id.rb_airpay)
    RadioButton rbAirpay;
    @BindView(R.id.bt_addBank)
    TextView btAddBank;
    @BindView(R.id.tv_bank_desc)
    TextView tvBankDesc;
    @BindView(R.id.bt_bankPay)
    LinearLayout btBankPay;
    @BindView(R.id.ll_bankPay)
    LinearLayout llBankPay;
    TakePhotoPopWin takePhotoPopWin;
    View popupView;
    @BindView(R.id.rb_wechatpay)
    RadioButton rbWechatpay;
    @BindView(R.id.tv_moenyFree)
    TextView tvMoenyFree;
    @BindView(R.id.tv_liFree)
    TextView tvLiFree;
    @BindView(R.id.tv_lateDay)
    TextView tvLateDay;
    @BindView(R.id.tv_lateFree)
    TextView tvLateFree;
    @BindView(R.id.tv_cFree)
    TextView tvCFree;
    @BindView(R.id.tv_totalFree)
    TextView tvTotalFree;
    @BindView(R.id.parent)
    BounceZoomScrollView parent;
    private int payType = 0;//支付方式：0：支付宝 1：微信 2：银行卡

    private PayHelper payHelper;

    private String repaymentNumber;

    private Login login;

    private DefaultLoadingLayout loadingLayout;

    private List<Card> cardList = new ArrayList<>();

    private RepayDetail repayDetail;
    private String bankDesc = "请添加";

    @Override
    protected int getContentView() {
        return R.layout.activity_repayment;
    }

    @Override
    protected void initView() {
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, parent);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initShowPsView();
        payHelper = new PayHelper(context);
        repaymentNumber = getIntent().getStringExtra("number");
        login = MyApplication.getLogin();

    }

    private void initShowPsView() {
        popupView = LayoutInflater.from(context).inflate(R.layout.pp_traspw, null);
        SecurityPasswordEditText passwordEditText = (SecurityPasswordEditText) popupView.findViewById(R.id.spe);
        passwordEditText.setOnEditTextListener(new SecurityPasswordEditText.OnEditTextListener() {
            @Override
            public void inputComplete(int state, String password) {

            }
        });
        takePhotoPopWin = new TakePhotoPopWin(popupView, context, null);
        takePhotoPopWin.setFocusable(true);
        takePhotoPopWin.setOutsideTouchable(false);// 设置允许在外点击消失
    }

    @Override
    protected void setUpView() {

        initBanks();
        getRepayDetail();
        rbAirpay.setChecked(true);

        rbAirpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rgBanks.getChildCount() > 0)
                    if (isChecked) {
                        payType = PayHelper.ALIPAY;
                        rbWechatpay.setChecked(false);
                        rgBanks.clearCheck();
                        tvBankDesc.clearComposingText();
                        llBankPay.setVisibility(View.GONE);
                        tvBankDesc.setText(bankDesc);
                    }


            }
        });


        rbWechatpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rgBanks.getChildCount() > 0)
                    if (isChecked) {
                        payType = PayHelper.WECHANTPAY;
                        rbAirpay.setChecked(false);
                        rgBanks.clearCheck();
                        tvBankDesc.clearComposingText();
                        llBankPay.setVisibility(View.GONE);
                        tvBankDesc.setText(bankDesc);
                    }


            }
        });

        rgBanks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                if (radioButton != null)
                    if (radioButton.isChecked()) {
                        payType = PayHelper.LIANLIANPAY;
                        rbAirpay.setChecked(false);
                        rbWechatpay.setChecked(false);
                        llBankPay.setVisibility(View.GONE);
                        tvBankDesc.setText(radioButton.getText());
                    }
            }
        });

    }


    private void initBanks() {

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.getCard(stringStringMap, new ApiBaseResponseCallback<Card>() {
            @Override
            public void onSuccessful(Card card) {
                if (card == null) {
                    return;
                }
                cardList.add(0, card);
                bankDesc = "请选择";
                tvBankDesc.setText(bankDesc);
                btAddBank.setVisibility(View.GONE);

                rgBanks.removeAllViews();
                for (int i = 0; i < cardList.size(); i++) {
                    View vH = LayoutInflater.from(context).inflate(R.layout.item_horizontal_gray, null);
                    RadioButton radioButton = new RadioButton(context);
                    radioButton.setId(i);
                    radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    radioButton.setTextColor(getResources().getColor(R.color.text_gray));
                    radioButton.setPadding(DisplayUtils.dip2px(context, 30), DisplayUtils.dip2px(context, 15), DisplayUtils.dip2px(context, 15), DisplayUtils.dip2px(context, 15));
                    vH.setPadding(DisplayUtils.dip2px(context, 30), DisplayUtils.dip2px(context, 15), 0, DisplayUtils.dip2px(context, 15));
                    radioButton.setBackgroundColor(getResources().getColor(R.color.white));
                    radioButton.setText(cardList.get(i).getBankname() + "(" + TextUtils.sub4end(cardList.get(i).getAccount()) + ")");
                    radioButton.setButtonDrawable(new BitmapDrawable());
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.repayment_ckbg_selector, 0);
                    rgBanks.addView(radioButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    rgBanks.addView(vH, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                }
                rgBanks.invalidate();

            }

            @Override
            public void onFailure(String msg) {
                bankDesc = "请添加";
                tvBankDesc.setText(bankDesc);
                btAddBank.setVisibility(View.VISIBLE);
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                loadingLayout.onDone();
            }
        });


    }

    @OnClick({R.id.bt_addBank, R.id.bt_gorepayment, R.id.bt_bankPay})
    public void onClick(View view) {

        if (view.getId() != R.id.bt_bankPay && AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.bt_addBank:
                startActivity(new Intent(this, AddBankActivity.class));
                break;

            case R.id.bt_gorepayment:
                if (repayDetail == null)
                    return;
                if (payType == 2)//暂时只有银联支付
                    payHelper.setPayType(payType, repayDetail).pay();
                else {//微信、支付宝支付指南
                    Intent intentGl = new Intent(activity, CommonClientWebActivity.class);
                    intentGl.putExtra(CommonWebActivity.TITLE, "还款");
                    intentGl.putExtra(CommonWebActivity.URL, URL.ISSUE);
                    startActivity(intentGl);
                }
                break;

            case R.id.bt_bankPay:
                if (llBankPay.getVisibility() == View.VISIBLE)
                    llBankPay.setVisibility(View.GONE);
                else
                    llBankPay.setVisibility(View.VISIBLE);

                break;
        }
    }

    private void getRepayDetail() {
        loadingLayout.onLoading();
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, parent);
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("uid", login.getUserid());
        stringMap.put("number", repaymentNumber);
        httpLoader.repayDetail(stringMap, new ApiBaseResponseCallback<RepayDetail>() {
            @Override
            public void onSuccessful(RepayDetail repayDetail) {
                RepaymentActivity.this.repayDetail = repayDetail;
                tvLiFree.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(repayDetail.getApiBorrowInfo())));
                tvLateDay.setText(String.valueOf(repayDetail.getDaysOverdue()));
                tvMoenyFree.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(repayDetail.getMoney())));
                tvLateFree.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(repayDetail.getLateFee())));
                tvTotalFree.setText(String.format(getResources().getString(R.string.rmb), String.valueOf(repayDetail.getRepayTotal())));
            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals(Config.NONETWORK))
                    loadingLayout.onNonet();
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                loadingLayout.onDone();
            }
        });
    }

}
