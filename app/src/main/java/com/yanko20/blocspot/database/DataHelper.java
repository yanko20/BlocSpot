package com.yanko20.blocspot.database;

import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yanko on 1/14/2017.
 */

public class DataHelper {

    private static Realm realm = Realm.getDefaultInstance();

    public static void savePoi(final PointOfInterest poi) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(poi);
            }
        });
    }

    public static RealmResults<PointOfInterest> getAllPois() {
        return realm.where(PointOfInterest.class)
                .findAll();
    }

    public static void saveCategory(final Category category) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(category);
            }

        });
    }

    public static RealmResults<Category> getAllCategories() {
        return realm.where(Category.class)
                .findAll();
    }

    public static PointOfInterest getPoi(String id) {
        return realm.where(PointOfInterest.class).equalTo("id", id).findFirst();
    }

    public static void addCategoryToPoi(final PointOfInterest poi, final String categoryName) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Category category = realm.where(Category.class)
                        .equalTo("name", categoryName)
                        .findFirst();
                poi.getCategories().add(category);
            }
        });
    }

    public static void removeCategoryFromPoi(final PointOfInterest poi, final String categoryName) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Category category = poi.getCategories()
                        .where()
                        .equalTo("name", categoryName)
                        .findFirst();
                poi.getCategories().remove(category);
            }
        });
    }

    public static boolean isCategoryAssignedToPoi(PointOfInterest poi, String categoryName) {
        Category category = poi.getCategories()
                .where()
                .equalTo("name", categoryName).findFirst();
        return category == null ? false : true;
    }

    public static void setCategoryFilter(final Category category, final boolean isFilter){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                category.setFilter(isFilter);
            }
        });
    }
}
