package com.mathheals.euvou.controller.edit_event;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.event_registration.LocalEventActivity;
import com.mathheals.euvou.controller.showPlaceRanking.ShowTop5Rank;
import com.mathheals.euvou.controller.utility.EditAndRegisterUtility;
import com.mathheals.euvou.controller.utility.Mask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Vector;

import dao.CategoryDAO;
import dao.EventCategoryDAO;
import dao.EventDAO;
import exception.EventException;
import model.Event;

public class EditEventFragment extends Fragment implements View.OnClickListener {
    private int idEvent;
    private static final String SUCCESSFULL_UPDATE_MESSAGE = "Evento alterado com sucesso :)";
    private String latitude;
    private String longitude;
    private EditText nameField, dateField, hourField, descriptionField, addressField, priceDecimalField, priceRealField;
    private CheckBox showCheckBox, expositionCheckBox, cinemaCheckBox, museumCheckBox, theaterCheckBox, educationCheckBox,
                     othersCheckBox,sportsCheckBox, partyCheckBox;
    Vector<String> categories= new Vector<>();
    private EditAndRegisterUtility  editAndRegisterUtility = new EditAndRegisterUtility();


    public EditEventFragment() {
        // Required empty public constructor
    }

    public void formatDate(JSONObject jsonEvent) throws JSONException {

        String dateHourEvent = jsonEvent.getJSONObject("0").getString("dateTimeEvent");
        String[] dateHourEventSplit = dateHourEvent.split(" ");

        String dateEvent = dateHourEventSplit[0];
        String[] dateEventSplit = dateEvent.split("-");
        dateEvent = dateEventSplit[2]+"/"+dateEventSplit[1]+"/"+dateEventSplit[0];

        String hourEvent = dateHourEventSplit[1];

        this.dateField.setText(dateEvent);
        this.hourField.setText(hourEvent);

    }

    public void formatPrice(JSONObject jsonEvent) throws JSONException {
        Integer priceEvent = jsonEvent.getJSONObject("0").getInt("price");
        this.priceRealField.setText(Integer.toString(priceEvent/100));
        this.priceDecimalField.setText(Integer.toString(priceEvent - priceEvent / 100 * 100));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        idEvent = this.getArguments().getInt("idEvent");

        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);

        setingEditText(view);
        dateField.addTextChangedListener(Mask.insert("##/##/####", dateField));
        setingCheckBoxs(view);

        EventDAO eventDAO = new EventDAO(getActivity());
        EventCategoryDAO eventCategoryDAO = new EventCategoryDAO(getActivity());
        CategoryDAO categoryDAO = new CategoryDAO(getActivity());

        //Change the value of idEvent when the consultEvent was finished
        JSONObject jsonEvent = eventDAO.searchEventById(idEvent);
        JSONObject jsonEventCategory = eventCategoryDAO.searchCategoriesByEventId(idEvent);

