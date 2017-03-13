package com.yanko20.blocspot.geo;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.PointOfInterest;

import java.util.concurrent.TimeUnit;

import io.realm.RealmResults;

/**
 * Created by ykomizor on 3/8/2017.
 */

public class BlocSpotGeoFence implements ResultCallback{

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private final int GEOFENCE_REQ_CODE = 0;
    private static final float GEOFENCE_RADIUS = 500.0f; // in meters
    private PendingIntent geofencePendingIntent;
    private Circle geoFenceLimits;

    // Create a Geofence
    public Geofence createGoefence(LatLng latlng, float radius){
        Log.d(BlocSpotApp.TAG, "createGoefence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion(latlng.latitude, latlng.longitude, radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }

    // Create a Geofencing request
    public GeofencingRequest createGeofenceRequest(Geofence geofence){
        Log.d(BlocSpotApp.TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();
    }

    /*
        We use a PendingIntent object to call a IntentService that will handle the GeofenceEvent.
        We create the GeofenceTrasitionService.class later.
    */
    public PendingIntent createGeofencePendingIntent(){
        Log.d(BlocSpotApp.TAG, "createGeofenceRequest");
        if(geofencePendingIntent != null){
            return  geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionService.class);
        return PendingIntent.getService(BlocSpotApp.getAppContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request, GoogleApiClient googleApiClient){
        Log.d(BlocSpotApp.TAG, "addGeofence");
        if (BlocSpotApp.checkPermission()){
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
        }
    }

    @Override
    public void onResult(@NonNull Result result) {
        Log.i(BlocSpotApp.TAG, "onResult: " + result);
        if(result.getStatus().isSuccess()){
            drawGeofence();
        } else{
            Log.e(BlocSpotApp.TAG, "onResult: FAILED");
        }
    }

    private void drawGeofence(){
        Log.d(BlocSpotApp.TAG, "drawGeofence()");
        if(geoFenceLimits!=null){
            geoFenceLimits.remove();
        }
        Database db = Database.getInstance();
        RealmResults<PointOfInterest> poiList = db.getPoiList();
        for(PointOfInterest poi : poiList){
            poi.getLat();
            poi.getLng();
            LatLng latLng = new LatLng(poi.getLat(), poi.getLng());
            CircleOptions circleOptions = new CircleOptions()
                    .center(latLng)
                    .strokeColor(Color.argb(50, 70,70,70))
                    .fillColor(Color.argb(100, 150,150,150))
                    .radius(GEOFENCE_RADIUS);
            geoFenceLimits = map.addCircle(circleOptions);
        }

        public void startGeofence(){}

    }
}