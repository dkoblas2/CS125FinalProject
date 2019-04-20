package com.example.csfinalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("6508682328", null, "sms message", null, null);
    }
}
