package com.coders.djjs.walksafe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

public class StudentActivity extends AppCompatActivity {

    private TextView textHome;
    private TextView textStatus;
    private TextView textMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textHome = (TextView) findViewById(R.id.text_favorites);
        textStatus = (TextView) findViewById(R.id.text_schedules);
        textMap = (TextView) findViewById(R.id.text_music);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                textHome.setVisibility(View.VISIBLE);
                                textStatus.setVisibility(View.GONE);
                                textMap.setVisibility(View.GONE);
                                break;
                            case R.id.action_schedules:
                                textHome.setVisibility(View.GONE);
                                textStatus.setVisibility(View.VISIBLE);
                                textMap.setVisibility(View.GONE);
                                break;
                            case R.id.action_music:
                                textHome.setVisibility(View.GONE);
                                textStatus.setVisibility(View.GONE);
                                textMap.setVisibility(View.VISIBLE);
                                break;
                        }
                        return false;
                    }
                });
    }

}
