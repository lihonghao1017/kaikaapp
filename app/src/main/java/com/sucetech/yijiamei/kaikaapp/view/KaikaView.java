package com.sucetech.yijiamei.kaikaapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbar.scale.ScaleLinearLayout;
import com.sucetech.yijiamei.kaikaapp.Configs;
import com.sucetech.yijiamei.kaikaapp.MainActivity;
import com.sucetech.yijiamei.kaikaapp.R;
import com.sucetech.yijiamei.kaikaapp.UserMsg;
import com.sucetech.yijiamei.kaikaapp.bean.BukaBean;
import com.sucetech.yijiamei.kaikaapp.tool.TaskManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KaikaView extends ScaleLinearLayout implements View.OnClickListener {
    private TextView commit, address, phone, name;
    public String kaId;
    private XieKaDailog xieKaDailog;
    private View tab01,tab02,tabCursor01,tabCursor02;
    private boolean isKaika=true;
    private BukaView bukaView;

    public void setKaikaiId(final String kaId){
        this.kaId=kaId;
        if (isKaika){
            send();
        }else{
            xieKaDailog.setStuta(1);
            TaskManager.getInstance().addTask(new Runnable() {
                @Override
                public void run() {
                    requestLoing3(kaId,bukaBean.residentId);
                }
            });

        }

    }
    public String getName(){
        return name.getText().toString();
    }
    public String getPhone(){
        return phone.getText().toString();
    }

    public KaikaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.kaika_layout, null);
        commit = v.findViewById(R.id.commit);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        name = v.findViewById(R.id.name);
        commit.setOnClickListener(this);
        this.addView(v,-1,-1);
        tab01=v.findViewById(R.id.tabLayout01);
        tab02=v.findViewById(R.id.tabLayout02);
        tab01.setOnClickListener(this);
        tab02.setOnClickListener(this);
        tabCursor01 = v.findViewById(R.id.tabCursor01);
        tabCursor02 = v.findViewById(R.id.tabCursor02);
        bukaView=v.findViewById(R.id.bukaView);
        bukaView.setkaiCards(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commit) {
            if (name.getText().toString() == null || name.getText().toString().equals("")) {
                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                return;
            }
            if (phone.getText().toString() == null || phone.getText().toString().equals("")) {
                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                return;
            }

            xieKaDailog=new XieKaDailog(getContext());
            xieKaDailog.show();

        }else if(v.getId() == R.id.tabLayout01){
            tabCursor01.setVisibility(View.VISIBLE);
            tabCursor02.setVisibility(View.GONE);
            bukaView.setVisibility(View.GONE);
            isKaika=true;
        }else if(v.getId() == R.id.tabLayout02){
            tabCursor01.setVisibility(View.GONE);
            tabCursor02.setVisibility(View.VISIBLE);
            bukaView.setVisibility(View.VISIBLE);
            isKaika=false;
        }
    }
    public boolean isShuaka(){
        return xieKaDailog!=null&&xieKaDailog.isShowing();
    }

    public void send(){
        final String nameStr = name.getText().toString();
        final String phoneStr = phone.getText().toString();
        final String addressStr = address.getText().toString();
        xieKaDailog.setStuta(1);
        TaskManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {

                requestLoing2(nameStr, phoneStr, addressStr);
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String requestLoing2(String n, String p, String a) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", n);
            jsonObject.put("cellphone", p);
            if (a != null && !a.equals(""))
                jsonObject.put("address", a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(Configs.baseUrl + ":8081/datong/v1/residents")
                .header("Authorization", UserMsg.getToken())
                .post(body)
                .build();
        try {
            final Response response = ((MainActivity) getContext()).client.newCall(request).execute();
            if (response.isSuccessful()) {
                final String ll=response.body().string();

                Log.e("LLL","ll--》"+ll);
                try {
                    JSONObject object=new JSONObject(ll);
                    String ididid=object.optString("id");
                    requestLoing3(kaId,ididid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("LLL","ll-->"+ll);
//
            } else {
                Log.e("LLL", "shibai--->"+response.body().string());
                sendError();
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendError();
        }
        return null;
    }

    private String requestLoing3(String cardNumber, String residentsId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cardNumber", cardNumber);
            jsonObject.put("residentsId", residentsId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));

        Request request = new Request.Builder()
                .url(Configs.baseUrl + ":8081/datong/v1/residents/card?cardNumber="+cardNumber+"&residentsId="+residentsId)
                .header("Authorization", UserMsg.getToken())
                .post(body)
                .build();
        try {
            final Response response = ((MainActivity) getContext()).client.newCall(request).execute();
            if (response.isSuccessful()) {
                final String ll=response.body().string();

                Log.e("LLL","ll-requestLoing3->"+ll);
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        xieKaDailog.setStuta(3);
                        Toast.makeText(getContext(), "chengong -->", Toast.LENGTH_LONG).show();
                    }
                });
//
            } else {
                Log.e("LLL", "shibai--->");
                sendError();
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendError();
        }
        return null;
    }
    public BukaBean bukaBean;
    public void showBukaDailog(BukaBean bukaBean){
        this.bukaBean=bukaBean;
        xieKaDailog=new XieKaDailog(getContext());
        xieKaDailog.show();
    }
    public boolean isBukaing(){
        return isKaika;
    }
    private void sendError(){
        this.post(new Runnable() {
            @Override
            public void run() {
                xieKaDailog.setStuta(2);
            }
        });
    }
}
