package com.yanko20.blocspot;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yanko on 1/14/2017.
 */

public class BlocSpotApplication extends Application {

    private static BlocSpotApplication instance;

    public static BlocSpotApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder().name("blocspot.realm").build();
        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
