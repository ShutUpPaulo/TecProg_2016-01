/**
 * File: ShowPlaceInfo.java
 * Purpose: Show information of a given place
 */


package com.mathheals.euvou.controller.show_place;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;

import android.view.View;
import android.widget.RatingBar;
import android.widget.RatingBar.*;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.utility.LoginUtility;

import dao.EvaluatePlaceDAO;
import model.Evaluation;

/**
 * The type Show place info.
 */
public class ShowPlaceInfo extends FragmentActivity{

    private static final String STRING_EMPTY = "";
    private static final double DOUBLE_ZERO = 0.0;
    private static final float FLOAT_ZERO = 0.0f;
    private static final int INT_ZERO = 0;
    private static final Integer LOGGED_OUT = -1;

    private GoogleMap placeMap = null;

    private String name = STRING_EMPTY;
    private String phone = STRING_EMPTY;
    private String operation = STRING_EMPTY;
    private String description = STRING_EMPTY;
    private double longitude = DOUBLE_ZERO;
    private double latitude = DOUBLE_ZERO;
    private String address = STRING_EMPTY;
    private float grade = FLOAT_ZERO;
    private int idPlace = INT_ZERO;

    private Button showMapButton = null;
    private Button hideMapButton = null;
    private SupportMapFragment placeMapFragment = null;

    private RatingBar ratingBar = null;
    private Integer userId = LOGGED_OUT;
    private boolean isUserLoggedIn = false;

