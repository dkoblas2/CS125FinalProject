package com.example.csfinalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.editText1);
        editText.setOnClickListener(v -> {
            editText.setText("");
        });


//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("6508682328", null, "sms message", null, null);
    }
}
