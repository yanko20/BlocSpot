package com.yanko20.blocspot.geo;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;
import com.yanko20.blocspot.BlocSpotApp;

/**
 * Created by ykomizor on 3/8/2017.
 */

public class BlocSpotGeoFence {

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 500.0f; // in meters
    private PendingIntent geofencePendingIntent;
    private final int GEOFENCE_REQ_ID = 0;

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
    }



}
