package com.example.lwq.demotest.html;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * User:lwq
 * Date:2017-10-20
 * Time:17:31
 * introduction:
 */

public class PercentageImgSpan extends ReplacementSpan {

    private String mPercentage;
    private int mWight;
    private final int mAccentColor = 0xff3ccfcf;

    public PercentageImgSpan(String percentage) {
        mPercentage = percentage;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, @Nullable Paint.FontMetricsInt fm) {
        mWight = getTextWidth(getDrawText(), paint) + 20 + 10 * 3;
        return mWight;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.save();
        paint.setColor(mAccentColor);
        RectF rect = new RectF(x + 5, top, x + mWight, bottom);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, paint);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText(getDrawText(), x + 10, y, paint);

        Path path = new Path();
        path.moveTo(x + getTextWidth(getDrawText(), paint) + 10 + 10, bottom - (bottom - top) / 2);
        path.lineTo(x + getTextWidth(getDrawText(), paint) + 10 + 20 + 10, bottom - (bottom - top) / 2);
        path.lineTo(x + getTextWidth(getDrawText(), paint) + 10 + 10 + 10, bottom - (bottom - top) / 4);
        path.close();
        canvas.drawPath(path, paint);

    }

    private String getDrawText(){
        return mPercentage;
    }

    private int getTextWidth(String text, Paint paint) {
        if (text == null) {
            return 0;
        }
        return (int) paint.measureText(text);
    }
}
