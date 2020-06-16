package com.example.my_alarm_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class activity_set_time extends AppCompatActivity {
    private TimePicker tp;
    private Button btn_setOK;
    private EditText et;
    private int hour=0;
    private int min=0;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        tp = findViewById(R.id.tp);
        btn_setOK = findViewById(R.id.btn_setOK);
        et = findViewById(R.id.et_content);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
            }
        });
        btn_setOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et.getText().toString();
                Intent intent_data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("hour", hour);
                bundle.putInt("min", min);
                bundle.putString("content", content);
                intent_data.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent_data);  //设置setActivityForResult的返回结果，
                // result_code是要返回的页面中onActivityResult函数用于方便确定是哪个请求的哪个结果而设定的
                Toast.makeText(activity_set_time.this, "the alarm is has been setted!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
