package com.yanko20.blocspot;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;

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
        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
        dataSetup();
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
        PointOfInterest fitLifePoi = new PointOfInterest(
                UUID.randomUUID().toString(),
                "FitLife",
                "Fitness Center",
                40.059298,
                -75.190488);
        PointOfInterest cafeTataPoi = new PointOfInterest(
                UUID.randomUUID().toString(),
                "Cafe Tata",
                "Cafe in Mount Airy",
                40.060377, -75.191048);

        DataHelper.savePoi(fitLifePoi);
        DataHelper.savePoi(cafeTataPoi);

        DataHelper.saveCategory(new Category("Fitness Center"));
        DataHelper.saveCategory(new Category("Restaurant"));
        DataHelper.saveCategory(new Category("Cafe"));
    }


}
