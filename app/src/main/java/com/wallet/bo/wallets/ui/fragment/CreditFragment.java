package com.wallet.bo.wallets.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;
import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.GsonUtils;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.CreditItemBinding;
import com.wallet.bo.wallets.databinding.FragmentCreditBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Credit;
import com.wallet.bo.wallets.pojo.Credits;
import com.wallet.bo.wallets.pojo.ISLoan;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.pojo.UserInfo;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.LoanActivity;
import com.wallet.bo.wallets.ui.activity.LogingActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.MyWalletCloseActivity;
import com.wallet.bo.wallets.ui.activity.OpenLoanActivity;
import com.wallet.bo.wallets.ui.activity.ZmWebActivity;
import com.wallet.bo.wallets.ui.weiget.hp.HorizontalPageLayoutManager;
import com.wallet.bo.wallets.ui.weiget.hp.PagingScrollHelper;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import cn.fraudmetrix.octopus.aspirit.main.OctopusManager;
import cn.fraudmetrix.octopus.aspirit.main.OctopusTaskCallBack;


/**
 * author:ggband
 * date:2017/8/8 9:05
 * email:ggband520@163.com
 * desc:信用
 */
//芝麻信用 url接口地址    ApiUrl第一步检测请求地址，这个如果是空表示认证通过了，然后就直接去请求url


