package com.coders.djjs.walksafe;

import android.*;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class StudentActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
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

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.SEND_SMS},
                1);

    }

    //Method taken directly from the other repo
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    //Method taken directly from the other repo
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if(mSelectedItem != homeItem.getItemId()) {
            selectFragment(homeItem);
        }
        else {
            super.onBackPressed();
        }
    }

    //Method taken directly from the other repo
    private void selectFragment(MenuItem item) {
        android.support.v4.app.Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_home:
                //frag = new StudentHomeActivity();
                frag = StudentHomeFragment.newInstance(getString(R.string.text_home), getIntent().getStringExtra("username"));
                break;
            case R.id.menu_status:
                //frag = new StudentHomeActivity();
                frag = StudentStatusFragment.newInstance(getResources().getString(R.string.text_status), getColorFromRes(R.color.color_notifications));
                //MenuFragment.newInstance(getString(R.string.text_status), getColorFromRes(R.color.color_notifications));
                break;
            case R.id.menu_map:
                //Ratchet way to do this
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                /*
                System.out.println("Entered into map case");
                MapsActivity map = new MapsActivity();
                //frag = (SupportMapFragment)map.getChildFragmentManager().findFragmentById(R.id.map);
                SupportMapFragment mapFragment = (SupportMapFragment) map.getSupportFragmentManager().findFragmentById(R.id.map);
                GoogleMap mMap = mapFragment.getMapASync(map);
                fMenuFragment.newInstance(getString(R.string.text_status), getColorFromRes(R.color.color_notifications));
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

        if (frag != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }

    }

    //Method copied exactly
    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    //Method copied exactly
    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }

}
