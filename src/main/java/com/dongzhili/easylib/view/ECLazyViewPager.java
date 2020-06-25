package com.dongzhili.easylib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ECLazyViewPager extends LazyViewPager {

    private boolean isPagingEnabled = true;

    public ECLazyViewPager(Context context) {
        super(context);
    }

    public ECLazyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
