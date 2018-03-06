package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.pojo.Config;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:pxb
 * date:2017/7/16 11:32
 * email:ggband520@163.com
 * des:拨打电话
 */
public class TelActivity extends BaseSwipeNoStatusActivity {


    @BindView(R.id.dialog_text)
    TextView dialogText;

    @Override
    protected int getContentView() {
        return R.layout.activity_tel;
    }

    @Override
    protected void initView() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.3
        wl.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha = 0.6f;
        getWindow().setAttributes(wl);
        dialogText.setText(Config.TEL);


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
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.TEL));
                startActivity(intent);
                finish();
                break;
        }

    }


}
