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
public class RecommendEvent extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    ArrayList<Event> events;
    private JSONObject eventDATA;
    private int idUser;


    public RecommendEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_recommend_event, container, false);
        // Inflate the layout for this fragment
        listView = (ListView) vw.findViewById(R.id.list_view_event_recomendations);
        listView.setOnItemClickListener(this);

        LoginUtility loginUtility = new LoginUtility(getActivity());
        idUser = loginUtility.getUserId();

        if(idUser == -1){
            Toast.makeText(getActivity().getBaseContext(), "Sem eventos recomendados!", Toast.LENGTH_LONG).show();
        }else {
            fillList();
        }
        return  vw;
    }

    private void fillList() {
        EventRecommendationDAO eventRecommendationDAO = new EventRecommendationDAO();

        events = new ArrayList<>();

        try{
            eventDATA = eventRecommendationDAO.recommendEvents(idUser);

            for(int i=0; i<eventDATA.length(); i++){
                    int idEvent = eventDATA.getJSONObject(Integer.toString(i)).getInt("idEvent");
                    String nameEvent = eventDATA.getJSONObject(Integer.toString(i)).getString("nameEvent");
                    int eventEvaluation = 4;

                    Event event = new Event(idEvent, nameEvent, eventEvaluation);

                    events.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (EventException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Toast.makeText(getActivity().getBaseContext(), "Sem eventos recomendados!", Toast.LENGTH_LONG).show();
        }

        EventAdapter eventAdapter = new EventAdapter(getActivity(),events);

        listView.setAdapter(eventAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String ID_COLUMN = "idEvent";

        int eventId;
        final Bundle bundle = new Bundle();
        final ShowEvent event = new ShowEvent();

        try {
            final android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            eventId = new Integer(eventDATA.getJSONObject(Integer.toString(position)).getString(ID_COLUMN));
            bundle.putString("idEventSearch", Integer.toString(eventId));

            event.setArguments(bundle);
            //fragmentTransaction.replace(R.id.content_frame, event);
            fragmentTransaction.add(R.id.content_frame, event);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
