package com.yanko20.blocspot.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.geo.BlocSpotGeofence;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoiMapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    MapView mapView;
    private GoogleApiClient googleApiClient;
    private static Marker locationMarker;
    private Location location;
    private LocationRequest locationRequest;
    public static final int REQ_PERMISSION = 1;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;
    private static boolean isCenteredToCurrentLocation = false;

    private static GoogleMap map;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient = createGoogleApi();
    }

    public GoogleApiClient createGoogleApi() {
        Log.v(BlocSpotApp.TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        return googleApiClient;
    }

    public PoiMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(BlocSpotApp.TAG, "onMapReady()");
        PoiMapFragment.map = map;
        // TODO: 4/17/2017 add category filter to map 
        RealmResults<PointOfInterest> poiList = DataHelper.getAllPois();
        for (PointOfInterest poi : poiList) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(poi.getLat(), poi.getLng()))
                    .title(poi.getTitle()));
        }
        mapAndLocationReady(map, location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(BlocSpotApp.TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    getLastKnownLocation();
                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    public void permissionsDenied() {
        Log.w(BlocSpotApp.TAG, "permissionsDenied()");
    }

    public void mapAndLocationReady(GoogleMap map, Location location) {
        if (map != null && location != null) {
            if (BlocSpotApp.checkPermission()) {
                map.setMyLocationEnabled(true);
                centerToCurrentLocation();
            }
        }
    }

    private void centerToCurrentLocation() {
        if (!PoiMapFragment.isCenteredToCurrentLocation) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(13)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            PoiMapFragment.isCenteredToCurrentLocation = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        mapAndLocationReady(map, location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(BlocSpotApp.TAG, "GoogleApiClient onConnected()");
        getLastKnownLocation();
        BlocSpotGeofence blocSpotGeofence = new BlocSpotGeofence(googleApiClient, map);
        blocSpotGeofence.clearGeofence();
        blocSpotGeofence.startGeofence();
    }

    public void getLastKnownLocation() {
        Log.d(BlocSpotApp.TAG, "getLastKnownLocation()");
        if (BlocSpotApp.checkPermission()) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                Log.i(BlocSpotApp.TAG, "LasKnown location. " +
                        "Long: " + location.getLongitude() +
                        " | Lat: " + location.getLatitude());
            } else {
                Log.w(BlocSpotApp.TAG, "No location retrieved yet");
            }
            startLocationUpdates();
        } else askPermission();
    }

    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                getActivity(),
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

        if (BlocSpotApp.checkPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(BlocSpotApp.TAG, "GoogleApiClient onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v(BlocSpotApp.TAG, "GoogleApiClient onConnectionFailed()");
    }
}
