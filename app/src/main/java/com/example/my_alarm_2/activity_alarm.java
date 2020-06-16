package com.example.my_alarm_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.util.Collections;

public class activity_alarm extends AppCompatActivity {
    // private SpeechSynthesizer mTts;
    int code;
    private Toast mToast;
    private String content;
    private String repeated;
    private Button bnt_stop;
    int ack=0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        bnt_stop = findViewById(R.id.btn_stop);
        Bundle bundle_con = getIntent().getExtras();
        content = bundle_con.getString("content");
        SpeechUtility.createUtility(activity_alarm.this, SpeechConstant.APPID+"=5ed6a6f2");
        final SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(activity_alarm.this, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        bnt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTts.stopSpeaking();
            }
        });

        repeated = String.join("", Collections.nCopies(5, content));
        code = mTts.startSpeaking(repeated, null);



    }
    private SynthesizerListener mListener = new SynthesizerListener(){

        @Override
        public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onCompleted(SpeechError arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(activity_alarm.this, "合成完成", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakBegin() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakPaused() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakProgress(int arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSpeakResumed() {
            // TODO Auto-generated method stub

        }

    };
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            //Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码："+code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");

            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    private void showTip(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }
}
