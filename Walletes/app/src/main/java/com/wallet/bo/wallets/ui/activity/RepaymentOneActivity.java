package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.sr.FullyLinearLayoutManager;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

/**
 * author:ggband
 * date:2017/7/19 14:03
 * email:ggband520@163.com
 * desc:借款页面
 */

public class RepaymentOneActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.rv_repay)
    RecyclerView basicRv;
    @BindView(R.id.rl)
    SwipeRefreshLayout rl;
    CommAdapter commAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_repayment_one;
    }

    @Override
    protected void initView() {
        easeTitlebar.getRightImage().setVisibility(View.GONE);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        rl.setColorSchemeColors(
                Color.parseColor("#ff00ddff"),
                Color.parseColor("#ff99cc00"),
                Color.parseColor("#ffffbb33"),
                Color.parseColor("#ffff4444"));

        rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                SystemClock.sleep(2000);
                rl.setRefreshing(false);

            }
        });

    }

    @Override
    protected void setUpView() {

        basicRv.setLayoutManager(new FullyLinearLayoutManager(context));
        basicRv.setHasFixedSize(true);
        basicRv.setItemAnimator(new DefaultItemAnimator());
        String[] shopName = {"拍拍贷", "信用钱包", "信而富"};
        commAdapter = new CommAdapter<String>(Arrays.asList(shopName), -1, R.layout.repayment_item) {

            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, String s, ViewHolder holder) {

            }
        };
        basicRv.setAdapter(commAdapter);
        commAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {

            }
        });

    }


    @OnClick({R.id.bt_gorepayment})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bt_gorepayment:
                startActivity(new Intent(this, RepaymentActivity.class));
                break;

        }

    }


}
