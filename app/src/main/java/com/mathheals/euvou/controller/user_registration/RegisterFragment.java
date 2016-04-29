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

    private EditText nameField;
    private EditText birthDateField;
    private EditText mailField;
    private EditText mailConfirmationField;
    private EditText usernameField;
    private EditText passwordField;
    private EditText passwordConfirmField;

    private String name;
    private String birthDate;
    private String username;
    private String mail;
    private String password;
    private String passwordConfirm;
    private String mailConfirm;


    public RegisterFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_user, container, false);

        Button register = (Button) view.findViewById(R.id.saveButton);
        register.setOnClickListener(this);

        this.settingEditText(view);

        birthDateField.addTextChangedListener(Mask.insert("##/##/####", birthDateField));

        return view;
    }

    private void registerUser(User user){
        UserDAO userDAO = new UserDAO(getActivity());
        userDAO.saveUser(user);
    }

    private void startLoginActivity(){
        Activity activity = getActivity();
        Intent loginIntent = new Intent(activity, LoginActivity.class);
        activity.startActivity(loginIntent);
    }

    private void settingEditText(View view){
        this.nameField = (EditText) view.findViewById(R.id.nameField);
        this.birthDateField = (EditText) view.findViewById(R.id.dateField);
        this.mailField = (EditText) view.findViewById(R.id.mailField);
        this.usernameField = (EditText) view.findViewById(R.id.loginField);
        this.passwordField = (EditText) view.findViewById(R.id.passwordField);
        this.mailConfirmationField = (EditText) view.findViewById(R.id.confirmMailField);
        this.passwordConfirmField = (EditText) view.findViewById(R.id.confirmPasswordField);
        this.birthDateField = (EditText) view.findViewById(R.id.dateField);
    }

    private void settingTextTyped(){
        this.name = nameField.getText().toString();
        this.username = usernameField.getText().toString();
        this.mail = mailField.getText().toString();
        this.mailConfirm = mailConfirmationField.getText().toString();
        this.password = passwordField.getText().toString();
        this.passwordConfirm = passwordConfirmField.getText().toString();
        this.birthDate = birthDateField.getText().toString();
    }

    @Override
    public void onClick(View view){
        this.settingTextTyped();

        try {
            User user = new User(name, username, mail, mailConfirm,
                    password, passwordConfirm, birthDate);
            registerUser(user);

            String SUCCESSFUL_REGISTRATION_MESSAGE = "Bem vindo ao #EuVou :)";
            Toast.makeText(getActivity().getBaseContext(),
                    SUCCESSFUL_REGISTRATION_MESSAGE, Toast.LENGTH_LONG).show();

            startLoginActivity();

        } catch (UserException e){
            String exceptionMessage = e.getMessage();

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
