package com.coders.djjs.walksafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText emailEditText;
    MaterialEditText passEditText;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Map<String, String> userData = new HashMap<>();
        userData.put("password", "pass");
        userData.put("position", "RA");
        userData.put("firstName", "Joshua");
        myRef.child("Users").child("jy2xj@virginia,edu").setValue(userData);
        Map<String, String> userData2 = new HashMap<>();
        userData2.put("password", "pass");
        userData2.put("position", "Student");
        userData2.put("firstName", "Sarah");
        myRef.child("Users").child("ss4nd@virginia,edu").setValue(userData2);

        emailEditText = (MaterialEditText) findViewById(R.id.email);
        passEditText = (MaterialEditText) findViewById(R.id.password);
    }

    public void login(View view) {
        // Read from the database

        // get the email and password as Strings
        String email = emailEditText.getText().toString().replace(".", ",");
        pass = passEditText.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(email);

        // access Firebase to confirm the user exists
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("Firebase", "Value is: " + dataSnapshot);

                // get information about the user
                Map<String, Object> userMap = (Map<String, Object>) dataSnapshot.getValue();

                String actualPass = (String) userMap.get("password");
                // check to make sure user put in correct password
                if (pass.equals(actualPass)) {
                    Class dest;
                    if (((String) userMap.get("position")).equals("RA")) {
                        dest = RAActivity.class;
                    } else {
                        dest = StudentActivity.class;
                    }
                    Intent intent = new Intent(LoginActivity.this, dest);
                    startActivity(intent);
                } else {
                    // Username or password false, display and an error
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);

                    dlgAlert.setMessage("Wrong password or username");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
                // Username or password false, display and an error
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);

                dlgAlert.setMessage("Wrong password or username");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });
    }
}
