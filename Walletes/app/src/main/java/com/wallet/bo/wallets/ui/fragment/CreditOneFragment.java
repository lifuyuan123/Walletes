package com.wallet.bo.wallets.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.CreditsItemBinding;
import com.wallet.bo.wallets.ui.activity.CreditWebActivity;
import com.wallet.bo.wallets.ui.activity.LoanActivity;
import com.wallet.bo.wallets.ui.activity.LoanPhotoActivity;
import com.wallet.bo.wallets.ui.weiget.CircleRefreshView;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.HoloCircularProgressBar;
import com.wallet.bo.wallets.ui.weiget.ObservableScrollView;
import com.wallet.bo.wallets.ui.weiget.sr.FullyLinearLayoutManager;
import com.wallet.bo.wallets.ui.weiget.sr.RcScrollview;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:ggband
 * date:2017/8/8 9:05
 * email:ggband520@163.com
 * desc:信用
 */


public class CreditOneFragment extends BaseFragment {


    @BindView(R.id.rv_credit)
    RecyclerView rvCredit;
    @BindView(R.id.holoCircularProgressBar)
    HoloCircularProgressBar holoCircularProgressBar;
    @BindView(R.id.cc_view)
    CircleRefreshView ccView;
    private ObjectAnimator mProgressBarAnimator;
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.scroll_view)
    RcScrollview scrollView;
    @BindView(R.id.ll_viewAnimal)
    LinearLayout llViewAnimal;
    private int height = 259;
    private CommAdapter<String> commAdapter;

    private int drawableCredits[];
    private String[] decCredits;
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    int mHeaderForeColor = 0xffffbf6c;


    @Override
    public void initView() {

        ccView.setmHeaderForeColor(mHeaderForeColor);
        ccView.setViewGroup(llViewAnimal);
        ccView.add();

        easeTitlebar.setAlpha(1);
        easeTitlebar.setBackgroundColor(Color.argb(0, 0x00, 0x00, 0x00));
        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                if (y <= height && y >= 0) {
                    float scale = (float) y / height;
                    float alpha = (255 * scale);
                    //layout全部透明
                    easeTitlebar.setAlpha(scale);
                    //只是layout背景透明(仿知乎滑动效果)
                    easeTitlebar.setBackgroundColor(Color.argb((int) alpha, 0xff, 0x11, 0x00));
                } else if (y > height) {
                    easeTitlebar.getBackground().setAlpha(255);
                }

            }

        });


    }

    @Override
    public void setViewUp() {

        holoCircularProgressBar.setProgress(0.0f);

        drawableCredits = new int[]{
                R.drawable.im_zmf,

                R.drawable.im_yys,
                R.drawable.im_pz,
                R.drawable.im_facerecognition,

                R.drawable.im_zfb,
                R.drawable.im_tb,
                R.drawable.im_jd,

                R.drawable.im_xxw,
                R.drawable.im_sb,
                R.drawable.im_gjj,
                R.drawable.im_yed,
                R.drawable.im_yszx,
                R.drawable.im_xykyx,

        };
        decCredits = getResources().getStringArray(R.array.credit_array);

        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(context);
        rvCredit.setLayoutManager(layoutManager);
        commAdapter = new CommAdapter<String>(Arrays.asList(decCredits), -1, R.layout.credits_item) {

            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, String s, ViewHolder holder) {

                CreditsItemBinding creditsItemBinding = (CreditsItemBinding) binding;
                //ui布局
                if (position == 1 || position == 4 || position == 6) {
                    params.setMargins(0, 20, 0, 0);

                } else
                    params.setMargins(0, 0, 0, 0);
                binding.getRoot().setLayoutParams(params);
                creditsItemBinding.imHead.setImageDrawable(getResources().getDrawable(drawableCredits[position]));
                creditsItemBinding.tvTitle.setText(decCredits[position]);
            }
        };


        rvCredit.setAdapter(commAdapter);


        commAdapter.setOnItemClickListener(new CommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, long position) {
                switch ((int) position) {
                    case 2:
                        Intent intent = new Intent(activity, LoanPhotoActivity.class);
                        startActivityForResult(intent, -1);
                        break;
                    default:
                        Intent intentw = new Intent(activity, CreditWebActivity.class);
                        startActivityForResult(intentw, -1);
                        break;
                }


            }
        });


    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_creditone;
    }

    @OnClick({R.id.bt_loan})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bt_loan:
                startActivity(new Intent(activity, LoanActivity.class));

                break;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressBarAnimator != null) {
            mProgressBarAnimator.cancel();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        animate(holoCircularProgressBar, null, 0.7f, 1000);
    }


    /**
     * Animate.
     *
     * @param progressBar the progress bar
     * @param listener    the listener
     */

    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener,
                         final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}


