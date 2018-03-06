package com.wallet.bo.wallets.ui.weiget.srv;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wallet.bo.wallets.R;

import java.text.SimpleDateFormat;

public class PtrClassicDefaultXiaomoHeader extends FrameLayout implements PtrUIHandler {


    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;
    private static final int RATIO = 3;
    private View headerView;
    private int headerViewHeight;
    private float startY;
    private float offsetY;
    private TextView tv_pull_to_refresh;
    private int state;
    private int mFirstVisibleItem;
    private boolean isRecord;
    private boolean isEnd;
    private boolean isRefreable;
    private FrameLayout mAnimContainer;
    private Animation animation;
    private SimpleDateFormat format;
    private MeiTuanRefreshFirstStepView mFirstView;
    private MeiTuanRefreshSecondStepView mSecondView;
    private AnimationDrawable secondAnim;
    private MeiTuanRefreshThirdStepView mThirdView;
    private AnimationDrawable thirdAnim;


    public PtrClassicDefaultXiaomoHeader(Context context) {
        super(context);
        initViews(context);
    }

    public PtrClassicDefaultXiaomoHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public PtrClassicDefaultXiaomoHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    protected void initViews(Context context) {


        headerView = LayoutInflater.from(context).inflate(R.layout.meituan_item, this);
        mFirstView = (MeiTuanRefreshFirstStepView) headerView.findViewById(R.id.first_view);
        tv_pull_to_refresh = (TextView) headerView.findViewById(R.id.tv_pull_to_refresh);
        mSecondView = (MeiTuanRefreshSecondStepView) headerView.findViewById(R.id.second_view);
        mSecondView.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
        secondAnim = (AnimationDrawable) mSecondView.getBackground();
        mThirdView = (MeiTuanRefreshThirdStepView) headerView.findViewById(R.id.third_view);
        mThirdView.setBackgroundResource(R.drawable.pull_to_refresh_third_anim);
        thirdAnim = (AnimationDrawable) mThirdView.getBackground();

        state = DONE;
        isEnd = true;
        isRefreable = false;


        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }


    private void resetView() {

    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

        Log.i("ggband", "onUIRefreshPrepare");
        //设置文字为下拉刷新
        tv_pull_to_refresh.setText("下拉刷新");
        //第一状态view显示出来
        mFirstView.setVisibility(View.VISIBLE);
        //第二状态view隐藏起来
        mSecondView.setVisibility(View.GONE);
        //第二状态动画停止
        secondAnim.stop();
        //第三状态view隐藏起来
        mThirdView.setVisibility(View.GONE);
        //第三状态动画停止
        thirdAnim.stop();

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

        Log.i("ggband", "onUIRefreshBegin");
        //加载中
        //文字设置为正在刷新
        tv_pull_to_refresh.setText("正在刷新");
        //第一状态view隐藏起来
        mFirstView.setVisibility(View.GONE);
        //第三状态view显示出来
        mThirdView.setVisibility(View.VISIBLE);
        //第二状态view隐藏起来
        mSecondView.setVisibility(View.GONE);
        //停止第二状态动画
        secondAnim.stop();
        //启动第三状态view
        thirdAnim.start();

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {


        Log.i("ggband", "onUIRefreshComplete");
        //更新完成

        tv_pull_to_refresh.setText("刷新完成");
        //设置headerView的padding为隐藏
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        //第一状态的view显示出来
        mFirstView.setVisibility(View.VISIBLE);
        //第二状态的view隐藏起来
        mSecondView.setVisibility(View.GONE);
        //停止第二状态的动画
        secondAnim.stop();
        //第三状态的view隐藏起来
        mThirdView.setVisibility(View.GONE);
        //停止第三状态的动画
        thirdAnim.stop();
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {

            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
                //则改变headerView的padding来实现下拉的效果
                headerView.setPadding(0, (int) (-headerViewHeight + offsetY / RATIO), 0, 0);
                //给第一个状态的View设置当前进度值
                mFirstView.setCurrentProgress(currentPos);
                //重画
                mFirstView.postInvalidate();

            }


        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
//如果为放开刷新状态

            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {

                crossRotateLineFromTopUnderTouch(frame);
                //改变headerView的padding值
                headerView.setPadding(0, (int) (-headerViewHeight + offsetY / RATIO), 0, 0);
                //给第一个状态的View设置当前进度值
                mFirstView.setCurrentProgress(currentPos);
                //重画
                mFirstView.postInvalidate();

            }

        }


    }


    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {

        //释放刷新
        if (!frame.isPullToRefresh()) {
            tv_pull_to_refresh.setText(R.string.cube_ptr_release_to_refresh);
        }
    }


    protected void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
            tv_pull_to_refresh.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            tv_pull_to_refresh.setText(getResources().getString(R.string.cube_ptr_pull_down));
        }
    }


}