    private Evaluation ratingEvaluation = null;

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        assert savedInstanceState != null;
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_show_place_info);

        Button showMapButton = (Button) this.findViewById(R.id.button_show_map);
        this.setShowMapButton(showMapButton);

        Button hideMapButton = (Button) this.findViewById(R.id.button_hide_map);
        this.setHideMapButton(hideMapButton);

        this.setUserId(new LoginUtility(this).getUserId());
        this.setIsUserLoggedIn(!userId.equals(LOGGED_OUT));

        this.setPlaceInformation();
        this.setAllTextViews();
        this.setUpMapIfNeeded();
        this.placeMapFragment.getView().setVisibility(View.INVISIBLE);

        this.setRatingMessage(isUserLoggedIn);
        this.setRatingBarIfNeeded();
    }

    /**
     * Sets rating bar if user is logged in
     */
    private void setRatingBarIfNeeded(){
        if(this.isUserLoggedIn){
            this.setRatingBar();
        }else{
            //nothing to do
        }
    }

    /**
     * Sets rating bar on place view
     */
    private void setRatingBar(){
        this.ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        assert this.ratingBar != null;

        this.ratingBar.setVisibility(View.VISIBLE);

        this.ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rateValue, boolean fromUser){
                setRatingEvaluation(idPlace, userId, rateValue);
                EvaluatePlaceDAO evaluatePlaceDAO = new EvaluatePlaceDAO();
                evaluatePlaceDAO.evaluatePlace(ratingEvaluation);
            }
        });

        this.setRatingBarStyle();
    }

    /**
     * Sets rating bar style
     */
    private void setRatingBarStyle(){
        LayerDrawable stars = (LayerDrawable) this.ratingBar.getProgressDrawable();
        assert stars != null;

        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(getBaseContext(),
                R.color.turquesa_app), PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Sets up map if is not null
     */
    private void setUpMapIfNeeded(){
        // Do a null check to confirm that we have not already instantiated the map.
        if(this.placeMap != null){
            //nothing to do
        }else{
            // Try to obtain the map from the SupportMapFragment.
            this.placeMapFragment =
                    (SupportMapFragment) getSupportFragmentManager().
                    findFragmentById(R.id.fragment_show_place_info_map);

            this.placeMap = this.placeMapFragment.getMap();

            if(placeMap != null){
                this.setUpMap();
            }else{
                //nothing to do
            }
        }
    }

    /**
     * Sets up map
     */
    private void setUpMap(){
        this.placeMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getLatitude(),
                getLongitude()), 9));
        this.markPlaceOnMap();
    }

    /**
     * Marks place on map
     */
    private void markPlaceOnMap(){
        placeMap.addMarker(
                new MarkerOptions()
                        .title(getName())
                        .snippet(getAddress())
                        .position(new LatLng(getLatitude(), getLongitude()))
        );
    }

    /**
     * Show place info when the user clicks on this option
     * @param view view where the place will be shown
     */
    public void showPlaceInfoOnClick(View view){
        assert view != null;

        switch(view.getId()){
            case R.id.button_show_map:
                setUpMapIfNeeded();
                hideMapButton.setVisibility(View.VISIBLE);
                showMapButton.setVisibility(View.GONE);
                placeMapFragment.getView().setVisibility(View.VISIBLE);
                break;
            case R.id.button_hide_map:
                hideMapButton.setVisibility(View.GONE);
                showMapButton.setVisibility(View.VISIBLE);
                placeMapFragment.getView().setVisibility(View.GONE);
                break;
            default:
                //nothing to do
                break;
        }
    }

    /**
     * Sets place information
     */
    private void setPlaceInformation(){
        Intent intent = getIntent();
        this.setName(intent.getStringExtra("name"));
        this.setPhone(intent.getStringExtra("phone"));
        this.setAddress(intent.getStringExtra("address"));
        this.setGrade(intent.getFloatExtra("grade", FLOAT_ZERO));
        this.setDescription(intent.getStringExtra("description"));
        this.setLatitude(intent.getDoubleExtra("latitude", DOUBLE_ZERO));
        this.setLongitude(intent.getDoubleExtra("longitude", DOUBLE_ZERO));
        this.setOperation(intent.getStringExtra("operation"));
        this.setIdPlace(intent.getIntExtra("idPlace", INT_ZERO));
    }

    /**
     * Returns the place name
     * @return Current place name
     */
    private String getName(){
        return this.name;
    }

    /**
     * Returns place latitude
     * @return Current place latitude
     */
    private double getLatitude(){
        return this.latitude;
    }

    /**
     * Returns the place address
     * @return Current place address
     */
    private String getAddress(){
        return this.address;
    }

    /**
     * Returns the place longitude
     * @return Current place longitude
     */
    private double getLongitude(){
        return this.longitude;
    }

    /**
     * Sets grade value
     * @param grade New grade value
     */
    private void setGrade(float grade){
        this.grade = grade;
    }

    /**
     * Sets address value
     * @param address New address value
     */
    private void setAddress(String address){
        assert address != null;
        this.address = address;
    }

    /**
     * Sets longitude value
     * @param longitude New longitude value
     */
    private void setLongitude(double longitude){
        this.longitude = longitude;
    }

    /**
     * Sets place description
     * @param description New place description
     */
    private void setDescription(String description){
        assert description != null;
        this.description = description;
    }

    /**
     * Sets operation value
     * @param operation New operation value
     */
    private void setOperation(String operation){
        assert operation != null;
        this.operation = operation;
    }

    /**
     * Sets place name
     * @param name New place name
     */
    private void setName(String name){
        assert name != null;
        this.name = name;
    }

    /**
     * Sets place phone
     * @param phone New place phone
     */
    private void setPhone(String phone){
        assert phone != null;
        this.phone = phone;
    }

    /**
     * Sets new latitude value
     * @param latitude New latitude value
     */
    private void setLatitude(double latitude){
        this.latitude = latitude;
    }

    /**
     * Sets address text on TextView
     * @param addressText Address text to be put in view
     */
    private void setAddressText(String addressText){
        assert addressText != null;

        TextView addressTextView = (TextView) findViewById(R.id.address_text);
        assert  addressTextView != null;

        addressTextView.setText(addressText);
        addressTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Sets operation text on TextView
     * @param operationText Operation text to be put in view
     */
    private void setOperationText(String operationText){
        assert operationText != null;

        TextView operationTextView = (TextView) findViewById(R.id.operation_text);
        assert operationTextView != null;

        operationTextView.setText(operationText);
        operationTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Sets phone text on TextView
     * @param phoneText Phone text to be put in view
     */
    private void setPhoneText(String phoneText){
        assert phoneText != null;

        TextView phoneTextView = (TextView) findViewById(R.id.phone_text);
        assert phoneTextView != null;

        phoneTextView.setText(phoneText);
    }

    /**
     * Sets grade text on TextView
     * @param gradeText Grade text to be put in view
     */
    private void setGradeText(String gradeText){
        assert gradeText != null;

        TextView gradeTextView = (TextView) findViewById(R.id.grade_text);
        assert gradeTextView != null;

        gradeTextView.setText(gradeText);
    }

    /**
     * Sets description text on TextView
     * @param descriptionText Description text to be put in view
     */
    private void setDescriptionText(String descriptionText){
        assert descriptionText != null;

        TextView descriptionTextView = (TextView) findViewById(R.id.description_text);
        assert descriptionTextView != null;

        descriptionTextView.setText(descriptionText);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Sets place text views
     */
    private void setAllTextViews(){
        setAddressText(this.address);
        setOperationText(this.operation);
        setPhoneText(this.phone);
        setGradeText(Float.toString(this.grade));
        setDescriptionText(this.description);
    }

    /**
     * Sets the showMapButton
     * @param showMapButton Button that shows map on click
     */
    private void setShowMapButton(Button showMapButton){
        assert showMapButton != null;
        this.showMapButton = showMapButton;
    }

    /**
     * Sets the hideMapButton
     * @param hideMapButton Button that hides map on click
     */
    private void setHideMapButton(Button hideMapButton){
        assert hideMapButton != null;
        this.hideMapButton = hideMapButton;
    }

    /**
     * Sets rating message according with the login status
     * @param isUserLoggedIn Indicates if user is logged in or not
     */
    private void setRatingMessage(boolean isUserLoggedIn){
        String message = STRING_EMPTY;

        if(isUserLoggedIn){
            message = "Sua avaliação:";
        }else{
            message = "Faça login para avaliar!";
        }
        TextView ratingMessage = (TextView) findViewById(R.id.rate_it_text);
        assert ratingMessage != null;

        ratingMessage.setText(message);
    }

    /**
     * Sets userId
     * @param userId New userId
     */
    private void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Sets isUserLoggedIn
     * @param isUserLoggedIn Indicates if user is logged in or not
     */
    private void setIsUserLoggedIn(boolean isUserLoggedIn){
        this.isUserLoggedIn = isUserLoggedIn;
    }

    /**
     * Sets place id
     * @param idPlace New place id
     */
    private void setIdPlace(int idPlace){
        this.idPlace = idPlace;
    }

    /**
     * Sets rating evaluation of a place
     * @param idPlace Id of place evaluated
     * @param idUser Id of user who evaluated the place
     * @param grade Current grade value
     */
    private void setRatingEvaluation(int idPlace, int idUser, float grade){
        this.ratingEvaluation = new Evaluation(idPlace, idUser, grade);
    }
}