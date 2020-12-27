package com.hznu.transactionofidlegoods.myview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hznu.transactionofidlegoods.R;

public class MyTitleBar extends LinearLayout {
    private ImageView iv_backward;
    private TextView tv_title, tv_forward;

    public MyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LinearLayout bar_title = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.my_bar_title, this);

        iv_backward = (ImageView) bar_title.findViewById(R.id.iv_backward);
        tv_title = (TextView) bar_title.findViewById(R.id.tv_title);
        tv_forward = (TextView) bar_title.findViewById(R.id.tv_forward);

        //设置监听器
        //如果点击back则结束活动
        iv_backward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setTvTitleText(String text) {
        this.tv_title.setText(text);
    }

    public void setTvForwardText(String text) {
        this.tv_forward.setText(text);
    }

    public TextView getTvForward() {
        return this.tv_forward;
    }

}
