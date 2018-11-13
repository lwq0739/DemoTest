package com.example.lwq.demotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * @author lwq
 * @date 2018-07-16 11:29
 * introduction:
 */
public class RoundCornerImageView extends RatioImageView {

    private int mRadius;
    private Paint mPaint;
    private RectF mRectF;
    private int mCheckLine;
    private BitmapShader mBitmapShader;
    private boolean mChecked = false;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = dip2px(10);
        mCheckLine = dip2px(3);
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setCheck(boolean check){
        mChecked = check;
        invalidate();
    }

    @Override
    @SuppressWarnings("all")
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = drawable2Bitmap(getDrawable());
        if (bitmap != null){
            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mBitmapShader);
            mRectF.set(mCheckLine,mCheckLine,bitmap.getWidth()-mCheckLine,bitmap.getHeight()-mCheckLine);
            canvas.drawRoundRect(mRectF,mRadius,mRadius,mPaint);
            if (mChecked){
                mPaint.setShader(null);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.BLUE);
                mPaint.setStrokeWidth(mCheckLine);
                mRectF.set(mCheckLine,mCheckLine,bitmap.getWidth()-mCheckLine,bitmap.getHeight()-mCheckLine);
                canvas.drawRoundRect(mRectF,mRadius,mRadius,mPaint);
            }
        }

    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
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
