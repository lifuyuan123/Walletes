package com.wallet.bo.wallets.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.JudmentLoanState;
import com.wallet.bo.wallets.ui.activity.FeedbackActivity;
import com.wallet.bo.wallets.ui.activity.HelpActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.MyBankActivity;
import com.wallet.bo.wallets.ui.activity.MyMessageActivity;
import com.wallet.bo.wallets.ui.activity.MyWalletActivity;
import com.wallet.bo.wallets.ui.activity.SetActivity;
import com.wallet.bo.wallets.ui.activity.WeActivity;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.riv.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/24 11:15
 * email:ggband520@163.com
 * desc:我的
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.im_head)
    RoundedImageView imHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;

    @Override
    public void initView() {
        if (MyApplication.getLogin() != null)
            tvUserName.setText(MyApplication.getLogin().getPhone() == null ? "" : TextUtils.dosubtext(MyApplication.getLogin().getPhone()));
    }

    @Override
    public void setViewUp() {
        Picasso.with(context).load(MyApplication.getLogin().getHead_img()).placeholder(R.drawable.im_myhead).error(R.drawable.im_myhead).into(imHead);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.tv_mybank, R.id.tv_mywallete, R.id.tv_myloan, R.id.tv_mymessage,
            R.id.tv_help, R.id.tv_myopnion, R.id.tv_we, R.id.ll_head})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }

        switch (view.getId()) {

            case R.id.tv_mybank:
                startActivity(new Intent(activity, MyBankActivity.class));
                break;

            case R.id.tv_mywallete:
                navigationWallete();
                break;

            case R.id.tv_myloan:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示：").setMessage("此功能暂未开通").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                //  startActivity(new Intent(activity, RepaymentOneActivity.class));
                //  startActivity(new Intent(activity, LoanLogActivity.class));

                break;
            case R.id.tv_mymessage:
                startActivity(new Intent(activity, MyMessageActivity.class));

                break;
            case R.id.tv_help:

                startActivity(new Intent(activity, HelpActivity.class));


                break;
            case R.id.tv_myopnion:
                startActivity(new Intent(activity, FeedbackActivity.class));


                break;
            case R.id.tv_we:
                startActivity(new Intent(activity, WeActivity.class));
                break;

            case R.id.ll_head:
                startActivity(new Intent(activity, SetActivity.class));

                break;

        }

    }

    private void navigationWallete() {

        mainDialog.show();

        httpLoader.getLoanScore(MyApplication.getLogin().getUserid(), new ApiBaseResponseCallback<JudmentLoanState>() {
            @Override
            public void onSuccessful(JudmentLoanState judmentLoanState) {

                Intent intent = null;
                if (judmentLoanState.isLoan()) {
                    intent = new Intent(activity, MyWalletActivity.class);
                    startActivity(intent);
                } else {
                    navigationWallete();

                }


            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();
            }
        });

    }


}
