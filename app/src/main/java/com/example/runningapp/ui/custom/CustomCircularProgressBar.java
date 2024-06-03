package com.example.runningapp.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.runningapp.R;

public class CustomCircularProgressBar extends View {
    private Paint circlePaint;
    private Paint arcPaint;
    private Paint textPaint;
    private int max = 10000; // Max value for progress
    private int progress = 0; // Current progress

    public CustomCircularProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCircularProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.LTGRAY);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(20f);
        circlePaint.setAntiAlias(true);

        arcPaint = new Paint();
        arcPaint.setColor(getResources().getColor(R.color.buttonColor));
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20f);
        arcPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(100f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public void setProgress(float progress) {
        this.progress = (int) progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20;

        canvas.drawCircle(width / 2, height / 2, radius, circlePaint);

        float angle = 360 * progress / max;
        canvas.drawArc(20, 20, width - 20, height - 20, -90, angle, false, arcPaint);

        canvas.drawText(String.valueOf(progress), width / 2, height / 2 + 35, textPaint);
    }
}

