/**
 * File: LocalEventActivity.java
 * Purpose: Shows a map with a mark in the local of the event
 */

package com.mathheals.euvou.controller.event_registration;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mathheals.euvou.R;

public class LocalEventActivity extends FragmentActivity implements GoogleMap.OnMapClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment and loads
     * the XML layouts used in the activity
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert savedInstanceState != null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_event);
        setUpMapIfNeeded();
        mMap.setOnMapClickListener(this);
    }

    /**
     * Called when the activity will start interacting with the user
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/updateUser the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }else{
                // Nothing to do
            }
        }else{
            // Nothing to do
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    }

    /**
     * Save the latitude and the longitude in a bundle to be used in the map
     * @param latLng - A pair with latitude and longitude coordinates
     */
    @Override
    public void onMapClick(LatLng latLng) {
        assert latLng != null;

        Intent resultado = new Intent();
        resultado.putExtra("longitude", " "+latLng.longitude);
        resultado.putExtra("latitude", " "+latLng.latitude);
        setResult(Activity.RESULT_OK, resultado);
        finish();
    }
}
