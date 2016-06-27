/**
 * File: RegisterEventFragment.java
 * Purpose: Fragment responsible for register a new user
 */

package com.mathheals.euvou.controller.event_registration;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.utility.LoginUtility;
import com.mathheals.euvou.controller.utility.Mask;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import dao.EventDAO;
import exception.EventException;
import model.Event;

/**
 * Created by izabela on 13/10/15.
 */
public class RegisterEventFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private String latitude;
    private String longitude;

    private Vector<String> categories= new Vector<>();

    ArrayList <Integer> checkBoxOptions = new ArrayList<>();

    HashMap <String, EditText> messages = new HashMap<>();

    EditText nameEventField;
    EditText dateEventField;
    EditText hourEventField;
    EditText descriptionEventField;
    EditText addressEventField;
    EditText priceEventRealField;
    EditText priceEventDecimalField;

    /**
     * Required constructor to instantiate a fragment object
     */
    public RegisterEventFragment(){

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the fragment
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_event, container, false);

        initializeOptions();

        //Adding listener to saveEvent Button
        Button registerEvent = (Button) view.findViewById(R.id.saveEvent);
        registerEvent.setOnClickListener(this);

        //Adding listener to eventLocal EditText
        Button eventLocal = (Button) view.findViewById(R.id.eventLocal);
        eventLocal.setOnClickListener(this);

        //Adding mask to eventDate Field
        EditText eventDate = (EditText) view.findViewById(R.id.eventDate);
        eventDate.addTextChangedListener(Mask.insert("##/##/####", eventDate));

        //Adding listener to CheckBoxs to verify if each CheckBox is checked or not
        addCheckBoxListeners(view);

        nameEventField = (EditText) this.getActivity().findViewById(R.id.eventName);
        dateEventField = (EditText) this.getActivity().findViewById(R.id.eventDate);
        hourEventField = (EditText) this.getActivity().findViewById(R.id.eventHour);
        descriptionEventField = (EditText) this.getActivity().findViewById(R.id.eventDescription);
        addressEventField = (EditText) this.getActivity().findViewById(R.id.eventAddress);
        priceEventRealField = (EditText) this.getActivity().findViewById(R.id.eventPriceReal);
        priceEventDecimalField = (EditText) this.getActivity().findViewById(R.id.eventPriceDecimal);

        return view;
    }

    /**
     * Fill an ArrayList with the Checkbox Options Id's
     */
    private void initializeOptions(){
        checkBoxOptions.add(R.id.optionCinema);
        checkBoxOptions.add(R.id.optionEducation);
        checkBoxOptions.add(R.id.optionExposition);
        checkBoxOptions.add(R.id.optionMuseum);
        checkBoxOptions.add(R.id.optionOthers);
        checkBoxOptions.add(R.id.optionParty);
        checkBoxOptions.add(R.id.optionShow);
        checkBoxOptions.add(R.id.optionSports);
        checkBoxOptions.add(R.id.optionTheater);
    }

    /**
     * Adds the names of the categories which were clicked in a vector of strings
     * @param view - Current view being used in the fragment
     */
    private void addEventCategories(View view){
        assert view != null;

        for(int i = 0; i < checkBoxOptions.size(); i++){
            if(view.getId() == checkBoxOptions.get(i)){
                CheckBox optionCheckBox = (CheckBox) view;
                String checkBoxText = getCheckBoxText(optionCheckBox, checkBoxOptions.get(i));

                if(optionCheckBox.isChecked()){
                    categories.add(checkBoxText);
                }
                else{
                    categories.remove(checkBoxText);
                }
            }
            else{
                //Nothing to do
            }
        }

    }

    /**
     * Gets the text of the checkbox option
     * @param optionCheckBox - An object of the checkbox option
     * @param idOption - The id of the checkbox option
     * @return String - The text of the checkbox option
     */
    private String getCheckBoxText(CheckBox optionCheckBox, Integer idOption){
        assert optionCheckBox != null;
        assert idOption != null;

        String checkBoxText;

        final String EDUCATION_TEXT = "Educacao";
        final String EXPOSITION_TEXT = "Exposicao";

        if(idOption == R.id.optionEducation){
            checkBoxText = EDUCATION_TEXT;
        }
        else if(idOption == R.id.optionExposition){
            checkBoxText = EXPOSITION_TEXT;
        }
        else{
            checkBoxText = optionCheckBox.getText().toString();
        }

        return checkBoxText;
    }

    /**
     * Saves the new event in case of success, or throws an exception in case of failure
     * @param view Current view being used in the fragment
     */
    @Override
    public void onClick(View view) {
        assert view != null;

        if(view.getId() == R.id.saveEvent){
            createEvent();
        }
        else if(view.getId() == R.id.eventLocal){
            Intent map = new Intent(getActivity(), LocalEventActivity.class);
            startActivityForResult(map, 2);
        }
        else{
            addEventCategories(view);
        }
    }

    /**
     * Creates an instance of Event and fill with the typed data
     */
    private void createEvent(){
        String nameEvent = nameEventField.getText().toString();
        String dateEvent = dateEventField.getText().toString();
        String eventHour = hourEventField.getText().toString();
        String descriptionEvent = descriptionEventField.getText().toString();
        String addressEvent = addressEventField.getText().toString();
        String priceEventReal = priceEventRealField.getText().toString();
        String priceEventDecimal = priceEventDecimalField.getText().toString();

        LoginUtility loginUtility = new LoginUtility(getActivity());
        int idOwner = loginUtility.getUserId();

        try {
            Event event = new Event(idOwner, nameEvent, dateEvent, eventHour, priceEventReal,
                    priceEventDecimal, addressEvent, descriptionEvent, latitude, longitude,
                    categories);
            registerEvent(event);

            final Context context = getActivity().getBaseContext();

            final String EVENT_REGISTERED_WITH_SUCCESS_MESSAGE = context.getResources().
                    getString(R.string.event_registered_with_success);

            Toast.makeText(context, EVENT_REGISTERED_WITH_SUCCESS_MESSAGE,
                    Toast.LENGTH_LONG).show();
        } catch (EventException e) {
            String message = e.getMessage();

            showErrorMessages(message);
        } catch (ParseException e) {
            e.printStackTrace();

        }

    }

    /**
     * Show a error message for each field that is not right
     * @param message - The error message that must be showed
     */
    private void showErrorMessages(String message){
        assert message != null;

        initializeMessages();

        for(int i = 0; i < messages.size(); i++){
            if(messages.containsKey(message)){
                messages.get(message).requestFocus();
                messages.get(message).setError(message);
            }else{
                //Nothing to do
            }
        }
    }

    /**
     * Put all the messages in a HashMap
     */
    private void initializeMessages(){
        messages.put(Event.ADDRESS_IS_EMPTY, addressEventField);
        messages.put(Event.INVALID_EVENT_HOUR, hourEventField);
        messages.put(Event.EVENT_HOUR_IS_EMPTY, hourEventField);
        messages.put(Event.DESCRIPTION_CANT_BE_EMPTY, descriptionEventField);
        messages.put(Event.DESCRIPTION_CANT_BE_GREATER_THAN, descriptionEventField);
        messages.put(Event.EVENT_DATE_IS_EMPTY, dateEventField);
        messages.put(Event.EVENT_NAME_CANT_BE_EMPTY_NAME, nameEventField);
        messages.put(Event.INVALID_EVENT_DATE, dateEventField);
        messages.put(Event.NAME_CANT_BE_GREATER_THAN_50, nameEventField);
        messages.put(Event.PRICE_REAL_IS_EMPTY, priceEventRealField);
        messages.put(Event.PRICE_DECIMAL_IS_EMPTY, priceEventDecimalField);
    }

    /**
     * Called when an activity which was launched exits
     * @param requestCode - Request code which the activity started with
     * @param resultCode - Result code which was returned
     * @param data - Additional data which the activity may contain
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        assert data != null;

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    latitude = bundle.getString("latitude");
                    longitude = bundle.getString("longitude");

                    final Context context = getContext();

                    final String LOCAL_SUCCESSFULLY_SELECTED_MESSAGE = context.getResources().
                            getString(R.string.local_successfully_selected);

                    Toast.makeText(context, LOCAL_SUCCESSFULLY_SELECTED_MESSAGE, Toast.LENGTH_LONG).show();
                }
                else{
                    //Nothing to do
                }
                break;
            }
        }
    }

    /**
     * Saves an event on the DataBase
     * @param event - Object with event data
     */
    private void registerEvent(Event event){
        assert event != null;

        EventDAO eventDAO = new EventDAO(getActivity());
        eventDAO.saveEvent(event);
    }

    /**
     * Adds listeners of the checkboxes
     * @param v - Current view being used in the fragment
     */
    private void addCheckBoxListeners(View v){
        assert v != null;

        CheckBox showCategory = (CheckBox) v.findViewById(R.id.optionShow);
        CheckBox expositionCategory = (CheckBox) v.findViewById(R.id.optionExposition);
        CheckBox museumCategory = (CheckBox) v.findViewById(R.id.optionMuseum);
        CheckBox cinemaCategory = (CheckBox) v.findViewById(R.id.optionCinema);
        CheckBox theaterCategory = (CheckBox) v.findViewById(R.id.optionTheater);
        CheckBox partyCategory = (CheckBox) v.findViewById(R.id.optionParty);
        CheckBox educationCategory = (CheckBox) v.findViewById(R.id.optionEducation);
        CheckBox sportsCategory = (CheckBox) v.findViewById(R.id.optionSports);
        CheckBox othersCategory = (CheckBox) v.findViewById(R.id.optionOthers);

        showCategory.setOnClickListener(this);
        expositionCategory.setOnClickListener(this);
        museumCategory.setOnClickListener(this);
        cinemaCategory.setOnClickListener(this);
        theaterCategory.setOnClickListener(this);
        partyCategory.setOnClickListener(this);
        educationCategory.setOnClickListener(this);
        sportsCategory.setOnClickListener(this);
        othersCategory.setOnClickListener(this);

    }


}
