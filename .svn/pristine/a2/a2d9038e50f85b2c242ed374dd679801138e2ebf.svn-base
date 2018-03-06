package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wallet.bo.wallets.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:pxb
 * date:2017/7/16 11:32
 * email:ggband520@163.com
 * des:贷款金额选择界面
 */
public class LoanMoneyActivity extends BaseSwipeActivity {
    @BindView(R.id.rg_money)
    RadioGroup rgMoney;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.bt_checkIn)
    Button btCheckIn;
    int defaultRadioButtonID;
    public static final String TAG = "MONEY";

    @Override
    protected int getContentView() {
        return R.layout.activity_ppchoose_money;
    }

    @Override
    protected void initView() {

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height =  WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);

        defaultRadioButtonID = ((RadioButton) rgMoney.getChildAt(0)).getId();
        rgMoney.check(defaultRadioButtonID);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    rgMoney.clearCheck();
                } else {
                    rgMoney.check(defaultRadioButtonID);
                }

            }
        });


    }

    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.bt_checkIn})
    public void onClick(View view) {
        String money = "";
        if (rgMoney.getCheckedRadioButtonId() != -1)
            money = ((RadioButton) findViewById(rgMoney.getCheckedRadioButtonId())).getText().toString();
        else
            money = etMoney.getText().toString();

        Intent intent = getIntent();
        intent.putExtra(TAG, money.trim());
        setResult(RESULT_OK, intent);
        finish();

    }


}
