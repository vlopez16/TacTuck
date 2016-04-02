package com.ioptime.tactuck;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Umair Saeed on 3/30/2016.
 */
public class FindTackFragment extends Fragment {
    MapView mMapView;
    GPSTracker gpsTracker;
    ScrollView sv;
    Button pickMeBtn, cancelBtn;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_tactuk, container,
                false);
        gpsTracker = new GPSTracker(getActivity());
        sv = (ScrollView) rootView.findViewById(R.id.sv);
        pickMeBtn = (Button) rootView.findViewById(R.id.pickMeBtn);
        cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        mMapView = (MapView) rootView.findViewById(R.id.mapFrag);


        mMapView.onCreate(savedInstanceState);


        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            googleMap = mMapView.getMap();
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                LatLng position = new LatLng(gpsTracker.latitude, gpsTracker.longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(position);
                googleMap.animateCamera(CameraUpdateFactory
                        .newLatLngZoom(position, 15f));
                Marker marker = googleMap
                        .addMarker(markerOptions);
                marker.setPosition(position);
            } else {
                gpsTracker.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sv.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        pickMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMeBtn.setVisibility(View.GONE);
                cancelBtn.setVisibility(View.VISIBLE);
            }
        });
        return rootView;
    }
}
