package com.yanko20.blocspot.ui;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.geo.GoogleApiImplementation;
import com.yanko20.blocspot.geo.LocationListenerImplementation;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoiMapFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    private GoogleApiClient googleApiClient;
    private GoogleApiImplementation googleApiImplementation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiImplementation = new GoogleApiImplementation(getActivity());
        googleApiClient = googleApiImplementation.createGoogleApi();
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
    public void onMapReady(GoogleMap map) {
        Log.d(BlocSpotApp.TAG, "onMapReady()");
        Database db = Database.getInstance();
        RealmResults<PointOfInterest> poiList = db.getPoiList();
        for (PointOfInterest poi : poiList) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(poi.getLat(), poi.getLng()))
                    .title(poi.getTitle()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(BlocSpotApp.TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LocationListenerImplementation.REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    googleApiImplementation.getLocationListenerImpl().getLastKnownLocation();
                } else {
                    // Permission denied
                    googleApiImplementation.getLocationListenerImpl().permissionsDenied();
                }
                break;
            }
        }
    }



}
