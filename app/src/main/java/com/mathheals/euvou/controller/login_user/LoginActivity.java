package com.mathheals.euvou.controller.login_user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isPasswordValid;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button doLogin = (Button) findViewById(R.id.doLogin);
        doLogin.setOnClickListener(this);
        initViews();
        onConfigActionBar();
    }

    private void initViews(){
        actionBar = getSupportActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else{
            //Nothing to do
        }

        return super.onOptionsItemSelected(item);
    }

    private void onConfigActionBar(){

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008B8B")));

    }

    @Override
    public void onClick(View v) {
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        String typedUsername = usernameField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        String typedPassword = passwordField.getText().toString();

        LoginValidation loginValidation = new LoginValidation(LoginActivity.this);

        boolean isUsernameValid = loginValidation.isUsernameValid(typedUsername);

        if(isUsernameValid == true && loginValidation.isActivity(typedUsername) == true){
            isPasswordValid=loginValidation.checkPassword(typedUsername, typedPassword);

            if(isPasswordValid == true){
                //Nothing to do
            }else{
                passwordField.requestFocus();
                passwordField.setError(loginValidation.getInvalidPasswordMessage());
            }
        }else{
            usernameField.requestFocus();
            usernameField.setError(loginValidation.getInvalidUsernameMessage());
        }

        if(isUsernameValid == true && isPasswordValid == true){
            LoginUtility loginUtility = new LoginUtility(LoginActivity.this);

            try {
                int idUser = loginUtility.getUserId(typedUsername);
                loginUtility.setUserLogIn(idUser);
                Intent i = new Intent(this, HomePage.class);
                finish();
                startActivityForResult(i, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            //Nothing to do
        }
    }
}
