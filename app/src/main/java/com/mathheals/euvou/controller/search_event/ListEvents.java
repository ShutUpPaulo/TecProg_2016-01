package com.mathheals.euvou.controller.search_event;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
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
import com.mathheals.euvou.controller.showPlaceRanking.ShowTop5Rank;
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
    private  Event clicado;

    public ListEvents() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_list_events, container, false);
        // Inflate the layout for this fragment
        listView = (ListView) vw.findViewById(R.id.eventList);
        listView.setOnItemClickListener(this);
        populaList();
        return vw;
    }

    private void populaList() {
        try {
            int id = (new LoginUtility(getActivity())).getUserId();
            events = new EventDAO(getActivity()).searchEventByOwner(id);
            if(events==null){
                Toast.makeText(getContext(), "Você ainda não criou nenhum evento, que tal criar um agora?", Toast.LENGTH_LONG).show();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new ShowTop5Rank());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else {
                List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();

                for (Event e : events)
                    eventList.add(createEvent("Nome", e.getNameEvent()));

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
    private HashMap<String, String> createEvent(String name, String number) {
        HashMap<String, String> eventName = new HashMap<String, String>();
        eventName.put(name, number);
        return eventName;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        clicado = events.get(position);
        EditOrRemoveFragment editOrRemoveFragment = new EditOrRemoveFragment();
        editOrRemoveFragment.evento = clicado;
        fragmentTransaction.replace(R.id.content_frame, editOrRemoveFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}