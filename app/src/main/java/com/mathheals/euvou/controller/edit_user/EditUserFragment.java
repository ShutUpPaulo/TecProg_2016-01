/*
 * File: EditUserFragment.java
 * Purpose: allows users to edit their personal data in the application
 */
package com.mathheals.euvou.controller.edit_user;


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
import com.mathheals.euvou.controller.utility.EditAndRegisterUtility;
import com.mathheals.euvou.controller.utility.LoginUtility;
import com.mathheals.euvou.controller.utility.Mask;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAO;
import model.User;

public class EditUserFragment extends Fragment implements View.OnClickListener{
    private int USER_STATUS;
    private final int LOGGED_OUT = -1;
    private EditAndRegisterUtility utilityForEdit = new EditAndRegisterUtility();
    private String name, birthDate, mail, mailConfirm, password, passwordConfirm;
    private EditText nameField, birthDateField, mailField, mailConfirmationField, passwordField,
            passwordConfirmField;
    private EditAndRegisterUtility  editAndRegisterUtility = new EditAndRegisterUtility();

    public EditUserFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        UserDAO userDAO = new UserDAO(this.getActivity());

        setingEditText(view);
        birthDateField.addTextChangedListener(Mask.insert("##/##/####", birthDateField));

        LoginUtility loginUtility = new LoginUtility(this.getActivity());
        USER_STATUS = loginUtility.getUserId();

        String str = userDAO.searchUserById(USER_STATUS);
        JSONObject json = null;
        try{
            json = new JSONObject(str);
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            String nameUser = json.getJSONObject("0").getString("nameUser");
            String birthDate = json.getJSONObject("0").getString("birthDate");
            String mail = json.getJSONObject("0").getString("email");

            String[] birthDateSplit = birthDate.split("-");
            birthDate = birthDateSplit[2]+"/"+birthDateSplit[1]+"/"+birthDateSplit[0];

            nameField.setText(nameUser);
            birthDateField.setText(birthDate);
            mailField.setText(mail);

        }catch(JSONException e){
            e.printStackTrace();
        }

        Button update = (Button)view.findViewById(R.id.updateButton);
        update.setOnClickListener(this);

        return view;
    }

    private void updateUser(User user){
        UserDAO userDAO = new UserDAO(getActivity());
        userDAO.updateUser(user);
    }

    private void setingEditText(View view){
        this.nameField = (EditText) view.findViewById(R.id.nameField);
        this.birthDateField = (EditText) view.findViewById(R.id.dateField);
        this.mailField = (EditText) view.findViewById(R.id.mailField);
        this.passwordField = (EditText) view.findViewById(R.id.passwordField);
        this.mailConfirmationField = (EditText) view.findViewById(R.id.confirmMailField);
        this.passwordConfirmField = (EditText) view.findViewById(R.id.confirmPasswordField);
        this.birthDateField = (EditText) view.findViewById(R.id.dateField);
    }

    private void setingTextTyped(){
        this.name = nameField.getText().toString();
        this.mail = mailField.getText().toString();
        this.mailConfirm = mailConfirmationField.getText().toString();
        this.password = passwordField.getText().toString();
        this.passwordConfirm = passwordConfirmField.getText().toString();
        this.birthDate = birthDateField.getText().toString();
    }

    @Override
    public void onClick(View v){

        setingTextTyped();

        LoginUtility loginUtility = new LoginUtility(this.getActivity());
        USER_STATUS = loginUtility.getUserId();

        try{
            User user = new User(USER_STATUS, name, birthDate, mail, mailConfirm, password, passwordConfirm);
            updateUser(user);
            Toast.makeText(this.getActivity().getBaseContext(), "Usuário alterado com sucesso", Toast.LENGTH_LONG).show();

            Activity activity = getActivity();
            Intent intent = activity.getIntent();
            activity.finish();
            startActivity(intent);

        }catch (Exception e){
            String message = e.getMessage();

            if(message.equals(User.EMAIL_CANT_BE_EMPTY_EMAIL)) {
                editAndRegisterUtility.setMessageError(mailField, message);
            }
            if(message.equals(User.NAME_CANT_BE_EMPTY_NAME)){
                editAndRegisterUtility.setMessageError(nameField, message);
            }
            if(message.equals(User.NAME_CANT_BE_HIGHER_THAN_50)){
                editAndRegisterUtility.setMessageError(nameField, message);
            }
            if(message.equals(User.EMAIL_CANT_BE_HIGHER_THAN_150)){
                editAndRegisterUtility.setMessageError(mailField, message);
            }
            if(message.equals(User.INVALID_EMAIL)){
                editAndRegisterUtility.setMessageError(mailField, message);
            }
            if(message.equals(User.EMAIL_ARE_NOT_EQUALS)){
                editAndRegisterUtility.setMessageError(mailField, message);
            }
            if(message.equals(User.PASSWORD_CANT_BE_EMPTY_PASSWORD)){
                editAndRegisterUtility.setMessageError(passwordField, message);
            }
            if(message.equals(User.PASSWORD_CANT_BE_LESS_THAN_6)){
                editAndRegisterUtility.setMessageError(passwordField, message);
            }
            if(message.equals(User.PASSWORD_ARE_NOT_EQUALS)){
                editAndRegisterUtility.setMessageError(passwordField, message);
            }
            if(message.equals(User.BIRTH_DATE_CANT_BE_EMPTY)){
                editAndRegisterUtility.setMessageError(birthDateField, message);
            }
            if(message.equals(User.INVALID_BIRTH_DATE)){
                editAndRegisterUtility.setMessageError(birthDateField, message);
            }
            if(message.equals(User.EMAIL_CONFIRMATION_CANT_BE_EMPTY)){
                editAndRegisterUtility.setMessageError(mailConfirmationField, message);
            }
            if(message.equals(User.CONFIRM_PASSWORD_CANT_BE_EMPTY)){
                editAndRegisterUtility.setMessageError(passwordConfirmField, message);
            }
        }

    }
}