package com.yanko20.blocspot.database;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

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

    public Database(Context context){
        TAG = "realmtag";
        Realm.init(context);
        realmObj = Realm.getDefaultInstance();
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

    public Realm getRealmObj() {
        return realmObj;
    }

}
