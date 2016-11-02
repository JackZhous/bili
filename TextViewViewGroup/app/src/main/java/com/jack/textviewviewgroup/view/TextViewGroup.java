/*
 *         Copyright (C) 2016-2017 宙斯
 *         All rights reserved
 *
 *        filename :Class4
 *        description :
 *
 *         created by jackzhous at  11/07/2016 12:12:12
 *         http://blog.csdn.net/jackzhouyu
 */

package com.jack.textviewviewgroup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/***********
 * author: jackzhous
 * file: TextViewGroup.java
 * create date: 2016/10/18 10:17
 * desc:
 ************/
public class TextViewGroup extends ViewGroup {

    private static final String TAG = "jackzhous";
    private static final int default_space = 30;                                                      //默认组件与组件之间的间隔

    private static final int expand_space = 200;                                                     //默认增大布局

    private int view_default_height = 0;

    private boolean isExpand = false;                                                               //组件是否已经扩展

    private int childCount = 0;
    private int parent_bottom, parent_width;                                                                        //当前组件的宽度，子组件都必须在这个宽度之内
    private int second_enter = 0;                                                            //第一次进入

    private Scroller mScroller;


    public TextViewGroup(Context context) {
        super(context);
        init(context);
    }

    public TextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        mScroller = new Scroller(context);
    }

    /**
     * 返回当前组件的默认布局参数，该组件下的子组件都会使用这个默认的LayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LinearLayout.LayoutParams(getContext(), attrs);
    }


    //宽高均固定
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        childCount = getChildCount();

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        view_default_height = (view_default_height == 0) ? sizeHeight : view_default_height;
        //计算出所有children的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(sizeWidth, view_default_height);


        Log.i(TAG, "sizeHeight " + sizeHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG + "-----", "l " + l + " t " + t + " r " + r + " b " + b);
        parent_bottom = b;
        parent_width = r;

        int childViewWidth = l;                                                                          //child宽度和
        int start = 0;
        int end = 0;
        int startWidth = l;
        int startTop = t;
        int space;
        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);

            int sizeWidth = child.getMeasuredWidth();
            int sizeHeight = child.getMeasuredHeight();
            space = r - childViewWidth;
            childViewWidth = childViewWidth + default_space + sizeWidth;
            if(childViewWidth > r){
                end = i;
                onLayoutChildView(start, end, startWidth, startTop, space);
                startTop = startTop + default_space + sizeHeight;
                childViewWidth = l + sizeWidth;
                start = i;
            }

            Log.i(TAG, "childViewWidth " + childViewWidth + " end " + end);
        }

        /**
         * 说明还有一部分没有布局的child
         */
        Log.i(TAG," end is "+ end + " count " + childCount);
        if(end != childCount){
            Log.i(TAG," is over");
            onLayoutChildView(start, childCount, l, startTop, 0);
        }
        second_enter++;
    }

    /**
     * 布局一行的组件视图
     * @param start_index  其实child
     * @param end_index    结束child
     * @param start_x      x开始的位置
     * @param start_y      y开始的位置
     * @param space        一行剩余的空间
     */
    private void onLayoutChildView(int start_index, int end_index, int start_x, int start_y, int space){

        int endX = 0;
        int endY = 0;
        int sub_space = 0;              //分摊到每个组件上的间隔
        if(space > 1){
            int view_numbers = end_index - start_index + 1;
            sub_space = (space - 10)/ view_numbers;
            Log.i(TAG, "space " + space + " sub_space " + sub_space);
        }
        int i;
        for(i = start_index; i < end_index; i++){
            View child = getChildAt(i);
            endX = start_x + child.getMeasuredWidth() + sub_space;
            endY = start_y + child.getMeasuredHeight();
            if(second_enter < 2){

                int paddingL = child.getPaddingLeft() + sub_space / 2;
                int paddingR = child.getPaddingRight() + sub_space / 2;
                int paddingTB = child.getPaddingBottom();
                Log.i(TAG, "paddingL " + paddingL + " paddingR " + paddingR);
                child.setPadding(paddingL, paddingTB, paddingR, paddingTB);
            }

            if(endX > parent_width - 10){
                endX = parent_width - 10;
            }
            Log.i(TAG, "endX " + endX + " parent_width " + parent_width);
            child.layout(start_x, start_y, endX, endY);

            start_x = endX + default_space;
        }
        Log.i(TAG, "onLayoutChildView endWidth " + endX + " parent_width " + parent_width);
        if(i == childCount){
            parent_bottom = endY - parent_bottom + default_space;
        }


    }

    /**
     * 放大或者缩小组件
     */
    public void expandOrClose(){
        if(!isExpand){
            view_default_height = view_default_height + expand_space;
            isExpand = true;
        }else{
            isExpand = false;
            view_default_height = view_default_height - expand_space;
        }
        requestLayout();
        postInvalidate();
    }

    private int downY;
    private int moveY;
    private int upY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //只有在展开的情况下才能进行滑动操作

        Log.i(TAG, " onTouchEvent parent_bottom " + parent_bottom);
        if(!isExpand){
            Log.i(TAG, "isExpand " + isExpand);
            return super.onTouchEvent(event);
        }

        int action = event.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                downY = (int)event.getY();
                Log.i(TAG, "down y" + downY );
                break;

            case MotionEvent.ACTION_MOVE:

                moveY = (int)event.getY();

                int dy = downY - moveY;                                                             //移动的变量
                int need_move_y = getScrollY() + dy;                                           //scrollTo 向上滑动参数为证  向下滑动参数为负
                if(need_move_y < 0){
                    scrollTo(0, 0);
                    Log.i(TAG, "0 ------------------- 0");
                }else if(need_move_y > parent_bottom){
                    scrollTo(0, parent_bottom);
                    Log.i(TAG, "0 ------------------- 300");
                }else{
                    scrollBy(0, dy);
                    Log.i(TAG, " 0 -----------------nedd_move " + dy);
                }

                downY = moveY;
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                Log.i(TAG, "default " + action);
                break;
        }
        //postInvalidate();
        return true;
    }


    @Override
    public void computeScroll() {

        if(mScroller.computeScrollOffset()){
            Log.i(TAG, "computeScroll");
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag = super.dispatchTouchEvent(ev);
        Log.i(TAG, " dispatchTouchEvent " + flag);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.i(TAG, " onInterceptTouchEvent " + flag);
        return flag;
    }
}
