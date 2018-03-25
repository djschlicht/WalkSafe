package com.coders.djjs.walksafe;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RAActivity extends AppCompatActivity implements LocationListener {
    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ra);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mBottomNav = findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if(savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        }
        else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);
    }


    // Method taken directly from the other repo
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    // Method taken directly from the other repo
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if(mSelectedItem != homeItem.getItemId()) {
            selectFragment(homeItem);
        }
        else {
            super.onBackPressed();
        }
    }

    // Method taken directly from the other repo
    private void selectFragment(MenuItem item) {
        android.support.v4.app.Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_home:
                //frag = new StudentHomeActivity();
                frag = MenuFragment.newInstance(getString(R.string.text_home), getColorFromRes(R.color.color_home));
                break;
            case R.id.menu_status:
                //frag = new StudentHomeActivity();
                //frag = StudentStatusFragment.newInstance(getResources().getString(R.string.text_status), getColorFromRes(R.color.color_notifications));
                frag = MenuFragment.newInstance(getString(R.string.text_status), getColorFromRes(R.color.color_notifications));
                break;
            case R.id.menu_map:
                frag = MenuFragment.newInstance(getString(R.string.text_map), getColorFromRes(R.color.color_notifications));
                //Ratchet way to do this
                //Intent intent = new Intent(this, MapsActivity.class);
                //startActivity(intent);
                /*
                System.out.println("Entered into map case");
                MapsActivity map = new MapsActivity();
                //frag = (SupportMapFragment)map.getChildFragmentManager().findFragmentById(R.id.map);
                SupportMapFragment mapFragment = (SupportMapFragment) map.getSupportFragmentManager().findFragmentById(R.id.map);
                GoogleMap mMap = mapFragment.getMapASync(map);
                MenuFragment.newInstance(getString(R.string.text_status), getColorFromRes(R.color.color_notifications));
                break;
                */
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());
        System.out.println(frag);
        if (frag != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }

    }

    // Method copied exactly
    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    // Method copied exactly
    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }


    @Override
    public void onLocationChanged(Location location) {
        // Access the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        HashMap<String, Double> locationData = new HashMap<>();
        locationData.put("latitude", location.getLatitude());
        locationData.put("longitude", location.getLongitude());
        // Get the username from the LoginActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String email = extras.getString("username");
            if (email != null) myRef.child("Users").child(email).setValue(locationData);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
