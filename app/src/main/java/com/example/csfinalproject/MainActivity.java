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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    //Added
    protected EditText message;
    protected LocationManager locationManager;
    protected Double latitude, longitude;
    protected boolean includeLocation;
    protected String contactName, contactNumber;

    //Ends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button sendB = findViewById(R.id.send);

        final TextView locationView = findViewById(R.id.locationView);

        message = findViewById(R.id.txtMessage);


        // creates listener for current location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            System.out.println("Location permission denied");
        }

        // sends message
        sendB.setOnClickListener(v -> {
            sendMessage();
        });

        // location box + display
        final CheckBox locationCheckBox = findViewById(R.id.includeLocation);
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

        // make contact list
        final Button getContactButton = findViewById(R.id.getContact);
        final ListView contactList = findViewById(R.id.ContactList);
        final TextView displayName = findViewById(R.id.contact);
        getContactButton.setOnClickListener(v -> {
            getContact(contactList);
        });

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((Cursor) (parent.getItemAtPosition(position)));

                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                displayName.setText("Contact: \n" + contactName);
            }
        });
    }

    public void sendMessage() {
        String number = contactNumber;
        String mess = message.getText().toString().trim();
        if (number == null || number.equals("")) {
            Toast.makeText(this,"field can't be empty",Toast.LENGTH_LONG).show();
        } else {
            if (TextUtils.isDigitsOnly(number)) {

                if (includeLocation) {
                    mess = "Current Location: \n" +
                            "Latitude: " + latitude +
                            "Longitude: " + longitude + "\n" +
                            "Message: " + mess;
                }

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, mess, null, null);
                Toast.makeText(this, "message sent successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"please enter integer only",Toast.LENGTH_LONG).show();
            }
        }
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
        contactList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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
