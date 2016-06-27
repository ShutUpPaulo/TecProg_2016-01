/**
 * File: LoginUtility.java
 * Purpose: Contains methods to set the id of a user who is logging in and get
 *          data of a user who's already logged in
 */

package com.mathheals.euvou.controller.utility;

import android.app.Activity;
import android.content.SharedPreferences;

import org.json.JSONObject;

import dao.UserDAO;

public class LoginUtility {
    private static final int LOGGED_OUT = -1;
    private static final String ID_FIELD =  "idField";
    private static final String COLUMN_USER_ID = "idUser";
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Required constructor to instantiate the class passing the current activity
     */
    public LoginUtility(Activity activity) {
        assert activity != null;

        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(ID_FIELD, activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Required constructor to instantiate the class
     */
    public LoginUtility(){

    }

    /**
     * Verifies if has some user logged in
     * @return boolean - Returns true if has some user logged in, or false if has not
     */
    public boolean hasUserLoggedIn() {
        return getUserId() != LOGGED_OUT;
    }

    /**
     * Gets user's ID by username, assuming that username dos exist
     * @param username - Username of the user
     * @return int - Returns the id of the username passed
     * @throws org.json.JSONException
     */
    public int getUserId(String username) throws org.json.JSONException{
        assert username != null;

        UserDAO userDAO = new UserDAO(this.activity);
        JSONObject jsonObject = userDAO.searchUserByUsername(username);
        return Integer.parseInt(jsonObject.getJSONObject("0").getString(COLUMN_USER_ID));
    }

    /**
     * Gets current user's ID
     * @return int - Returns the id of the user logged in
     */
    public int getUserId() {
        SharedPreferences sharedId = activity.getSharedPreferences(ID_FIELD, activity.MODE_PRIVATE);
        return sharedId.getInt(ID_FIELD, LOGGED_OUT);
    }

    /**
     * Sets a new user logged in
     * @param userId - ID of the user who is logging in
     */
    public void setUserLogIn(int userId) {
        assert userId >= 1;

        editor.putInt(ID_FIELD, userId);
        editor.commit();
    }

    /**
     * Logs off the current user
     */
    public void setUserLogOff() {
        editor.putInt(ID_FIELD, LOGGED_OUT);
        editor.commit();
    }
}
