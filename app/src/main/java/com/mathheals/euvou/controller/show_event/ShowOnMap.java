package com.mathheals.euvou.controller.show_event;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mathheals.euvou.R;


public class ShowOnMap extends FragmentActivity {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Double latitude;
    private Double longitude;
    private String filter;

    public String getFilter() {
        return filter;
    }

    private void setFilter(String filter) {
        this.filter = filter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle b=this.getIntent().getExtras();
        String[] array = b.getStringArray("LatitudeAndLongitude");
        latitude = Double.parseDouble(array[0]);
        longitude = Double.parseDouble(array[1]);
        //Toast.makeText(getBaseContext(),array[0]+"\n"+array[1],Toast.LENGTH_LONG).show();
        // setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 9));
        setFilter(getIntent().getStringExtra("query"));
        addMarkerPlace(latitude,longitude);

    }

    private void addMarkerPlace(Double latitude, Double longitude) {
        mMap.addMarker(
                new MarkerOptions()
                        .title("Nome do Evento")
                        .snippet("Endereço")
                        .position(new LatLng(latitude, longitude))
        );

    }
}