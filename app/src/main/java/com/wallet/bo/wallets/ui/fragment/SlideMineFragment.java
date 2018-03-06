package com.wallet.bo.wallets.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.DpUtils;
import com.wallet.bo.wallets.Utils.ImageUtils;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.databinding.FragmentSlideMineBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Head;
import com.wallet.bo.wallets.pojo.ISLoan;
import com.wallet.bo.wallets.pojo.JudmentLoanState;
import com.wallet.bo.wallets.pojo.LoanLog;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.pojo.WalletLoanLog;
import com.wallet.bo.wallets.ui.activity.CommonClientWebActivity;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.FeedbackActivity;
import com.wallet.bo.wallets.ui.activity.LoanLogActivity;
import com.wallet.bo.wallets.ui.activity.LogingActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.MyBankActivity;
import com.wallet.bo.wallets.ui.activity.MyMessageActivity;
import com.wallet.bo.wallets.ui.activity.MyWalletActivity;
import com.wallet.bo.wallets.ui.activity.MyWalletCloseActivity;
import com.wallet.bo.wallets.ui.activity.OpenLoanActivity;
import com.wallet.bo.wallets.ui.activity.SetActivity;
import com.wallet.bo.wallets.ui.activity.WeActivity;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.HashMap;
import java.util.Map;


/**
 * author:ggband
 * date:2017/7/24 11:15
 * email:ggband520@163.com
 * desc:我的
 */

public class SlideMineFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener{

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;//定义的滑动距离    大于25开始头像隐藏的动画

