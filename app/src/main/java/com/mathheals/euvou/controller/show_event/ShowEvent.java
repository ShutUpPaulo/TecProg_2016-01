/**
 * File: ShowEvent.java
 * Purpose: Show event information
 */

package com.mathheals.euvou.controller.show_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.utility.LoginUtility;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Button;
import java.util.ArrayList;
import dao.CategoryDAO;
import dao.EventCategoryDAO;
import dao.EventDAO;
import dao.EventEvaluationDAO;
import exception.EventEvaluationException;
import model.EventEvaluation;

public class ShowEvent extends android.support.v4.app.Fragment implements View.OnClickListener,
        RatingBar.OnRatingBarChangeListener {

    private String eventLongitude;
    private String eventLatitude;
    private String eventId;
    private EventDAO eventDAO;
    private int userId;
    EventEvaluation eventEvaluation;

    public ShowEvent(){
        // Required empty public constructor
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the ShowEvent fragment
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState){

        assert inflater != null;

        View showEventView = inflater.inflate(R.layout.fragment_show_event, container, false);

        assert showEventView != null;

        //Sets the listener to the button that open the event on map
        Button showEventOnMapButton = (Button) showEventView.findViewById(R.id.
                showEventOnMapButton);

        assert showEventOnMapButton != null;
        assert showEventOnMapButton.getId() == R.id.showEventOnMapButton;

        showEventOnMapButton.setOnClickListener(this);

        assert showEventOnMapButton.hasOnClickListeners();

        getEventIdFromDataBase();

        assert this.getActivity() != null;

        //Gets the user login status
        LoginUtility loginUtility = new LoginUtility(this.getActivity());

        assert loginUtility != null;

        boolean isUserLoggedIn = loginUtility.hasUserLoggedIn();

        //Gets the current user identifier
        userId = loginUtility.getUserId();

        assert userId <= Integer.MAX_VALUE;

        //Sets EventDAO object to communicate with database
        eventDAO = new EventDAO(this.getActivity());

        assert eventDAO != null;

        getEventInfoFromDataBase(showEventView);

        setUpRatingBar(isUserLoggedIn, showEventView);

        setUpParticipateButton(isUserLoggedIn, showEventView);

        return showEventView;
    }

    /**
     * Gets the event identifier from database
     */
    private void getEventIdFromDataBase(){
        assert this.getArguments() != null;

        this.eventId = this.getArguments().getString("id");

        assert this.eventId != null;
        assert Integer.parseInt(eventId) >= 0;
        assert Integer.parseInt(eventId) <= Integer.MAX_VALUE;
    }

    /**
     * Sets the TextViews to show the user information
     * @param showEventView - View that contains the TextViews
     * @param eventName - Name to be set at the text view that shows the event name
     * @param eventDate - Date to be set at the text view that shows the event date
     * @param eventAddress - Address to be set at the text view that shows the event address
     * @param eventDescription - Description to be set at the text view that shows the event
     *                           description
     * @param eventPrice - Price to be set at the text view that shows the event price
     */
    private void showEventInformationOnTextView(final View showEventView, final String eventName,
                                                final String eventAddress, final String eventDate,
                                                final String eventDescription,
                                                final String eventPrice){

        assert showEventView != null;

        //Gets the text views of the fragment view
        TextView eventNameTextView = (TextView) showEventView.findViewById(R.id.nameEventShow);
        TextView eventDateTextView = (TextView) showEventView.findViewById(R.id.dateEvent);
        TextView eventAddressTextView = (TextView) showEventView.findViewById(R.id.eventPlaces);
        TextView eventPriceTextView = (TextView) showEventView.findViewById(R.id.eventPrice);
        TextView eventCategoriesTextView = (TextView) showEventView.findViewById(R.id.
                eventCategories);
        TextView eventDescriptionTextView = (TextView) showEventView.findViewById(R.id.
                descriptionEvent);

        assert eventNameTextView != null;
        assert eventNameTextView.getId() == R.id.nameEventShow;

        assert eventDateTextView != null;
        assert eventDateTextView.getId() == R.id.dateEvent;

        assert eventAddressTextView != null;
        assert eventAddressTextView.getId() == R.id.eventPlaces;

        assert eventPriceTextView != null;
        assert eventPriceTextView.getId() == R.id.eventPrice;

        assert eventCategoriesTextView != null;
        assert eventCategoriesTextView.getId() == R.id.eventCategories;

        assert eventDescriptionTextView != null;
        assert eventDescriptionTextView.getId() == R.id.descriptionEvent;

        //Sets the text of text views for the data of the parameters
        eventNameTextView.setText(eventName);
        assert eventNameTextView.getText() == eventName;

        eventDescriptionTextView.setText(eventDescription);
        assert eventDescriptionTextView.getText() == eventDescription;

        eventDateTextView.setText(eventDate);
        assert eventDateTextView.getText() == eventDate;

        eventAddressTextView.setText(eventAddress);
        assert eventAddressTextView.getText() == eventAddress;

        setPriceText(eventPriceTextView, eventPrice);
        setCategoriesText(Integer.parseInt(eventId), eventCategoriesTextView);
    }

    /**
     * Gets the event name, date, address, description, price, latitude and longitude from database
     * @param showEventView - View of ShowEvent fragment
     */
    private void getEventInfoFromDataBase(final View showEventView){
        try{
            //Gets event data according to the eventId
            JSONObject eventDATA = eventDAO.searchEventById(Integer.parseInt(eventId));
            assert eventDATA != null;

            String eventName = eventDATA.getJSONObject("0").getString("nameEvent");
            assert eventName != null;

            String eventAddress = eventDATA.getJSONObject("0").getString("address");
            assert eventAddress != null;

            String eventDescription = eventDATA.getJSONObject("0").getString("description");
            assert eventDescription != null;

            String eventDateTime = eventDATA.getJSONObject("0").getString("dateTimeEvent");
            assert eventDateTime != null;

            String eventPrice = eventDATA.getJSONObject("0").getString("price");
            assert eventPrice != null;

            eventLongitude = eventDATA.getJSONObject("0").getString("longitude");
            assert eventLongitude != null;

            eventLatitude = eventDATA.getJSONObject("0").getString("latitude");
            assert eventLatitude != null;

            //Shows the event data on the text view
            showEventInformationOnTextView(showEventView, eventName, eventAddress, eventDateTime,
                    eventDescription, eventPrice);

        }catch(JSONException jsonException){
            jsonException.printStackTrace();
        }
    }

    /**
     * Sets visibility and configuration of participate button according to the user login status
     * @param isUserLoggedIn - User login status (true if user is logged in, false if user is not)
     * @param showEventView - View that contains the participate button
     */
    private void setUpParticipateButton(final boolean isUserLoggedIn,
                                        final View showEventView){

        assert showEventView != null;

        //Sets the listener to the participate button
        Button participateButton = (Button) showEventView.findViewById(R.id.EuVou);

        assert participateButton != null;
        assert participateButton.getId() == R.id.EuVou;

        participateButton.setOnClickListener(this);

        assert participateButton.hasOnClickListeners();

        //Sets visibility as visible for user logged in, and config button text
        if(isUserLoggedIn){

            assert userId >= 0;

            participateButton.setVisibility(View.VISIBLE);

            assert participateButton.getVisibility() == View.VISIBLE;

            /* If user participation is on, sets button text to the option to turn off the
               participation */
            assert eventDAO != null;

            if(eventDAO.verifyParticipate(userId, Integer.parseInt(eventId)) != null){
                final String NOTGO = "#NÃOVOU";
                participateButton.setText(NOTGO);

                assert participateButton.getText() == NOTGO;
            }
            /* If user participation is off or user never set a participation, sets button text to
               the option to turn on the participation */
            else{
                final String GO = "#EUVOU";
                participateButton.setText(GO);

                assert participateButton.getText() == GO;
            }
        }
        //Sets the visibility as gone for user logged out
        else{
            participateButton.setVisibility(showEventView.GONE);

            assert participateButton.getVisibility() == showEventView.GONE;

        }
    }

    /**
     * Gets the event categories from database and saves it in a String array
     * @param eventId - Identifier of the event
     * @return String[] - Array of strings with the event categories
     */
    private String[] getEventCategoriesById(final int eventId){

        assert getActivity() != null;

        //Gets the JSON with all event categories
        EventCategoryDAO eventCategoryDAO = new EventCategoryDAO(getActivity());

        assert eventCategoryDAO != null;

        assert eventId >= 0;
        assert eventId <= Integer.MAX_VALUE;

        JSONObject eventCategoryJSON = eventCategoryDAO.searchCategoriesByEventId(eventId);

        assert eventCategoryJSON != null;

        ArrayList<String> categories = new ArrayList<>();

        for(int i = 0; i < eventCategoryJSON.length(); ++i){
            try{

                final String ID_CATEGORY = "idCategory";
                final String NAME_CATEGORY = "nameCategory";
                final String FIRST_COLUMN = "0";

                //Gets each category name
                int categoryId = eventCategoryJSON.getJSONObject(Integer.toString(i))
                        .getInt(ID_CATEGORY);

                CategoryDAO categoryDAO = new CategoryDAO(getActivity());

                assert categoryDAO != null;

                JSONObject categoryJSON = categoryDAO.searchCategoryById(categoryId);

                assert categoryJSON != null;

                String categoryName = categoryJSON.getJSONObject(FIRST_COLUMN)
                        .getString(NAME_CATEGORY);

                assert categoryName != null;

                assert categories != null;
                int categoriesSizeBeforeAddCategories = categories.size();

                //Adds the category name in the categories ArrayList
                categories.add(categoryName);

                assert categories.size() == categoriesSizeBeforeAddCategories + 1;

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        //Convert the categories ArrayList in a new array of strings
        String[] categoriesArray = new String[categories.size()];

        assert categoriesArray != null;

        categoriesArray = categories.toArray(categoriesArray);

        assert categoriesArray instanceof String[];

        return categoriesArray;
    }

    /**
     * Unifies the categories strings in one, adding commas to separate the words
     * @param eventId - Identifier of the event to have its categories configured
     * @param eventCategoriesText - TextView that shows the categories
     */
    public void setCategoriesText(final int eventId, final TextView eventCategoriesText){

        assert eventId <= Integer.MAX_VALUE;
        assert eventId >= 0;
        assert eventCategoriesText != null;
        assert eventCategoriesText.getId() == R.id.eventCategories;

        //Gets the categories names
        String[] eventCategories = getEventCategoriesById(eventId);

        assert eventCategories != null;

        //Initializes the final categories text with the first category
        String text = eventCategories[0];

        //Updates the final categories text with the other categories names separating it with comma
        for(int i = 1; i < eventCategories.length; ++i){
            text += (", " + eventCategories[i]);
        }

        //Sets the final categories text at the text view
        eventCategoriesText.setText(text);
    }


    /**
     * Formats price to R$XX,XX format
     * @param eventPriceText - TextView that shows the price
     * @param eventPrice - Price of the event
     */
    public void setPriceText(final TextView eventPriceText, final String eventPrice){

        assert eventPriceText != null;
        assert eventPriceText.getId() == R.id.eventPrice;
        assert eventPrice != null;

        final int PRICE = new Integer(eventPrice);

        assert PRICE >= 0;

        //Gets the "Real" part of the price
        final String REAIS_PART = Integer.toString(PRICE / 100);

        assert REAIS_PART != null;

        //Gets the cents part of the price
        final String CENTS = Integer.toString(PRICE % 100);

        assert CENTS != null;

        String centsPart = CENTS;

        assert centsPart != null;

        //If the cents part is only one unity, adds a zero before it Ex: 02
        if(CENTS.length() <= 1){
            centsPart = "0" + CENTS;

            assert centsPart.charAt(0) == '0';
        }
        else{
            //Otherwise, remains being cents
        }

        eventPriceText.setText("R$ " + REAIS_PART + "," + centsPart);

    }

    /**
     * Shows the event location on the map
     */
    private void showEventOnMap(){
        //Saves event latitude and longitude in a bundle
        Bundle latitudeAndLongitude = new Bundle();

        assert latitudeAndLongitude != null;

        latitudeAndLongitude.putStringArray("LatitudeAndLongitude", new String[]{eventLatitude,
                eventLongitude});

        /* Starts the activity that shows the map, sending the latitude and longitude through the
           bundle created above */
        Intent intent = new Intent(getContext(), ShowOnMap.class);

        assert intent != null;

        intent.putExtras(latitudeAndLongitude);

        assert intent.hasExtra("LatitudeAndLongitude");

        startActivity(intent);
    }

    /**
     * Turn on the user participation in the event at database
     */
    private void markParticipate(){

        assert eventDAO != null;

        //Verify user participation in the event
        JSONObject participation = eventDAO.verifyParticipate(userId,Integer.parseInt(eventId));

        //If the user already marked participation, a message will inform it
        if(participation != null){
            assert participation != null;
            Toast.makeText(getActivity(), getResources()
                            .getString(R.string.already_mark_participation),
                    Toast.LENGTH_SHORT).show();
        }
        //Otherwise, a message will inform that the participation was saved
        else{
            assert participation == null;

            eventDAO.markParticipate(userId, Integer.parseInt(eventId));
            Toast.makeText(getActivity(), getResources().getString(R.string.success_saved),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Turn off the user participation in the event at database
     */
    private void markOffParticipate(){

        //Verify user participation in the event
        JSONObject participation = eventDAO.verifyParticipate(userId,Integer.parseInt(eventId));

        assert eventDAO != null;

        if(participation != null){
            assert participation != null;

            eventDAO.markOffParticipate(userId, Integer.parseInt(eventId));
            Toast.makeText(getActivity(), getResources().getString(R.string.success_saved),
                    Toast.LENGTH_SHORT).show();
        }
        else{
            assert participation == null;

            Toast.makeText(getActivity(), getResources()
                            .getString(R.string.already_unmarked_participation),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Marks the user participation in the event according to the text at the button
     * @param view View that contains the participation button
     */
    private void updateParticipation(View view){

        assert view != null;

        final String GO = getResources().getString(R.string.go);

        //Gets the current text at participate button
        Button participateButton = (Button) view.findViewById(R.id.EuVou);

        assert participateButton != null;

        String participateButtonText = participateButton.getText().toString();

        //Updates the participation based on the current text at the button
        if(participateButtonText.equals(GO)){
            markParticipate();
        }
        else{
            markOffParticipate();
        }
    }

    /**
     * Calls method to update the user participation in the event when the user clicks on
     * the EuVou button and method that shows the event on map when the user clicks on the
     * showEventOnMapButton
     * @param view View that contains the buttons that triggers the actions by user click
     */
    public void onClick(final View view){

        assert view != null;

        switch(view.getId()){
            case R.id.showEventOnMapButton:
                showEventOnMap();
                break;
            case R.id.EuVou:
                updateParticipation(view);
                break;
            default:
                //nothing to do
        }
    }

    /**
     * Sets the message of the ratingBar label based on user login status
     * @param showUserView - View that contains the ratingBar label
     * @param message - Message to be displayed at the label
     */
    private void setRatingMessage(final View showUserView, final String message){
        TextView ratingMessageTextView = (TextView) showUserView.findViewById(R.id.rate_event_text);

        assert ratingMessageTextView != null;
        assert ratingMessageTextView.getId() == R.id.rate_event_text;

        ratingMessageTextView.setText(message);
    }

    /**
     * Sets the necessary configurations of the ratingBar based on user login status
     * @param isUserLoggedIn - User login status
     * @param showUserView - View that contains the ratingBar
     */
    private void setUpRatingBar(final boolean isUserLoggedIn, final View showUserView){
        assert showUserView != null;

        if(isUserLoggedIn){
            assert isUserLoggedIn == true;

            //Sets the logged in label of the rating bar
            final String LOGGED_IN_MESSAGE = "Sua avaliação:";
            setRatingMessage(showUserView, LOGGED_IN_MESSAGE);

            //Sets the rating bar as visible
            RatingBar ratingBar = (RatingBar) showUserView.findViewById(R.id.ratingBar);

            assert ratingBar != null;

            ratingBar.setOnRatingBarChangeListener(this);
            ratingBar.setVisibility(View.VISIBLE);

            assert ratingBar.hasOnClickListeners();
            assert ratingBar.getVisibility() == View.VISIBLE;

            setEvaluationAtRatingBar(ratingBar);
        }
        else{
            assert isUserLoggedIn == false;

            //Sets the logged out label of the rating bar
            final String LOGGED_OUT_MESSAGE = "Faça login para avaliar este usuário!";
            setRatingMessage(showUserView, LOGGED_OUT_MESSAGE);
        }
    }

    /**
     * If the event already has an evaluation, this method sets it at ratingBar
     * @param ratingBar - RatingBar to sets the evaluation
     */
    private void setEvaluationAtRatingBar(final RatingBar ratingBar){
        assert ratingBar != null;

        //Searches the event evaluation at database
        EventEvaluationDAO eventEvaluationDAO = new EventEvaluationDAO();

        assert eventEvaluationDAO != null;

        JSONObject evaluationJSON = eventEvaluationDAO.searchEventEvaluation(Integer.
                parseInt(eventId), userId);

        if(evaluationJSON != null){
            assert evaluationJSON != null;

            try{
                //Gets the event evaluation from database and sets it at rating bar
                Float eventEvaluation = new Float(evaluationJSON.getJSONObject("0")
                        .getDouble("grade"));

                assert eventEvaluation >= 0F;
                assert eventEvaluation <= 5F;

                ratingBar.setRating(eventEvaluation);

                assert ratingBar.getRating() == eventEvaluation;

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        else{
            assert evaluationJSON == null;
            //If event don't have an evaluation, it don't need to be set at ratingBar
        }
    }

    /**
     * Instantiates an UserEvaluation object
     * @param rating - Evaluation given to the user (>=0 ... <=5)
     * @param userId - Identifier of the evaluator user
     * @param eventId - Identifier of the event evaluated
     */
    private void setEventEvaluation(final Float rating, final Integer userId,
                                    final Integer eventId){

        assert rating >= 0F;
        assert rating <= 5F;
        assert userId <= Integer.MAX_VALUE;
        assert eventId <= Integer.MAX_VALUE;
        assert eventId >= 0;


        try{
            //Tries to instantiate an event evaluation
            eventEvaluation = new EventEvaluation(rating, userId, eventId);

            assert eventEvaluation != null;

            //Shows a successful message when the evaluation is instantiated with success
            String SUCCESSFUL_EVALUATION_MESSAGE = "Avaliação cadastrada com sucesso";
            Toast.makeText(getActivity().getBaseContext(), SUCCESSFUL_EVALUATION_MESSAGE,
                    Toast.LENGTH_LONG).show();

        }catch (EventEvaluationException exception){

            assert exception != null;

            //Sets the error message if the evaluation is invalid
            if(exception.getMessage().equals(EventEvaluation.EVALUATION_IS_INVALID)){
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
            if(exception.getMessage().equals(EventEvaluation.EVENT_ID_IS_INVALID)){
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
            if(exception.getMessage().equals(EventEvaluation.USER_ID_IS_INVALID)){
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Triggers actions that must be done when the rating changes
     * @param ratingBar - The RatingBar whose rating has changed
     * @param rating - The current rating (>=0 ... <=5)
     * @param fromUser - True if the rating change was initiated by a user's touch gesture or
     *                 arrow key/horizontal trackbell movement
     */
    @Override
    public void onRatingChanged(final RatingBar ratingBar, final float rating,
                                final boolean fromUser){

        assert ratingBar != null;
        assert rating >= 0F;
        assert rating <= 5F;

        //Tries to instantiate an event evaluation
        setEventEvaluation(rating, userId, new Integer(eventId));

        //Saves the evaluation at database
        EventEvaluationDAO eventEvaluationDAO = new EventEvaluationDAO();

        assert eventEvaluationDAO != null;

        eventEvaluationDAO.evaluateEvent(eventEvaluation);
    }
}