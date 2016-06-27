/*
 *File: SearchPlaceMaps.java
 * Purpose: search for the asked place in the Map
 */

package com.mathheals.euvou.controller.search_place;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.analytics.Logger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.show_place.ShowPlaceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import dao.PlaceDAO;
import exception.PlaceException;
import model.Place;

public class SearchPlaceMaps extends FragmentActivity implements GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ArrayList<Place> places;
    private String filter;
    private Place clickedPlace;
    private int selectedPlaceId;
    private JSONObject foundPlaces;
    private static final String TAG = "setUpMapIfNeeded";

    /**
     * Gets the filter to the search
     * @return String - an filter to make the search easier
     */
    private String getFilter(){
        return filter;
    }

    /**
     * Sets the filter to the search
     * @param filter
     */
    private void setFilter(String filter){
        this.filter = filter;
    }

    /**
     *  Calls the parent onCreate to setup the activity view that contains this fragment
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setOnMarkerClickListener(this);
    }

    /**
     * Calls the method to resume the app and sets up the map if it isn´t instantiated yet.
     */
    @Override
    protected void onResume(){
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map in case it isn't instantiated yet
     */
    private void setUpMapIfNeeded(){
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null){
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
        }else{
            //nothing to do
        }
        if (mMap != null){
                setUpMap();
            }else{
            Log.d(TAG, "setUpMapIfNeeded: Map can't be used.");
        }
    }

    /**
     * Searches for Places as decided by the user
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    private JSONObject searchPlaces(){
        return new PlaceDAO(this).searchPlaceByPartName(getFilter());
    }

    /**
     * Instantiates up the Map up
     */
    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-15.7941454, -47.8825479), 9));
        setFilter(getIntent().getStringExtra("query"));
        foundPlaces = searchPlaces();

        try {
            convertJsonToPlace(foundPlaces);
            addMarkerPlace();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (PlaceException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }

    }

    /**
     * Converts the result from JSONObject to Place data
     * @param result - The result from the search
     * @throws JSONException
     * @throws PlaceException
     * @throws ParseException
     */
    private void convertJsonToPlace(JSONObject result) throws JSONException, PlaceException,
            ParseException{

        places = new ArrayList<>();
        if(result == null) {
            Toast.makeText(this, getResources().getString(R.string.no_results), Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < result.length(); i++){
            Place aux;
            aux = new Place(
                    result.getJSONObject("" + i).getString("namePlace"),
                    result.getJSONObject("" + i).getString("evaluate"),
                    result.getJSONObject("" + i).getString("longitude"),
                    result.getJSONObject("" + i).getString("latitude"),
                    result.getJSONObject("" + i).getString("operation"),
                    result.getJSONObject("" + i).getString("description"),
                    result.getJSONObject("" + i).getString("address"),
                    result.getJSONObject("" + i).getString("phonePlace")
                    );
            assert aux != null;

            places.add(aux);
        }
    }

    /**
     * Adds a marker to be used on a desired place
     */
    private void addMarkerPlace(){
        if(places != null) {
            for (int i = 0; i < places.size(); ++i){
                mMap.addMarker(
                        new MarkerOptions()
                                .title(places.get(i).getPlaceName())
                                .snippet(places.get(i).getPlaceAddress())
                                .position(new LatLng(places.get(i).getPlacetLatitude(),
                                        places.get(i).getPlaceLongitude()))
                );
            }
        }
    }

    /**
     * Shows basic information from the event when the user clicks on the marker
     * @param marker - The marker on a place
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker){
        String placeMarker = marker.getId().substring(1);
        int id = Integer.parseInt(placeMarker);
        select(id);
        startShowInfoActivity();
        return false;
    }

    /**
     * Select an place by it's id
     * @param id
     */
    private void select(int id){
        clickedPlace = places.get(id);
        try {
            selectedPlaceId = foundPlaces.getJSONObject(Integer.toString(id)).getInt("idPlace");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Starts the activity that shows the info from a place
     */
    private void startShowInfoActivity(){
        Intent intent = new Intent(this, ShowPlaceInfo.class);
        intent.putExtras(getPlaceInfoAsBundle());
        startActivity(intent);
    }

    /**
     * Receives and organize the information of a place in a Bundle
     * @return Bunlde - The information of a place
     */
    private Bundle getPlaceInfoAsBundle(){
        Bundle placeInfo = new Bundle();
        placeInfo.putString("name", clickedPlace.getPlaceName());
        placeInfo.putString("phone", clickedPlace.getPlacePhone());
        placeInfo.putString("address", clickedPlace.getPlaceAddress());
        placeInfo.putString("description", clickedPlace.getPlaceDescription());
        placeInfo.putDouble("latitude", clickedPlace.getPlacetLatitude());
        placeInfo.putDouble("longitude", clickedPlace.getPlaceLongitude());
        placeInfo.putString("operation", clickedPlace.getOperation());
        placeInfo.putInt("idPlace", selectedPlaceId);

        return placeInfo;
    }
}
