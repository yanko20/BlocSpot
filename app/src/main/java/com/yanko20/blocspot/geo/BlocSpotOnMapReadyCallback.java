package com.yanko20.blocspot.geo;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.PointOfInterest;
import com.yanko20.blocspot.ui.PoiMapFragment;

import io.realm.RealmResults;

/**
 * Created by ykomizor on 3/7/2017.
 */

public class BlocSpotOnMapReadyCallback implements OnMapReadyCallback {

    private GoogleMap map;
    private Location location;

    public BlocSpotOnMapReadyCallback(GoogleMap map, Location location){
        this.map = map;
        this.location = location;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(BlocSpotApp.TAG, "onMapReady()");
        this.map = map;
        Database db = Database.getInstance();
        RealmResults<PointOfInterest> poiList = db.getPoiList();
        for (PointOfInterest poi : poiList) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(poi.getLat(), poi.getLng()))
                    .title(poi.getTitle()));
        }
        PoiMapFragment.mapAndLocationReady(map, location);
    }
}
