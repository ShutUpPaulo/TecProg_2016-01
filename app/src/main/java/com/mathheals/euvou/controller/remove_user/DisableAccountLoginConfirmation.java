/**
 * File: DisableAccountLoginConfirmation.java
 * Purpose: Disable login confirmation of removed user account
 */


package com.mathheals.euvou.controller.remove_user;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.login_user.LoginValidation;
import com.mathheals.euvou.controller.utility.ActivityUtility;
import com.mathheals.euvou.controller.utility.LoginUtility;

import dao.UserDAO;

public class DisableAccountLoginConfirmation extends android.support.v4.app.Fragment
        implements View.OnClickListener{

    private Activity homePage;

    /**
     * Empty constructor required DisableAccountLoginConfirmation tests
     */
    public DisableAccountLoginConfirmation(){
    }


    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater Object used to inflate any views in the fragment
     * @param container If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a
     *                             previous saved state as given here
     * @return View of Disable Account Login Confirmation
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        assert inflater != null;
        assert container != null;

        homePage = getActivity();
        View loginView = inflater.inflate(R.layout.fragment_disable_account_login_confirmation,
                container, false);
        assert loginView != null;

        this.createButtons(loginView);

        return loginView;
    }

    /**
     * Method invoked when view is clicked
     * @param view View that was clicked
     */
    @Override
    public void onClick(View view){
        assert view != null;

        FragmentActivity activity = this.getActivity();
        assert activity != null;

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        assert fragmentManager != null;

        switch(view.getId()){
            case R.id.button_back_id:
                returnToConfigurationOptions(fragmentManager);

                RemoveUserViewMessages.showWelcomeBackMessage(activity.getBaseContext());

                break;

            case R.id.button_disable_account_confirmation_id:
                if(isLoginConfirmationValid()){
                    LoginUtility loginUtility = new LoginUtility(homePage);
                    UserDAO userDAO = new UserDAO(getActivity());

                    userDAO.disableUserById(new LoginUtility(homePage).getUserId());
                    loginUtility.setUserLogOff();

                    ActivityUtility.restartActivity(homePage);
                    RemoveUserViewMessages.showAccountDeactivateMessage(homePage.getBaseContext());
                }else{
                    //nothing to do
                }
                break;

            default:
                //nothing to do
                break;
        }
    }

    /**
     * Returns to configuration options menu
     * @param fragmentManager Object that manages the fragment
     */
    private void returnToConfigurationOptions(FragmentManager fragmentManager){
        assert fragmentManager != null;

        fragmentManager.popBackStack();
        fragmentManager.popBackStack();
    }

    /**
     * Checks if login confirmation is valid
     * @return True if login confirmation is valid, false otherwise.
     */
    private boolean isLoginConfirmationValid(){
        View loginView = getView();
        assert loginView != null;

        EditText usernameField = (EditText) loginView.findViewById(R.id.edit_text_login_id);
        assert usernameField != null;

        String typedUsername = usernameField.getText().toString();

        EditText passwordField = (EditText) loginView.findViewById(R.id.edit_text_password_id);
        assert passwordField != null;

        String typedPassword = passwordField.getText().toString();

        LoginValidation loginValidation = new LoginValidation(homePage);

        boolean isUsernameValid = loginValidation.isUsernameValid(typedUsername);
        boolean isLoginConfirmationValid = false;

        if(isUsernameValid){
            boolean isPasswordValid = loginValidation.checkPassword(typedUsername, typedPassword);

            if(isPasswordValid){
                isLoginConfirmationValid = true;
            }else{
                passwordField.requestFocus();
                passwordField.setError(loginValidation.getInvalidPasswordMessage());

                isLoginConfirmationValid = false;
            }
        }else{
            usernameField.requestFocus();
            usernameField.setError(loginValidation.getInvalidUsernameMessage());

            isLoginConfirmationValid = false;
        }
        return isLoginConfirmationValid;
    }

    /**
     * Creates buttons in login view
     * @param loginView View where buttons will be created
     */
    private void createButtons(View loginView){
        Button backButton = (Button) loginView.findViewById(R.id.button_back_id);
        assert backButton != null;

        backButton.setOnClickListener(this);

        Button disableButton = (Button) loginView
                .findViewById(R.id.button_disable_account_confirmation_id);
        assert disableButton != null;

        disableButton.setOnClickListener(this);
    }
}