public class CreditFragment extends BaseFragment {
    private CommAdapter<Credit> creditCommAdapter;
    private List<Credit> credits = new ArrayList<>();
    private List<Credit> mustCredits = new ArrayList<>();
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private String type = "";
    private boolean isOpen;
    private Credits credit;
    private FragmentCreditBinding creditBinding;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        creditBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_credit,container,false);
        return creditBinding.getRoot();
    }


    //一些初始化操作
    @Override
    public void initView() {
   
        EventBus.getDefault().register(this);
        creditBinding.setOnclick(this);//设置监听
        creditBinding.refreshLayout.setColorSchemeColors(
                Color.parseColor("#ff00ddff"),
                Color.parseColor("#ff99cc00"),
                Color.parseColor("#ffffbb33"),
                Color.parseColor("#ffff4444"));

        requestCredit();
    }

    @Override
    public void onResume() {
        super.onResume();
        initCredit();

    }

    //设置监听 适配器等  item的点击事件
    @Override
    public void setViewUp() {


        creditCommAdapter = new CommAdapter<Credit>(credits, -1, R.layout.credit_item) {

            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, Credit credit, ViewHolder holder) {

                CreditItemBinding creditItemBinding = (CreditItemBinding) binding;
                Picasso.with(context)
                        .load(credit.getImg())
//                        .placeholder(R.drawable.im_myhead)
                        .error(R.drawable.im_myhead)
                        .into(creditItemBinding.imHead);

                creditItemBinding.tvTitle.setText(credit.getName());
            }
        };
        creditBinding.rvCredit.setAdapter(creditCommAdapter);
        scrollHelper.setUpRecycleView(creditBinding.rvCredit);
        if ( creditBinding.scrollView != null) {//解决ScroView和creditBinding.refreshLayout冲突
             creditBinding.scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (creditBinding.refreshLayout != null) {
                        creditBinding.refreshLayout.setEnabled( creditBinding.scrollView.getScrollY() == 0);
                    }
                }
            });
        }

        creditBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false))
                    creditBinding.refreshLayout.setRefreshing(false);
                else
                    requestCredit();

            }
        });


        creditCommAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, final long position) {
                if (AntiShake.check(view.getId())) {    //判断是否多次点击
                    return;
                }
                if (!isOpen) {
                    CreditFragment.this.startActivity(new Intent(activity, OpenLoanActivity.class));
                    return;
                }
                final Credit credit = credits.get((int) position);
                if (credit.getI() == 1)//已认证
                    return;
                Log.i(Config.LOGTAG, "name:" + credit.getName());
                Log.i(Config.LOGTAG, "Apiurl:" + credit.getApiUrl());
                Log.i(Config.LOGTAG, "Url:" + credit.getUrl());
                Log.e("credit", credit.toString());
                Log.e("credits", credits.toString());

                type = credit.getType();

                switch ((int) position) {
                    case 0://人脸识别
                        goFaceAuth(credit.getType());
                        break;
                    case 2://芝麻信用
                        if (credit.getApiUrl() == null || TextUtils.isEmpty(credit.getApiUrl())) {
                            requestZmCredit(credit.getUrl());
                            break;
                        }
                        Intent zMIntent = new Intent(activity, ZmWebActivity.class);
                        zMIntent.putExtra(ZmWebActivity.TITLE, credit.getName());
                        zMIntent.putExtra(ZmWebActivity.URL, credit.getApiUrl());
                        startActivityForResult(zMIntent, (int) position);
                        break;

                    case 3://淘宝
                        creditMoHe("005003");
                        break;
                    case 4://支付宝
                        creditMoHe("005004");
                        break;
                    case 1://运营商
                    case 5://京东
                    case 6://学信网
                    case 7://社保
                        Intent intent = new Intent(activity, CommonWebActivity.class);
                        intent.putExtra(CommonWebActivity.TITLE, credit.getName());
                        intent.putExtra(CommonWebActivity.URL, credit.getApiUrl());
                        startActivityForResult(intent, 100);
                        break;

                }


            }
        });


    }

    //人脸识别
    private void goFaceAuth(final String type) {
        AuthBuilder mAuthBuilder = new AuthBuilder(UUID.randomUUID().toString(), "606800ff-8646-454b-bf56-e35c02d8e785", "url", new OnResultListener() {
            @Override
            public void onResult(String s) {
                Log.e("goFaceAuth", "result:" + s);
                Map<String, String> stringMap = GsonUtils.GsonToMaps(s);
                stringMap.put("type", type);
                stringMap.put("uid", MyApplication.getLogin().getUserid());
                Log.e("goFaceAuth_",stringMap.toString());
                httpLoader.returnFace(stringMap, new ApiBaseResponseCallback<UserInfo>() {
                    @Override
                    public void onSuccessful(UserInfo userInfo) {
                        toastShow("识别成功");
                        requestCredit();
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                    }
                });
            }

        });
        mAuthBuilder.setUserId(MyApplication.getLogin().getUserid());
        //下文调用方法做为范例，请以对接文档中的调用方法为准
        mAuthBuilder.faceAuth(activity);
    }

    //初始化
    private void initHpager() {
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(2, 4);
        creditBinding.rvCredit.setLayoutManager(horizontalPageLayoutManager);
        scrollHelper.setUpRecycleView(creditBinding.rvCredit);
        scrollHelper.updateLayoutManger();
    }


    //初始化征信列表
    private void initCredit() {
        final boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
        if (!isLogin) {
            creditBinding.btLoan.setVisibility(View.VISIBLE);
            creditBinding.btLoan.setText("未登录");
            //原来的信用刻度表
//            holoCircularProgressBar.animate(holoCircularProgressBar, null, 0 * 0.001f, 1, 0);
            creditBinding.dvPregress.setCreditValueWithAnim(0);
            credits.clear();
            creditCommAdapter.notifyDataSetChanged();
            creditBinding.dvPregress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false))
                        return;
                    Intent intent = new Intent(activity, LogingActivity.class);
                    intent.putExtra("credit", true);
                    startActivity(intent);
                }
            });
            //原来的信用刻度表
//            holoCircularProgressBar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false))
//                        return;
//                    Intent intent = new Intent(activity, LogingActivity.class);
//                    intent.putExtra("credit", true);
//                    startActivity(intent);
//                }
//            });
            return;
        }
//        creditBinding.btLoan.setText("");
        //原来的信用刻度表
//        holoCircularProgressBar.setProgress(0);
//        creditBinding.dvPregress.setCreditValueWithAnim(0);

