package com.wallet.bo.wallets.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.ScreenPopwindow;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.FragmentLoanproductBinding;
import com.wallet.bo.wallets.databinding.ShopItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Products;
import com.wallet.bo.wallets.ui.activity.CommonClientWebActivity;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.ProductDetailsActivity;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PtrDefaultHandlerWithLoadMore;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:第三方产品列表
 */
public class LoanProductFragment extends BaseFragment {
    
    private CommAdapter commAdapter;
    private List<Products> productseLi = new ArrayList<>();
    private DefaultLoadingLayout loadingLayout;
    int page = 1;
    private View footerView;
    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    List<String> list=new ArrayList<>();//假数据   时间
    private FragmentLoanproductBinding loanproductBinding;
    private boolean isfirst=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loanproductBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_loanproduct,container,false);
        return loanproductBinding.getRoot();
    }

    //一些初始化工作
    @Override
    public void initView() {
        getdata();//假数据   时间
        loanproductBinding.basicPl.setMode(PtrFrameLayout.Mode.BOTH);
        footerView = LayoutInflater.from(context).inflate(R.layout.view_list_nomore_footer, null);
        footerView.setVisibility(View.GONE);
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, loanproductBinding.basicPl);
        loanproductBinding.rvShop.setLayoutManager(new LinearLayoutManager(context));
        commAdapter = new CommAdapter<Products>(productseLi, -1, R.layout.shop_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, Products product, ViewHolder holder) {
                ShopItemBinding shopItemBinding = (ShopItemBinding) binding;

                if(product.getIsaudit()==1){//获取假数据  新闻页面
                    shopItemBinding.linBorrow.setVisibility(View.GONE);
                    shopItemBinding.linLoan.setVisibility(View.GONE);
                    shopItemBinding.tvCount.setVisibility(View.GONE);
                    shopItemBinding.tvData.setText(list.get(position));
                    shopItemBinding.tvData.setVisibility(View.VISIBLE);
                }else if (product.getIsaudit()==0){//获取正式页面
                    shopItemBinding.tvData.setVisibility(View.GONE);
                    shopItemBinding.linBorrow.setVisibility(View.VISIBLE);
                    shopItemBinding.linLoan.setVisibility(View.VISIBLE);
                    shopItemBinding.tvCount.setVisibility(View.VISIBLE);
                }
                shopItemBinding.tvCount.setText(product.getApplycount()+"人已放款");
                shopItemBinding.tvPName.setText(product.getTitle());
                shopItemBinding.tvBottom.setText(product.getAd_slogan());
                shopItemBinding.tvBorrowingLimit.setText(product.getMoney());
                shopItemBinding.tvTerm.setText(product.getDeadline());
                Picasso.with(context)
                        .load(product.getImg())
                        .error(R.drawable.im_myhead)
                        .into(shopItemBinding.imShop);

            }

        };
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commAdapter);
        mHeaderAndFooterWrapper.addFootView(footerView);
        loanproductBinding.rvShop.setAdapter(mHeaderAndFooterWrapper);

        commAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {
                Products products = productseLi.get((int) position);
                if (AntiShake.check(item.getId())) {    //判断是否多次点击
                    return;
                }
                //1:新闻页面   0：正式的详情页
                if(products.getIsaudit()==1){


                    Intent intent = new Intent(activity, CommonClientWebActivity.class);
                    intent.putExtra(CommonWebActivity.TITLE, products.getTitle());
                    intent.putExtra(CommonWebActivity.URL, products.getUrl());
                    NavigationLogin.navigation2Login(activity, intent);

                }else if(products.getIsaudit()==0){
                    Intent intent = new Intent(activity, ProductDetailsActivity.class);
                    intent.putExtra("products", products);
                    activity.startActivity(intent);
                }

            }
        });


        //弹出筛选popwindow
        loanproductBinding.tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (context==null)
                        return;
                    View view=LayoutInflater.from(context).inflate(R.layout.screen_layout,null);
                    final ScreenPopwindow screenPopwindow=new ScreenPopwindow(view,WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                    //显示的时候背景加点透明度
                    screenPopwindow.backgroundAlpaha(activity,0.5f);
                    //返回的时候背景恢复
                    screenPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            screenPopwindow.backgroundAlpaha(activity,1f);
                        }
                    });
                    //回调过来的数据
                    screenPopwindow.setCallBack(new ScreenPopwindow.ChoiceCallBack() {
                        @Override
                        public void onClick(Map<String, String> map) {
                            screenPopwindow.dismiss();
                            Log.e("choice",map.toString());
                        }
                    });
