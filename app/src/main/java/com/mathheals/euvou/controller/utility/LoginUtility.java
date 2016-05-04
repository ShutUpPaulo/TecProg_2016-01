package com.mathheals.euvou.controller.utility;

import android.app.Activity;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import dao.UserDAO;
import exception.UserException;
import model.User;

/**
 * Created by marlonmendes on 30/09/15.
 */
public class LoginUtility {
    private static final int LOGGED_OUT = -1;
    private static final String ID_FIELD =  "idField";
    private static final String COLUMN_USER_ID = "idUser";
    private static final String COLUMN_USER_NAME = "nameUser";
    private static final String COLUMN_USER_LOGIN = "login";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "passwordUser";
    private static final String COLUMN_USER_BIRTHDATE = "birthDate";
    private static final String COLUMN_USER_STATE = "isActivity";
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LoginUtility(Activity activity) {
        assert activity != null;

        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(ID_FIELD, activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public LoginUtility(){}

    public boolean hasUserLoggedIn() {
        return getUserId() != LOGGED_OUT;
    }
    // gets user's ID by username. OBS: IT'S ASSUMED THAT USERNAME DOES EXIST
    public int getUserId(String username) throws org.json.JSONException{
        assert username != null;

        UserDAO userDAO = new UserDAO(this.activity);
        JSONObject jsonObject = userDAO.searchUserByUsername(username);
        return Integer.parseInt(jsonObject.getJSONObject("0").getString(COLUMN_USER_ID));
    }

    public User getUser(String username){
        assert username != null;

        User user=null;
        try {
            UserDAO userDAO = new UserDAO(this.activity);
            JSONObject jsonObject = userDAO.searchUserByUsername(username);

            user = new User(jsonObject.getJSONObject("0").getString(COLUMN_USER_NAME),
                    jsonObject.getJSONObject("0").getString(COLUMN_USER_LOGIN),
                    jsonObject.getJSONObject("0").getString(COLUMN_USER_EMAIL),
                    jsonObject.getJSONObject("0").getString(COLUMN_USER_PASSWORD),
                    formatDateToBr(jsonObject.getJSONObject("0").getString(COLUMN_USER_BIRTHDATE)));
            return user;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UserException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    // gets current user's ID
    public int getUserId() {
        SharedPreferences sharedId = activity.getSharedPreferences(ID_FIELD, activity.MODE_PRIVATE);
        return sharedId.getInt(ID_FIELD, LOGGED_OUT);
    }

    public void setUserLogIn(int userId) {
        assert userId >= 1;

        editor.putInt(ID_FIELD, userId);
        editor.commit();
    }

    public void setUserLogOff() {
        editor.putInt(ID_FIELD, LOGGED_OUT);
        editor.commit();
    }

    public boolean isUserActive(String username) {
        assert username != null;

        UserDAO userDAO = new UserDAO(this.activity);
        JSONObject jsonObject = userDAO.searchUserByUsername(username);
        String userState = null;
        try {
            userState = jsonObject.getJSONObject("0").getString(COLUMN_USER_STATE);
            return userState == "Y";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String formatDateToBr(String birthDate){
        assert birthDate != null;

        String[] birthDateSplit = birthDate.split("-");
        return birthDateSplit[2]+"/"+birthDateSplit[1]+"/"+birthDateSplit[0];
    }
}
