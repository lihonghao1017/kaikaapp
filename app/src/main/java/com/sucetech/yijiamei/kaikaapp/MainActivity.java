package com.sucetech.yijiamei.kaikaapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sucetech.yijiamei.kaikaapp.view.KaikaView;
import com.sucetech.yijiamei.kaikaapp.view.LoginView;
import com.sucetech.yijiamei.kaikaapp.view.ProgressDailogView;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends Activity {
    private LoginView loginView;
    private KaikaView kaikaView;
    private ProgressDailogView progressDailogView;
    public final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginView=findViewById(R.id.loginView);
        progressDailogView=findViewById(R.id.progressDailogView);
        kaikaView=findViewById(R.id.kaikaView);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xff4AA9FD);
        //初始化NfcAdapter
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        //初始化PendingIntent
        // 初始化PendingIntent，当有NFC设备连接上的时候，就交给当前Activity处理
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }
    public void showProgressDailogView(String des) {
        progressDailogView.setVisibility(View.VISIBLE);
        progressDailogView.setDes(des);
    }

    public void hideProgressDailogView() {
        progressDailogView.setVisibility(View.GONE);
    }


    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent){
        getTagInfo(intent);
    }
    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] tagId = tag.getId();
        Log.e("LLL",tagId+"");
        String str = ByteArrayToHexString(tagId);
       if (kaikaView.getVisibility()==View.VISIBLE&&kaikaView.isShuaka()){
           String name=kaikaView.getName();
           String phone=kaikaView.getPhone();
           if (name==null||name.equals(""))
               return;
           if (phone==null||phone.equals(""))
               return;
           writeMsg(tag,name+":"+phone+":"+str);
           kaikaView.setKaikaiId(str);
       }
    }
    public void writeMsg(Tag detectedTag,String mText){
        NdefMessage ndefMessage = new NdefMessage(
                new NdefRecord[]{createTextRecord(mText)});
        boolean result = writeTag(ndefMessage, detectedTag);
        if (result) {
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean writeTag(NdefMessage ndefMessage, Tag tag) {
        try {
            Ndef ndef = Ndef.get(tag);
            ndef.connect();
            ndef.writeNdefMessage(ndefMessage);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    public static NdefRecord createTextRecord(String text) {
        byte[] langBytes = Locale.CHINA.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = Charset.forName("UTF-8");
        //将文本转换为UTF-8格式
        byte[] textBytes = text.getBytes(utfEncoding);
        //设置状态字节编码最高位数为0
        int utfBit = 0;
        //定义状态字节
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        //设置第一个状态字节，先将状态码转换成字节
        data[0] = (byte) status;
        //设置语言编码，使用数组拷贝方法，从0开始拷贝到data中，拷贝到data的1到langBytes.length的位置
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        //设置文本字节，使用数组拷贝方法，从0开始拷贝到data中，拷贝到data的1 + langBytes.length
        //到textBytes.length的位置
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        //通过字节传入NdefRecord对象
        //NdefRecord.RTD_TEXT：传入类型 读写
        NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return ndefRecord;
    }

    private String flipHexStr(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= s.length() - 2; i = i + 2) {
            result.append(new StringBuilder(s.substring(i, i + 2)).reverse());
        }
        return result.reverse().toString();

    }

    private void processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String CardId =ByteArrayToHexString(tagFromIntent.getId());
    }
    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F" };
        String out = "";


        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

}
