package com.wallet.bo.wallets.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.ppdai.loan.PPDLoanAgent;
import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.GalleryAdapter;
import com.wallet.bo.wallets.databinding.FragmentLoanBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.imp.IListener;
import com.wallet.bo.wallets.imp.ListenerManager;
import com.wallet.bo.wallets.pojo.ButImag;
import com.wallet.bo.wallets.pojo.CenterImg;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Gallery;
import com.wallet.bo.wallets.pojo.LoanRepayBean;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.pojo.Notice;
import com.wallet.bo.wallets.pojo.NoticeAndGallery;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.ui.activity.CommonClientWebActivity;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.LoanMoneyActivity;
import com.wallet.bo.wallets.ui.activity.LogingActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.OpenLoanActivity;
import com.wallet.bo.wallets.ui.activity.RepaymentActivity;
import com.wallet.bo.wallets.ui.activity.VipClubActivity;
import com.wallet.bo.wallets.ui.weiget.citypicker.citypickerview.widget.wheel.OnePicker;
import com.wallet.bo.wallets.ui.weiget.gallery.EcoGalleryAdapterView;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * author:ggband
 * date:2017/8/8 9:05
 * email:ggband520@163.com
 * desc:首页借款 compile 'com.sunfusheng: loanBinding.marqueeView:1.3.2'
 */

public class LoanFragment extends BaseFragment implements IListener{
    private String number = "";

