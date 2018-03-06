package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.pojo.Order;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:借款成功
 */

public class LoanSuccessActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    private Order order;


    @Override
    protected int getContentView() {
        return R.layout.activity_loan_success;
    }

    @Override
    protected void initView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void setUpView() {
        order = (Order) getIntent().getSerializableExtra("order");
        init();

    }

    private void init() {
        if (order == null)
            return;
        tvMoney.setText(order.getMoney());
        tvDeadline.setText(order.getDeadline());
        String card = order.getCard().getBankname() + "(" + TextUtils.dosubtext424(order.getCard().getAccount()) + ")";
        tvCard.setText(card);
        tvNumber.setText(order.getNumber());
        tvTime.setText(order.getTime());
        tvPhone.setText(TextUtils.dosubtext(order.getPhone()));

    }

    @OnClick({R.id.bt_goto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_goto:
                startActivity(new Intent(this, MyWalletActivity.class));
                finish();
                break;
        }

    }

}
