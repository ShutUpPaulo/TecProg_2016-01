package com.mathheals.euvou.controller.home_page;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.event_consultation.EventConsultation;
import com.mathheals.euvou.controller.event_registration.RegisterEventFragment;
import com.mathheals.euvou.controller.edit_user.EditUserFragment;
import com.mathheals.euvou.controller.login_user.LoginActivity;
import com.mathheals.euvou.controller.remove_user.RemoveUserFragment;
import com.mathheals.euvou.controller.search_event.ListEvents;
import com.mathheals.euvou.controller.search_place.SearchPlaceMaps;
import com.mathheals.euvou.controller.showPlaceRanking.ShowTop5Ranking;
import com.mathheals.euvou.controller.utility.ActivityUtility;
import com.mathheals.euvou.controller.utility.LoginUtility;

public class HomePage extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private static final String QUERY = "query";
    private CharSequence mTitle;
    private DrawerLayout drawerLayout;
    private LinearLayout linearLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private String[] textOptions;
    private ActionBar actionBar;
    public static final String OPTION = "option";
    private int USER_STATUS;
    private final int LOGGED_OUT = -1;

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment and
     * loads the XML layouts used in the activity
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_navigation_drawer);

        initViews();
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,
                textOptions));
        EditText placeSearch = (EditText) findViewById(R.id.place_search);

        callGoogleMaps();
        onConfigActionBar();

        ShowTop5Ranking showTop5Ranking = new ShowTop5Ranking();
        openFragment(showTop5Ranking);
    }

    /**
     * Opens a new fragment
     * @param fragmentToBeOpen - Fragment to be open
     */
    private void openFragment(Fragment fragmentToBeOpen){
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Searches a place based on the query typed by the user
     * @param view - View that contains the EditText with the filter typed by the user
     */
    public void searchPlace(View view){

        final String INVALID_SEARCH = "Pesquisa Invalida";

        String filter = ((EditText)findViewById(R.id.place_search)).getText().toString();
        Intent map = new Intent(HomePage.this, SearchPlaceMaps.class);
        if(filter.isEmpty()){
            Toast.makeText(this, INVALID_SEARCH, Toast.LENGTH_LONG).show();
        }
        else{
            map.putExtra(QUERY, filter);
            HomePage.this.startActivity(map);
            drawerLayout.closeDrawer(linearLayout);
        }
    }

    private void callGoogleMaps(){
        drawerList.setOnItemClickListener(this);
    }

    /**
     * Initialize the views of the fragment
     */
    private void initViews(){
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer_list);
        drawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                        R.string.drawer_open, R.string.drawer_close){
                    public void onDrawerClosed(View view){

                        supportInvalidateOptionsMenu();
                    }

                    public void onDrawerOpened(View drawerView){

                        supportInvalidateOptionsMenu();
                    }
                };

        textOptions = getResources().getStringArray(R.array.itens_menu_string);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        actionBar = getSupportActionBar();
    }

    /**
     * Sets acton bar configuration
     */
    private void onConfigActionBar(){

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0C3")));

    }

    /**
     * Create and configure the options of the menu at the action bar
     * @param menu - Menu of the action bar
     * @return boolean - True if the options of the menu was created with success
     */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        LoginUtility loginUtility = new LoginUtility(HomePage.this);
        // Inflating menu for logged users

        USER_STATUS = loginUtility.getUserId();

        if(USER_STATUS != LOGGED_OUT){
            inflater.inflate(R.menu.home_page_logged_in, menu);
        }
        // Inflating menu for not logged users
        else if(USER_STATUS == LOGGED_OUT){
            inflater.inflate(R.menu.home_page_logged_out, menu);
        }
        else{
            return false;
        }

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Called by the system when the device configuration changes while the activity is running
     * @param newConfig -  The new device configuration
     */
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Called when activity start-up is complete
     * @param savedInstanceState -  If the activity is being re-initialized after previously being
     *                              shut down then this Bundle contains the data it most recently
     *                              supplied
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    /**
     * Triggers the actions by click in an option at the action bar menu
     * @param item - The menu item that was selected
     * @return boolean - Return false to allow normal menu processing to proceed,
     *                   true to consume it here
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        // Handle your other action bar items...

        if(USER_STATUS != LOGGED_OUT){
            userLoggedInOptions(item);
        }
        else if(USER_STATUS == LOGGED_OUT){
            userLoggedOutOptions(item);
        }
        else{
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Opens the fragment according to the item clicked for user logged in menu
     * @param item - The menu item that was selected
     * @return boolean - True if the fragment was open with success
     */
    private boolean userLoggedInOptions(MenuItem item){

        switch(item.getItemId()){
            case R.id.edit_register:
                EditUserFragment editUserFragment = new EditUserFragment();
                openFragment(editUserFragment);
                return true;
            case R.id.settings:
                ActivityUtility.clearBackStack(this);
                RemoveUserFragment removeUserFragment = new RemoveUserFragment();
                openFragment(removeUserFragment);
                return true;
            case R.id.register_event:
                RegisterEventFragment registerEventFragment = new RegisterEventFragment();
                openFragment(registerEventFragment);
                return true;
            case R.id.logout:
                new LoginUtility(HomePage.this).setUserLogOff();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;
            case R.id.myEvents:
                try{
                    ListEvents listEvents = new ListEvents();
                    openFragment(listEvents);
                }
                catch (NullPointerException exception){
                    Toast.makeText(getBaseContext(),"Sem eventos criados",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Opens the fragment according to the item clicked for user logged out menu
     * @param item - The menu item that was selected
     * @return boolean - True if the fragment was open with success
     */
    private boolean userLoggedOutOptions(MenuItem item){
        switch (item.getItemId()){
            case R.id.registration:
                RegisterEventFragment registerEventFragment = new RegisterEventFragment();
                openFragment(registerEventFragment);
                return true;
            case R.id.log_in:
                Intent myIntent = new Intent(HomePage.this, LoginActivity.class);
                HomePage.this.startActivity(myIntent);
                return true;
            default:
                return false;
        }
    }

    /**
     * Shows message with success when data is updated
     * @param view - View that shows the message
     */
    public void editUserUpdateButtonOnClick(View view){
        final String SUCESS_EDIT_MESSAGE = "Dados alterados com sucesso :)";
        Toast.makeText(getBaseContext(), SUCESS_EDIT_MESSAGE, Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * Starts the EventConsultation activity by click at search button
     * @param item - Item that was clicked
     */
    public void searchOnclick(MenuItem item){
        Intent eventConsultation = new Intent(HomePage.this, EventConsultation.class);
        HomePage.this.startActivity(eventConsultation);
    }

    /**
     * Opens the activity that shows the places with the category clicked
     * @param parent - The AdapterView where the click happened
     * @param view - The view within the AdapterView that was clicked
     * @param position - The position of the view in the adapter
     * @param id - The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        String aux = "";
        switch (position){
            case 1:
                aux = "Museu";
                break;
            case 2:
                aux = "Parque";
                break;
            case 3:
                aux = "Teatro";
                break;
            case 4:
                aux = "shop";
                break;
            case 5:
                aux = "Unidade";
                break;

        }
        Intent map = new Intent(HomePage.this, SearchPlaceMaps.class);
        map.putExtra(QUERY, aux);
        HomePage.this.startActivity(map);
        drawerLayout.closeDrawer(linearLayout);
    }
}