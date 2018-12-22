package com.sucetech.yijiamei.kaikaapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sucetech.yijiamei.kaikaapp.R;


/**
 * Created by lihh on 2017/4/11.
 */

public class ProgressDailogView extends LinearLayout {
    private TextView desTv;
    public ProgressDailogView(Context context) {
        super(context);
        initView(context);
    }

    public ProgressDailogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProgressDailogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }
    private void initView(Context context){
        View v = LayoutInflater.from(context).inflate(R.layout.navi_progress_dailog, null);
        v.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        desTv=(TextView)v.findViewById(R.id.dialog_content_tv);
        this.addView(v,new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        v.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
    public void setDes(String des){
        desTv.setText(des);
    }
}
