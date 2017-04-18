package com.yanko20.blocspot.geo;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.model.PointOfInterest;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ykomizor on 3/8/2017.
 */

public class BlocSpotGeofence implements ResultCallback {

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private final int GEOFENCE_REQ_CODE = 0;
    private static final float GEOFENCE_RADIUS = 200.0f; // in meters
    private PendingIntent geofencePendingIntent;
    private Circle geoFenceLimits;
    private GoogleApiClient googleApiClient;
    private GoogleMap map;

    public BlocSpotGeofence(GoogleApiClient googleApiClient, GoogleMap map) {
        this.googleApiClient = googleApiClient;
        this.map = map;
    }

    public Geofence createGeofence(LatLng latlng, String poiId) {
        Log.d(BlocSpotApp.TAG, "createGeofence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion(latlng.latitude, latlng.longitude, GEOFENCE_RADIUS)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    public GeofencingRequest createGeofenceRequest(Geofence geofence) {
        Log.d(BlocSpotApp.TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();
    }


    public PendingIntent createGeofencePendingIntent() {
        // We use a PendingIntent object to call a IntentService that will handle the GeofenceEvent.
        // We create the GeofenceTrasitionService.class later.
        Log.d(BlocSpotApp.TAG, "createGeofencePendingIntent");
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(BlocSpotApp.getAppContext(), GeofenceTransitionService.class);
        geofencePendingIntent = PendingIntent.getService(BlocSpotApp.getAppContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }


    private void addGeofence(GeofencingRequest request, GoogleApiClient googleApiClient) {
        // Add the created GeofenceRequest to the device's monitoring list
        Log.d(BlocSpotApp.TAG, "addGeofence");
        if (BlocSpotApp.checkPermission()) {
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
        if (result.getStatus().isSuccess()) {
            drawGeofence();
        } else {
            Log.e(BlocSpotApp.TAG, "onResult: FAILED");
        }
    }

    private void drawGeofence() {
        Log.d(BlocSpotApp.TAG, "drawGeofence()");
        if (geoFenceLimits != null) {
            geoFenceLimits.remove();
        }
        RealmResults<PointOfInterest> poiList = DataHelper.getAllPois();
        for (PointOfInterest poi : poiList) {
            LatLng latLng = new LatLng(poi.getLat(), poi.getLng());
            CircleOptions circleOptions = new CircleOptions()
                    .center(latLng)
                    .strokeWidth(5)
                    .strokeColor(Color.argb(50, 70, 70, 70))
                    .fillColor(Color.argb(100, 150, 150, 150))
                    .radius(GEOFENCE_RADIUS);
            geoFenceLimits = map.addCircle(circleOptions);
        }
    }

    public void startGeofence() {
        Log.i(BlocSpotApp.TAG, "startGeofence");
        RealmResults<PointOfInterest> poiList = DataHelper.getAllPois();
        for (PointOfInterest poi : poiList) {
            LatLng latLng = new LatLng(poi.getLat(), poi.getLng());
            Geofence geofence = createGeofence(latLng, poi.getId());
            GeofencingRequest geofencingRequest = createGeofenceRequest(geofence);
            addGeofence(geofencingRequest, googleApiClient);
        }
    }

    public void clearGeofence(){
        Log.i(BlocSpotApp.TAG, "clearGeofence");
        RealmResults<PointOfInterest> poiList = DataHelper.getAllPois();
        ArrayList<String> poiIdList = new ArrayList<>();
        for (PointOfInterest poi : poiList) {
            poiIdList.add(poi.getId());
        }
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, poiIdList);
    }
}