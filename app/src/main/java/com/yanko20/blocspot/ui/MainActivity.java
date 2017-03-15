package com.yanko20.blocspot.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.PoiFragmentPageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(BlocSpotApp.TAG, "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PoiFragmentPageAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static Intent makeNotificationIntent(Context geofenceService, String msg){
        Log.d(BlocSpotApp.TAG, "makeNotificationIntent mmsg: " + msg);
        return new Intent(geofenceService, MainActivity.class);
    }
}
