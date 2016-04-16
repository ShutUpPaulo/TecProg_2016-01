package com.mathheals.euvou.controller.login_user;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAO;

/**
 * Created by igor on 29/09/15.
 */
public class LoginValidation {
    private final String JSON_FORMAT = "0";
    private static final String COLUMN_USER_STATE = "isActivity";
    Activity activity;

    public LoginValidation(Activity activity){
        this.activity=activity;
    }

    public LoginValidation(){}

    private boolean checkUsernameCharacters(String username){
        if(username.isEmpty())
            return false;
        if(username.contains(" "))
            return false;
        return true;
    }

    public boolean isActivity(String username){
        UserDAO userDAO = new UserDAO(this.activity);

        JSONObject json = null;
        String isActivity = null;
        try {
            json = userDAO.searchUserByUsername(username);
            isActivity = json.getJSONObject(JSON_FORMAT).getString(COLUMN_USER_STATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(json!=null && isActivity.equals("Y")){
            return true;
        }else{
            return false;
        }
    }

    public boolean isUsernameRegistred(String username){
        UserDAO userDAO = new UserDAO(this.activity);

        JSONObject json = userDAO.searchUserByUsername(username);

        if(json!=null){
            return true;
        }else{
            return false;
        }

    }

    public boolean isUsernameValid(String username){

        return checkUsernameCharacters(username) && isUsernameRegistred(username);
    }

    public boolean checkPassword(String validUsername, String passwordTyped){
        UserDAO userDAO = new UserDAO(this.activity);

        JSONObject json = userDAO.searchUserByUsername(validUsername);

        try {
            String PASSWORD_USER = "passwordUser";
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

    public String getInvalidUsernameMessage() {
        String INVALID_USERNAME_MESSAGE = "Ops, acho que você digitou o login errado";
        return INVALID_USERNAME_MESSAGE;
    }

    public String getInvalidPasswordMessage() {
        String INVALID_PASSWORD_MESSAGE = "Ops, acho que você digitou a senha errada";
        return INVALID_PASSWORD_MESSAGE;
    }

}