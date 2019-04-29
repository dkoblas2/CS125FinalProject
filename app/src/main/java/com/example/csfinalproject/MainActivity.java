package com.example.csfinalproject;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    //Added
    protected EditText phone, message;
    protected LocationManager locationManager;
    protected Double latitude, longitude;
    protected boolean includeLocation;

    //Ends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button sendB = (Button) findViewById(R.id.send);

        final TextView locationView = findViewById(R.id.locationView);

        phone = (EditText) findViewById(R.id.txtPhone);
        message = (EditText) findViewById(R.id.txtMessage);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            System.out.println("Location permission denied");
        }

        sendB.setOnClickListener(v -> {
            sendMessage();


        });

        // location box + display
        final CheckBox locationCheckBox = (CheckBox) findViewById(R.id.includeLocation);
        includeLocation = locationCheckBox.isChecked();
        locationCheckBox.setOnClickListener(v -> {
            locationView.setText("Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            includeLocation = locationCheckBox.isChecked();
            if (includeLocation) {
                locationView.setVisibility(View.VISIBLE);
            } else {
                locationView.setVisibility(View.INVISIBLE);
            }
        });

        final Button getContactButton = findViewById(R.id.getContact);
        final ListView contactList = findViewById(R.id.ContactList);
        getContactButton.setOnClickListener(v -> {
            getContact(contactList);
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude()
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
