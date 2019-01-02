package com.example.study.third_pass_customer_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 继承ViewGroup自定义View
 */
public class HScrollView extends ViewGroup {
    //分别记录上一次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    //分别记录上一次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept;
    private int mLastYIntercept;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HScrollView(Context context) {
        super(context);
        init();
    }

    public HScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (childCount == 0) {
            //如果没有子View，则设置宽高为0
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //宽高都为包裹内容，则宽设置为所有子View的宽度之和，高设置为子View中的最大高度
            setMeasuredDimension(getTotalWidth(), getMaxHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //只有宽度为包裹内容，则设置宽度为所有子View宽度之和，高度为测量高度
            setMeasuredDimension(getTotalWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //只有高度为包裹内容，则设置宽度为测量宽度，高度设置为子View的最大高度
            setMeasuredDimension(widthSize, getMaxHeight());
        }
    }

    private int getTotalWidth() {
        int mChildrenWidth = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            mChildrenWidth += v.getMeasuredWidth();
        }
        return mChildrenWidth;
    }

    private int getMaxHeight() {
        int maxHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v.getMeasuredHeight() > maxHeight) {
                maxHeight = v.getMeasuredHeight();
            }
        }
        return maxHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;

        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v.getVisibility() != View.GONE) {
                //如果子view的显示状态不为GONE，则通过layout将其放置在合适的位置
                int childWidth = v.getMeasuredWidth();
                v.layout(childLeft, 0, childLeft + childWidth, v.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastXIntercept;
                int dy = y - mLastYIntercept;
                if (Math.abs(dx) > Math.abs(dy)) {
                    //如果横向移动大于纵向移动，则拦截事件
                    intercept = true;
                } else {
                    //否则不拦截
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                scrollBy(-dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                int deltaX = 0;
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 5000) {
                    deltaX = xVelocity > 0 ? -scrollX : getTotalWidth() - scrollX;
                } else {
                    if (scrollX < 0) {
                        deltaX = -scrollX;
                    } else if (scrollX > getTotalWidth()) {
                        deltaX = getTotalWidth() - scrollX;
                    } else {
                        deltaX = 0;
                    }
                }
                smoothScrollBy(deltaX, 0);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
