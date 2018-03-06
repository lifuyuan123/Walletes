package com.wallet.bo.wallets.ui.weiget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wallet.bo.wallets.ui.weiget.circlerefresh.AnimationView;

/**
 * Created by zhanglei on 15/7/20.
 */
public class CircleRefreshView extends FrameLayout {

    private AnimationView animationView;

    private int mHeaderForeColor;
    private ViewGroup viewGroup;
    float mTouchCurY = 0;
    float mTouchStartY = 0;
    float mTouchCurX = 0;
    float mTouchStartX = 0;
    private View mChildView;


    public CircleRefreshView(Context context) {
        this(context, null, 0);
    }

    public CircleRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {


    }


    private void addView() {
        this.post(new Runnable() {
            @Override
            public void run() {
                mChildView = getChildAt(0);
            }
        });
        animationView = new AnimationView(getContext());
        animationView.setAniBackColor(mHeaderForeColor);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.gravity = Gravity.TOP;
        animationView.setLayoutParams(params);
        viewGroup.addView(animationView, 1);

    }

    public void setmHeaderForeColor(int mHeaderForeColor) {
        this.mHeaderForeColor = mHeaderForeColor;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public void add() {
        addView();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = ev.getY();
                mTouchCurY = mTouchStartY;
                mTouchStartX = ev.getX();
                mTouchCurX = mTouchStartX;
                break;
            case MotionEvent.ACTION_MOVE:
                float curY = ev.getY();
                float dy = curY - mTouchStartY;
                float curX = ev.getX();
                float dX = curY - mTouchStartX;
                if (dy > 5 && !canChildScrollUp()) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                mTouchCurY = event.getY();
                float dy = mTouchCurY - mTouchStartY;
                float dx = mTouchCurX - mTouchStartX;
                animationView.getLayoutParams().height = (int) dy;
               // animationView.setWidthOffset(getX()/getWidth()+1);
                animationView.requestLayout();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                animationView.releaseDrag();
                break;
            default:
                return super.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        return ViewCompat.canScrollVertically(mChildView, -1);
    }
}
