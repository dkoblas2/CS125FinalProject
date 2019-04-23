package com.example.csfinalproject;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final EditText editText = findViewById(R.id.);
//        editText.setOnClickListener(v -> {
//            editText.setText("");
//        });

        findViewById(R.id.clear).setOnClickListener(v -> {

        });

        final Button getContactButton = findViewById(R.id.getContact);
        final ListView contactList = findViewById(R.id.ContactList);
        getContactButton.setOnClickListener(v -> {
            getContact(contactList);
        });



        findViewById(R.id.ContactList).setOnClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {



                Cursor item = (Cursor) parent.getItemAtPosition(position);
                item.getString(position);

                TextView contactName = findViewById(R.id.contact);
                contactName.setText(item.getString(position));

//                ListEntry entry= (ListEntry) parent.getAdapter().getItem(position);
//                Intent intent = new Intent(MainActivity.this, SendMessage.class);
//                String message = entry.getMessage();
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
            }
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

    public void onActivityResult() {

    }
}
