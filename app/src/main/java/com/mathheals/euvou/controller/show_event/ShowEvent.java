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

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState){

        View showEventView = inflater.inflate(R.layout.fragment_show_event, container, false);

        //Sets the listener to the button that open the event on map
        Button showEventOnMapButton = (Button) showEventView.findViewById(R.id.
                showEventOnMapButton);
        showEventOnMapButton.setOnClickListener(this);

        getEventIdFromDataBase();

        //Gets the user login status
        LoginUtility loginUtility = new LoginUtility(this.getActivity());
        boolean isUserLoggedIn = loginUtility.hasUserLoggedIn();

        //Gets the current user identifier
        userId = loginUtility.getUserId();

        eventDAO = new EventDAO(this.getActivity());

        getEventInfoFromDataBase(showEventView);

        setUpRatingBar(isUserLoggedIn, showEventView);

        setUpParticipateButton(isUserLoggedIn, showEventView);

        return showEventView;
    }

    private void getEventIdFromDataBase(){
        this.eventId = this.getArguments().getString("id");
    }

    private void showEventInformationOnTextView(final View showEventView, final String eventName,
                                                final String eventAddress, final String eventDate,
                                                final String eventDescription,
                                                final String eventPrice){

        //Gets the text views of the fragment view
        TextView eventNameTextView = (TextView) showEventView.findViewById(R.id.nameEventShow);
        TextView eventDateTextView = (TextView) showEventView.findViewById(R.id.dateEvent);
        TextView eventAddressTextView = (TextView) showEventView.findViewById(R.id.eventPlaces);
        TextView eventPriceTextView = (TextView) showEventView.findViewById(R.id.eventPrice);
        TextView eventCategoriesTextView = (TextView) showEventView.findViewById(R.id.
                eventCategories);
        TextView eventDescriptionTextView = (TextView) showEventView.findViewById(R.id.
                descriptionEvent);

        //Sets the text of text views for the data of the parameters
        eventNameTextView.setText(eventName);
        eventDescriptionTextView.setText(eventDescription);
        eventDateTextView.setText(eventDate);
        eventAddressTextView.setText(eventAddress);
        setPriceText(eventPriceTextView, eventPrice);
        setCategoriesText(Integer.parseInt(eventId), eventCategoriesTextView);
    }

    private void getEventInfoFromDataBase(final View showEventView){
        try{
            JSONObject eventDATA = eventDAO.searchEventById(Integer.parseInt(eventId));

            String eventName = eventDATA.getJSONObject("0").getString("nameEvent");
            String eventAddress = eventDATA.getJSONObject("0").getString("address");
            String eventDescription = eventDATA.getJSONObject("0").getString("description");
            String eventDateTime = eventDATA.getJSONObject("0").getString("dateTimeEvent");
            String eventPrice = eventDATA.getJSONObject("0").getString("price");
            eventLongitude = eventDATA.getJSONObject("0").getString("longitude");
            eventLatitude = eventDATA.getJSONObject("0").getString("latitude");

            showEventInformationOnTextView(showEventView, eventName, eventAddress, eventDateTime,
                    eventDescription, eventPrice);

        }catch(JSONException jsonException){
            jsonException.printStackTrace();
        }
    }

    private void setUpParticipateButton(final boolean isUserLoggedIn,
                                        final View showEventView){

        Button participateButton = (Button) showEventView.findViewById(R.id.EuVou);
        participateButton.setOnClickListener(this);

        if(isUserLoggedIn){
            participateButton.setVisibility(View.VISIBLE);

            if(eventDAO.verifyParticipate(userId, Integer.parseInt(eventId)) != null){
                final String NOTGO = "#NÃOVOU";
                participateButton.setText(NOTGO);
            }
            else{
                final String GO = "#EUVOU";
                participateButton.setText(GO);
            }
        }
        else{
            participateButton.setVisibility(showEventView.GONE);

        }
    }


    private String[] getEventCategoriesById(final int eventId){

        EventCategoryDAO eventCategoryDAO = new EventCategoryDAO(getActivity());
        JSONObject eventCategoryJSON = eventCategoryDAO.searchCategoriesByEventId(eventId);

        ArrayList<String> categories = new ArrayList<>();

        for(int i = 0; i < eventCategoryJSON.length(); ++i){
            try{

                final String ID_CATEGORY = "idCategory";
                final String NAME_CATEGORY = "nameCategory";
                final String FIRST_COLUMN = "0";

                int categoryId = eventCategoryJSON.getJSONObject(Integer.toString(i))
                        .getInt(ID_CATEGORY);
                CategoryDAO categoryDAO = new CategoryDAO(getActivity());
                JSONObject categoryJSON = categoryDAO.searchCategoryById(categoryId);
                String categoryName = categoryJSON.getJSONObject(FIRST_COLUMN)
                        .getString(NAME_CATEGORY);
                categories.add(categoryName);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        String[] categoriesArray = new String[categories.size()];
        categoriesArray = categories.toArray(categoriesArray);

        return categoriesArray;
    }

    public void setCategoriesText(final int eventId, final TextView eventCategoriesText){
        String[] eventCategories = getEventCategoriesById(eventId);
        String text = eventCategories[0];

        for(int i = 1; i < eventCategories.length; ++i){
            text += (", " + eventCategories[i]);
        }
        eventCategoriesText.setText(text);
    }

    public void setPriceText(final TextView eventPriceText, final String eventPrice){
        final int PRICE = new Integer(eventPrice);
        final String REAIS_PART = Integer.toString(PRICE / 100);
        final String CENTS = Integer.toString(PRICE % 100);
        final String CENTS_PART = CENTS.length() > 1 ? CENTS : "0" + CENTS;
        eventPriceText.setText("R$ " + REAIS_PART + "," + CENTS_PART);

    }

    private void showEventOnMap(){
        Bundle latitudeAndLongitude = new Bundle();
        latitudeAndLongitude.putStringArray("LatitudeAndLongitude", new String[]{eventLatitude,
                eventLongitude});
        Intent intent = new Intent(getContext(), ShowOnMap.class);
        intent.putExtras(latitudeAndLongitude);
        startActivity(intent);
    }

    private void markParticipate(){

        if(eventDAO.verifyParticipate(userId,Integer.parseInt(eventId)) != null){
            Toast.makeText(getActivity(), "Hey, você já marcou sua participação",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            eventDAO.markParticipate(userId, Integer.parseInt(eventId));
            Toast.makeText(getActivity(),"Salvo com sucesso" , Toast.LENGTH_SHORT).show();
        }
    }

    private void markOffParticipate(){

        if(eventDAO.verifyParticipate(userId,Integer.parseInt(eventId)) != null){
            eventDAO.markOffParticipate(userId, Integer.parseInt(eventId));
            Toast.makeText(getActivity(),"Salvo com sucesso" , Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(), "Hey, você já desmarcou sua participação",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateParticipation(View view){
        final String GO = "#EUVOU";

        //Get the current text at participate button
        Button participateButton = (Button) view.findViewById(R.id.EuVou);
        String participateButtonText = participateButton.getText().toString();

        if(participateButtonText.equals(GO)){
            markParticipate();
        }
        else{
            markOffParticipate();
        }
    }

    public void onClick(final View view){

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
        ratingMessageTextView.setText(message);
    }

    /**
     * Sets the necessary configurations of the ratingBar based on user login status
     * @param isUserLoggedIn - User login status
     * @param showUserView - View that contains the ratingBar
     */
    private void setUpRatingBar(final boolean isUserLoggedIn, final View showUserView){
        if(isUserLoggedIn){
            final String LOGGED_IN_MESSAGE = "Sua avaliação:";
            setRatingMessage(showUserView, LOGGED_IN_MESSAGE);

            RatingBar ratingBar = (RatingBar) showUserView.findViewById(R.id.ratingBar);
            ratingBar.setOnRatingBarChangeListener(this);
            ratingBar.setVisibility(View.VISIBLE);

            setEvaluationAtRatingBar(ratingBar);
        }
        else{
            final String LOGGED_OUT_MESSAGE = "Faça login para avaliar este usuário!";
            setRatingMessage(showUserView, LOGGED_OUT_MESSAGE);
        }
    }

    private void setEvaluationAtRatingBar(final RatingBar ratingBar){

        //Searches the event evaluation at database
        EventEvaluationDAO eventEvaluationDAO = new EventEvaluationDAO();
        JSONObject evaluationJSON = eventEvaluationDAO.searchEventEvaluation(Integer.
                parseInt(eventId), userId);

        if(evaluationJSON != null){
            try{
                Float eventEvaluation = new Float(evaluationJSON.getJSONObject("0")
                        .getDouble("grade"));
                ratingBar.setRating(eventEvaluation);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        else{
            //If event don't have an evaluation, it don't need to be set at ratingBar
        }
    }

    private void setEventEvaluation(final Float rating, final Integer userId, final Integer eventId){
        try{
            eventEvaluation = new EventEvaluation(rating, userId, eventId);
            String SUCCESSFUL_EVALUATION_MESSAGE = "Avaliação cadastrada com sucesso";
            Toast.makeText(getActivity().getBaseContext(), SUCCESSFUL_EVALUATION_MESSAGE,
                    Toast.LENGTH_LONG).show();

        }catch (EventEvaluationException exception){
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

    @Override
    public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser){
        setEventEvaluation(rating, userId, new Integer(eventId));

        EventEvaluationDAO eventEvaluationDAO = new EventEvaluationDAO();

        eventEvaluationDAO.evaluateEvent(eventEvaluation);
    }
}