    private String cheatsUrl = "";
    private String cheatsTitle = "";
    private String helpUrl = "";
    private String helpTitle = "";
    private FragmentLoanBinding loanBinding;
    private String isload="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loanBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_loan,container,false);
        return loanBinding.getRoot();
    }

    //设置监听
    @Override
    public void initView() {
        //注册监听器
        loanBinding.setOnclick(this);//设置监听
        //注册观察者
        ListenerManager.getInstance().registerListtener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
    }

    //获取借还款信息
    private void getInfo() {
        boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);
        if (isLogin) {
            //获取借还款信息

            httpLoader.loanrepa(MyApplication.getLogin().getUserid(), new ApiBaseResponseCallback<LoanRepayBean>() {
                @Override
                public void onSuccessful(LoanRepayBean loanRepayBean) {
                    Log.e("loanrepa", loanRepayBean.toString());
                    if (loanRepayBean.getBorrowState() == 0) {//借款
                        if (loanBinding.linBorrow!=null&& loanBinding.linRepay!=null){
                            loanBinding.linBorrow.setVisibility(View.VISIBLE);
                             loanBinding.linRepay.setVisibility(View.GONE);
                             loanBinding.btPlay.setText("立即借款");
                             isload="0";
                        }


                    } else if (loanRepayBean.getBorrowState() == 1) {//还款
                        if (loanBinding.linBorrow!=null&& loanBinding.linRepay!=null){
                            loanBinding.linBorrow.setVisibility(View.GONE);
                             loanBinding.linRepay.setVisibility(View.VISIBLE);
                             loanBinding.tvRepayMoney.setText(loanRepayBean.getMoney() + "元");
                            loanBinding.tvTerm.setText(loanRepayBean.getOrderExpirationDate() + "还清");
                             loanBinding.btPlay.setText("立即还款");
                            number = loanRepayBean.getNumber();
                            isload="1";
                        }

                    }


                }

                @Override
                public void onFailure(String msg) {
                    if (msg!= null){
                            toastShow(msg.toString());
                            Log.e("loanrepaonFailure", msg.toString());
                }
                if( loanBinding.refreshLayout!=null)
                     loanBinding.refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFinish() {
                    Log.e("loanrepaonFinish", "完成" + "   " + MyApplication.getLogin().getUserid());
                }
            });


        }
    }


    //解析广播数据
    private void initNotice(List<Notice> notices) {
        if (notices == null)
            return;
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < notices.size(); i++) {
            strings.add(notices.get(i).getContent());
        }
        if( loanBinding.marqueeView!=null)
         loanBinding.marqueeView.startWithList(strings);


    }

    //设置背景
    private void initBackground(String img) {
        if(loanBinding.ivBackground!=null&&context!=null)
        Picasso.with(context).load(img).into(loanBinding.ivBackground);
    }

    //一些初始化工作
    @Override
    public void setViewUp() {
        if (loanBinding.scrollView != null) {//解决ScroView和 loanBinding.refreshLayout冲突
            loanBinding.scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if ( loanBinding.refreshLayout != null) {
                         loanBinding.refreshLayout.setEnabled(loanBinding.scrollView.getScrollY() == 0);
                    }
                }
            });
        }
        if ( loanBinding.refreshLayout==null)
            return;
       
         loanBinding.refreshLayout.setColorSchemeColors(
                Color.parseColor("#ff00ddff"),
                Color.parseColor("#ff99cc00"),
                Color.parseColor("#ffffbb33"),
                Color.parseColor("#ffff4444"));

         loanBinding.refreshLayout.setRefreshing(true);
         loanBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取本页数据
                getNoticeAndGallery();
            }
        });
        //获取本页数据
        getNoticeAndGallery();

    }


    //点击事件
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.tv_money:
                OnePicker cityPicker = new OnePicker.Builder(activity)
                        .textSize(20)
                        .titleTextColor("#000000")
                        .title("选择金额")
                        .backgroundPop(0xa0000000)
                        .province("6000")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(false)
                        .dats(new String[]{"2000", "4000", "6000", "10000"})
                        .visibleItemsCount(7)
                        .build();

                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new OnePicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //防止出现Fragment not attached to Activity
                        if(isAdded())
                           loanBinding.tvMoney.setText(String.format(getResources().getString(R.string.rmb), citySelected[0]));
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                break;
            case R.id.bt_cheats:
                Log.e("loan_秘籍",cheatsTitle     +"      "+cheatsUrl);
                Intent intentMj = new Intent(activity, CommonClientWebActivity.class);
                intentMj.putExtra(CommonWebActivity.TITLE, cheatsTitle);
                intentMj.putExtra(CommonWebActivity.URL, cheatsUrl);
                startActivity(intentMj);
                break;
            case R.id.bt_help:
                Log.e("loan_帮助",helpTitle     +"      "+helpUrl);
                Intent intentHelp = new Intent(activity, CommonClientWebActivity.class);
                intentHelp.putExtra(CommonWebActivity.TITLE, helpTitle);
                intentHelp.putExtra(CommonWebActivity.URL, helpUrl);
                startActivity(intentHelp);
                break;
            case R.id.bt_play:
                if (MyApplication.isOpen()) {
                    if ( loanBinding.btPlay.getText().equals("立即还款")) {
                        Intent intent = new Intent(activity, RepaymentActivity.class);
                        intent.putExtra("number", number);
                        NavigationLogin.navigation2Login(activity, intent);
                    } else if ( loanBinding.btPlay.getText().equals("立即借款")) {
                        if(SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)){
                            EventBus.getDefault().post(new Navagation(2));
                        }else {
                            startActivity(new Intent(activity,LogingActivity.class));
                            toastShow("请先登录");
                        }

                    }

                } else {
                    NavigationLogin.navigation2Login(activity, new Intent(activity, OpenLoanActivity.class));
                }
                break;


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK)
            loanBinding.tvMoney.setText(data.getStringExtra(LoanMoneyActivity.TAG));
    }


    //获取本页数据
    private void getNoticeAndGallery() {
        httpLoader.noticAndGallery(new HashMap<String, String>(), new ApiBaseResponseCallback<NoticeAndGallery>() {
            @Override
            public void onSuccessful(final NoticeAndGallery noticeAndGallery) {
                Log.e("getdata", noticeAndGallery.toString());
                //保存是否开启上传应用的状态   0未上传状态  1表示上传状态
                SharedPreferencesUtil.getInstance().putInt("isAppStore",noticeAndGallery.getIsAppStore());
                if(noticeAndGallery.getImgList()!=null)
                initGallery(noticeAndGallery.getImgList(),noticeAndGallery.getButImag().getState());
                if (noticeAndGallery.getScrollingMessage()!=null)
                initNotice(noticeAndGallery.getScrollingMessage());
                if (noticeAndGallery.getHeaderImg().getImg()!=null)
                initBackground(noticeAndGallery.getHeaderImg().getImg());
                if(noticeAndGallery.getCentImgList()!=null)
                initHelp(noticeAndGallery.getCentImgList());//适配帮助块的数据
                if(noticeAndGallery.getButImag()!=null)
                intiImg(noticeAndGallery.getButImag(),noticeAndGallery.getIsAppStore());//是否显示图片

            }


            @Override
            public void onFailure(String msg) {
                if (msg!=null)
                    toastShow(msg.toString());
                if ( loanBinding.refreshLayout!=null)
                     loanBinding.refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFinish() {
                if ( loanBinding.refreshLayout!=null)
                 loanBinding.refreshLayout.setRefreshing(false);
            }
        });


    }

    //是否显示图片
    private void intiImg(ButImag butImg,int isAppStore) {
        //判断首页借款部分是否用图片遮挡butImg.getState()
        if (isAppStore== 1) {//1: 显示图片  0：不显示
            Picasso.with(context).load(butImg.getImg()).into(loanBinding.ivImg);
            loanBinding.ivImg.setVisibility(View.VISIBLE);
            loanBinding.linCenter.setVisibility(View.GONE);
        } else {
            loanBinding.ivImg.setVisibility(View.GONE);
            loanBinding.linCenter.setVisibility(View.VISIBLE);
        }

        loanBinding.tvText1.setText(butImg.getText1());
        loanBinding.tvAmount.setText(butImg.getText2());
        loanBinding.tvText3.setText(butImg.getText3());
    }

    //适配帮助块的数据
    private void initHelp(List<CenterImg> list) {
        //秘籍
        if( loanBinding.tvCheats!=null&&list.get(0).getTitles()!=null)
         loanBinding.tvCheats.setText(list.get(0).getTitles());
        if (loanBinding.ivCheats!=null)
        Picasso.with(context).load(list.get(0).getImg()).resize(50, 50).into(loanBinding.ivCheats);
        cheatsUrl = list.get(0).getUrl();
        cheatsTitle = list.get(0).getTitles();
        if (TextUtils.isEmpty(list.get(0).getUrl()) || list.get(0).getUrl().equals("")) {
            if(loanBinding.btCheats!=null)
            loanBinding.btCheats.setEnabled(false);
        }else {
            if(loanBinding.btCheats!=null)
                loanBinding.btCheats.setEnabled(true);
        }


        //帮助
        if( loanBinding.tvHelp!=null)
         loanBinding.tvHelp.setText(list.get(1).getTitles());
        if (loanBinding.ivHelp!=null)
        Picasso.with(context).load(list.get(1).getImg()).resize(50, 50).into(loanBinding.ivHelp);
        helpUrl = list.get(1).getUrl();
        helpTitle = list.get(1).getTitles();
        if (TextUtils.isEmpty(list.get(1).getUrl()) || list.get(1).getUrl().equals("")) {
            if (loanBinding.ivHelp!=null)
              loanBinding.btHelp.setEnabled(false);
        }else {
            if (loanBinding.ivHelp!=null)
                loanBinding.btHelp.setEnabled(true);
        }

    }

    //首页的banner图
    private void initGallery(final List<Gallery> galleries, final int status) {
        if (loanBinding.wg == null)
            return;
        loanBinding.wg.setAdapter(new GalleryAdapter(context, galleries));
        if (galleries != null && galleries.size() != 0) {
            loanBinding.wg.setSelection(Integer.MAX_VALUE / 2 + 1);
            loanBinding.wg.setOnItemClickListener(new EcoGalleryAdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(EcoGalleryAdapterView<?> parent, View view, int position, long id) {
                    //根据status判断是否可以跳转
                    if (status==0){

                        if(galleries.get(position % galleries.size()).getTitle().equals("拍拍贷")){
                            if (MyApplication.getLogin()==null||MyApplication.getLogin().getPhone()==null)
                                return;
                            PPDLoanAgent.getInstance().initLaunch(activity, MyApplication.getLogin().getPhone());
                            return;
                        }
                        Intent intent = new Intent(activity, CommonWebActivity.class);
                        intent.putExtra(CommonWebActivity.TITLE, galleries.get(position % galleries.size()).getTitle());
                        intent.putExtra(CommonWebActivity.URL, galleries.get(position % galleries.size()).getUrl());
                        startActivity(intent);
                    }

                }
            });
        }
    }

    //去重影
    @Override
    public void onHiddenChanged(boolean hidden) {//去除重影
        super.onHiddenChanged(hidden);
        if (!hidden) {
             loanBinding.marqueeView.startFlipping();
        } else {
             loanBinding.marqueeView.stopFlipping();
        }
        onPause();
    }


    //观察者模式   监听局部刷新
    @Override
    public void notifyAllActivity(String str) {
        if (!str.isEmpty()&&!str.equals("")){
            Log.e("isloand",str);
            //不一样就刷新
            if (!str.equals(isload)){
                getInfo();
            }

        }

    }

    //销毁后注销掉观察者
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ListenerManager.getInstance().unRegisterListener(this);
    }
}
