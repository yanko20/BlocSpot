package com.yanko20.blocspot.geo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yanko20.blocspot.BlocSpotApp;

import static android.content.ContentValues.TAG;

/**
 * Created by ykomizor on 3/6/2017.
 */

public class LocationListenerImplementation implements LocationListener {

    private Context context;
    private Location lastLocation;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    public static final int REQ_PERMISSION = 1;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;

    public LocationListenerImplementation(Context context, GoogleApiClient googleApiClient) {
        this.context = context;
        this.googleApiClient = googleApiClient;
    }

    public void getLastKnownLocation() {
        Log.d(BlocSpotApp.TAG, "getLastKnownLocation()");
        if (checkPermission()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                Log.i(BlocSpotApp.TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
            } else {
                Log.w(BlocSpotApp.TAG, "No location retrieved yet");
            }
            startLocationUpdates();
        } else askPermission();
    }

    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                (Activity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    private void startLocationUpdates() {
        Log.i(BlocSpotApp.TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private boolean checkPermission() {
        Log.d(BlocSpotApp.TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(BlocSpotApp.TAG, "onLocationChanged [" + location + "]");
        lastLocation = location;
    }

    public void permissionsDenied() {
        Log.w(BlocSpotApp.TAG, "permissionsDenied()");
    }

}
