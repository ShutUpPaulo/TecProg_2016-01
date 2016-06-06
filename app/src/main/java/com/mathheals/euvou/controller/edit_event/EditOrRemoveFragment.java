/*
 * File: EditOrRemoveFragment.java
 * Purpose: Creates the view, and allows to update de Fragment or remove it.
 */
package com.mathheals.euvou.controller.edit_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.show_event.ShowEvent;
import com.mathheals.euvou.controller.utility.Mask;

import model.Event;

public class EditOrRemoveFragment extends android.support.v4.app.Fragment  implements View.OnClickListener{

    /**
     * Required constructor to instantiate the class
     */
    public EditOrRemoveFragment(){
        // Required empty public constructor
    }

    public Event evento;
    private ShowEvent showEvent = new ShowEvent();

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.edit_or_remove_event_fragment, container, false);

        Button editOrRemoveButton = (Button) view.findViewById(R.id.editRemoveButton);
        editOrRemoveButton.setOnClickListener(this);

        getEventInfo(view);

        return view;
    }

    /**
     * Sets the information of an event on the Text View.
     * @param view - View of the fragment
     * @param eventName - The name of the event to be showed
     * @param eventDateTime - Date and time of the event to be showed
     * @param eventDescription - Description of the event to be showed
     * @param eventAddress - Address of the event to be showed
     * @param eventPrice - Price of the event to be showed
     */
    public void setEventInfoOnTextView(View view, String eventName, String eventDateTime,
                                       String eventDescription, String eventAddress,
                                       Integer eventPrice){

        TextView nameOfEvent = (TextView) view.findViewById(R.id.nameEventShow);
        TextView dateEvent = (TextView) view.findViewById(R.id.dateEvent);
        TextView descriptionOfEvent = (TextView) view.findViewById(R.id.descriptionEvent);
        TextView eventAddressTextView = (TextView) view.findViewById(R.id.eventPlaces);
        TextView eventPriceText = (TextView) view.findViewById(R.id.eventPrice);
        TextView eventCategoriesText = (TextView) view.findViewById(R.id.eventCategories);

        nameOfEvent.setText(eventName);
        descriptionOfEvent.setText(eventDescription);
        dateEvent.setText(Mask.getDateTimeInBrazilianFormat(eventDateTime));
        eventAddressTextView.setText(eventAddress);

        showEvent.setPriceText(eventPriceText, eventPrice+"");
        showEvent.setCategoriesText(Integer.valueOf(evento.getIdEvent()), eventCategoriesText);
    }

    /**
     * Gets the information of an event
     * @param view - View of the fragment
     */
    public void getEventInfo(View view){

        String eventName = evento.getNameEvent();
        String eventDescription = evento.getDescription();
        String eventDateTime = evento.getDateTimeEvent();
        Integer eventPrice = evento.getPrice();
        String eventAddress = evento.getAddress();

        //Calls the method to show the information obtained above
        setEventInfoOnTextView(view, eventName, eventDateTime, eventDescription, eventAddress,
                eventPrice);
    }

    /**
     * Updates the Event data when the user clicks on the update button
     * @param v Current view being used in the fragment
     */
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.editRemoveButton){
            EditEventFragment editEventFragment = new EditEventFragment();
            Bundle bundle = new Bundle();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().
                    getSupportFragmentManager().beginTransaction();
            bundle.putInt("idEvent", evento.getIdEvent());
            editEventFragment.setArguments(bundle);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.content_frame, editEventFragment);

            fragmentTransaction.commit();
        }
    }
}