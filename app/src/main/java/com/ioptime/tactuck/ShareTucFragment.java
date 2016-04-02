package com.ioptime.tactuck;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

/**
 * Created by Umair Saeed on 3/29/2016.
 */
public class ShareTucFragment extends Fragment implements LocationListener {
    MapView mMapView;
    TextView timeTV, dateTV;
    int year, month, day;
    GPSTracker gpsTracker;
    ScrollView sv;
    private GoogleMap googleMap;
    private int hour;
    private int minutes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.share_tactuk, container,
                false);
        gpsTracker = new GPSTracker(getActivity());
        timeTV = (TextView) rootView.findViewById(R.id.timeTV);
        dateTV = (TextView) rootView.findViewById(R.id.dateTV);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        sv = (ScrollView) rootView.findViewById(R.id.sv);

        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minutes = c.get(Calendar.MINUTE);

        // set current time into output textview
        updateTime(hour, minutes);


        mMapView = (MapView) rootView.findViewById(R.id.mapFrag);
        dateTV.setText(month + "/" + day + "/" + year);
        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(), new mDateSetListener(dateTV), year,
                        month, day);
                dialog.show();
            }
        });
        timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new timePickerListener(), hour, minutes, false);
                dialog.show();
            }
        });
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

        return rootView;
    }

    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        timeTV.setText(aTime);
    }

    @Override
    public void onLocationChanged(Location location) {
        googleMap.clear();
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            double latti = location.getLatitude();
            // To get longitude value from location object
            double longi = location.getLongitude();
            Log.d("latandlng", latti + "---" + longi);
            // To hold lattitude and longitude values
            LatLng position = new LatLng(latti, longi);

            // Creating object to pass our current location to the map
            MarkerOptions markerOptions = new MarkerOptions();
            // To store current location in the markeroptions object
            markerOptions.position(position);

            // Zooming to our current location with zoom level 17.0f
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,
                    17f));
            Marker marker = googleMap.addMarker(markerOptions);
            // adding markeroptions class object to the map to show our current
            // location in the map with help of default marker
            marker.setPosition(new LatLng(latti, longi));
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class LoadMapBT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }
    }

    public class mDateSetListener implements DatePickerDialog.OnDateSetListener {
        TextView txt;

        public mDateSetListener(TextView txt) {
            this.txt = txt;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            // Month is 0 based so add 1
            mMonth = mMonth + 1;
            String sMonth = mMonth + "";
            if (mMonth <= 9) {
                sMonth = "0" + sMonth;
            }
            txt.setText(new StringBuilder().append(sMonth).append("/")
                    .append(mDay).append("/").append(mYear));

        }
    }

    public class timePickerListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minute = minutes;

            updateTime(hour, minute);
        }
    }
}
