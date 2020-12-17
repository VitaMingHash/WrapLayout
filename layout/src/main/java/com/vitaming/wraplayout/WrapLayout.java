package com.vitaming.wraplayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaming.layout.R;

import java.util.List;

/**
 * Created by Ming
 * 2020/12/11
 */
public class WrapLayout<T> extends ViewGroup {
    private BaseAdapter baseAdapter;
    /**
     * 每个item的间隔
     */
    private int interval;
    /**
     * 纵向行数，超过的隐藏
     */
    private int maxLines;

    public WrapLayout(Context context) {
        super(context);
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public void setAdapter(BaseAdapter baseAdapter) {
        this.baseAdapter = baseAdapter;
        addViews();
    }

    private void initAttr(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.WrapLayout);
            interval = arr.getInteger(R.styleable.WrapLayout_jiange, 0);
            maxLines = arr.getInteger(R.styleable.WrapLayout_maxLines, 10);
            arr.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            int lines = 1;
            int cumulateLayoutWidth = 0;
            int cumulateLayoutHeight = 0;
            int maxLineHeight = 0;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                if (cumulateLayoutWidth + lp.leftMargin + childWidth + lp.rightMargin > getMeasuredWidth()) {
                    lines += 1;
                    cumulateLayoutWidth = 0;
                    cumulateLayoutHeight = cumulateLayoutHeight + maxLineHeight;
                    maxLineHeight = 0;
                }
                if (lines <= maxLines) {
                    if (i == count - 1) {
                        cumulateLayoutHeight += childHeight + lp.topMargin + lp.bottomMargin;
                    }
                    cumulateLayoutWidth = cumulateLayoutWidth + lp.leftMargin + childWidth + lp.rightMargin;
                    maxLineHeight = Math.max(maxLineHeight, lp.topMargin + childHeight + lp.bottomMargin);
                }
            }
            setMeasuredDimension(getMeasuredWidth(), cumulateLayoutHeight + getPaddingBottom() + getPaddingTop());
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return p;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    /**
     * 对子控件进行摆放
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /*总行数*/
        int lines = 1;
        /*用于记录子控件添加到同一行后的累计宽度，以此作为是否换行的依据*/
        int cumulateLayoutWidth = 0;
        /*累计每行最大的高度值，以此作为下一行与父容器的顶边距的值*/
        int cumulateLayoutHeight = 0;
        /*用于定位每个子控件的位置时用的临时变量*/
        int left, top, right, bottom;
        /*记录每行的最大高度的临时变量，在换行时使用*/
        int maxLineHeight = 0;
        /*ViewGroup容器里的子控件数*/
        int count = getChildCount();
        /*摆放ViewGroup容器里的子控件*/
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            /*子控件的测量宽度和高度，不要使用child.getWidth()和child.getHeight()*/
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            /*getWidth()是ViewGroup的宽度,如果累计的宽度再加一个子控件的宽度超过了父容器的宽度getWidth(),那么就要另起一行了*/
            if (cumulateLayoutWidth + lp.leftMargin + childWidth + lp.rightMargin < getWidth()) {
                left = cumulateLayoutWidth + lp.leftMargin;
                top = cumulateLayoutHeight + lp.topMargin;
                right = left + childWidth;
                bottom = top + childHeight;
            } else {
                lines += 1;
                cumulateLayoutWidth = 0;
                cumulateLayoutHeight = cumulateLayoutHeight + maxLineHeight;
                maxLineHeight = 0;
                left = cumulateLayoutWidth + lp.leftMargin;
                top = cumulateLayoutHeight + lp.topMargin;
                right = left + childWidth;
                bottom = top + childHeight;
            }
            if (lines <= maxLines) {
                /*累加宽度*/
                cumulateLayoutWidth = cumulateLayoutWidth + lp.leftMargin + childWidth + lp.rightMargin;
                /*选出行高*/
                maxLineHeight = Math.max(maxLineHeight, lp.topMargin + childHeight + lp.bottomMargin + getPaddingTop() + getPaddingBottom());
                child.layout(left, top, right, bottom);
            }
        }
    }

    private void addViews() {
        List<T> list = baseAdapter.getData();
        int layout = baseAdapter.getLayout();
        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(layout, this, false);
            baseAdapter.convert(new ViewHolder(view), list.get(i), i);
            addView(view);
        }
    }
}