/*
 *File: EventConsultation.java
 * Purpose: allows to search details of events
 */
package com.mathheals.euvou.controller.event_consultation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.show_event.ShowEvent;
import com.mathheals.euvou.controller.user_profile.ShowUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import dao.EventDAO;
import dao.UserDAO;

public class EventConsultation extends AppCompatActivity implements OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private ActionBar actionBar;
    private SearchView searchView;

    private ListView listView;
    private Integer idItem;
    private JSONObject eventDATA;
    private TextView event_not_found_text;

    private JSONObject peopleDATA;
    private static final String PEOPLE_NOT_FOUND_MESSAGE = "Nenhum usuário foi encontrado :(";

    private String option;

    private GoogleApiClient client;


    /**
     * Creates the data that should be printed in the screem.
     * @param savedInstanceState - Saved instance state from the last time the app was executed.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        assert savedInstanceState != null;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_consultation);
        listView = (ListView) findViewById(R.id.events_list);
        assert listView != null;
        assert listView.getId() == R.id.events_list;

        event_not_found_text = (TextView) findViewById(R.id.event_not_found_text);
        assert event_not_found_text != null;
        assert event_not_found_text.getId() == R.id.event_not_found_text;
        setListViewListener();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Creates the options menu and calls the method that set the search bar in the menu.
     * @param menu - menu of the application.
     * @return boolean  - ensure that the options menu will be created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        assert menu != null;

        getMenuInflater().inflate(R.menu.menu_event_consultation, menu);
        actionBar = getSupportActionBar();
        assert getMenuInflater() != null;

        setSearchBar(menu);
        configActionBar();

        radioGroup = (RadioGroup) findViewById(R.id.search_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        return true;
    }

    /**
     * Initiates the search bar, receiving the data to be find and making the search.
     * @param menu - menu of the application
     * MUST BE REFACTORED!
     */
    private void setSearchBar(Menu menu){
        assert menu != null;

        final String SEARCH_VIEW_HINT = "Pesquisar";

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(SEARCH_VIEW_HINT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            /**
             * Analyze the option selected by the user, to see if heś looking for event or person.
             * @param query
             * @return boolean - must show the results for the search - MUST BE REFACTORED
             */
            @Override
            public boolean onQueryTextSubmit(String query){
                int checkedButton = radioGroup.getCheckedRadioButtonId();
                switch(checkedButton){
                    case R.id.radio_events:
                        option = "event";
                        EventDAO eventDAO = new EventDAO(getParent());

                        ArrayList<String> eventsFound = new ArrayList<String>();
                        eventDATA = eventDAO.searchEventByNameGroup(query);
                        final String EVENT_COLUMN = "nameEvent";

                        if(eventDATA != null){
                            event_not_found_text.setVisibility(View.GONE);
                            try{
                                for(int i = 0; i < eventDATA.length(); ++i){
                                    eventsFound.add(eventDATA.getJSONObject(
                                            Integer.toString(i)).getString(EVENT_COLUMN));
                                }

                                String[] eventsFoundArray = eventsFound.toArray(
                                        new String[eventsFound.size()]);
                                showEventsAsList(eventsFoundArray);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        } else{
                            listView.setAdapter(null);
                            event_not_found_text.setVisibility(View.VISIBLE);
                        }
                        break;

                    case R.id.radio_people:
                        option = "people";
                        UserDAO userDAO = new UserDAO(getParent());

                        ArrayList<String> peopleFound = new ArrayList<String>();
                        peopleDATA = userDAO.searchUserByName(query);
                        final String NAME_USER_COLUMN = "nameUser";

                        if (peopleDATA != null){
                            event_not_found_text.setVisibility(View.GONE);
                            try {
                                for (int i = 0; i < peopleDATA.length(); i++){
                                    peopleFound.add(peopleDATA.getJSONObject(
                                            Integer.toString(i)).getString(NAME_USER_COLUMN));
                                }

                                String[] peopleFoundArray = peopleFound.toArray(
                                        new String[peopleFound.size()]);
                                showPeopleAsList(peopleFoundArray);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        } else{
                            listView.setAdapter(null);
                            event_not_found_text.setText(PEOPLE_NOT_FOUND_MESSAGE);
                            event_not_found_text.setVisibility(View.VISIBLE);
                        }

                        break;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                return true;
            }
        });
    }

    /**
     * Makes a list with the results to the search for events
     * @param eventNames - the names of the events found in database
     */
    private void showEventsAsList(String[] eventNames){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventConsultation.this,
                R.layout.event_consultation_list_view,
                eventNames);
        listView.setAdapter(adapter);

    }

    /**
     * Makes a list with the results to the search for people
     * @param peopleNames - the names of the people found in database
     */
    private void showPeopleAsList(String[] peopleNames){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventConsultation.this,
                R.layout.event_consultation_list_view,
                peopleNames);
        listView.setAdapter(adapter);
    }

    /**
     * Starts a listener to see where the user will click in the list of results to the search made.
     */
    private void setListViewListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            final Bundle bundle = new Bundle();
            final ShowEvent event = new ShowEvent();
            final ShowUser user = new ShowUser();

            /**
             * analyze the choosen item by the user and show details of the item searched.
             * @param parent
             * @param clickView
             * @param position - position of the item in the list of results
             * @param id - id of the choosen item
             */
            public void onItemClick(AdapterView<?> parent, View clickView,
                                    int position, long id){
                final String ID_COLUMN = Objects.equals(option, "event") ? "idEvent" :
                        (Objects.equals(option, "people") ? "idUser" : "idPlace");

                try{
                    final FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                            .beginTransaction();
                    idItem = Integer.valueOf((Objects.equals(option, "event") ?
                            eventDATA : peopleDATA).getJSONObject(Integer.toString(position))
                            .getString(ID_COLUMN));
                    bundle.putString("id", Integer.toString(idItem));

                    event.setArguments(bundle);
                    user.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content,
                            Objects.equals(option, "event") ? event : user);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Configures the action bar to be drawable and show the option Home enabled
     */
    private void configActionBar(){
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0C3")));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Send the application to the home page, if the home option is choosen in the option menu.
     * @param item - item selected by the user
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sees if the user has selected the radio button for events or people
     * @param group
     * @param checkedId - Id of the choosen item
     */
    public void onCheckedChanged(RadioGroup group, int checkedId){
        String query = searchView.getQuery().toString();
        switch (checkedId){
            case R.id.radio_events:
                break;
            case R.id.radio_people:
                break;
        }
    }
}