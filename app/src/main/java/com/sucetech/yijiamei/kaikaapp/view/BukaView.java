package com.sucetech.yijiamei.kaikaapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sucetech.yijiamei.kaikaapp.Configs;
import com.sucetech.yijiamei.kaikaapp.MainActivity;
import com.sucetech.yijiamei.kaikaapp.R;
import com.sucetech.yijiamei.kaikaapp.adapter.CardAdapter;
import com.sucetech.yijiamei.kaikaapp.bean.BukaBean;
import com.sucetech.yijiamei.kaikaapp.tool.TaskManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BukaView extends LinearLayout implements CardAdapter.BuKaClick {
    private EditText searchView;
    private ListView listView;
    private CardAdapter cardAdapter;
    private List<BukaBean> datas;
    private KaikaView kaikaView;
    public void setkaiCards( KaikaView kaikaView){
        this.kaikaView=kaikaView;
    }
    public BukaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.buka_layout, null);
        searchView = v.findViewById(R.id.searchview);
        listView = v.findViewById(R.id.listView);
        addView(v, -1, -1);
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(final TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String searText = v.getText().toString();
                    if (searText != null && !searText.equals(""))
                        TaskManager.getInstance().addTask(new Runnable() {
                            @Override
                            public void run() {
                                requestLoing2(searText);
                            }
                        });
                    return true;
                }
                return false;
            }
        });
        datas=new ArrayList<>();
        cardAdapter=new CardAdapter(getContext(),datas,this);
        listView.setAdapter(cardAdapter);
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String requestLoing2(String searchStr) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", searchStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(Configs.baseUrl + ":8081/datong/v1/residents/search")
                .post(body)
                .build();
        try {
            final Response response = ((MainActivity) getContext()).client.newCall(request).execute();
            if (response.isSuccessful()) {
                final String ll = response.body().string();
                JSONObject object = new JSONObject(ll);
                JSONArray array = object.optJSONArray("content");
                List<BukaBean> datas=new ArrayList<>();
                if (array != null && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject chi = array.getJSONObject(i);
                        BukaBean bukaBean = new BukaBean();
                        bukaBean.name = chi.optString("name");
                        bukaBean.cellphone = chi.optString("cellphone");
                        bukaBean.residentId = chi.optString("residentId");
                        bukaBean.name = chi.optString("name");
                        JSONArray array1=chi.optJSONArray("cards");
                        if (array1!=null&&array1.length()>0){
                            bukaBean.cards=new ArrayList<>();
                            for (int j = 0; j < array1.length(); j++) {
                                bukaBean.cards.add(array1.getString(j));
                            }
                        }
                        datas.add(bukaBean);
                        searchOK(datas);
                    }
                }
            } else {
                Log.e("LLL", "shibai--->");
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void searchOK(final List<BukaBean> data){
        this.post(new Runnable() {
            @Override
            public void run() {
                datas.clear();
                datas.addAll(data);
                cardAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onBuKaClick(BukaBean bukaBean) {
        kaikaView.showBukaDailog(bukaBean);
    }
}
