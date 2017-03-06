package com.yanko20.blocspot;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yanko on 1/14/2017.
 */

public class BlocSpotApp extends Application {

    public static final String TAG = "App.class";

    @Override
    public void onCreate() {
        super.onCreate();
        setupRealm();
    }

    private void setupRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder().name("blocspot.realm").build();
        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
