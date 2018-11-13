package com.example.lwq.demotest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author lwq
 * @date 2018-10-24 09:08
 * introduction:
 */
public class RatingBar extends View {

    private int mNumStars;
    private float mStepSize;
    private float mRating;
    private int mInterval;
    private int mItemWidth;
    private int mItemHeight;
    private Paint mPaint;
    private Drawable mBackgroundDrawable;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int backgroundId = ta.getResourceId(R.styleable.RatingBar_background, R.drawable.ic_star_background);
        int progressId = ta.getResourceId(R.styleable.RatingBar_progress, R.drawable.ic_star_progress);
        mNumStars = ta.getInt(R.styleable.RatingBar_numStars, 3);
        mStepSize = ta.getFloat(R.styleable.RatingBar_stepSize, 0.5f);
        mRating = ta.getFloat(R.styleable.RatingBar_rating, 0f);
        mInterval = (int) ta.getDimension(R.styleable.RatingBar_interval, dip2px(6));
        mItemWidth = (int) ta.getDimension(R.styleable.RatingBar_itemWidth, dip2px(30));
        mItemHeight = (int) ta.getDimension(R.styleable.RatingBar_itemHeight, dip2px(30));
        ta.recycle();
        initPaintAndDrawable();

        mBackgroundDrawable = ContextCompat.getDrawable(getContext(), backgroundId);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        Drawable progressDrawable = ContextCompat.getDrawable(getContext(), progressId);
        if (progressDrawable == null) {
            return;
        }
        progressDrawable.setBounds(0, 0, mItemWidth, mItemHeight);
        mPaint.setShader(new BitmapShader(drawable2Bitmap(progressDrawable), Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT));
    }

    private void initPaintAndDrawable() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mItemWidth * mNumStars + (mNumStars - 1) * mInterval;
        int height = mItemHeight;
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBackgroundDrawable == null) {
            return;
        }
        //当前进度临时变量
        float currentStar = mRating;
        for (int i = 0; i < mNumStars; i++) {
            int left = i * (mItemWidth + mInterval);
            int top = 0;
            int right = i * (mItemWidth + mInterval) + mItemWidth;
            int bottom = mItemHeight;
            //绘制底部背景
            mBackgroundDrawable.setBounds(left, top, right, bottom);
            mBackgroundDrawable.draw(canvas);
            //canvas.save();
            ////绘制进度层
            //canvas.translate(left, 0);
            //if (currentStar >= 1) {
            //    canvas.drawRect(0, 0, mItemWidth, bottom, mPaint);
            //    currentStar -= 1;
            //} else if (currentStar > 0) {
            //    canvas.drawRect(0, 0, mItemWidth * currentStar, bottom, mPaint);
            //    currentStar = 0;
            //}
            //canvas.restore();
        }
        //canvas.drawRect(0, 0,currentStar *(mItemWidth + mInterval), mItemHeight, mPaint);
        canvas.drawRect(0, 0,currentStar/mNumStars *(getWidth()), mItemHeight, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            dealTouchEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dealTouchEvent(event);
        }
        return true;
    }

    private void dealTouchEvent(MotionEvent event) {
        float progress = event.getX() / getWidth();
        float progressNumF = (progress + 0.05f) * mNumStars;
        float currentStarNum = ((int) (progressNumF / mStepSize) * mStepSize);
        if (currentStarNum != mRating) {
            mRating = currentStarNum;
            invalidate();
        }
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(mItemWidth+mInterval, mItemHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }

    @SuppressWarnings("all")
    private int dip2px(float dipValue) {
        final float scale = getContext()
            .getResources()
            .getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
