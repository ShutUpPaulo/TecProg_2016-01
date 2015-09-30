package com.mathheals.euvou.controller.login_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isUsernameValid;
    private boolean isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button doLogin = (Button) findViewById(R.id.doLogin);
        doLogin.setOnClickListener(this);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        String typedUsername = usernameField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        String typedPassword = passwordField.getText().toString();

        LoginValidation loginValidation = new LoginValidation(LoginActivity.this);

        isUsernameValid=loginValidation.isUsernameValid(typedUsername);

        if(isUsernameValid==false){
            usernameField.requestFocus();
            usernameField.setError(loginValidation.getInvalidUsernameMessage());
        }else{
            isPasswordValid=loginValidation.checkPassword(typedUsername, typedPassword);

            if(isPasswordValid==false){
                passwordField.requestFocus();
                passwordField.setError(loginValidation.getInvalidPasswordMessage());
            }
        }

        if(isUsernameValid && isPasswordValid){
            UserDAO userDAO = new UserDAO();
            JSONObject json = userDAO.searchUserByUsername(typedUsername);
            LoginUtility loginUtility = new LoginUtility(LoginActivity.this);

            try {
                int idUser = loginUtility.getUserId(typedUsername);
                loginUtility.setUserLogIn(idUser);
                Intent i = new Intent(this, HomePage.class);
                startActivityForResult(i, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
