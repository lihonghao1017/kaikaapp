package com.sucetech.yijiamei.kaikaapp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.sucetech.yijiamei.kaikaapp.R;

import java.util.ArrayList;

public class XieKaDailog extends Dialog {
    private Context context;
    private TextView bluthtitle;

    public XieKaDailog(Context context) {
        super(context, R.style.BottomDialog);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.shuakai_layout, null);
        bluthtitle = view.findViewById(R.id.bluthtitle);
        setContentView(view);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay(); //获取屏幕宽高
        Point point = new Point();
        display.getSize(point);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes(); //获取当前对话框的参数值
        layoutParams.width = (int) (point.x); //宽度设置为屏幕宽度的0.5
        layoutParams.height = (int) (point.y); //高度设置为屏幕高度的0.5
        window.setAttributes(layoutParams);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public void setStuta(int type) {
        stuta=type;
        switch (type) {
            case stuta_ing:
                bluthtitle.setText("开卡中，请稍后...");
                break;
            case stuta_ok:
                bluthtitle.setText("开卡成功");
                break;
            case stuta_error:
                bluthtitle.setText("开卡失败，请重试...");
                break;
        }
    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        homePage.XiaoQuBean = xiaoqus.get(position);
//        dismiss();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (stuta == stuta_ing) {
            return true;
        } else {
            this.dismiss();
            return false;
        }
    }

    public int stuta = 0;
    public final int stuta_ing = 1;
    public final int stuta_error = 2;
    public final int stuta_ok = 3;

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.bluthClose) {
//            if (homePage.XiaoQuBean != null && !homePage.XiaoQuBean.equals("")) {
//                dismiss();
//            } else {
//                Toast.makeText(context,"请选择小区",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
