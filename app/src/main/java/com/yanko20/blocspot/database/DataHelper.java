package com.yanko20.blocspot.database;

import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yanko on 1/14/2017.
 */

public class DataHelper {

    public static void savePoi(Realm realm, final PointOfInterest poi) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(poi);
            }
        });
    }

    public static RealmResults<PointOfInterest> getAllPois(Realm realm) {
        return realm.where(PointOfInterest.class)
                .findAll();
    }

    public static void saveCategory(Realm realm, final Category category) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(category);
            }

        });
    }

    public static RealmResults<Category> getAllCategories(Realm realm) {
        return realm.where(Category.class)
                .findAll();
    }

}
