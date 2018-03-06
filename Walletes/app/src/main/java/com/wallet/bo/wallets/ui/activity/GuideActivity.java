package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.wallet.bo.wallets.MainActivity;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.adapter.GuideAdapter;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.ui.weiget.GuideIndicator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/8/29 13:54
 * email:ggband520@163.com
 * desc:引导界面
 */

public class GuideActivity extends BaseNoStatusActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    GuideIndicator indicator;
    @BindView(R.id.bt_start)
    TextView btStart;

    @Override
    protected int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setUpView() {


        GuideAdapter adapter = new GuideAdapter(this);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        indicator.setViewPager(viewpager);
        indicator.startAnimation();

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) btStart.setVisibility(View.VISIBLE);
                else btStart.setVisibility(View.GONE);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.bt_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (Config.LOGINTYPE) {//强制登陆
                    boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
                    if (isLogin)
                        startActivity(new Intent(this,
                                MainActivity.class));
                    else
                        startActivity(new Intent(this,
                                LogingActivity.class));
                } else {
                    startActivity(new Intent(this,
                            MainActivity.class));
                }
                finish();
                break;
        }

    }
}
