package com.yanko20.blocspot.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.PoiFragmentPageAdapter;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.PointOfInterest;

import java.util.UUID;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ui
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PoiFragmentPageAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // data
        PointOfInterest poiMachPichu = new PointOfInterest();
        poiMachPichu.setId(UUID.randomUUID().toString());
        poiMachPichu.setTitle("Machu Picchu");
        poiMachPichu.setDescription("This is not Kremlin, this is Machu Picchu!");
        poiMachPichu.setLat(-13.163141);
        poiMachPichu.setLng(-72.544963);

        PointOfInterest poiKremlin = new PointOfInterest();
        poiKremlin.setId(UUID.randomUUID().toString());
        poiKremlin.setTitle("Kremlin");
        poiKremlin.setDescription("This is Kremlin!");
        poiKremlin.setLat(55.752023);
        poiKremlin.setLng(37.617499);

        // database
        db = new Database(getApplicationContext());
        db.savePoi(poiMachPichu);
        db.savePoi(poiKremlin);
        db.queryData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.getRealmObj().close();
    }
}
