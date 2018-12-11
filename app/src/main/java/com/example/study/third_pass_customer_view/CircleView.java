package com.example.study.third_pass_customer_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.study.R;

public class CircleView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int color = Color.RED;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        color = a.getColor(R.styleable.CircleView_circle_color, color);
        a.recycle();
        init();
    }

    private void init() {
        mPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasureSize(widthMeasureSpec, 200);
        int height = getMeasureSize(heightMeasureSpec, 200);
        setMeasuredDimension(width, height);
    }

    private int getMeasureSize(int measureSpec, int defaultSize) {
        int mSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                mSize = Math.min(defaultSize, size);
                break;
            case MeasureSpec.EXACTLY:
                mSize = size;
                break;
            default:
                mSize = defaultSize;
                break;
        }
        return mSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //处理View的padding值
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int r = Math.min(width, height) / 2;
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, mPaint);
    }
}
