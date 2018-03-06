package com.wallet.bo.wallets.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;
import com.sunfusheng.marqueeview.MarqueeView;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.adapter.GalleryAdapter;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Gallery;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.pojo.Notice;
import com.wallet.bo.wallets.pojo.NoticeAndGallery;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.ui.activity.CommonClientWebActivity;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.LoanMoneyActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.activity.OpenLoanActivity;
import com.wallet.bo.wallets.ui.weiget.BounceScrollView;
import com.wallet.bo.wallets.ui.weiget.ChooseView;
import com.wallet.bo.wallets.ui.weiget.citypicker.citypickerview.widget.wheel.OnePicker;
import com.wallet.bo.wallets.ui.weiget.gallery.EcoGalleryAdapterView;
import com.wallet.bo.wallets.ui.weiget.gallery.WGallery;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * author:ggband
 * date:2017/8/8 9:05
 * email:ggband520@163.com
 * desc:首页借款 compile 'com.sunfusheng:marqueeview:1.3.2'
 */

public class LoanFragment extends BaseFragment {

    @BindView(R.id.vf_notice)
    ViewFlipper vfNotice;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.cv_time)
    ChooseView cvTime;
    @BindView(R.id.wg)
    WGallery wg;
    @BindView(R.id.rl_loanbg)
    RelativeLayout rlLoanbg;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.scrollView)
    BounceScrollView scrollView;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;//上下滚动View 也可以用ViewFlipper
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    Unbinder unbinder;

    @Override
    public void initView() {
    }

    private void initNotice(List<Notice> notices) {
        if (notices == null)
            return;
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < notices.size(); i++) {
            strings.add(notices.get(i).getContent());
        }
        marqueeView.startWithList(strings);


    }

    //设置背景
    private void initBackground(String img) {
        Picasso.with(getActivity()).load(img).into(ivBackground);
    }

    @Override
    public void setViewUp() {
        if (scrollView != null) {//解决ScroView和refreshLayout冲突
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (refreshLayout != null) {
                        refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                    }
                }
            });
        }
        refreshLayout.setColorSchemeColors(
                Color.parseColor("#ff00ddff"),
                Color.parseColor("#ff99cc00"),
                Color.parseColor("#ffffbb33"),
                Color.parseColor("#ffff4444"));
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNoticeAndGallery();
            }
        });
        getNoticeAndGallery();

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_loan;
    }

    @OnClick({R.id.tv_money, R.id.bt_cheats, R.id.bt_help, R.id.bt_play, R.id.tv_enable, R.id.bt_loangl})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.tv_money:
                OnePicker cityPicker = new OnePicker.Builder(activity).textSize(20)
                        .titleTextColor("#000000")
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
                        tvMoney.setText(String.format(getResources().getString(R.string.rmb), citySelected[0]));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                // startActivityForResult(new Intent(activity, LoanMoneyActivity.class), 1);
                break;
            case R.id.bt_cheats:
                Intent intentMj = new Intent(activity, CommonClientWebActivity.class);
                intentMj.putExtra(CommonWebActivity.TITLE, "借款秘籍");
                intentMj.putExtra(CommonWebActivity.URL, URL.JKCENTER);
                startActivity(intentMj);
                break;
            case R.id.bt_help:
                Intent intentHelp = new Intent(activity, CommonClientWebActivity.class);
                intentHelp.putExtra(CommonWebActivity.TITLE, "帮助中心");
                intentHelp.putExtra(CommonWebActivity.URL, URL.HELPCENTER);
                startActivity(intentHelp);
                break;
            case R.id.bt_play:
                if (MyApplication.isOpen())
                    EventBus.getDefault().post(new Navagation(2));
                else {
                    NavigationLogin.navigation2Login(activity, new Intent(activity, OpenLoanActivity.class));
                }

                break;

            case R.id.bt_loangl:
                Intent intentGl = new Intent(activity, CommonClientWebActivity.class);
                intentGl.putExtra(CommonWebActivity.TITLE, "借款攻略");
                intentGl.putExtra(CommonWebActivity.URL, URL.JKCENTER);
                startActivity(intentGl);
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK)
            tvMoney.setText(data.getStringExtra(LoanMoneyActivity.TAG));
    }


    private void getNoticeAndGallery() {
        httpLoader.noticAndGallery(new HashMap<String, String>(), new ApiBaseResponseCallback<NoticeAndGallery>() {
            @Override
            public void onSuccessful(final NoticeAndGallery noticeAndGallery) {
                initGallery(noticeAndGallery.getImgList());
                initNotice(noticeAndGallery.getScrollingMessage());
                initBackground(noticeAndGallery.getHeaderImg().getImg());
            }


            @Override
            public void onFailure(String msg) {
            }

            @Override
            public void onFinish() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void initGallery(final List<Gallery> galleries) {
        if (wg == null)
            return;
        wg.setAdapter(new GalleryAdapter(context, galleries));
        if (galleries != null && galleries.size() != 0) {
            wg.setSelection(Integer.MAX_VALUE / 2 + 1);
            wg.setOnItemClickListener(new EcoGalleryAdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(EcoGalleryAdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(activity, CommonWebActivity.class);
                    intent.putExtra(CommonWebActivity.TITLE, galleries.get(position % galleries.size()).getTitle());
                    intent.putExtra(CommonWebActivity.URL, galleries.get(position % galleries.size()).getUrl());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//去除重影
        super.onHiddenChanged(hidden);
        if (!hidden)
            marqueeView.startFlipping();
        else
            marqueeView.stopFlipping();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
