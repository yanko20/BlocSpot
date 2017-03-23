package com.yanko20.blocspot.database;

import android.util.Log;

import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by yanko on 1/14/2017.
 */

public class Database {

    private static RealmResults<PointOfInterest> realmResultsPoiList = null;

    public static void savePoi(final PointOfInterest poi){
        Realm realm =  null;
        try{
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(poi);
                }

            });
        }finally{
            if(realm == null){
                realm.close();
            }
        }
    }

    public static RealmResults<PointOfInterest> getAllPois(){
        Realm realm =  null;
        try{
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // todo question: how do I return value without using static realmResultsPoiList
                    realmResultsPoiList =
                            realm.where(PointOfInterest.class)
                            .findAll();

                }

            });
        }finally{
            if(realm == null){
                realm.close();
            }
        }
        return realmResultsPoiList;
    }

//    public void savePoi_old(PointOfInterest poi){
//        realmObj.beginTransaction();
//        realmObj.copyToRealm(poi);
//        realmObj.commitTransaction();
//    }
//
//    public RealmResults<PointOfInterest> getPoiList_old(){
//        RealmResults<PointOfInterest> results = realmObj
//                .where(PointOfInterest.class)
//                .findAll();
//        return results;
//    }

}
