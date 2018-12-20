package com.sucetech.yijiamei.kaikaapp.view;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mapbar.scale.ScaleLinearLayout;
import com.sucetech.yijiamei.kaikaapp.Configs;
import com.sucetech.yijiamei.kaikaapp.R;
import com.sucetech.yijiamei.kaikaapp.UserMsg;
import com.sucetech.yijiamei.kaikaapp.tool.TaskManager;


/**
 * Created by lihh on 2018/9/19.
 */

public class LoginView extends ScaleLinearLayout implements View.OnClickListener {
    private EditText user, pwd,baseUrl;
    private View commit,pwdEyeIcon;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginView(Context context) {
        super(context);
    }


    public void initView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_login, null);
        user = (EditText) v.findViewById(R.id.username);
        pwd = (EditText) v.findViewById(R.id.pwd);
        baseUrl=(EditText)v.findViewById(R.id.baseUrl);
        commit = v.findViewById(R.id.commit);
        commit.setOnClickListener(this);
        this.addView(v, -1, -1);
        pwdEyeIcon= v.findViewById(R.id.pwdEyeIcon);
        pwdEyeIcon.setOnClickListener(this);
        user.setText(UserMsg.getUserName());
        pwd.setText(UserMsg.getPwd());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commit) {
//            ((MainActivity) getContext()).showProgressDailogView("登陆中...");
            UserMsg.saveUserName(user.getText().toString());
            UserMsg.savePwd(pwd.getText().toString());
            if(baseUrl.getText()!=null&&!baseUrl.getText().toString().equals("")&&baseUrl.getText().toString().contains("http://")){
                Configs.baseUrl=baseUrl.getText().toString();
            }
            TaskManager.getInstance().addTask(new Runnable() {
                @Override
                public void run() {
//                    requestLoing2();
                }
            });
        }else if(v.getId() == R.id.pwdEyeIcon){
            pwdEyeIcon.setSelected(!pwdEyeIcon.isSelected());
            if(pwdEyeIcon.isSelected()) {
                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    private String TAG = "LLL";
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//    private String requestLoing2() {
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("username", UserMsg.getUserName());
//            jsonObject.put("password", UserMsg.getPwd());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));
//        Request request = new Request.Builder()
//                .url(Configs.baseUrl+"/api/v1/yijiamei/login")
//                .post(body)
//                .build();
//        try {
//            final Response response = ((MainActivity) getContext()).client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                UserMsg.saveToken(response.header("Authorization"));
//                requestYiyuan();
////                this.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        ((MainActivity)getContext()).hideProgressDailogView();
////                        mEventManager.notifyObservers(EventStatus.logined,null);
////                        LoginView.this.setVisibility(View.GONE);
//////                        mEventManager.notifyObservers(EventStatus.logined,null);
////                        Toast.makeText(getContext(),"chengong -->",Toast.LENGTH_LONG);
////                    }
////                });
////
//                return response.body().string();
//            } else {
//                Log.e("LLL", "shibai--->");
//
//                loginFail();
////                Toast.makeText(getContext(),"shibai -->"+response.message(),Toast.LENGTH_LONG);
//                throw new IOException("Unexpected code " + response);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            loginFail();
//        }
//        return null;
//    }
//    private void loginFail(){
//        this.post(new Runnable() {
//            @Override
//            public void run() {
//                ((MainActivity) getContext()).hideProgressDailogView();
//                Toast.makeText(getContext(), "登陆失败，请检查用户名或密码", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void requestYiyuan() {
//
//
////        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));
//        Request request = new Request.Builder()
//                .header("Authorization", UserMsg.getToken())
//                .addHeader("Accept", "application/json")
//                .url(Configs.baseUrl+"/api/v1/yijiamei/medical/")
//                .get()
//                .build();
//        try {
//            final Response response = ((MainActivity) getContext()).client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                Log.e("LLL", "chenggong--requestYiyuan->");
////                Log.e("LLL","chenggong--requestYiyuan22->"+response.body().string());
////                UserMsg.saveYiyuan(response.body().string());
//                final List<yiyaunBean> data= new Gson().fromJson(response.body().string(), new TypeToken<List<yiyaunBean>>(){}.getType());//把JSON字符串转为对象
//                Log.e("LLL", "chenggong--requestYiyuan->" + UserMsg.getYiyuan());
//                wuiaoYiyuanBean.yiyaunBeanList=data;
//                this.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (wuiaoYiyuanBean.wuliaoTypes!=null&&wuiaoYiyuanBean.wuliaoTypes.size()>0){
//                            ((MainActivity) getContext()).hideProgressDailogView();
//                            mEventManager.notifyObservers(EventStatus.hospitalData, data);
//                            LoginView.this.setVisibility(View.GONE);
////                        mEventManager.notifyObservers(EventStatus.logined,null);
//                            Toast.makeText(getContext(), "chengong -->", Toast.LENGTH_LONG);
//                        }
//                    }
//                });
//                getMeteriType();
////
////                return response.body().string();
//            } else {
//                Log.e("LLL", "shibai---requestYiyuan>");
////                ((MainActivity)getContext()).hideProgressDailogView();
//                this.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getContext(), "shibai --requestYiyuan>", Toast.LENGTH_LONG);
//                    }
//                });
////                Toast.makeText(getContext(),"shibai -->"+response.message(),Toast.LENGTH_LONG);
//                throw new IOException("Unexpected code " + response);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void getMeteriType(){
//        Request request = new Request.Builder()
//                .header("Authorization", UserMsg.getToken())
//                .addHeader("Accept", "application/json")
//                .url(Configs.baseUrl+"/api/v1/yijiamei/materialType")
//                .get()
//                .build();
//        try {
//            final Response response = ((MainActivity) getContext()).client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                Log.e("LLL", "chenggong--getMeteriType->");
////                Log.e("LLL","chenggong--getMeteriType->"+response.body().string());
////                UserMsg.saveYiyuan(response.body().string());
//                final List<WuliaoType> data= new Gson().fromJson(response.body().string(), new TypeToken<List<WuliaoType>>(){}.getType());//把JSON字符串转为对象
//   wuiaoYiyuanBean.wuliaoTypes=data;
////                Log.e("LLL", "chenggong--requestYiyuan->" + UserMsg.getYiyuan());
//                this.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (wuiaoYiyuanBean.yiyaunBeanList!=null&&wuiaoYiyuanBean.yiyaunBeanList.size()>0){
//                            ((MainActivity) getContext()).hideProgressDailogView();
//                            mEventManager.notifyObservers(EventStatus.hospitalData, wuiaoYiyuanBean);
//                            LoginView.this.setVisibility(View.GONE);
////                        mEventManager.notifyObservers(EventStatus.logined,null);
//                            Toast.makeText(getContext(), "chengong -->", Toast.LENGTH_LONG);
//                        }
//                    }
//                });
////
////                return response.body().string();
//            } else {
//                Log.e("LLL", "shibai---requestYiyuan>");
////                ((MainActivity)getContext()).hideProgressDailogView();
//                this.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getContext(), "shibai --requestYiyuan>", Toast.LENGTH_LONG);
//                    }
//                });
////                Toast.makeText(getContext(),"shibai -->"+response.message(),Toast.LENGTH_LONG);
//                throw new IOException("Unexpected code " + response);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
