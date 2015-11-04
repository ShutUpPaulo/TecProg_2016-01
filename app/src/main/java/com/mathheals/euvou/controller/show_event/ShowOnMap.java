package com.mathheals.euvou.controller.show_event;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mathheals.euvou.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import dao.PlaceDAO;
import exception.PlaceException;
import model.Place;

public class ShowOnMap extends FragmentActivity {
    protected GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Double latitude;
    private Double longitude;
    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toast.makeText(getBaseContext(),"Map",Toast.LENGTH_LONG).show();
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
                new LatLng(-15.7941454, -47.8825479), 9));
        setFilter(getIntent().getStringExtra("query"));
        addMarkerPlace(latitude,longitude);

    }

    private void addMarkerPlace(Double latitude, Double longitude) {
        mMap.addMarker(
                new MarkerOptions()
                        //.title(place.getName())
                        //.snippet(place.getAddress())
                        .position(new LatLng(latitude, longitude))
        );

    }
}