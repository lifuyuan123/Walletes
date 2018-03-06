package com.wallet.bo.wallets.ui.weiget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.wallet.bo.wallets.R;


/**
 * author:ggband
 * date:2017/8/8 9:05
 * email:ggband520@163.com
 * desc:积分View
 */
public class HoloArcProgressBar extends View {

    int centerPoint;

    /**
     * TAG constant for logging
     */
    private static final String TAG = HoloArcProgressBar.class.getSimpleName();

    /**
     * used to save the super state on configuration change
     */
    private static final String INSTANCE_STATE_SAVEDSTATE = "saved_state";

    /**
     * used to save the progress on configuration changes
     */
    private static final String INSTANCE_STATE_PROGRESS = "progress";

    /**
     * used to save the marker progress on configuration changes
     */
    private static final String INSTANCE_STATE_MARKER_PROGRESS = "marker_progress";

    /**
     * used to save the background color of the progress
     */
    private static final String INSTANCE_STATE_PROGRESS_BACKGROUND_COLOR
            = "progress_background_color";

    /**
     * used to save the color of the progress
     */
    private static final String INSTANCE_STATE_PROGRESS_COLOR = "progress_color";

    /**
     * used to save and restore the visibility of the thumb in this instance
     */
    private static final String INSTANCE_STATE_THUMB_VISIBLE = "thumb_visible";

    /**
     * used to save and restore the visibility of the marker in this instance
     */
    private static final String INSTANCE_STATE_MARKER_VISIBLE = "marker_visible";

    /**
     * The rectangle enclosing the circle.
     */
    private final RectF mCircleBounds = new RectF();

    /**
     * the rect for the thumb square
     */
    private final RectF mSquareRect = new RectF();

    /**
     * the paint for the background.
     */
    private Paint mBackgroundColorPaint = new Paint();

    /**
     * The stroke width used to paint the circle.
     */
    private int mCircleStrokeWidth = 60;

    /**
     * The gravity of the view. Where should the Circle be drawn within the given bounds
     * <p>
     * {@link #computeInsets(int, int)}
     */
    private int mGravity = Gravity.CENTER;

    /**
     * The Horizontal inset calcualted in {@link #computeInsets(int, int)} depends on {@link
     * #mGravity}.
     */
    private int mHorizontalInset = 0;

    /**
     * true if not all properties are set. then the view isn't drawn and there are no errors in the
     * LayoutEditor
     */
    private boolean mIsInitializing = true;

    /**
     * flag if the marker should be visible
     */
    private boolean mIsMarkerEnabled = false;

    /**
     * indicates if the thumb is visible
     */
    private boolean mIsThumbEnabled = true;

    /**
     * The Marker color paint.
     */
    private Paint mMarkerColorPaint;

    /**
     * The Marker progress.
     */
    private float mMarkerProgress = 0.0f;

    /**
     * the overdraw is true if the progress is over 1.0.
     */
    private boolean mOverrdraw = false;

    /**
     * The current progress.
     */
    private float mProgress = 0.0f;

    /**
     * The color of the progress background.
     */
    private int mProgressBackgroundColor;

    /**
     * the color of the progress.
     */
    private int mProgressColor;

    /**
     * paint for the progress.
     */
    private Paint mProgressColorPaint;

    /**
     * Radius of the circle
     * <p>
     * <p> Note: (Re)calculated in {@link #onMeasure(int, int)}. </p>
     */
    private float mRadius = 180;

    /**
     * The Thumb color paint.
     */
    private Paint mThumbColorPaint = new Paint();

    /**
     * The Thumb pos x.
     * <p>
     * Care. the position is not the position of the rotated thumb. The position is only calculated
     * in {@link #onMeasure(int, int)}
     */
    private float mThumbPosX;

    /**
     * The Thumb pos y.
     * <p>
     * Care. the position is not the position of the rotated thumb. The position is only calculated
     * in {@link #onMeasure(int, int)}
     */
    private float mThumbPosY;

