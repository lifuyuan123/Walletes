package com.wallet.bo.wallets.ui.activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.ClubItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.RefundBean;
import com.wallet.bo.wallets.pojo.VipBeans;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PtrDefaultHandlerWithLoadMore;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PullLayout;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

//会员俱乐部
public class VipClubActivity extends BaseSwipeActivity {

    @BindView(R.id.vip_title)
    EaseTitleBar vipTitle;
    @BindView(R.id.vip_rv)
    RecyclerView vipRv;
    @BindView(R.id.vip_pull)
    PullLayout vipPull;
    private CommAdapter<VipBeans.MenberBean> adapter;
    private List<VipBeans.MenberBean> vipBeanList = new ArrayList<>();

    private DefaultLoadingLayout loadingLayout;
    int page = 1;
    private View footerView;
    private TextView vipDescribe;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private boolean isnormal=true;//普通会员
    private boolean isFirst=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_vip_club;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isFirst){
            vipPull.post(new Runnable() {
                @Override
                public void run() {
                    vipPull.autoRefresh(true);
                }
            });
        }

    }

    @Override
    protected void initView() {
        //footview
        footerView = LayoutInflater.from(context).inflate(R.layout.club_footview, null);
        vipDescribe= (TextView) footerView.findViewById(R.id.vip_describe);
        footerView.setVisibility(View.GONE);
        //加载  错误  无数据控件
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, vipPull);

        //返回按钮
        vipTitle.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //初始化adapter
        adapter = new CommAdapter<VipBeans.MenberBean>(vipBeanList, -1, R.layout.club_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, final int position, final VipBeans.MenberBean menberBean, ViewHolder holder) {
                final ClubItemBinding itemBinding = (ClubItemBinding) binding;

                //判断卡片
                switch (position) {
                    case 0://银卡
                        //如果银卡都可以升级  那么用户就是普通用户
                        if (menberBean.getCanupgrade()==1){
                            isnormal=false;
                        }else {
                            isnormal=true;
                        }
                        itemBinding.clubItemTvFree.setVisibility(View.INVISIBLE);
                        itemBinding.clubItemTvBack.setVisibility(View.INVISIBLE);
                        Picasso.with(activity).load(menberBean.getImg()).error(R.mipmap.icon_silver_no).into(itemBinding.vipItemIvBack);
                        break;
                    case 1://金卡
                        itemBinding.clubItemTvBack.setVisibility(View.INVISIBLE);
                        Picasso.with(activity).load(menberBean.getImg()).error(R.mipmap.icon_gold_no).into(itemBinding.vipItemIvBack);
                        break;
                    case 2://钻石
                        Picasso.with(activity).load(menberBean.getImg()).error(R.mipmap.icon_diamonds).into(itemBinding.vipItemIvBack);
                        break;
                }

                //设置数据源
                itemBinding.setVip(menberBean);


                /**
                 *0  不可升级   / 1可升级   字体变成灰色
                 **/
                //不可升级情况
                if (menberBean.getCanupgrade()==0){
                    //修改暗掉的字体和标签颜色
                    if (position<=1){
                        //会员名字
                        itemBinding.clubItemTvViptype.setTextColor(getResources().getColor(R.color.color_text_03));
                        //金额和单位
                        itemBinding.clubItemTvRemoney.setTextColor(getResources().getColor(R.color.color_text_03));
                        itemBinding.clibItemTvUnit.setTextColor(getResources().getColor(R.color.color_text_03));
                        //圆角标签
                        itemBinding.clubItemTvCanborrow.setTextColor(getResources().getColor(R.color.color_text_03));
                        itemBinding.clubItemTvCanborrow.getDelegate().setStrokeColor(getResources().getColor(R.color.color_text_03));
                        itemBinding.clubItemTvFree.setTextColor(getResources().getColor(R.color.color_text_03));
                        itemBinding.clubItemTvFree.getDelegate().setStrokeColor(getResources().getColor(R.color.color_text_03));

                        //隐藏可升级tag
                        itemBinding.clubItemTvTag.setVisibility(View.INVISIBLE);
                    }
                        //钻石会员  设置为可退款
                        if(position==2){
                        itemBinding.clubItemTvTag.setText("可退款");
                        }
                        //隐藏优惠价格
                         itemBinding.clubItemTvMoney.setVisibility(View.INVISIBLE);
                         //实际价格
                         itemBinding.clubItemTvRemoney.setText(menberBean.getMem_money()+"");//实际价格Mem_money    差价money

                 //可升级情况
                }else {
                    //普通用户进入的时候在上面position=0时且可升级的请款下做了判断设为false，则不会显示优惠价格
                    //vip用户进入则不会进入银卡选项可升级的情况   ismormal还是为true  则显示优惠价格
                    if (isnormal){
                        //显示优惠价格
                        itemBinding.clubItemTvMoney.setVisibility(View.VISIBLE);
                    }
                    //可升级时显示差价
                    itemBinding.clubItemTvRemoney.setText(menberBean.getMoney()+"");

                }

                //设置叉掉的优惠价格
                itemBinding.clubItemTvMoney.setText(menberBean.getMem_money()+"元/年");
                itemBinding.clubItemTvMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//删除线
                itemBinding.clubItemTvMoney.getPaint().setAntiAlias(true);//抗锯齿

                //需要刷新  此方法必须执行在UI线程，当绑定的数据修改时更新视图
                itemBinding.executePendingBindings();

            }
        };


        vipRv.setLayoutManager(new LinearLayoutManager(this));
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        mHeaderAndFooterWrapper.addFootView(footerView);
        vipRv.setAdapter(mHeaderAndFooterWrapper);

        vipPull.setMode(PtrFrameLayout.Mode.REFRESH);
        vipPull.setPtrHandler(new PtrDefaultHandlerWithLoadMore() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                initVip();
                frame.refreshComplete();
            }
        });


        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                vipPull.post(new Runnable() {
                    @Override
                    public void run() {
                        vipPull.autoRefresh(true);
                    }
                });
            }
        });

        adapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {
                //判断是否可升级  不可升级说明是灰色图片 不能点击  0  不可升级   / 1可升级
                if(vipBeanList.get((int) position).getCanupgrade()==0){
                    if (position<2){
                        return;
                        //跳转退款页面
                    }else if (position==2){
                        Intent intent = new Intent(VipClubActivity.this, RefundActivity.class);
                        intent.putExtra("money",vipBeanList.get((int) position).getMem_money());
                        intent.putExtra("url",vipBeanList.get((int) position).getImg_logo());
                        intent.putExtra("describe",vipBeanList.get((int) position).getRefund_explain());
                        startActivity(intent);
                        return;
                    }

                }
                Intent intent = new Intent(VipClubActivity.this, BuyMemberActivity.class);
                intent.putExtra("data", vipBeanList.get((int)position));
                startActivity(intent);
            }
        });

    }

    //获取会员数据
    private void initVip() {


        if (page == 1) {
            vipBeanList.clear();
        }

        final Map<String, String> map = new HashMap<>();
        map.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.GetUserGrade(map, new ApiBaseResponseCallback<VipBeans>() {
            @Override
            public void onSuccessful(VipBeans vipBeen) {
                if (vipBeen==null)
                    return;
                //添加到集合
                if (vipBeen.getDescribe()!=null){
                    vipDescribe.setText(vipBeen.getDescribe());
                }
                vipBeanList.addAll(vipBeen.getMenber());
                footerView.setVisibility(View.VISIBLE);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
                Log.e("vipbean_onSuccessful", vipBeen.toString());
            }

            @Override
            public void onFailure(String msg) {
                if (msg!=null){
                    if (msg.equals("网络似乎出问题了")){
                        loadingLayout.onNonet();
                        footerView.setVisibility(View.GONE);
                        vipRv.setVisibility(View.INVISIBLE);
                    }else if (msg.equals("已经没有啦")) {
                        footerView.setVisibility(View.VISIBLE);
                    }else if(msg.equals("没有此类信息")){
                        loadingLayout.onEmpty();
                        footerView.setVisibility(View.GONE);
                    } else{
                        loadingLayout.onError();
                        footerView.setVisibility(View.GONE);
                    }
                    toastShow(msg);
                    Log.e("vipbean_onFailure", msg.toString());
                }

            }

            @Override
            public void onFinish() {
                vipPull.refreshComplete();
                Log.e("vipbean_onFinish", "onFinish");
            }
        });




    }

    @Override
    protected void setUpView() {
        isFirst=false;
        vipPull.post(new Runnable() {
            @Override
            public void run() {
                vipPull.autoRefresh(true);
            }
        });
    }
}