//                    //滑动监听
//                    screenPopwindow.setTouchInterceptor(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            int x1 = 0,x2=0;
//                            if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                                //当手指按下的时候
//                                x1 = (int) event.getX();
//                            }
//                            if(event.getAction() == MotionEvent.ACTION_UP) {
//                                //当手指离开的时候
//                                x2 = (int) event.getX();
//                                if(x2 - x1 >50) {
//                                screenPopwindow.dismiss();
//                                }
//                            }
//                            return true;
//                        }
//                    });
                }
        });

    }

    //上架时的假数据
    private void getdata() {
        list.add("89人阅读  11-04");
        list.add("67人阅读  11-07");
        list.add("236人阅读  10-21");
        list.add("114人阅读  11-01");
        list.add("55人阅读  10-29");
        list.add("345人阅读  10-09");
        list.add("27人阅读  10-05");
        list.add("99人阅读  11-10");
        list.add("105人阅读  10-20");
    }

    @Override
    public void onResume() {
        super.onResume();
        //防止点击设置网络等操作返回后是空白页
        loanproductBinding.basicPl.post(new Runnable() {
            @Override
            public void run() {
                if (isfirst){
                    loanproductBinding.basicPl.autoRefresh(true);
                }else {
                    page=1;
                    initProduct();
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                }
                isfirst=false;
            }
        });
    }

    //刷新和加载控件监听设置
    @Override
    public void setViewUp() {

        loanproductBinding.basicPl.setPtrHandler(new PtrDefaultHandlerWithLoadMore() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                initProduct();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                initProduct();
            }
        });
        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                loanproductBinding.basicPl.post(new Runnable() {
                    @Override
                    public void run() {
                        loanproductBinding.basicPl.autoRefresh(true);
                    }
                });
            }
        });

    }

    //获取数据
    private void initProduct() {
        Map<String, String> rp = new HashMap<>();
        rp.put("page", String.valueOf(page));

        httpLoader.products(rp, new ApiBaseResponseCallback<List<Products>>() {
            @Override
            public void onSuccessful(List<Products> productses) {
                Log.e("products",productses.toString());
                if(page==1){
                    productseLi.clear();
                    loanproductBinding.basicPl.setMode(PtrFrameLayout.Mode.BOTH);
                }
                for (Products products : productses) {
                    productseLi.add(products);
                }
                page++;
                loanproductBinding.rvShop.setVisibility(View.VISIBLE);
                footerView.setVisibility(View.GONE);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                if (msg!=null){
                    if (msg.equals("网络似乎出问题了")){
                        loadingLayout.onNonet();
                        footerView.setVisibility(View.GONE);
                        loanproductBinding.rvShop.setVisibility(View.INVISIBLE);
                    }else if (msg.equals("已经没有啦")) {
                        footerView.setVisibility(View.VISIBLE);
                        loanproductBinding.basicPl.setMode(PtrFrameLayout.Mode.REFRESH);
                    }else if(msg.equals("没有此类信息")){
                        loadingLayout.onEmpty();
                        footerView.setVisibility(View.GONE);
                    } else{
                        loadingLayout.onError();
                        footerView.setVisibility(View.GONE);
                    }
                    toastShow(msg);
                }

            }

            @Override
            public void onFinish() {
                loanproductBinding.basicPl.refreshComplete();
            }
        });

    }


}



