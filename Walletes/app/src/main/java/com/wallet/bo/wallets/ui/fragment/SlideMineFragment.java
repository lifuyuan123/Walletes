package com.wallet.bo.wallets.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.ImageUtils;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Head;
import com.wallet.bo.wallets.pojo.ISLoan;
import com.wallet.bo.wallets.pojo.JudmentLoanState;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.pojo.WalletLoanLog;
import com.wallet.bo.wallets.ui.activity.CommonClientWebActivity;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.FeedbackActivity;
import com.wallet.bo.wallets.ui.activity.LoanActivity;
import com.wallet.bo.wallets.ui.activity.LoanLogActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.MyBankActivity;
import com.wallet.bo.wallets.ui.activity.MyMessageActivity;
import com.wallet.bo.wallets.ui.activity.MyWalletActivity;
import com.wallet.bo.wallets.ui.activity.MyWalletCloseActivity;
import com.wallet.bo.wallets.ui.activity.OpenLoanActivity;
import com.wallet.bo.wallets.ui.activity.SetActivity;
import com.wallet.bo.wallets.ui.activity.WeActivity;
import com.wallet.bo.wallets.ui.weiget.SlideView;
import com.wallet.bo.wallets.ui.weiget.riv.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/7/24 11:15
 * email:ggband520@163.com
 * desc:我的
 */

public class SlideMineFragment extends BaseFragment {


    @BindView(R.id.ivSetAvatar)
    RoundedImageView imHead;
    @BindView(R.id.tv_set_nick)
    TextView tvUserName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.stuff_container)
    SlideView stuffContainer;
    @BindView(R.id.stuff_container1)
    SlideView stuffContainer1;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        setLoginState();
    }

    @Override
    public void setViewUp() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_slide_mine;
    }

    @OnClick({R.id.tv_mybank, R.id.tv_mywallete, R.id.tv_myloan, R.id.tv_mymessage,
            R.id.tv_help, R.id.tv_myopnion, R.id.tv_we, R.id.collapsing_toolbar, R.id.toolbar, R.id.tv_mineInfo})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }

        switch (view.getId()) {

            case R.id.tv_mybank:
                NavigationLogin.navigation2Login(activity, new Intent(activity, MyBankActivity.class));
                break;

            case R.id.tv_mywallete:
                navigationWallete();
                break;

            case R.id.tv_myloan:
                //  new DisOpenDialog(context).show();
                NavigationLogin.navigation2Login(activity, new Intent(activity, LoanLogActivity.class));

                break;
            case R.id.tv_mymessage:
                NavigationLogin.navigation2Login(activity, new Intent(activity, MyMessageActivity.class));

                break;
            case R.id.tv_help:
                Intent intentHelp = new Intent(activity, CommonClientWebActivity.class);
                intentHelp.putExtra(CommonWebActivity.TITLE, "帮助中心");
                intentHelp.putExtra(CommonWebActivity.URL, URL.HELPCENTER);
                startActivity(intentHelp);

                break;
            case R.id.tv_myopnion:
                NavigationLogin.navigation2Login(activity, new Intent(activity, FeedbackActivity.class));

                break;
            case R.id.tv_we:
                startActivity(new Intent(activity, WeActivity.class));
                break;

            case R.id.collapsing_toolbar:
            case R.id.toolbar:
            case R.id.tv_mineInfo:
                NavigationLogin.navigation2Login(activity, new Intent(activity, SetActivity.class));

                break;

        }

    }

    private void navigationWallete() {

        if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
            Intent intent = new Intent(activity, MyWalletActivity.class);
            intent.putExtra("toWallet", true);
            NavigationLogin.navigation2Login(activity, intent);
        } else {
            mainDialog.show();


            //判断是否填写个人信息----------------------
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("uid", MyApplication.getLogin().getUserid());
            httpLoader.isLoan(stringMap, new ApiBaseResponseCallback<ISLoan>() {

                @Override
                public void onSuccessful(ISLoan isLoan) {
                    Log.e("isloan",isLoan.getMessage()+"   "+isLoan.getByborrow()+"  "+isLoan.getErrorcode());
                        if(isLoan.getErrorcode()!=0){
                            if(isLoan.getErrorcode() == 10001){
                                startActivity(new Intent(activity, OpenLoanActivity.class));
                                mainDialog.dismiss();
                            }else {
                                isLoan(); //填写个人信息后 判断积分数是否满足借款
                            }
                        }
                }

                @Override
                public void onFailure(String msg) {
                    toastShow(msg);
                }

                @Override
                public void onFinish() {
                }
            });
            //判断是否填写个人信息----------------------



        }


    }

    //填写个人信息后 判断积分数是否满足借款
    private void isLoan() {
        httpLoader.getLoanScore(MyApplication.getLogin().getUserid(), new ApiBaseResponseCallback<JudmentLoanState>() {
            @Override
            public void onSuccessful(JudmentLoanState judmentLoanState) {

                if (judmentLoanState.isLoan())
                    NavigationLogin.navigation2Login(activity, new Intent(activity, MyWalletActivity.class));
                else
                    NavigationLogin.navigation2Login(activity, new Intent(activity, MyWalletCloseActivity.class));
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(LoginOut loginOut) {
        setLoginState();
    }


    @Subscribe
    public void onEventMainThread(WalletLoanLog walletLoanLog) {
        navigationWallete();
    }

    @Subscribe
    public void onEventMainThread(Head head) {
        setLoginState();
    }


    private void setLoginState() {
        String urlHeader = "";
        if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
            tvUserName.setText("未登录");
            urlHeader = "https://www.baidu.com/";
        } else {
            tvUserName.setText(MyApplication.getLogin().getPhone() == null ? "" : TextUtils.dosubtext(MyApplication.getLogin().getPhone()));
            urlHeader = MyApplication.getLogin().getHead_img();
        }
        Picasso.with(context).load(urlHeader).placeholder(R.drawable.im_myhead).error(R.drawable.im_myhead).into(imHead);
        final RequestCreator creator = Picasso.with(context).load(urlHeader + "?time=" + new Date().getTime()).placeholder(R.drawable.im_myhead).error(R.drawable.im_myhead);
        creator.into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = ImageUtils.bitmap2Drawable(getResources(), ImageUtils.fastBlur(bitmap, 1, 22, true));
                collapsingToolbar.setBackground(drawable);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
    }


}