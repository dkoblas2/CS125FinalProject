package com.example.csfinalproject;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;

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
        getContactButton.setOnClickListener(v -> {
            Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            String aNameFromContacts[] = new String[contacts.getCount()];
            String aNumberFromContacts[] = new String[contacts.getCount()];
            int i = 0;

            int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            while(contacts.moveToNext()) {

                String contactName = contacts.getString(nameFieldColumnIndex);
                aNameFromContacts[i] =    contactName ;

                String number = contacts.getString(numberFieldColumnIndex);
                aNumberFromContacts[i] =    number ;
                i++;
            }

            contacts.close();
        });



//       smsManager.sendTextMessage("6508682328", null, "sms message", null, null);
    }
}
