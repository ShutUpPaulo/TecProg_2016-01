package com.mathheals.euvou.controller.user_registration;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.login_user.LoginActivity;
import com.mathheals.euvou.controller.utility.EditAndRegisterUtility;
import com.mathheals.euvou.controller.utility.Mask;

import java.text.ParseException;

import dao.UserDAO;
import exception.UserException;
import model.User;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private static final String STRING_EMPTY = "";

    private EditText nameField = null;
    private EditText birthDateField = null;
    private EditText mailField = null;
    private EditText mailConfirmationField = null;
    private EditText usernameField = null;
    private EditText passwordField = null;
    private EditText passwordConfirmField = null;

    private String name = STRING_EMPTY;
    private String birthDate = STRING_EMPTY;
    private String username = STRING_EMPTY;
    private String mail = STRING_EMPTY;
    private String password = STRING_EMPTY;
    private String passwordConfirmation = STRING_EMPTY;
    private String mailConfirmation = STRING_EMPTY;


    public RegisterFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        View view = inflater.inflate(R.layout.register_user, container, false);
        assert view != null;

        Button register = (Button) view.findViewById(R.id.saveButton);
        assert register != null;

        register.setOnClickListener(this);

        this.settingEditText(view);

        assert birthDateField != null;
        birthDateField.addTextChangedListener(Mask.insert("##/##/####", birthDateField));

        return view;
    }

    private void registerUser(User user){
        assert user != null;

        UserDAO userDAO = new UserDAO(getActivity());

        userDAO.saveUser(user);
    }

    private void startLoginActivity(){
        Activity activity = getActivity();
        assert activity != null;

        Intent loginIntent = new Intent(activity, LoginActivity.class);

        activity.startActivity(loginIntent);
    }

    private void settingEditText(View view){
        this.nameField = (EditText) view.findViewById(R.id.nameField);
        assert this.nameField != null;

        this.birthDateField = (EditText) view.findViewById(R.id.dateField);
        assert this.birthDateField != null;

        this.mailField = (EditText) view.findViewById(R.id.mailField);
        assert this.mailField != null;

        this.usernameField = (EditText) view.findViewById(R.id.loginField);
        assert this.usernameField != null;

        this.passwordField = (EditText) view.findViewById(R.id.passwordField);
        assert this.passwordField != null;

        this.mailConfirmationField = (EditText) view.findViewById(R.id.confirmMailField);
        assert this.mailConfirmationField != null;

        this.passwordConfirmField = (EditText) view.findViewById(R.id.confirmPasswordField);
        assert this.passwordConfirmField != null;

        this.birthDateField = (EditText) view.findViewById(R.id.dateField);
        assert this.birthDateField != null;
    }

    private void settingTextTyped(){
        this.name = nameField.getText().toString();
        this.username = usernameField.getText().toString();
        this.mail = mailField.getText().toString();
        this.mailConfirmation = mailConfirmationField.getText().toString();
        this.password = passwordField.getText().toString();
        this.passwordConfirmation = passwordConfirmField.getText().toString();
        this.birthDate = birthDateField.getText().toString();
    }

    @Override
    public void onClick(View view){
        assert view != null;

        this.settingTextTyped();

        try {
            User user = new User(name, username, mail, mailConfirmation,
                    password, passwordConfirmation, birthDate);

            registerUser(user);

            String SUCCESSFUL_REGISTRATION_MESSAGE = "Bem vindo ao #EuVou :)";
            Toast.makeText(getActivity().getBaseContext(),
                    SUCCESSFUL_REGISTRATION_MESSAGE, Toast.LENGTH_LONG).show();

            startLoginActivity();

        } catch (UserException e){
            String exceptionMessage = e.getMessage();
            assert exceptionMessage != null;

            EditAndRegisterUtility editAndRegisterUtility = new EditAndRegisterUtility();

            switch (exceptionMessage){
                case User.NAME_CANT_BE_EMPTY_NAME:
                    editAndRegisterUtility.setMessageError(nameField, exceptionMessage);
                    break;
                case User.NAME_CANT_BE_HIGHER_THAN_50:
                    editAndRegisterUtility.setMessageError(nameField, exceptionMessage);
                    break;
                case User.EMAIL_CANT_BE_EMPTY_EMAIL:
                    editAndRegisterUtility.setMessageError(mailField, exceptionMessage);
                    break;
                case User.EMAIL_CANT_BE_HIGHER_THAN_150:
                    editAndRegisterUtility.setMessageError(mailField, exceptionMessage);
                    break;
                case User.INVALID_EMAIL:
                    editAndRegisterUtility.setMessageError(mailField, exceptionMessage);
                    break;
                case User.EMAIL_ARE_NOT_EQUALS:
                    editAndRegisterUtility.setMessageError(mailField, exceptionMessage);
                    break;
                case User.USERNAME_CANT_BE_EMPTY_USERNAME:
                    editAndRegisterUtility.setMessageError(usernameField, exceptionMessage);
                    break;
                case User.USERNAME_CANT_BE_HIGHER_THAN_100:
                    editAndRegisterUtility.setMessageError(usernameField, exceptionMessage);
                    break;
                case User.PASSWORD_CANT_BE_EMPTY_PASSWORD:
                    editAndRegisterUtility.setMessageError(passwordField, exceptionMessage);
                    break;
                case User.PASSWORD_CANT_BE_LESS_THAN_6:
                    editAndRegisterUtility.setMessageError(passwordField, exceptionMessage);
                    break;
                case User.PASSWORD_ARE_NOT_EQUALS:
                    editAndRegisterUtility.setMessageError(passwordField, exceptionMessage);
                    break;
                case User.BIRTH_DATE_CANT_BE_EMPTY:
                    editAndRegisterUtility.setMessageError(birthDateField, exceptionMessage);
                    break;
                case User.INVALID_BIRTH_DATE:
                    editAndRegisterUtility.setMessageError(birthDateField, exceptionMessage);
                    break;
                case User.USERNAME_EXISTENT:
                    editAndRegisterUtility.setMessageError(usernameField, exceptionMessage);
                    break;
                case User.CONFIRM_PASSWORD_CANT_BE_EMPTY:
                    editAndRegisterUtility.setMessageError(passwordConfirmField, exceptionMessage);
                    break;
                case User.EMAIL_CONFIRMATION_CANT_BE_EMPTY:
                    editAndRegisterUtility.setMessageError(mailConfirmationField, exceptionMessage);
                    break;
                default:
                    //nothing to do
                    break;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
    }
}
