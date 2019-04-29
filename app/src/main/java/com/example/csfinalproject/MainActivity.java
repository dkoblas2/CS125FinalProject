package com.example.csfinalproject;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {
    //Added
    Button sendB;
    EditText phone, message;

    //Ends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendB = (Button) findViewById(R.id.send);
        phone = (EditText) findViewById(R.id.txtPhone);
        message = (EditText) findViewById(R.id.txtMessage);
        sendB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage();
            }

        });
    }
    public void sendMessage() {
        String number = phone.getText().toString().trim();
        String mess = message.getText().toString().trim();
        if (number == null || number.equals("") || mess == null || mess.equals("")) {
            Toast.makeText(this,"field can't be empty",Toast.LENGTH_LONG).show();
        } else {
            if (TextUtils.isDigitsOnly(number)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, mess, null, null);
                Toast.makeText(this, "message sent successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"please enter integer only",Toast.LENGTH_LONG).show();
        }
    }

        //Added

//        final EditText editText = findViewById(R.id.editText1);
//        editText.setOnClickListener(v -> {
//            editText.setText("");
//        });

//        findViewById(R.id.clear).setOnClickListener(v -> {
//
//        });

        final Button getContactButton = findViewById(R.id.getContact);
        final ListView contactList = findViewById(R.id.ContactList);
        getContactButton.setOnClickListener(v -> {
            getContact(contactList);
        });
    }

    public void getContact(View v) {
        final ListView contactList = findViewById(R.id.ContactList);
        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, null, null, null);
        startManagingCursor(c);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID};

        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, from, to);
        contactList.setAdapter(simpleCursorAdapter);
        contactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
