package com.yanko20.blocspot.geo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.ui.MainActivity;

/**
 * Created by ykomizor on 3/6/2017.
 */

public class GoogleApiImplementation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    private Context context;
    private LocationListenerImplementation locationListenerImpl;


    public GoogleApiImplementation(Context context){
        this.context = context;
    }

    public LocationListenerImplementation getLocationListenerImpl(){
        return locationListenerImpl;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(BlocSpotApp.TAG, "GoogleApiClient onConnected()");
        locationListenerImpl = new LocationListenerImplementation(context, googleApiClient);
        locationListenerImpl.getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(BlocSpotApp.TAG, "GoogleApiClient onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v(BlocSpotApp.TAG, "GoogleApiClient onConnectionFailed()");
    }

    public GoogleApiClient createGoogleApi(){
        Log.v(BlocSpotApp.TAG, "createGoogleApi()");
        if (googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return googleApiClient;
    }

}
