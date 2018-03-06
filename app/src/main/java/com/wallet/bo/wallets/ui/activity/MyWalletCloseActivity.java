package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.ShopItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.imp.OnNavigationPerformClick;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.pojo.Products;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PtrDefaultHandlerWithLoadMore;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PullLayout;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:我的钱包(不符合条件)
 */

public class MyWalletCloseActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.rv_products)
    RecyclerView rv_products;
    @BindView(R.id.basic_pl)
    PullLayout basicPl;
    private List<Products> productseLi = new ArrayList<>();
    private DefaultLoadingLayout loadingLayout;
    private CommAdapter commAdapter;

    HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    public void setOnNavigationPerformClick(OnNavigationPerformClick onNavigationPerformClick) {
        this.onNavigationPerformClick = onNavigationPerformClick;
    }

    private OnNavigationPerformClick onNavigationPerformClick;


    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet_close;
    }

    @Override
    protected void initView() {
        easeTitlebar.getRightTitleView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        easeTitlebar.getRightImage().setVisibility(View.GONE);
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, rv_products);
        basicPl.setMode(PtrFrameLayout.Mode.REFRESH);
        basicPl.post(new Runnable() {
            @Override
            public void run() {
                basicPl.autoRefresh(true);
            }
        });
        basicPl.setPtrHandler(new PtrDefaultHandlerWithLoadMore() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {


            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initProduct();
            }
        });

    }

    @Override
    protected void setUpView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        commAdapter = new CommAdapter<Products>(productseLi, -1, R.layout.shop_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, Products product, ViewHolder holder) {
                ShopItemBinding shopItemBinding = (ShopItemBinding) binding;
                shopItemBinding.tvCount.setText(product.getApplycount()+"人已放款");
                shopItemBinding.tvPName.setText(product.getTitle());
                shopItemBinding.tvBottom.setText(product.getAd_slogan());
                shopItemBinding.tvBorrowingLimit.setText(product.getMoney());
                shopItemBinding.tvTerm.setText(product.getDeadline());
//                shopItemBinding.tvInfo.setText(product.getLoantime());
                Picasso.with(context).load(product.getImg()).placeholder(R.drawable.im_myhead).error(R.drawable.im_myhead).into(shopItemBinding.imShop);

            }

        };
        rv_products.setLayoutManager(new LinearLayoutManager(context));
        { //添加头

            View headerView = LayoutInflater.from(context).inflate(R.layout.view_wallet_close_header, null);
            View footerView = LayoutInflater.from(context).inflate(R.layout.view_wallet_close_footer, null);

            mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commAdapter);
            mHeaderAndFooterWrapper.addFootView(footerView);
            mHeaderAndFooterWrapper.addHeaderView(headerView);
            footerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AntiShake.check(v)) {    //判断是否多次点击
                        return;
                    }
                    EventBus.getDefault().post(new Navagation(1));
                    finish();
                }
            });
            rv_products.setAdapter(mHeaderAndFooterWrapper);
        }

    }


    private void initProduct() {
        Map<String, String> rp = new HashMap<>();
        httpLoader.products(rp, new ApiBaseResponseCallback<List<Products>>() {
            @Override
            public void onSuccessful(List<Products> productses) {
                productseLi.clear();
                for (Products products : productses) {
                    productseLi.add(products);
                }
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                basicPl.refreshComplete();
            }
        });


        commAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {
                if (AntiShake.check(item.getId())) {    //判断是否多次点击
                    return;
                }
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                intent.putExtra("products", productseLi.get((int) position - 1));
                activity.startActivity(intent);
            }
        });

    }
}
