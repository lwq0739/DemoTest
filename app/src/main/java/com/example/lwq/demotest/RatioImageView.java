package com.example.lwq.demotest;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/**
 * @author lwq
 * @date 2018-07-24 10:51
 * introduction:
 */
public class RatioImageView extends android.support.v7.widget.AppCompatImageView {

    private static final int TYPE_FIXED_WIDE = 1;
    private static final int TYPE_FIXED_HIGH = 2;
    /**
     * 非固定一方相对固定一方的比例
     */
    private float mAspectRatio;
    /**
     * 固定的一方WIDE或HIGH
     */
    private int mFixed;

    public RatioImageView(Context context) {
        this(context,null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mAspectRatio = ta.getFloat(R.styleable.RatioImageView_aspectRatio, 1f);
        mFixed = ta.getInt(R.styleable.RatioImageView_fixed, TYPE_FIXED_WIDE);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mFixed == TYPE_FIXED_WIDE && MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY){
            float height = MeasureSpec.getSize(widthMeasureSpec) * mAspectRatio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height,MeasureSpec.EXACTLY);
        }else if (mFixed == TYPE_FIXED_HIGH && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY){
            float width = MeasureSpec.getSize(heightMeasureSpec) * mAspectRatio;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) width,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
