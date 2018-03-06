package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wallet.bo.wallets.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:pxb
 * date:2017/7/16 11:32
 * email:ggband520@163.com
 * des:拨打电话
 */
public class JGuangMesActivity extends BaseSwipeActivity {


    @BindView(R.id.dialog_text)
    TextView dialogText;
    private String mes = "空消息";

    public static final String MESKEY = "MESKEY";

    @Override
    protected int getContentView() {
        return R.layout.activity_jgmes;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        if (intent != null)
            mes = intent.getStringExtra(MESKEY) != null ? intent.getStringExtra(MESKEY) : mes;

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        dialogText.setText(mes);


    }

    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.dialog_cancel, R.id.dialog_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_cancel:
                finish();
                break;

            case R.id.dialog_sure:
                Intent intent = new Intent(this, MyMessageActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }


}
