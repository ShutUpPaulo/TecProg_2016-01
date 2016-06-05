/**
 * File: LoginActivity.java
 * Purpose: Activity responsible for doing the login of the user
 */

package com.mathheals.euvou.controller.login_user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActionBar actionBar;

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment and
     * loads the XML layouts used in the activity
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        assert savedInstanceState != null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        onConfigActionBar();
    }

    /**
     * Initializes the ActionBar and the doLogin button used in the activity
     */
    private void initViews(){
        actionBar = getSupportActionBar();

        Button doLogin = (Button) findViewById(R.id.doLogin);
        doLogin.setOnClickListener(this);
    }


    /**
     * Initializes the contents of the Activity's standard options menu
     * @param menu - The options menu in which the items will be placed
     * @return boolean - You must return true for the menu to be displayed;
     * if you return false it will not be shown
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        assert menu != null;

        getMenuInflater().inflate(R.menu.menu_login, menu);

        return true;
    }

    /**
     * This method is called whenever an item in the options menu is selected
     * @param menuItem - The menu item that was selected
     * @return boolean - Return false to allow normal menu processing to proceed, true to consume
     * it here
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        assert menuItem != null;

        int itemId = menuItem.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemId == R.id.action_settings){
            return true;
        }else{
            //Nothing to do
        }

        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * Set resources of the Action Bar
     */
    private void onConfigActionBar(){
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);

        final String CYAN_HEX_COLOR = "#008B8B";
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(CYAN_HEX_COLOR)));
    }

    /**
     * Calls the function that validates the login when the login button has been clicked
     * @param view - The view of the button that was clicked
     */
    @Override
    public void onClick(View view){
        assert view != null;

        validateLogin();
    }

    /**
     * Verifies if username and password are valid and calls the function that does the login if so
     */
    private void validateLogin(){
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        String typedUsername = usernameField.getText().toString();

        LoginValidation loginValidation = new LoginValidation(LoginActivity.this);

        boolean isUsernameOk = verifyUsername(loginValidation);
        boolean isPasswordOk = false;

        if(isUsernameOk == true){
            isPasswordOk = verifyPassword(loginValidation, typedUsername);
        }else{
            //Nothing to do
        }

        if(isPasswordOk == true){
            doLogin(typedUsername);
        }else{
            //Nothing to do
        }
    }

    /**
     * Verifies if username exists on the database and it's not deactivated
     * @param loginValidation - Instance of the class that does the login validations
     * @return boolean - True if the username is ok, or false otherwise
     */
    private boolean verifyUsername(LoginValidation loginValidation){
        assert loginValidation != null;

        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        String typedUsername = usernameField.getText().toString();

        boolean isUsernameValid = loginValidation.isUsernameValid(typedUsername);
        boolean isUsernameOk = false;

        if(isUsernameValid == true && loginValidation.isActivity(typedUsername) == true){
            isUsernameOk = true;
        }else{
            usernameField.requestFocus();
            usernameField.setError(loginValidation.getInvalidUsernameMessage());
        }

        return isUsernameOk;
    }

    /**
     * Checks if the password is correct
     * @param loginValidation - Instance of the class that does the login validations
     * @param typedUsername - The username typed by the user
     * @return boolean - True if the password is ok, or false otherwise
     */
    private boolean verifyPassword(LoginValidation loginValidation, String typedUsername){
        assert loginValidation != null;
        assert typedUsername != null;

        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        String typedPassword = passwordField.getText().toString();

        boolean isPasswordOk=loginValidation.checkPassword(typedUsername, typedPassword);

        if(isPasswordOk == true){
            //Nothing to do
        }else{
            passwordField.requestFocus();
            passwordField.setError(loginValidation.getInvalidPasswordMessage());
        }

        return isPasswordOk;
    }

    /**
     * Does the login, setting the idUser as logged in
     * @param username - Validated username
     */
    private void doLogin(String username){
        assert username != null;

        LoginUtility loginUtility = new LoginUtility(LoginActivity.this);

        try {
            int idUserLoggedIn = loginUtility.getUserId(username);
            loginUtility.setUserLogIn(idUserLoggedIn);

            Intent intent = new Intent(this, HomePage.class);
            finish();

            final int REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}