    private boolean mIsAvatarShown = true; //头像显示隐藏
    private int mMaxScrollSize;   //最大滑动距离
    private  Map<String, Target> mTargetMap=new HashMap<>();
    private FragmentSlideMineBinding slideMineBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        slideMineBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_slide_mine,container,false);
        return slideMineBinding.getRoot();
    }

    //一些初始化和监听设置
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initShow();//是否显示我的借款  银行卡  帮助中心
        slideMineBinding.setMineclick(this);//设置监听
    }

    @Override
    public void onResume() {
        super.onResume();
        initShow();//是否显示我的借款  银行卡  帮助中心
        setLoginState();

    }

    @Override
    public void setViewUp() {
    }


    //点击事件
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }

        switch (view.getId()) {

            case R.id.tv_mybank://银行卡
                //判断是否登陆
                boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
                if (!isLogin){
                    activity.startActivity(new Intent(activity,LogingActivity.class));
                    return;
                }

                if(MyApplication.getLogin()==null||MyApplication.getLogin().getUserid()==null){
                    toastShow("用户信息异常");
                    return;
                }
               //获取银行卡信息
                getBankData();
                break;

            case R.id.tv_mywallete://我的借款
                navigationWallete();
                break;

            case R.id.tv_myloan://浏览记录
                //  new DisOpenDialog(context).show();
                NavigationLogin.navigation2Login(activity, new Intent(activity, LoanLogActivity.class));

                break;
            case R.id.tv_mymessage://我的消息
                NavigationLogin.navigation2Login(activity, new Intent(activity, MyMessageActivity.class));

                break;
            case R.id.tv_help://帮助中心
                Intent intentHelp = new Intent(activity, CommonClientWebActivity.class);
                intentHelp.putExtra(CommonWebActivity.TITLE, "帮助中心");
                intentHelp.putExtra(CommonWebActivity.URL, URL.HELPCENTER);
                startActivity(intentHelp);

                break;
            case R.id.tv_myopnion://反馈意见
                NavigationLogin.navigation2Login(activity, new Intent(activity, FeedbackActivity.class));

                break;
            case R.id.tv_we://关于我们
                startActivity(new Intent(activity, WeActivity.class));
                break;

            case R.id.collapsing_toolbar:
            case R.id.toolbar:
            case R.id.tv_mineInfo://个人信息
                NavigationLogin.navigation2Login(activity, new Intent(activity, SetActivity.class));
                break;

        }

    }

    //获取银行卡信息
    private void getBankData() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.isLoan(stringMap, new ApiBaseResponseCallback<ISLoan>() {
            @Override
            public void onSuccessful(ISLoan isLoan) {
                if (isLoan.getErrorcode()!=10001){
                    NavigationLogin.navigation2Login(activity, new Intent(activity, MyBankActivity.class));
                }else {
                    toastShow("请先填写基本信息");
                    Intent intent=new Intent(activity,OpenLoanActivity.class);
                    intent.putExtra("product","product");
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(String msg) {
                if (msg!=null)
                    toastShow(msg.toString());
            }
            @Override
            public void onFinish() {}
        });
    }

    //点击我的借款 判断是否登陆  判断是否可借款
    private void navigationWallete() {
         //没有登录 去登录
        if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
            Intent intent = new Intent(activity, MyWalletActivity.class);
            intent.putExtra("toWallet", true);
            NavigationLogin.navigation2Login(activity, intent);
        } else {

            if (MyApplication.getLogin()==null)
                return;
            mainDialog.show();
            //判断是否填写个人信息----------------------
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("uid", MyApplication.getLogin().getUserid());
            httpLoader.isLoan(stringMap, new ApiBaseResponseCallback<ISLoan>() {
                @Override
                public void onSuccessful(ISLoan isLoan) {
                    Log.e("isloan", isLoan.getMessage() + "   " + isLoan.getByborrow() + "  " + isLoan.getErrorcode());

                    if (isLoan.getByborrow() == 1) {
                        NavigationLogin.navigation2Login(activity, new Intent(activity, MyWalletActivity.class));
                        mainDialog.dismiss();
                    } else {
                        switch (isLoan.getErrorcode()) {
                            case 10001://填写基本信息
                                startActivity(new Intent(activity, OpenLoanActivity.class));
                                break;
                            case 10002://积分不够
                                startActivity(new Intent(activity, MyWalletCloseActivity.class));
                                break;
                            case 10003://继续认证
                                toastShow(isLoan.getMessage());
                                break;
                        }
                        mainDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(String msg) {

                    toastShow(msg);
                    if(mainDialog.isShowing()){
                        mainDialog.dismiss();
                    }
                }

                @Override
                public void onFinish() {
                    mainDialog.dismiss();
                }
            });
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
        slideMineBinding.waveline.stop();
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


    //登陆和未登录状态  以及背景图片的动画
    private void setLoginState() {
        String urlHeader = "";
           //防止出现Fragment not attached to Activity        if(isAdded())
            if(isAdded())
                slideMineBinding.tvSetNick.setTextColor(getResources().getColor(R.color.color_text_02));
        if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
            slideMineBinding.tvSetNick.setText("未登录");
            urlHeader = "https://www.baidu.com/";
            //未登录状态为默认背景
                slideMineBinding.profileBackdrop.setBackgroundResource(R.drawable.bg_credit_level);
            //设置头像
            Picasso.with(context)
                    .load(urlHeader)
                    .placeholder(R.drawable.im_myhead)
                    .error(R.drawable.im_myhead)
                    .into(slideMineBinding.ivSetAvatar);

            //会员标志
//            Picasso.with(context).load(R.mipmap.normal).into(slideMineBinding.slideIv);
            slideMineBinding.slideIv.setVisibility(View.GONE);
        } else {
            slideMineBinding.tvSetNick.setText(MyApplication.getLogin().getPhone() == null ? "" : TextUtils.dosubtext(MyApplication.getLogin().getPhone()));
            urlHeader = MyApplication.getLogin().getHead_img();
            //设置头像
            Picasso.with(context).load(urlHeader)
                    .placeholder(R.drawable.im_myhead)
                    .resize(DpUtils.dpToPx(activity,80),DpUtils.dpToPx(activity,80))
                    .error(R.drawable.im_myhead)
                    .into(slideMineBinding.ivSetAvatar);

             /**
              * 这里有个问题（第一次进入只执行onPrepareLoad方法  并不会执行图片加载成功的方法）
              * 解决方法是把 Target 通过强引用（比如类的属性或者Map之类）保存起来
              * */

             //设置背景模糊效果
            Target target=new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    if(isAdded()){
                        Drawable drawable = ImageUtils.bitmap2Drawable(getResources(), ImageUtils.fastBlur(bitmap, 1, 22, true));
                            slideMineBinding.profileBackdrop.setBackground(drawable);
                    }

                    Log.e("userimg", "成功");
                }
                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                        slideMineBinding.profileBackdrop.setBackgroundResource(R.drawable.bg_credit_level);
                    Log.e("userimg", "失败");
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.e("userimg", "onPrepareLoad");
                }
            };
            mTargetMap.put("key", target);
            Picasso.with(context).load(urlHeader)
                    .into(mTargetMap.get("key"));


            //选择会员logo
            slideMineBinding.slideIv.setVisibility(View.VISIBLE);
            switch (MyApplication.getLogin().getGrade()){
                case "1":
                    Picasso.with(context).load(R.mipmap.normal).into(slideMineBinding.slideIv);
                    break;
                case "2":
                    Picasso.with(context).load(R.mipmap.silver).into(slideMineBinding.slideIv);
                    break;
                case "3":
                    Picasso.with(context).load(R.mipmap.gold).into(slideMineBinding.slideIv);
                    break;
                case "4":
                    Picasso.with(context).load(R.mipmap.diamond).into(slideMineBinding.slideIv);
                    break;
            }


        }





        slideMineBinding.appBarLayout.addOnOffsetChangedListener(this);//设置滑动监听
        mMaxScrollSize = slideMineBinding.appBarLayout.getTotalScrollRange();//获取最大滑动距离

        slideMineBinding.waveline.waveAnim();
        slideMineBinding.collapsingToolbar.setTitle("我的");
        slideMineBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//上拉结束文字居中
        slideMineBinding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//上啦结束字体为白色
        slideMineBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.TOP|Gravity.LEFT);//我的   开始的位置在“上左”
        if(isAdded())
            slideMineBinding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.transparent));
    }


    //遮挡后重现
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initShow();//是否显示我的借款  银行卡  帮助中心
        }
    }

    //是否显示我的借款  银行卡  帮助中心
    private void initShow() {
//        HashMap<String, String> map = new HashMap<>();
//        httpLoader.UserMenuManagement(map, new ApiBaseResponseCallback<String>() {
//            @Override
//            public void onSuccessful(String menuBeanList) {
//                if (menuBeanList != null) {
                    int isAppStore=SharedPreferencesUtil.getInstance().getInt("isAppStore",0);
                    Log.e("UserMenu", ""+isAppStore);
                    if (isAppStore==1) {  //1为审核状态  0为上线状态
                        slideMineBinding.tvHelp.setVisibility(View.GONE);
                        slideMineBinding.tvMybank.setVisibility(View.GONE);
                        slideMineBinding.tvMywallete.setVisibility(View.GONE);
                    } else {
                        slideMineBinding.tvHelp.setVisibility(View.VISIBLE);
                        slideMineBinding.tvMybank.setVisibility(View.VISIBLE);
                        slideMineBinding.tvMywallete.setVisibility(View.VISIBLE);
                    }

//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                if (!android.text.TextUtils.isEmpty(msg)) {
//                    Log.e("UserMenuFailure", msg.toString());
//                    toastShow(msg);
//                }
//
//            }
//
//            @Override
//            public void onFinish() {
//                Log.e("UserMenuFinish", "完成");
//            }
//        });

    }


    //appbar 滑动监听
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        //Math.abs  取绝对值  没有负数返回的是double型
        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        //判断是否滑动大于25  并且头像显示（mIsAvatarShown）  隐藏头像
        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            slideMineBinding.minRl.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(500)
                    .start();
            slideMineBinding.materialupTitleContainer.animate().scaleX(0).scaleY(0).setDuration(300).start();
        }

        //相反显示头像
        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;
            slideMineBinding.minRl.animate()
                    .scaleY(1).scaleX(1)
                    .start();

            slideMineBinding.materialupTitleContainer.animate().scaleX(1).scaleY(1).start();
        }
    }
}
