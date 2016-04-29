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

/**
 * A simple {@link Fragment} subclass.
 */
public class DisableAccountLoginConfirmation extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Activity homePage;

    public DisableAccountLoginConfirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        homePage = getActivity();
        View view = inflater.inflate(R.layout.fragment_disable_account_login_confirmation, container, false);

        Button backButton = (Button)view.findViewById(R.id.button_back_id);
        backButton.setOnClickListener(this);

        Button disableButton = (Button)view.findViewById(R.id.button_disable_account_confirmation_id);
        disableButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

        FragmentActivity activity = this.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        switch (view.getId()) {
            case R.id.button_back_id:
                returnToConfigurationOptions(fragmentManager);
                RemoveUserVIewMessages.showWelcomeBackMessage(activity.getBaseContext());
                return;

            case R.id.button_disable_account_confirmation_id:
                if(isLoginConfirmationValid()) {
                    LoginUtility loginUtility = new LoginUtility(homePage);
                    UserDAO userDAO = new UserDAO(getActivity());

                    userDAO.disableUserById(new LoginUtility(homePage).getUserId());
                    loginUtility.setUserLogOff();

                    ActivityUtility.restartActivity(homePage);
                    RemoveUserVIewMessages.showAccountDeactivateMessage(homePage.getBaseContext());
                }else{
                    //nothing to do
                }
                break;

            default:
                //nothing to do
                break;
        }
    }

    private void returnToConfigurationOptions(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
        fragmentManager.popBackStack();
    }

    private boolean isLoginConfirmationValid() {
        View view = getView();

        EditText usernameField = (EditText) view.findViewById(R.id.edit_text_login_id);
        String typedUsername = usernameField.getText().toString();

        EditText passwordField = (EditText) view.findViewById(R.id.edit_text_password_id);
        String typedPassword = passwordField.getText().toString();

        LoginValidation loginValidation = new LoginValidation(homePage);

        boolean isUsernameValid = loginValidation.isUsernameValid(typedUsername);

        if(isUsernameValid==false){
            usernameField.requestFocus();
            usernameField.setError(loginValidation.getInvalidUsernameMessage());
        }else{
            boolean isPasswordValid=loginValidation.checkPassword(typedUsername, typedPassword);

            if(isPasswordValid==false){
                passwordField.requestFocus();
                passwordField.setError(loginValidation.getInvalidPasswordMessage());
            }
            else{
                return true;
            }
        }
        return false;
    }
}
