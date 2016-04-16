package com.mathheals.euvou.controller.event_registration;
import android.app.Activity;
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

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import dao.EventDAO;
import exception.EventException;
import model.Event;

/**
 * Created by izabela on 13/10/15.
 */
public class RegisterEventFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static final String SUCCESSFULL_CADASTRATION_MESSAGE = "Evento cadastrado com sucesso :)";
    private String latitude;
    private String longitude;
    Vector<String> categories= new Vector<>();

    public RegisterEventFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_event, container, false);

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

        return view;
    }

    public void addEventCategories(View v){
        if(v.getId() == R.id.optionCinema){
            CheckBox cinemaCheckBox = (CheckBox) v;

            if(cinemaCheckBox.isChecked()) {
                categories.add(cinemaCheckBox.getText().toString());
            }else{
                categories.remove(cinemaCheckBox.getText().toString());
            }
        }else if(v.getId() == R.id.optionEducation) {
            CheckBox educationCheckBox = (CheckBox) v;

            if(educationCheckBox.isChecked()) {
                categories.add("Educacao");
            }else{
                categories.remove("Educacao");
            }
        }else if(v.getId() == R.id.optionExposition){
            CheckBox expositionCheckBox = (CheckBox) v;

            if(expositionCheckBox.isChecked()) {
                categories.add("Exposicao");
            }else{
                categories.remove("Exposicao");
            }
        }else if(v.getId() == R.id.optionMuseum){
            CheckBox museumCheckBox = (CheckBox) v;

            if(museumCheckBox.isChecked()) {
                categories.add(museumCheckBox.getText().toString());
            }else{
                categories.remove(museumCheckBox.getText().toString());
            }
        }else if(v.getId() == R.id.optionOthers){
            CheckBox othersCheckBox = (CheckBox) v;

            if(othersCheckBox.isChecked()) {
                categories.add(othersCheckBox.getText().toString());
            }else{
                categories.remove(othersCheckBox.getText().toString());
            }
        }else if(v.getId() == R.id.optionParty){
            CheckBox partyCheckBox = (CheckBox) v;

            if(partyCheckBox.isChecked()) {
                categories.add(partyCheckBox.getText().toString());
            }else{
                categories.remove(partyCheckBox.getText().toString());
            }
        }else if(v.getId() == R.id.optionShow){
            CheckBox showCheckBox = (CheckBox) v;

            if(showCheckBox.isChecked()) {
                categories.add(showCheckBox.getText().toString());
            }else{
                categories.remove(showCheckBox.getText().toString());
            }
        }else if(v.getId() == R.id.optionSports){
            CheckBox sportsCheckBox = (CheckBox) v;

            if(sportsCheckBox.isChecked()) {
                categories.add(sportsCheckBox.getText().toString());
            }else{
                categories.remove(sportsCheckBox.getText().toString());
            }
        }else if(v.getId() == R.id.optionTheater){
            CheckBox theaterCheckBox = (CheckBox) v;

            if(theaterCheckBox.isChecked()) {
                categories.add(theaterCheckBox.getText().toString());
            }else{
                categories.remove(theaterCheckBox.getText().toString());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveEvent){
            EditText nameEventField = (EditText) this.getActivity().findViewById(R.id.eventName);
            String nameEvent = nameEventField.getText().toString();

            EditText dateEventField = (EditText) this.getActivity().findViewById(R.id.eventDate);
            String dateEvent = dateEventField.getText().toString();

            EditText hourEventField = (EditText) this.getActivity().findViewById(R.id.eventHour);
            String eventHour = hourEventField.getText().toString();

            EditText descriptionEventField = (EditText) this.getActivity().findViewById(R.id.eventDescription);
            String descriptionEvent = descriptionEventField.getText().toString();

            EditText addressEventField = (EditText) this.getActivity().findViewById(R.id.eventAddress);
            String addressEvent = addressEventField.getText().toString();

            EditText priceEventRealField = (EditText) this.getActivity().findViewById(R.id.eventPriceReal);
            String priceEventReal = priceEventRealField.getText().toString();

            EditText priceEventDecimalField = (EditText) this.getActivity().findViewById(R.id.eventPriceDecimal);
            String priceEventDecimal = priceEventDecimalField.getText().toString();

            LoginUtility loginUtility = new LoginUtility(getActivity());
            int idOwner = loginUtility.getUserId();

            try {
                Event event = new Event(idOwner, nameEvent, dateEvent, eventHour, priceEventReal, priceEventDecimal, addressEvent, descriptionEvent,
                                        latitude, longitude, categories);
                registerEvent(event);

                Toast.makeText(getActivity().getBaseContext(), SUCCESSFULL_CADASTRATION_MESSAGE, Toast.LENGTH_LONG).show();
            } catch (EventException e) {
                String message = e.getMessage();

                //Verify address field
                if(message.equals(Event.ADDRESS_IS_EMPTY)){
                    addressEventField.requestFocus();
                    addressEventField.setError(message);
                }

                if(message.equals(Event.INVALID_EVENT_HOUR)){
                    hourEventField.requestFocus();
                    hourEventField.setError(message);
                }

                if(message.equals(Event.EVENT_HOUR_IS_EMPTY)){
                    hourEventField.requestFocus();
                    hourEventField.setError(message);
                }

                if(message.equals(Event.DESCRIPTION_CANT_BE_EMPTY)){
                    descriptionEventField.requestFocus();
                    descriptionEventField.setError(message);
                }

                if(message.equals(Event.DESCRIPTION_CANT_BE_GREATER_THAN)){
                    descriptionEventField.requestFocus();
                    descriptionEventField.setError(message);
                }

                if(message.equals(Event.EVENT_DATE_IS_EMPTY)){
                    dateEventField.requestFocus();
                    dateEventField.setError(message);
                }

                if(message.equals(Event.EVENT_NAME_CANT_BE_EMPTY_NAME)){
                    nameEventField.requestFocus();
                    nameEventField.setError(message);
                }

                if(message.equals(Event.INVALID_EVENT_DATE)){
                    dateEventField.requestFocus();
                    dateEventField.setError(message);
                }

                if(message.equals(Event.NAME_CANT_BE_GREATER_THAN_50)){
                    nameEventField.requestFocus();
                    nameEventField.setError(message);
                }

                if(message.equals(Event.PRICE_REAL_IS_EMPTY)){
                    priceEventRealField.requestFocus();
                    priceEventRealField.setError(message);
                }

                if(message.equals(Event.PRICE_DECIMAL_IS_EMPTY)){
                    priceEventDecimalField.requestFocus();
                    priceEventDecimalField.setError(message);
                }

            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
        else if(v.getId() == R.id.eventLocal){
            Intent map = new Intent(getActivity(), LocalEventActivity.class);
            startActivityForResult(map, 2);
        }else{
            addEventCategories(v);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    latitude = bundle.getString("latitude");
                    longitude = bundle.getString("longitude");

                    Toast.makeText(getContext(), "Local selecionado com sucesso", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void registerEvent(Event event){
        EventDAO eventDAO = new EventDAO(getActivity());
        eventDAO.saveEvent(event);
    }

    private void addCheckBoxListeners(View v){

        CheckBox showCategory = (CheckBox) v.findViewById(R.id.optionShow);
        showCategory.setOnClickListener(this);

        CheckBox expositionCategory = (CheckBox) v.findViewById(R.id.optionExposition);
        expositionCategory.setOnClickListener(this);

        CheckBox museumCategory = (CheckBox) v.findViewById(R.id.optionMuseum);
        museumCategory.setOnClickListener(this);

        CheckBox cinemaCategory = (CheckBox) v.findViewById(R.id.optionCinema);
        cinemaCategory.setOnClickListener(this);

        CheckBox theaterCategory = (CheckBox) v.findViewById(R.id.optionTheater);
        theaterCategory.setOnClickListener(this);

        CheckBox partyCategory = (CheckBox) v.findViewById(R.id.optionParty);
        partyCategory.setOnClickListener(this);

        CheckBox educationCategory = (CheckBox) v.findViewById(R.id.optionEducation);
        educationCategory.setOnClickListener(this);

        CheckBox sportsCategory = (CheckBox) v.findViewById(R.id.optionSports);
        sportsCategory.setOnClickListener(this);

        CheckBox othersCategory = (CheckBox) v.findViewById(R.id.optionOthers);
        othersCategory.setOnClickListener(this);

    }


}
