package com.example.csfinalproject;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final EditText editText = findViewById(R.id.editText1);
//        editText.setOnClickListener(v -> {
//            editText.setText("");
//        });

        final Button getContactButton = findViewById(R.id.getContact);
        final ListView contactList = findViewById(R.id.ContactList);
        getContactButton.setOnClickListener(v -> {
            getContact(contactList);
        });



//       smsManager.sendTextMessage("6508682328", null, "sms message", null, null);
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