//        requestCredit();
    }


    //获取认证列表  获取认证积分等操作
    private void requestCredit() {

        creditBinding.refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                creditBinding.refreshLayout.setRefreshing(true);
            }
        });

        if (!creditBinding.refreshLayout.isRefreshing())
            creditBinding.refreshLayout.setRefreshing(true);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.credit(stringStringMap, new ApiBaseResponseCallback<Credits>() {
            @Override
            public void onSuccessful(Credits credits) {
                if(creditBinding.rvCredit!=null&&creditBinding.linError!=null&& creditBinding.linEmpty!=null&& creditBinding.linEmptyerror!=null){
                    creditBinding.rvCredit.setVisibility(View.VISIBLE);
                       creditBinding.linError.setVisibility(View.GONE);
                     creditBinding.linEmpty.setVisibility(View.GONE);
                     creditBinding.linEmptyerror.setVisibility(View.GONE);
                }

                Log.e("credits", credits.toString());
//                if (credits.getJudgmentLoanStatus().isLoan())
//                    creditBinding.btLoan.setText("立即借款");

                String msg = credits.getJudgmentLoanStatus().getMsg();
                if (creditBinding.tvCreditDescribe != null)
                    creditBinding.tvCreditDescribe.setText(msg);//设置额度提示
                if (creditBinding.tvDescribe != null)
                    creditBinding.tvDescribe.setText(credits.getJudgmentLoanStatus().getDescribe());//设置描述
                if (creditBinding.tvPrompt != null)
                    creditBinding.tvPrompt.setText(credits.getJudgmentLoanStatus().getPrompt());//设置提示
                
                creditBinding.btLoan.setVisibility(View.VISIBLE);
                int isjump = credits.getJudgmentLoanStatus().getIsjump();
                switch (isjump) {
                    case 0:
                        creditBinding.btLoan.setText("继续认证");
                        break;
                    case 1:
                        creditBinding.btLoan.setText("立即借款");
                        break;
                    case 2:
                        creditBinding.btLoan.setText("借款匹配");
                        break;
                    case 3:
                        creditBinding.btLoan.setText("立即认证");
                }


                CreditFragment.this.credits.clear();
                credit = credits;
                initHpager();
                for (Credit credit : credits.getInfo()
                        ) {
                    CreditFragment.this.credits.add(credit);
                    CreditFragment.this.mustCredits.clear();
                    if (credit.getId().equals("1") || credit.getId().equals("2") || credit.getId().equals("5") || credit.getId().equals("6") || credit.getId().equals("14"))
                        CreditFragment.this.mustCredits.add(credit);

                }
                isOpen = credits.isBasicInformationState();
                creditCommAdapter.notifyDataSetChanged();
                //原来的信用刻度表
//                holoCircularProgressBar.animate(holoCircularProgressBar, null, credits.getJudgmentLoanStatus().getTotal() * 0.001f, 1000, credits.getJudgmentLoanStatus().getTotal());
                creditBinding.dvPregress.setCreditValueWithAnim(credits.getJudgmentLoanStatus().getTotal());
                Log.e("total",credits.getJudgmentLoanStatus().getTotal()+"");
            }

            @Override
            public void onFailure(String msg) {
                if(creditBinding.rvCredit==null||   creditBinding.linError==null|| creditBinding.linEmpty==null|| creditBinding.linEmptyerror==null)
                    return;
                if (msg != null) {

                    if (msg.equals("网络似乎出问题了")) {
                           creditBinding.linError.setVisibility(View.VISIBLE);
                         creditBinding.linEmpty.setVisibility(View.GONE);
                        creditBinding.rvCredit.setVisibility(View.GONE);
                         creditBinding.linEmptyerror.setVisibility(View.GONE);
                    } else if (msg.equals("已经没有啦")) {
                         creditBinding.linEmpty.setVisibility(View.VISIBLE);
                           creditBinding.linError.setVisibility(View.GONE);
                        creditBinding.rvCredit.setVisibility(View.GONE);
                         creditBinding.linEmptyerror.setVisibility(View.GONE);
                    }else {//内容错误的情况
                         creditBinding.linEmptyerror.setVisibility(View.VISIBLE);
                         creditBinding.linEmpty.setVisibility(View.GONE);
                           creditBinding.linError.setVisibility(View.GONE);
                        creditBinding.rvCredit.setVisibility(View.GONE);
                    }


                    toastShow(msg.toString());
                    Log.e("creditsFailure", msg.toString());
                }else {
                     creditBinding.linEmptyerror.setVisibility(View.VISIBLE);
                     creditBinding.linEmpty.setVisibility(View.GONE);
                       creditBinding.linError.setVisibility(View.GONE);
                    creditBinding.rvCredit.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFinish() {
                if (creditBinding.refreshLayout!=null)
                creditBinding.refreshLayout.setRefreshing(false);
            }
        });
    }


    //点击事件
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_loan://借款按钮
                if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
                    Intent intent = new Intent(activity, LogingActivity.class);
                    intent.putExtra("credit", true);
                    startActivity(intent);
                    return;
                }
                mainDialog.show();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("uid", MyApplication.getLogin().getUserid());
                httpLoader.isLoan(stringMap, new ApiBaseResponseCallback<ISLoan>() {
                    @Override
                    public void onSuccessful(ISLoan isLoan) {
                        Log.e("isloan", isLoan.getMessage() + "   " + isLoan.getByborrow() + "  " + isLoan.getErrorcode() + "  " + isLoan.getCan_borrow());
                        if (isLoan == null)
                            return;

                        if (isLoan.getByborrow() == 1) {//立即借款
                            Intent intent = new Intent(activity, LoanActivity.class);
                            intent.putExtra("canborrow", isLoan.getCan_borrow() + "");
                            startActivity(intent);
                            mainDialog.dismiss();
                        } else {

                            switch (isLoan.getErrorcode()) {
                                case 10001://填写基本信息
                                    startActivityForResult(new Intent(activity, OpenLoanActivity.class), 101);
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
                        if (mainDialog.isShowing()) {
                            mainDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                });

                break;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    //淘宝征信
    public void taoBaoCredit(int code, String taskId) {
//        if (data != null && data.hasExtra(Constants.OCTOPUS_TASK_RESULT_CODE)) {
//            int code = data.getIntExtra(Constants.OCTOPUS_TASK_RESULT_CODE, -1);
//            if (code == 0) {//code为0表示成功，f非0表示失败
//                String taskId = data.getStringExtra(Constants.OCTOPUS_TASK_RESULT_TASKID);//只有code为0时taskid才会有值。
        httpLoader.returnTaobao(MyApplication.getLogin().getUserid(), type, taskId, String.valueOf(code), new ApiBaseResponseCallback<Object>() {

            @Override
            public void onSuccessful(Object o) {
                requestCredit();
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
            }
        });
    }

    //淘宝征信
    private void creditMoHe(String code) {
        OctopusManager.getInstance().setPrimaryColorResId(R.color.app_theme);//设置导航背景
        OctopusManager.getInstance().setShowWarnDialog(false);//设置成false 不然sdk会报错
        OctopusManager.getInstance().getChannel(activity, code, new OctopusTaskCallBack() {
            @Override
            public void onCallBack(int code, String taskId) {
                String msg = "成功";
                if (code == 0) {//code为0表示成功，f非0表示失败
                    //String taskId = data.getStringExtra(OctopusConstants.OCTOPUS_TASK_RESULT_TASKID);//只有code为0时taskid才会有值。
                    msg += taskId;

                    taoBaoCredit(code, taskId);
                } else {
                    msg = "失败：" + code;
                }
                toastShow(msg);

            }
        });
    }



    //activity返回内容
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(Config.LOGTAG, "requestCode:" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {//魔合
                requestZmCredit(data.getStringExtra("url"));
            } else if (requestCode == 101) {//填写基本信息
                requestCredit();
            } else {//芝麻
                requestZmCredit(credits.get(requestCode).getUrl());
            }
        }

    }


    //芝麻信用
    private void requestZmCredit(String url) {

        httpLoader.returnZmCredit(url, new ApiBaseResponseCallback<Object>() {

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccessful(Object o) {
                requestCredit();
                toastShow("认证成功");
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
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
        requestCredit();
    }




}


