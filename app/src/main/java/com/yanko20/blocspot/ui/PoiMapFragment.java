package com.yanko20.blocspot.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoiMapFragment extends Fragment implements OnMapReadyCallback{

    MapView mapView;

    public PoiMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView)view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Database db = Database.getInstance();
        RealmResults<PointOfInterest> poiList = db.getPoiList();
        for(PointOfInterest poi : poiList){
            map.addMarker(new MarkerOptions()
                .position(new LatLng(poi.getLat(), poi.getLng()))
                .title(poi.getTitle()));
        }
    }
}
