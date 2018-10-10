package com.nanshan.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanshan.support.R;
import com.nanshan.support.exception.CutException;
import com.nanshan.support.utils.LogUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by jiangbo on 2018/8/28.
 * 像用户同意用户协议一样
 * 总体思路：原本想用SpannableString来解决的，这个方案最简单方便
 * 但是它不能解决可点击文字和不可点击文字之间的间距，所以最终还是用了3个TextView
 */

public class CutTextView extends LinearLayout {
    //显示的文字
    private String text;
    //文字分配的集合，用来存放根据index截取的文字，一般定义为3段，分别置于0、1、2
    private List<String> mTextList;
    //一般文字的颜色
    private int normalColor = 0xff363636;
    private int clickColor = 0xff363636;
    //可点击字体颜色处于哪一段，值为0-2
    private int clickIndex;
    //开始的点和结束的点，包前不包后
    private int start;
    private int end;
    //可点击的文字与一般文字的间隔
    private int marginStart;
    private int marginEnd;
    //用来存放分段文字的集合，一般定义为3段，分别置于0、1、2
    private List<TextView> mTvList;

    public CutTextView(Context context) {
        super(context);
        init();
    }

    public CutTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CutTextView);
        start = array.getInt(R.styleable.CutTextView_start, start);
        end = array.getInt(R.styleable.CutTextView_end, end);
        marginStart = array.getDimensionPixelSize(R.styleable.CutTextView_marginStart, marginStart);
        marginEnd = array.getDimensionPixelSize(R.styleable.CutTextView_marginEnd, marginEnd);
        normalColor = array.getColor(R.styleable.CutTextView_normalColor, normalColor);
        clickColor = array.getColor(R.styleable.CutTextView_clickColor, clickColor);
        clickIndex = array.getInt(R.styleable.CutTextView_clickIndex, clickIndex);
        text = array.getString(R.styleable.CutTextView_cutText);
        array.recycle();
        init();
    }

    //设置文字
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 设置文字颜色
     *
     * @param normalColor 正常颜色
     * @param clickColor  可点击字体的颜色
     * @param clickIndex  可点击字体颜色处于哪一段，值为0-2
     */
    public void setTextColor(int normalColor, int clickColor, int clickIndex) {
        this.normalColor = normalColor;
        this.clickColor = clickColor;
        this.clickIndex = clickIndex;
    }

    //设置可点击文字的监听
    public void setClickListener(OnClickListener... listeners) {
        if (listeners == null) {
            LogUtils.w("listeners is null");
            return;
        }

        //用于存放设置了clickable为true的TextView
        Queue<TextView> queue = new LinkedList<>();
        for (TextView textView : mTvList) {
            if (textView.isClickable()) {
                queue.offer(textView);
            }
        }

        if (listeners.length > queue.size()) {
            throw new CutException("listeners's length " + listeners.length + " is longer than textview's queue " + queue.size());
        }

        for (OnClickListener listener : listeners) {
            queue.poll().setOnClickListener(listener);
        }
    }

    //设置可点击文字和正常文字之间的间隔
    public void setMargin(int marginStart, int marginEnd) {
        this.marginStart = marginStart;
        this.marginEnd = marginEnd;
    }

    //------------------------------私有方法--------------------------------
    private void init() {
        splitText();
        mTvList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            boolean isClick = clickIndex == i;
            int margin = 0;
            if (i == 1) {
                margin = marginStart;
            }
            if (i == 2) {
                margin = marginEnd;
            }
            TextView tv = createTextView(mTextList.get(i), isClick ? clickColor : normalColor,
                    margin, isClick);
            mTvList.add(tv);
        }
    }

    private void splitText() {
        mTextList = new ArrayList<>();
        if (TextUtils.isEmpty(text)) {
            LogUtils.e("cutTextView is error because of text is null");
            return;
        }

        //根据输入的start和end来截取text，分别放置在0、1、2位置
        String first = text.substring(0, start);
        mTextList.add(first);
        String second = text.substring(start, end);
        mTextList.add(second);
        String last = text.substring(end, text.length());
        mTextList.add(last);
    }

    private TextView createTextView(String text, int color, int marginStart, boolean isClick) {
        TextView textView = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setText(text);
        params.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(params);
        textView.setTextColor(color);
        params.leftMargin = marginStart;
        if (isClick) {
            textView.setClickable(true);
        } else {
            textView.setClickable(false);
        }
        addView(textView);
        return textView;
    }
}
