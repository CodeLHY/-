package com.example.my_alarm_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.Calendar;
// 科大讯飞appid 5ed6a6f2
public class MainActivity extends AppCompatActivity {
    private Button btn_set;
    private Button btn_cancel;
    private TextView tv_time;
    private String str_tv;
    private int hour;
    private int min;
    private String content;
    private AlarmManager am;
    private PendingIntent sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5ed6a6f2");
        btn_set = findViewById(R.id.btn_set);
        btn_cancel = findViewById(R.id.btn_cancel);
        tv_time = findViewById(R.id.tv_time);

        //显式跳转
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_set = new Intent(MainActivity.this, activity_set_time.class);
                startActivityForResult(intent_set, 0);  //requeset code是为了在下面的onacativityresult函数里面确定是哪一个请求数据的intent
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am.cancel(sender);
                tv_time.setText("暂无");
                Toast.makeText(MainActivity.this, "闹钟已取消", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 由于我们仅仅只有一个请求数据，因此这里不再判断request code 和result code了
        hour = data.getExtras().getInt("hour");
        min = data.getExtras().getInt("min");
        content = data.getExtras().getString("content");
        str_tv = String.format("%02d", hour) + " : "+String.format("%02d", min);
        tv_time.setText(str_tv);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Intent intent = new Intent(MainActivity.this, activity_alarm.class);
        Bundle bundle_content = new Bundle();
        bundle_content.putString("content", content);
        intent.putExtras(bundle_content);
        sender  = PendingIntent.getActivity(MainActivity.this, 0 ,intent, 0);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender); //设置到一定的时间就跳转到activity_alarm页面
    }
}
