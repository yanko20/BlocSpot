package com.yanko20.blocspot;

import android.*;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;
import com.yanko20.blocspot.ui.UIUtils;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yanko on 1/14/2017.
 */

public class BlocSpotApp extends Application {

    public static final String TAG = "App.class";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        BlocSpotApp.context = getApplicationContext();
        setupRealm();
    }

    private void setupRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder().name("blocspot.realm").build();
        //Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
        //dataSetup();
    }

    public static Context getAppContext(){
        return BlocSpotApp.context;
    }

    public static boolean checkPermission() {
        Log.d(BlocSpotApp.TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    private static void dataSetup(){

        //create poi data
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

        // create category data
        Category restaurants = new Category();
        restaurants.setName("Restaurants");
        // todo question - how do I persist colors between app restarts
        restaurants.setColor(UIUtils.generateRandomColor());
        Category gasStations = new Category();
        gasStations.setName("Gas Stations");
        gasStations.setColor(UIUtils.generateRandomColor());

        // save to database
        Database.savePoi(poiMachPichu);
        Database.savePoi(poiKremlin);
        Database.saveCategory(restaurants);
        Database.saveCategory(gasStations);
    }
}