    /**
     * The pointer width (in pixels).
     */
    private int mThumbRadius = 20;

    /**
     * The Translation offset x which gives us the ability to use our own coordinates system.
     */
    private float mTranslationOffsetX;

    /**
     * The Translation offset y which gives us the ability to use our own coordinates system.
     */
    private float mTranslationOffsetY;

    /**
     * The Vertical inset calcualted in {@link #computeInsets(int, int)} depends on {@link
     * #mGravity}..
     */
    private int mVerticalInset = 0;

    private String scoreText = "0";


    //渐变的颜色
    private int[] colors = new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.RED};
  //  private int[] colors = new int[]{Color.RED, Color.RED,Color.YELLOW, Color.GREEN};
    //    private PaintFlagsDrawFilter mDrawFilter;
    //扫描/梯度渲染
    private SweepGradient sweepGradient;

    private Matrix rotateMatrix;


    //开始的角度
    private float mStart = -210;
    //总的角度
    private static final int FULL_ANGLE = 240;


    /**
     * Instantiates a new holo circular progress bar.
     *
     * @param context the context
     */
    public HoloArcProgressBar(final Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new holo circular progress bar.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public HoloArcProgressBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, R.attr.circularProgressBarStyle);
    }

    /**
     * Instantiates a new holo circular progress bar.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public HoloArcProgressBar(final Context context, final AttributeSet attrs,
                              final int defStyle) {
        super(context, attrs, defStyle);

        // load the styled attributes and set their properties
        final TypedArray attributes = context
                .obtainStyledAttributes(attrs, R.styleable.HoloCircularProgressBar,
                        defStyle, 0);
        if (attributes != null) {
            try {
                setProgressColor(attributes
                        .getColor(R.styleable.HoloCircularProgressBar_progress_color, Color.parseColor("#ffcc33")));
                setProgressBackgroundColor(attributes
                        .getColor(R.styleable.HoloCircularProgressBar_progress_background_color,
                                Color.parseColor("#eeeeee")));
                setProgress(
                        attributes.getFloat(R.styleable.HoloCircularProgressBar_progress, 0.0f));
                setMarkerProgress(
                        attributes.getFloat(R.styleable.HoloCircularProgressBar_marker_progress,
                                0.0f));
                setWheelSize((int) attributes
                        .getDimension(R.styleable.HoloCircularProgressBar_stroke_width, 10));
                setThumbEnabled(attributes
                        .getBoolean(R.styleable.HoloCircularProgressBar_thumb_visible, true));
                setMarkerEnabled(attributes
                        .getBoolean(R.styleable.HoloCircularProgressBar_marker_visible, true));

                mGravity = attributes
                        .getInt(R.styleable.HoloCircularProgressBar_android_gravity,
                                Gravity.CENTER);
            } finally {
                // make sure recycle is always called.
                attributes.recycle();
            }
        }

        mThumbRadius = mCircleStrokeWidth * 2;

        updateBackgroundColor();

        updateMarkerColor();

        updateProgressColor();

        // the view has now all properties and can be drawn
        mIsInitializing = false;

    }


    private void drawTitleText(int distance, String textD, Canvas canvas, int size) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        final int width = getWidth();
        final int height = getHeight();

        final Rect bounds = new Rect();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(size);
        mPaint.setAlpha(250); // 透明度值在0~255之间
        mPaint.getTextBounds(textD, 0, textD.length(), bounds);
        canvas.drawText(textD, (width / 2) - (bounds.width() / 2), (height / 2) + distance, mPaint);
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        // All of our positions are using our internal coordinate system.
        // Instead of translating
        // them we let Canvas do the work for us.

        drawTitleText(0, scoreText, canvas, 42);
        drawTitleText(45, "信用额度", canvas, 32);
        canvas.translate(mTranslationOffsetX, mTranslationOffsetY);


        final float progressRotation = getCurrentRotation();

        // draw the background
        if (!mOverrdraw) {
            canvas.drawArc(mCircleBounds, mStart,FULL_ANGLE, false,
                    mBackgroundColorPaint);
        }

        // draw the progress or a full circle if overdraw is true
        canvas.drawArc(mCircleBounds, mStart, mOverrdraw ? FULL_ANGLE : progressRotation, false,
                mProgressColorPaint);


        // draw the marker at the correct rotated position
        if (mIsMarkerEnabled) {
            final float markerRotation = getMarkerRotation();

            canvas.save();
            canvas.rotate(markerRotation);
//            canvas.drawLine((float) (mThumbPosX + mThumbRadius / 2 * 1.4), mThumbPosY,
//                    (float) (mThumbPosX - mThumbRadius / 2 * 1.4), mThumbPosY, mMarkerColorPaint);
            canvas.restore();
        }

        if (isThumbEnabled()) {
            // draw the thumb square at the correct rotated position
            canvas.save();
            canvas.rotate(progressRotation);
            // rotate the square by 45 degrees
            canvas.rotate(45, mThumbPosX, mThumbPosY);
            mSquareRect.left = mThumbPosX - mThumbRadius / 3;
            mSquareRect.right = mThumbPosX + mThumbRadius / 3;
            mSquareRect.top = mThumbPosY - mThumbRadius / 3;
            mSquareRect.bottom = mThumbPosY + mThumbRadius / 3;
            //   canvas.drawRect(mSquareRect, mThumbColorPaint);


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.im_progress_point);
            mSquareRect.left = mThumbPosX - bitmap.getWidth() / 2;
            mSquareRect.top = mThumbPosY - bitmap.getHeight() / 2;
            canvas.drawBitmap(bitmap, mSquareRect.left, mSquareRect.top, mThumbColorPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int height = getDefaultSize(
                getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom(),
                heightMeasureSpec);
        final int width = getDefaultSize(
                getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight(),
                widthMeasureSpec);

        final int diameter;
        if (heightMeasureSpec == MeasureSpec.UNSPECIFIED) {
            // ScrollView
            diameter = width;
            computeInsets(0, 0);
        } else if (widthMeasureSpec == MeasureSpec.UNSPECIFIED) {
            // HorizontalScrollView
            diameter = height;
            computeInsets(0, 0);
        } else {
            // Default
            diameter = Math.min(width, height);
            computeInsets(width - diameter, height - diameter);
        }
        centerPoint = diameter;
        setMeasuredDimension(diameter, diameter);

        final float halfWidth = diameter * 0.5f;

        // width of the drawed circle (+ the drawedThumb)
        final float drawedWith;
        if (isThumbEnabled()) {
            drawedWith = mThumbRadius * (5f / 6f);
        } else if (isMarkerEnabled()) {
            drawedWith = mCircleStrokeWidth * 1.4f;
        } else {
            drawedWith = mCircleStrokeWidth / 2f;
        }

        // -0.5f for pixel perfect fit inside the viewbounds
        mRadius = halfWidth - drawedWith - 0.5f;

        mCircleBounds.set(-mRadius, -mRadius, mRadius, mRadius);

        mThumbPosX = (float) (mRadius * Math.cos(mStart));
        mThumbPosY = (float) (mRadius * Math.sin(mStart));

        mTranslationOffsetX = halfWidth + mHorizontalInset;
        mTranslationOffsetY = halfWidth + mVerticalInset;

    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            setProgress(bundle.getFloat(INSTANCE_STATE_PROGRESS));
            setMarkerProgress(bundle.getFloat(INSTANCE_STATE_MARKER_PROGRESS));

            final int progressColor = bundle.getInt(INSTANCE_STATE_PROGRESS_COLOR);
            if (progressColor != mProgressColor) {
                mProgressColor = progressColor;
                updateProgressColor();
            }

            final int progressBackgroundColor = bundle
                    .getInt(INSTANCE_STATE_PROGRESS_BACKGROUND_COLOR);
            if (progressBackgroundColor != mProgressBackgroundColor) {
                mProgressBackgroundColor = progressBackgroundColor;
                updateBackgroundColor();
            }

            mIsThumbEnabled = bundle.getBoolean(INSTANCE_STATE_THUMB_VISIBLE);

            mIsMarkerEnabled = bundle.getBoolean(INSTANCE_STATE_MARKER_VISIBLE);

            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE_SAVEDSTATE));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE_SAVEDSTATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STATE_PROGRESS, mProgress);
        bundle.putFloat(INSTANCE_STATE_MARKER_PROGRESS, mMarkerProgress);
        bundle.putInt(INSTANCE_STATE_PROGRESS_COLOR, mProgressColor);
        bundle.putInt(INSTANCE_STATE_PROGRESS_BACKGROUND_COLOR, mProgressBackgroundColor);
        bundle.putBoolean(INSTANCE_STATE_THUMB_VISIBLE, mIsThumbEnabled);
        bundle.putBoolean(INSTANCE_STATE_MARKER_VISIBLE, mIsMarkerEnabled);
        return bundle;
    }

    public int getCircleStrokeWidth() {
        return mCircleStrokeWidth;
    }

    /**
     * similar to {@link #getProgress}
     */
    public float getMarkerProgress() {
        return mMarkerProgress;
    }

    /**
     * gives the current progress of the ProgressBar. Value between 0..1 if you set the progress to
     * >1 you'll get progress % 1 as return value
     *
     * @return the progress
     */
    public float getProgress() {
        return mProgress;
    }

    /**
     * Gets the progress color.
     *
     * @return the progress color
     */
    public int getProgressColor() {
        return mProgressColor;
    }

    /**
     * @return true if the marker is visible
     */
    public boolean isMarkerEnabled() {
        return mIsMarkerEnabled;
    }

    /**
     * @return true if the marker is visible
     */
    public boolean isThumbEnabled() {
        return mIsThumbEnabled;
    }

    /**
     * Sets the marker enabled.
     *
     * @param enabled the new marker enabled
     */
    public void setMarkerEnabled(final boolean enabled) {
        mIsMarkerEnabled = enabled;
    }

    /**
     * Sets the marker progress.
     *
     * @param progress the new marker progress
     */
    public void setMarkerProgress(final float progress) {
        mIsMarkerEnabled = true;
        mMarkerProgress = progress;
    }

    /**
     * Sets the progress.
     *
     * @param progress the new progress
     */
    public void setProgress(final float progress) {
        if (progress == mProgress) {
            return;
        }

        if (progress == 1) {
            mOverrdraw = false;
            mProgress = 1;
        } else {

            if (progress >= 1) {
                mOverrdraw = true;
            } else {
                mOverrdraw = false;
            }

            mProgress = progress % 1.0f;
        }

        if (!mIsInitializing) {
            invalidate();
        }
    }

    /**
     * Sets the progress background color.
     *
     * @param color the new progress background color
     */
    public void setProgressBackgroundColor(final int color) {
        mProgressBackgroundColor = color;

        updateMarkerColor();
        updateBackgroundColor();
    }

    /**
     * Sets the progress color.
     *
     * @param color the new progress color
     */
    public void setProgressColor(final int color) {
        mProgressColor = color;

        updateProgressColor();
    }

    /**
     * shows or hides the thumb of the progress bar
     *
     * @param enabled true to show the thumb
     */
    public void setThumbEnabled(final boolean enabled) {
        mIsThumbEnabled = enabled;
    }

    /**
     * Sets the wheel size.
     *
     * @param dimension the new wheel size
     */
    public void setWheelSize(final int dimension) {
        mCircleStrokeWidth = dimension;

        // update the paints
        updateBackgroundColor();
        updateMarkerColor();
        updateProgressColor();
    }

    /**
     * Compute insets.
     * <p>
     * <pre>
     *  ______________________
     * |_________dx/2_________|
     * |......| /'''''\|......|
     * |-dx/2-|| View ||-dx/2-|
     * |______| \_____/|______|
     * |________ dx/2_________|
     * </pre>
     *
     * @param dx the dx the horizontal unfilled space
     * @param dy the dy the horizontal unfilled space
     */
    @SuppressLint("NewApi")
    private void computeInsets(final int dx, final int dy) {
        int absoluteGravity = mGravity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            absoluteGravity = Gravity.getAbsoluteGravity(mGravity, getLayoutDirection());
        }

        switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
                mHorizontalInset = 0;
                break;
            case Gravity.RIGHT:
                mHorizontalInset = dx;
                break;
            case Gravity.CENTER_HORIZONTAL:
            default:
                mHorizontalInset = dx / 2;
                break;
        }
        switch (absoluteGravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                mVerticalInset = 0;
                break;
            case Gravity.BOTTOM:
                mVerticalInset = dy;
                break;
            case Gravity.CENTER_VERTICAL:
            default:
                mVerticalInset = dy / 2;
                break;
        }
    }

    /**
     * Gets the current rotation.
     *
     * @return the current rotation
     */
    private float getCurrentRotation() {
        return 240 * mProgress;
    }

    /**
     * Gets the marker rotation.
     *
     * @return the marker rotation
     */
    private float getMarkerRotation() {
        return 240 * mMarkerProgress;
    }

    /**
     * updates the paint of the background
     */
    private void updateBackgroundColor() {
        mBackgroundColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundColorPaint.setColor(mProgressBackgroundColor);
        mBackgroundColorPaint.setStyle(Paint.Style.STROKE);
        mBackgroundColorPaint.setStrokeWidth(mCircleStrokeWidth);

        invalidate();
    }

    /**
     * updates the paint of the marker
     */
    private void updateMarkerColor() {
        mMarkerColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarkerColorPaint.setColor(mProgressBackgroundColor);
        mMarkerColorPaint.setStyle(Paint.Style.STROKE);
        mMarkerColorPaint.setStrokeWidth(mCircleStrokeWidth / 2);

        invalidate();
    }

    /**
     * updates the paint of the progress and the thumb to give them a new visual style
     */
    private void updateProgressColor() {
        mProgressColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //   mProgressColorPaint.setColor(mProgressColor);
        sweepGradient = new SweepGradient(getWidth()/2, getHeight()/2, colors, null);
        rotateMatrix = new Matrix();
        //这个方法很重要
        rotateMatrix.setRotate(118, getWidth()/2, getHeight()/2);
        sweepGradient.setLocalMatrix(rotateMatrix);
        mProgressColorPaint.setShader(sweepGradient);
        mProgressColorPaint.setStyle(Paint.Style.STROKE);
        mProgressColorPaint.setStrokeWidth(mCircleStrokeWidth);

        mThumbColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbColorPaint.setColor(mProgressColor);
        mThumbColorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mThumbColorPaint.setStrokeWidth(mCircleStrokeWidth);

        invalidate();
    }


    /**
     * Animate.
     *
     * @param progressBar the progress bar
     * @param listener    the listener
     */

    private ObjectAnimator mProgressBarAnimator;

    public void setScoreText(String scoreText){
        this.scoreText = scoreText;
        invalidate();
    }
    public void animate(final HoloArcProgressBar progressBar, final Animator.AnimatorListener listener,
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
                scoreText = String.valueOf((int)(((Float) animation.getAnimatedValue())*1000));
                progressBar.setProgress((Float) animation.getAnimatedValue());
                progressBar.setMarkerProgress((Float) animation.getAnimatedValue());
            }
        });
        //progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }

}