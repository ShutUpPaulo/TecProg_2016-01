/**
 * File: LoginValidation.java
 * Purpose: Contains the methods needed to validate the data typed by the user on the login screen
 */

package com.mathheals.euvou.controller.login_user;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAO;

public class LoginValidation {
    private final String JSON_FORMAT = "0";
    private Activity activity;

    public LoginValidation(Activity activity){
        this.activity = activity;
    }

    private boolean checkUsernameCharacters(String username){
        assert username != null;

        final String STRING_EMPTY = " ";
        if(username.isEmpty() == false && username.contains(STRING_EMPTY) == false){
            return true;
        }else{
            return false;
        }
    }

    public boolean isActivity(String username){
        assert username != null;

        UserDAO userDAO = new UserDAO(this.activity);

        JSONObject json = null;
        String isActivity = null;
        try {
            json = userDAO.searchUserByUsername(username);

            final String COLUMN_USER_STATE = "isActivity";
            isActivity = json.getJSONObject(JSON_FORMAT).getString(COLUMN_USER_STATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String YES_OPTION = "Y";
        if(json != null && isActivity.equals(YES_OPTION)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isUsernameRegistered(String username){
        assert username != null;

        UserDAO userDAO = new UserDAO(this.activity);

        JSONObject json = userDAO.searchUserByUsername(username);

        if(json != null){
            return true;
        }else{
            return false;
        }

    }

    public boolean isUsernameValid(String username){
        assert username != null;

        return checkUsernameCharacters(username) && isUsernameRegistered(username);
    }

    public boolean checkPassword(String validUsername, String passwordTyped){
        assert validUsername != null;
        assert passwordTyped != null;

        UserDAO userDAO = new UserDAO(this.activity);

        JSONObject json = userDAO.searchUserByUsername(validUsername);

        try {
            final String PASSWORD_USER = "passwordUser";
            String password = json.getJSONObject(JSON_FORMAT).getString(PASSWORD_USER);

            if(password.equals(passwordTyped)){
                return true;
            }else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getInvalidUsernameMessage(){
        final String INVALID_USERNAME_MESSAGE = "Ops, acho que você digitou o login errado";

        return INVALID_USERNAME_MESSAGE;
    }

    public String getInvalidPasswordMessage(){
        final String INVALID_PASSWORD_MESSAGE = "Ops, acho que você digitou a senha errada";

        return INVALID_PASSWORD_MESSAGE;
    }
}