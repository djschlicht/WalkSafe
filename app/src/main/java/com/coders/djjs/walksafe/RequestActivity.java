package com.coders.djjs.walksafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    public void accept(View view) {

        // set database as accepted

        Double lat = 38.033706;
        Double lon = -78.509856;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("Requests");
        Map<String, Object> userData = new HashMap<>();
        userData.put("hair", "Blonde");
        userData.put("eye", "Green");
        userData.put("height", "5ft,2in");
        userData.put("firstName", "Sarah");
        userData.put("lastInitial", "S");
        userData.put("lat", lat);
        userData.put("lon", lon);
        userData.put("requestor", "ss4nd@virginia.edu");
        userData.put("requested", "jy2xj@virginia.edu");
        userData.put("accepted", true);
        myRef.child("-L8R9lQ9W1sNztGVCMxy").setValue(userData);

        // send message to Student
        lat = 38.035606;
        lon = -78.511734;
        String txt = "WalkSafe (RA): Help is on the way! The closest RA is currently here: http://maps.google.com/?q=" + lat + "," + lon + ". Look for Joshua.";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("(650) 555-1212", null, txt, null, null);
    }
}
