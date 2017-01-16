package com.yanko20.blocspot.database;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.yanko20.blocspot.BlocSpotApplication;
import com.yanko20.blocspot.model.PointOfInterest;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by yanko on 1/14/2017.
 */

import android.app.Application;

public class Database {

    public String TAG;
    private Realm realmObj;

    private static Database instance = null;

    private Database(){
        TAG = "realmtag";
        realmObj = Realm.getDefaultInstance();
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void savePoi(PointOfInterest poi){
        realmObj.beginTransaction();
        realmObj.copyToRealm(poi);
        realmObj.commitTransaction();
    }

    public void queryData() {
        RealmResults<PointOfInterest> results = realmObj
                .where(PointOfInterest.class)
                .findAll();
        for(PointOfInterest resultPoi : results){
            Log.d(TAG, resultPoi.toString());
        }
    }

    public RealmResults<PointOfInterest> getAllData(){
        RealmResults<PointOfInterest> results = realmObj
                .where(PointOfInterest.class)
                .findAll();
        return results;
    }

    public Realm getRealmObj() {
        return realmObj;
    }

}
