package com.example.lwq.demotest.html;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    2017-04-01
 * Description:
 * 自定义ImageSpan,可以实现ImageSpan和Text居中对齐
 */

public class RichImageSpan extends ImageSpan {
    public static final int ALIGN_CENTER = 2;

    private Rect mRect = new Rect();

    /**
     * @param verticalAlignment one of {@link RichImageSpan#ALIGN_CENTER}
     * or {@link DynamicDrawableSpan#ALIGN_BOTTOM}
     * or {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public RichImageSpan(Drawable d, String source, int verticalAlignment) {
        super(d, source, verticalAlignment);
    }

    public RichImageSpan(Drawable d, String source) {
        this(d, source, ALIGN_CENTER);
    }

    /**
     * @return span的宽度
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
        Paint.FontMetricsInt fm) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        //普通模式
        if (getVerticalAlignment() != ALIGN_CENTER) {
            return super.getSize(paint, text, start, end, fm);
        }

        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.width();
    }

    /**
     * @param canvas 画布
     * @param text 要替换的原本text
     * @param start 替换起始位置
     * @param end 替换结束位置
     * @param x 这个Span的起始水平坐标
     * @param top 这个Span的起始垂直坐标
     * @param y 这个Span的baseline的垂直坐标
     * @param bottom 这个Span的结束垂直坐标
     * @param paint paint
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
        int bottom, Paint paint) {
        if (getVerticalAlignment() == ALIGN_CENTER) {
            Drawable b = getDrawable();
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();

            int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;

            //设置针对layout的Rect,便于后续根据坐标进行替换
            mRect.left = (int) x;
            mRect.top = transY;
            mRect.right = mRect.left + b
                .getBounds()
                .width();
            mRect.bottom = mRect.top + b
                .getBounds()
                .height();

            canvas.save();
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        }
    }

    public Rect getRect() {
        return mRect;
    }
}
