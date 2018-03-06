package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AddSpaceTextWatcher;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:交易密码 验证卡号
 */

public class AccountsecurityAuthenticationActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.et_card)
    MaterialEditText etCard;
    @BindView(R.id.et_tel)
    MaterialEditText etTel;
    @BindView(R.id.tv_alert)
    TextView tvAlert;
    @BindView(R.id.tv_idCard)
    TextView tvIdCard;
    @BindView(R.id.tv_bankNum)
    TextView tvBankNum;
    @BindView(R.id.tv_bankIphone)
    TextView tvBankIphone;
    private Card card;


    @Override
    protected int getContentView() {
        return R.layout.activity_account_security_authentication;
    }

    @Override
    protected void initView() {
        card = (Card) getIntent().getSerializableExtra("card");
        AddSpaceTextWatcher[] asEditTexts = new AddSpaceTextWatcher[2];
        asEditTexts[1] = new AddSpaceTextWatcher(etTel, 13);
        asEditTexts[1].setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        asEditTexts[0] = new AddSpaceTextWatcher(etCard, 48);
        asEditTexts[0].setSpaceType(AddSpaceTextWatcher.SpaceType.bankCardNumberType);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {
        if (card == null)
            return;
        etTel.setText(card.getPhone());
        tvAlert.setText(String.format(getResources().getString(R.string.updataTraPassWordUserName), MyApplication.getLogin().getName()));
        tvIdCard.setText(TextUtils.dosubtext424(MyApplication.getLogin().getCard()));
        tvBankIphone.setText(card.getPhone());
        tvBankNum.setText(card.getAccount());

    }

    @OnClick({R.id.bt_goto})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_goto:
//                String tCard = TextUtils.repaceTrim(etCard.getText().toString().trim());
//                String tel = TextUtils.repaceTrim(etTel.getText().toString().trim());
//                if (!tCard.equals(card.getAccount())) {
//                    toastShow("银行卡号不符");
//                    return;
//                }
//                if (AccountValidatorUtil.isMobile(tel)) {
                Intent intent = new Intent(this, AccountsecutsecurityAuthentictionMesckActivity.class);
                card.setPhone(MyApplication.getLogin().getPhone());
                intent.putExtra("bank", card);
                startActivity(intent);
                finish();
                break;

        }


    }

}
