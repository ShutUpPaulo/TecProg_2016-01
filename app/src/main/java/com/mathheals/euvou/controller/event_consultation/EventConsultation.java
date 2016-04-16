package com.mathheals.euvou.controller.event_consultation;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.show_event.ShowEvent;
import com.mathheals.euvou.controller.user_profile.ShowUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dao.EventDAO;
import dao.UserDAO;

public class EventConsultation extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private ActionBar actionBar;
    private SearchView searchView;

    private ListView listView;
    private Integer idItem;
    private JSONObject eventDATA;
    private TextView event_not_found_text;

    private JSONObject peopleDATA;
    private static final String PEOPLE_NOT_FOUND_MESSAGE = "Nenhum usuário foi encontrado :(";

    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_consultation);
        listView = (ListView) findViewById(R.id.events_list);
        event_not_found_text = (TextView) findViewById(R.id.event_not_found_text);
        setListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_consultation, menu);
        actionBar = getSupportActionBar();

        setSearchBar(menu);
        configActionBar();

        radioGroup = (RadioGroup) findViewById(R.id.search_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        return true;
    }

    private void setSearchBar(Menu menu) {
        final String SEARCH_VIEW_HINT = "Pesquisar";

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(SEARCH_VIEW_HINT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                int checkedButton = radioGroup.getCheckedRadioButtonId();
                switch (checkedButton) {
                    case R.id.radio_events:
                        //Toast.makeText(getBaseContext(), "EVENTOS: " + query, Toast.LENGTH_LONG).show();
                        option="event";
                        EventDAO eventDAO = new EventDAO(getParent());

                        ArrayList<String> eventsFound = new ArrayList<String>();
                        eventDATA = eventDAO.searchEventByNameGroup(query);
                        final String EVENT_COLUMN = "nameEvent";

                        if (eventDATA != null) {
                            event_not_found_text.setVisibility(View.GONE);
                            try {
                                for (int i = 0; i < eventDATA.length(); ++i) {
                                    eventsFound.add(eventDATA.getJSONObject(new Integer(i).toString()).getString(EVENT_COLUMN));
                                }

                                String[] eventsFoundArray = eventsFound.toArray(new String[eventsFound.size()]);
                                showEventsAsList(eventsFoundArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            listView.setAdapter(null);
                            event_not_found_text.setVisibility(View.VISIBLE);
                        }
                        break;

                    case R.id.radio_people:
                        option="people";
                        UserDAO userDAO = new UserDAO(getParent());

                        ArrayList<String> peopleFound = new ArrayList<String>();
                        peopleDATA = userDAO.searchUserByName(query);
                        final String NAME_USER_COLUMN = "nameUser";

                        if (peopleDATA != null) {
                            event_not_found_text.setVisibility(View.GONE);
                            try {
                                for (int i = 0; i < peopleDATA.length(); i++) {
                                    peopleFound.add(peopleDATA.getJSONObject(new Integer(i).toString()).getString(NAME_USER_COLUMN));
                                }

                                String[] peopleFoundArray = peopleFound.toArray(new String[peopleFound.size()]);
                                showPeopleAsList(peopleFoundArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            listView.setAdapter(null);
                            event_not_found_text.setText(PEOPLE_NOT_FOUND_MESSAGE);
                            event_not_found_text.setVisibility(View.VISIBLE);
                        }

                        break;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void showEventsAsList(String[] eventNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventConsultation.this,
                R.layout.event_consultation_list_view,
                eventNames);
        listView.setAdapter(adapter);

    }

    private void showPeopleAsList(String[] peopleNames){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventConsultation.this,
                R.layout.event_consultation_list_view,
                peopleNames);
        listView.setAdapter(adapter);
    }

    private void setListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final Bundle bundle = new Bundle();
            final ShowEvent event = new ShowEvent();
            final ShowUser user = new ShowUser();

            public void onItemClick(AdapterView<?> parent, View clickView,
                                    int position, long id) {
                //final String ID_COLUMN = "idEvent";
                final String ID_COLUMN = option=="event" ? "idEvent" : (option=="people" ? "idUser" : "idPlace");

                try {
                    final android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    idItem = new Integer((option=="event" ? eventDATA : peopleDATA).getJSONObject(Integer.toString(position)).getString(ID_COLUMN));
                    bundle.putString("id", Integer.toString(idItem));

                    event.setArguments(bundle);
                    user.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, option == "event" ? event : user);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void configActionBar() {
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0C3")));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String query = searchView.getQuery().toString();
        switch(checkedId) {
            case R.id.radio_events:
                break;
            case R.id.radio_people:
                break;
        }
    }
}