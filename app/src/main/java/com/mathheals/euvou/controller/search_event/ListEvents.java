/**
 * File: DisableAccountFragment.java
 * Purpose: List the events created by the user logged in
 */

package com.mathheals.euvou.controller.search_event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.edit_event.EditOrRemoveFragment;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import dao.EventDAO;
import exception.EventException;
import model.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListEvents extends android.support.v4.app.Fragment
        implements AdapterView.OnItemClickListener {
    private Vector<Event> events;

    /**
     * Required constructor to instantiate a fragment object
     */
    public ListEvents(){

    }

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

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
        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        View view = inflater.inflate(R.layout.fragment_list_events, container, false);

        setListView(view);

        return view;
    }

    /**
     * Set the listView on view
     * @param view - Current view
     */
    private void setListView(View view){
        assert view != null;

        ListView listView = (ListView) view.findViewById(R.id.eventList);
        listView.setOnItemClickListener(this);

        populateList(listView);
    }

    /**
     * Populates a ArrayList with the events created by the user logged in
     * @param listView - List of scrollable items
     */
    private void populateList(ListView listView){
        assert listView != null;

        try{
            setListViewAdapter(listView);
        }catch (JSONException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }catch (EventException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();

            final String NO_EVENT_CREATED = "Sem eventos criados";
            Toast.makeText(getContext(), NO_EVENT_CREATED,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set the Adapter of the the listView
     * @param listView - List of scrollable items
     * @throws JSONException
     * @throws ParseException
     * @throws EventException
     * @throws NullPointerException
     */
    private void setListViewAdapter(ListView listView) throws JSONException, ParseException,
            EventException, NullPointerException{
        assert listView != null;

        int idUserLoggedIn = (new LoginUtility(getActivity())).getUserId();
        List < Map<String, String> > eventList = new ArrayList<>();
        events = new EventDAO(getActivity()).searchEventByOwner(idUserLoggedIn);

        final String NAME = "Nome";

        for(Event event : events){
            eventList.add(createEvent(NAME , event.getNameEvent()));
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), eventList,
                android.R.layout.simple_list_item_1,
                new String[]{NAME}, new int[]{android.R.id.text1});

        listView.setAdapter(simpleAdapter);
    }

    /**
     * Creates a event
     * @param name - Key value of the event which will be inserted on the map
     * @param number - Name of the event which will be inserted on the map
     * @return HashMap - HashMap where the event was stored
     */
    private HashMap<String, String> createEvent(String name, String number){
        assert name != null;
        assert number != null;

        HashMap<String, String> eventName = new HashMap<String, String>();
        eventName.put(name, number);
        return eventName;
    }

    /**
     * Invoked when an item in the AdapterView was clicked
     * @param parent - AdapterView where click happened
     * @param view - View within the AdapterView that was clicked
     * @param positionEvent - Position of view in the adapter
     * @param id - Id of the clicked item
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int positionEvent, long id){
        assert view != null;
        assert positionEvent >= 0;
        assert id >= 0;

        final android.support.v4.app.FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();

        EditOrRemoveFragment editOrRemoveFragment = new EditOrRemoveFragment();
        Event eventClicked = events.get(positionEvent);
        editOrRemoveFragment.evento = eventClicked;

        fragmentTransaction.replace(R.id.content_frame, editOrRemoveFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}