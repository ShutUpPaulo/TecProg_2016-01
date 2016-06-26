/**
 * File: RecommendEvent.java
 * Purpose: Show recommended events to the user
 */

package com.mathheals.euvou.controller.event_recommendation;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.show_event.ShowEvent;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import dao.EventRecommendationDAO;
import exception.EventException;
import model.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendEvent extends android.support.v4.app.Fragment
        implements AdapterView.OnItemClickListener{

    private static final int USER_NOT_LOGGED_IN = -1;
    private static final String ID_EVENT = "idEvent";
    private static final String NAME_EVENT = "nameEvent";
    private static final String ID_EVENT_SEARCH = "idEventSearch";
    private final String NO_RECOMMENDED_EVENTS =
            getResources().getString(R.string.no_recommended_events);

    private ListView listViewEventRecommendations;
    private JSONObject eventDATA;
    private int idUser;

    /**
     * Empty constructor required in RecommendEvent tests
     */
    public RecommendEvent(){
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater Object used to inflate any views in the fragment
     * @param container If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a
     *                             previous saved state as given here
     * @return View of the Recommended Event
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        assert inflater != null;
        assert container != null;

        View view = inflater.inflate(R.layout.fragment_recommend_event, container, false);

        this.listViewEventRecommendations =
                (ListView) view.findViewById(R.id.list_view_event_recomendations);
        assert this.listViewEventRecommendations != null;

        this.listViewEventRecommendations.setOnItemClickListener(this);

        LoginUtility loginUtility = new LoginUtility(getActivity());
        this.idUser = loginUtility.getUserId();

        if(this.idUser != USER_NOT_LOGGED_IN){
            fillEventsList();
        }else{
            Toast.makeText(getActivity().getBaseContext(),
                    NO_RECOMMENDED_EVENTS, Toast.LENGTH_LONG).show();
        }
        return view;
    }

    /**
     * Fill list of recommended events
     */
    private void fillEventsList(){
        EventRecommendationDAO eventRecommendationDAO = new EventRecommendationDAO();

        ArrayList<Event> events = new ArrayList<>();

        try{
            this.eventDATA = eventRecommendationDAO.recommendEvents(idUser);

            for(int i = 0; i < this.eventDATA.length(); i++){
                    final int idEvent = this.eventDATA.
                            getJSONObject(Integer.toString(i)).getInt(ID_EVENT);
                    assert this.eventDATA != null;

                    String nameEvent = this.eventDATA.getJSONObject(Integer.toString(i))
                            .getString(NAME_EVENT);
                    assert nameEvent != null;

                    final int eventEvaluation = 4;

                    Event event = new Event(idEvent, nameEvent, eventEvaluation);

                    events.add(event);
            }
        }catch(JSONException | ParseException | EventException e){
            e.printStackTrace();
        } catch(NullPointerException e){
            Toast.makeText(getActivity().getBaseContext(),
                    NO_RECOMMENDED_EVENTS, Toast.LENGTH_LONG).show();
        }

        EventAdapter eventAdapter = new EventAdapter(getActivity(),events);

        this.listViewEventRecommendations.setAdapter(eventAdapter);
    }

    /**
     * Invoked when an item in the AdapterView was clicked
     * @param parent AdapterView where click happened
     * @param view View within the AdapterView that was clicked
     * @param position Position of view in the adapter
     * @param id Id of the clicked item
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        assert parent != null;
        assert view != null;
        assert id >= 0;

        int eventId = 0;
        final Bundle bundle = new Bundle();
        final ShowEvent event = new ShowEvent();

        try{
            final android.support.v4.app.FragmentTransaction fragmentTransaction
                    = getActivity().getSupportFragmentManager().beginTransaction();

            eventId = new Integer(eventDATA.getJSONObject
                    (Integer.toString(position)).getString(ID_EVENT));

            bundle.putString(ID_EVENT_SEARCH, Integer.toString(eventId));

            event.setArguments(bundle);
            //fragmentTransaction.replace(R.id.content_frame, event);
            fragmentTransaction.add(R.id.content_frame, event);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