        try {
            String nameEvent = jsonEvent.getJSONObject("0").getString("nameEvent");
            nameField.setText(nameEvent);

            String descriptionEvent = jsonEvent.getJSONObject("0").getString("description");
            descriptionField.setText(descriptionEvent);

            String addressEvent = jsonEvent.getJSONObject("0").getString("address");
            addressField.setText(addressEvent);

            formatDate(jsonEvent);
            formatPrice(jsonEvent);

            latitude = jsonEvent.getJSONObject("0").getString("latitude");
            longitude = jsonEvent.getJSONObject("0").getString("longitude");

            Vector <Integer> idCategories = new Vector<>();
            String idCategory;
            for(int i=0; i<jsonEventCategory.length(); i++){
                idCategory=jsonEventCategory.getJSONObject(Integer.toString(i)).getString("idCategory");
                idCategories.add(Integer.parseInt(idCategory));
            }

            for(int i=0; i<idCategories.size(); i++){
                JSONObject jsonCategory = categoryDAO.searchCategoryById(idCategories.get(i));
                String nameCategory = jsonCategory.getJSONObject("0").getString("nameCategory");

                switch (nameCategory){
                    case "Show":
                        showCheckBox.setChecked(true);
                        categories.add("Show");
                        break;
                    case "Teatro":
                        theaterCheckBox.setChecked(true);
                        categories.add("Teatro");
                        break;
                    case "Cinema":
                        cinemaCheckBox.setChecked(true);
                        categories.add("Cinema");
                        break;
                    case "Balada":
                        partyCheckBox.setChecked(true);
                        categories.add("Balada");
                        break;
                    case "Museu":
                        museumCheckBox.setChecked(true);
                        categories.add("Museu");
                        break;
                    case "Educacao":
                        educationCheckBox.setChecked(true);
                        categories.add("Educacao");
                        break;
                    case "Exposicao":
                        expositionCheckBox.setChecked(true);
                        categories.add("Exposicao");
                        break;
                    case "Esporte":
                        sportsCheckBox.setChecked(true);
                        categories.add("Esporte");
                        break;
                    case "Outros":
                        othersCheckBox.setChecked(true);
                        break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Adding listener to eventLocal EditText
        Button eventLocal = (Button) view.findViewById(R.id.eventLocal);
        eventLocal.setOnClickListener(this);

        //Adding listener to CheckBoxs to verify if each CheckBox is checked or not
        addCheckBoxListeners(view);

        Button removeEvent = (Button)view.findViewById(R.id.removeEvent);
        removeEvent.setOnClickListener(this);

        Button updateEvent = (Button)view.findViewById(R.id.updateEvent);
        updateEvent.setOnClickListener(this);

        return view;
    }

    private void setingEditText(View view){
        this.nameField = (EditText) view.findViewById(R.id.eventName);
        this.dateField = (EditText) view.findViewById(R.id.eventDate);
        this.hourField = (EditText) view.findViewById(R.id.eventHour);
        this.descriptionField = (EditText) view.findViewById(R.id.eventDescription);
        this.priceRealField = (EditText) view.findViewById(R.id.eventPriceReal);
        this.priceDecimalField = (EditText) view.findViewById(R.id.eventPriceDecimal);
        this.addressField = (EditText) view.findViewById(R.id.eventAddress);
    }

    private void setingCheckBoxs(View view){
        this.showCheckBox = (CheckBox) view.findViewById(R.id.optionShow);
        this.expositionCheckBox = (CheckBox) view.findViewById(R.id.optionExposition);
        this.cinemaCheckBox = (CheckBox) view.findViewById(R.id.optionCinema);
        this.theaterCheckBox = (CheckBox) view.findViewById(R.id.optionTheater);
        this.partyCheckBox = (CheckBox) view.findViewById(R.id.optionParty);
        this.educationCheckBox = (CheckBox) view.findViewById(R.id.optionEducation);
        this.museumCheckBox = (CheckBox) view.findViewById(R.id.optionMuseum);
        this.sportsCheckBox = (CheckBox) view.findViewById(R.id.optionSports);
        this.othersCheckBox = (CheckBox) view.findViewById(R.id.optionOthers);
    }

    private void updateEventOnDataBase(Event event){
        EventDAO eventDAO = new EventDAO(getActivity());
        eventDAO.updateEvent(event);
    }

    public void updateEvent(){

        String nameEvent = nameField.getText().toString();
        String dateEvent = dateField.getText().toString();

        String[] dateEventSplit = dateEvent.split("/");
        dateEvent = dateEventSplit[2]+"-"+dateEventSplit[1]+"-"+dateEventSplit[0];

        String hourEvent = hourField.getText().toString();

        String dateHourEvent = dateEvent + " " + hourEvent;

        String descriptionEvent = descriptionField.getText().toString();

        String addresEvent = addressField.getText().toString();

        Integer eventPriceReal = Integer.parseInt(priceRealField.getText().toString());
        Integer eventPriceDecimal = Integer.parseInt(priceDecimalField.getText().toString());
        Integer priceEvent = eventPriceReal * 100 + eventPriceDecimal;

        try {
            Event event = new Event(idEvent, nameEvent, priceEvent, addresEvent, dateHourEvent, descriptionEvent,
                    latitude, longitude, categories);

            updateEventOnDataBase(event);

            Toast.makeText(getActivity().getBaseContext(), SUCCESSFULL_UPDATE_MESSAGE, Toast.LENGTH_LONG).show();
        } catch (EventException e) {
            String message = e.getMessage();

            //Verify address field
            if(message.equals(Event.ADDRESS_IS_EMPTY)){
                editAndRegisterUtility.setMessageError(addressField, message);
            }
            if(message.equals(Event.DESCRIPTION_CANT_BE_EMPTY)){
                editAndRegisterUtility.setMessageError(descriptionField, message);
            }
            if(message.equals(Event.DESCRIPTION_CANT_BE_GREATER_THAN)){
                editAndRegisterUtility.setMessageError(descriptionField, message);
            }
            if(message.equals(Event.EVENT_DATE_IS_EMPTY)){
                editAndRegisterUtility.setMessageError(dateField, message);
            }
            if(message.equals(Event.EVENT_NAME_CANT_BE_EMPTY_NAME)){
                editAndRegisterUtility.setMessageError(nameField, message);
            }
            if(message.equals(Event.INVALID_EVENT_DATE)){
                editAndRegisterUtility.setMessageError(dateField, message);
            }
            if(message.equals(Event.NAME_CANT_BE_GREATER_THAN_50)){
                editAndRegisterUtility.setMessageError(nameField, message);
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
    }

    private void addEventCategories(View v){
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
        if(v.getId() == R.id.updateEvent) {
            updateEvent();
        }
        else if(v.getId() == R.id.removeEvent){
            removeEvent(idEvent);
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

    private void removeEvent(int eventId)
    {
        EventDAO eventDAO = new EventDAO(getActivity());
        if(eventDAO.deleteEvent(eventId).contains("Salvo")) {
            Toast.makeText(getActivity(), "Deletado com sucesso", Toast.LENGTH_LONG).show();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new ShowTop5Rank());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else
            Toast.makeText(getActivity(),"Houve um erro",Toast.LENGTH_LONG).show();
    }
}