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
public class ListEvents extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Vector<Event> events;

    /**
     * Required constructor to instantiate a fragment object
     */
    public ListEvents() {

    }

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the ShowPlaceRank fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        View view = inflater.inflate(R.layout.fragment_list_events, container, false);
        // Inflate the layout for this fragment
        listView = (ListView) view.findViewById(R.id.eventList);
        listView.setOnItemClickListener(this);
        populateList();
        return view;
    }

    /**
     * Populates a ArrayList with the events created by the user logged in
     */
    private void populateList() {
        try {
            int idUserLoggedIn = (new LoginUtility(getActivity())).getUserId();
            events = new EventDAO(getActivity()).searchEventByOwner(idUserLoggedIn);

            if(events!=null){
                List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();

                for (Event event : events)
                    eventList.add(createEvent("Nome", event.getNameEvent()));

                SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), eventList,
                        android.R.layout.simple_list_item_1,
                        new String[]{"Nome"}, new int[]{android.R.id.text1});

                listView.setAdapter(simpleAdapter);
            }else{
                List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();

                for (Event event : events)
                    eventList.add(createEvent("Nome", event.getNameEvent()));

                SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), eventList,
                        android.R.layout.simple_list_item_1,
                        new String[]{"Nome"}, new int[]{android.R.id.text1});

                listView.setAdapter(simpleAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (EventException e) {
            e.printStackTrace();
        }catch( NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Sem eventos criados",Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Creates a event
     * @param name - Key value of the event which will be inserted on the map
     * @param number - Name of the event which will be inserted on the map
     * @return HashMap - HashMap where the event was stored
     */
    private HashMap<String, String> createEvent(String name, String number) {
        assert name != null;
        assert number != null;

        HashMap<String, String> eventName = new HashMap<String, String>();
        eventName.put(name, number);
        return eventName;
    }

    /**
     * Invoked when an item in the AdapterView was clicked
     * @param parent AdapterView where click happened
     * @param view View within the AdapterView that was clicked
     * @param positionEvent Position of view in the adapter
     * @param id Id of the clicked item
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int positionEvent, long id) {
        assert view != null;
        assert positionEvent >= 0;
        assert id >= 0;

        final android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Event eventClicked = events.get(positionEvent);
        EditOrRemoveFragment editOrRemoveFragment = new EditOrRemoveFragment();
        editOrRemoveFragment.evento = eventClicked;
        fragmentTransaction.replace(R.id.content_frame, editOrRemoveFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}