/**
 * File: User.java
 * Purpose: Maintain user data
 */

package model;

import android.app.Activity;
import android.util.Patterns;
import android.widget.Toast;

import com.mathheals.euvou.controller.login_user.LoginValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.UserDAO;
import exception.UserException;

public class User {

    private static final String ID_IS_INVALID = "Id inválido";
    public static final String NAME_CANT_BE_EMPTY_NAME = "Hey, acho que você está esquecendo de nos dizer seu nome.";
    public static final String NAME_CANT_BE_HIGHER_THAN_50 = "Hey, acho que você ultrapassou o número de caracteres permitido para o nome, tente novamente.";
    public static final String EMAIL_CANT_BE_EMPTY_EMAIL = "Hey, acho que você está esquecendo de nos dizer seu email.";
    public static final String EMAIL_CANT_BE_HIGHER_THAN_150 = "Hey, acho que você ultrapassou o número de caracteres permitido para email, tente novamente.";
    public static final String INVALID_EMAIL = "Ops, esse e-mail é inválido.";
    public static final String CONFIRM_PASSWORD_CANT_BE_EMPTY = "Hey, confirme sua senha";
    public static final String EMAIL_CONFIRMATION_CANT_BE_EMPTY = "Hey, confirme seu e-mail";
    public static final String USERNAME_CANT_BE_EMPTY_USERNAME = "Hey, acho que você está esquecendo de nos dizer seu login.";
    public static final String USERNAME_CANT_BE_HIGHER_THAN_100 = "Hey, acho que você ultrapassou o número de caracteres permitido para o login, tente novamente.";
    public static final String PASSWORD_CANT_BE_EMPTY_PASSWORD = "Hey, acho que você está esquecendo de nos dizer sua senha.";
    public static final String PASSWORD_CANT_BE_LESS_THAN_6 = "Hey, acho que vocẽ não atingiu o número mínimo de caracteres.";
    public static final String BIRTH_DATE_CANT_BE_EMPTY = "Hey, acho que você está esquecendo de nos dizer um dia muito especial, a data do seu nascimento.";
    public static final String EMAIL_ARE_NOT_EQUALS = "Ops, E-mails não conferem.";
    public static final String PASSWORD_ARE_NOT_EQUALS = "Ops, as senhas não conferem.";
    public static final String INVALID_BIRTH_DATE = "Ops, essa data é inválida";
    public static final String USERNAME_EXISTENT = "Ops, esse login já existe";
    private static final int MAX_LENGTH_NAME = 50;
    private static final int MAX_LENGTH_EMAIL = 150;
    private static final int MAX_LENGTH_USERNAME = 100;
    private static final int MIN_LENGTH_PASSWORD = 6;

    private int idUser;
    private String name;
    private String username;
    private String email;
    private String password;
    private String birthDate;

    public boolean equals(User user){
        return this.name.equals(user.getName()) &&
                this.username.equals(user.getUsername()) &&
                this.email.equals(user.getEmail()) &&
                this.password.equals(user.getPassword()) &&
                this.birthDate.equals(user.getBirthDate());
    }

    public User(int idUser, String name, String username, String birthDate, String email, String mailConfirmation, String password, String passwordConfirmation) throws UserException, ParseException{
        setIdUser(idUser);
        setName(name);
        setUsername(username);
        setBirthDate(birthDate);
        setEmail(email);
        verifyEmailConfirmation(mailConfirmation);
        setPassword(password);
        verifyPasswordConfirmation(passwordConfirmation);

    }

    public User(int idUser, String name, String birthDate, String email) throws UserException, ParseException {
        setName(name);
        setBirthDate(birthDate);
        setIdUser(idUser);
        setEmail(email);
    }

    public User(int idUser, String name, String birthDate, String email, String mailConfirmation, String password, String passwordConfirmation) throws UserException, ParseException{
        setIdUser(idUser);
        setName(name);
        setBirthDate(birthDate);
        setEmail(email);
        verifyEmailConfirmation(mailConfirmation);
        setPassword(password);
        verifyPasswordConfirmation(passwordConfirmation);

    }

    public User(String name, String username, String email, String password,String birthDate) throws UserException, ParseException {

        setName(name);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
    }

    public User(String name, String username, String email, String mailConfirmation, String password, String passwordConfirmation, String birthDate) throws UserException, ParseException {
        setName(name);
        setBirthDate(birthDate);
        setEmail(email);
        verifyEmailConfirmation(mailConfirmation);
        setUsername(username);
        setPassword(password);
        verifyPasswordConfirmation(passwordConfirmation);
    }


    private void setIdUser(int idUser) throws UserException {

        if(idUser <= Integer.MAX_VALUE && idUser >= 1){
            this.idUser =idUser;
        }
        else {
            throw new UserException(ID_IS_INVALID);
        }
    }

    private void setName(String name) throws UserException {

        if(!name.isEmpty() && name!=null){
            if(name.length() <= MAX_LENGTH_NAME){
                this.name = name;
            }else{
                throw new UserException(NAME_CANT_BE_HIGHER_THAN_50);
            }
        }else{
            throw new UserException(NAME_CANT_BE_EMPTY_NAME);
        }
    }

    private  void  setEmail(String email) throws UserException{

        if (!email.isEmpty() && email!=null) {
            if(email.length() <= MAX_LENGTH_EMAIL){
                CharSequence emailCharSequence = email;
                if(Patterns.EMAIL_ADDRESS.matcher(emailCharSequence).matches()) {
                    this.email = email;
                }
                else{
                    throw new UserException(INVALID_EMAIL);
                }
            }
            else{
                throw  new UserException(EMAIL_CANT_BE_HIGHER_THAN_150);
            }
        }
        else {
            throw  new UserException(EMAIL_CANT_BE_EMPTY_EMAIL);
        }
    }

    private void verifyEmailConfirmation(String confirmationMail) throws UserException{
        if(confirmationMail!=null && !confirmationMail.isEmpty()) {
            if (!email.equals(confirmationMail)) {
                throw new UserException(EMAIL_ARE_NOT_EQUALS);
            }else{
                // Nothing to do
            }
        }
        else{
            throw new UserException(EMAIL_CONFIRMATION_CANT_BE_EMPTY);
        }
    }

    private  void  setUsername (String username) throws UserException{

        if (username!=null && !username.isEmpty()) {
            if (new UserDAO().searchUserByUsername(username) != null){
                throw new UserException(USERNAME_EXISTENT);
            }else{
                // Nothing to do
            }

            if(username.length() <= MAX_LENGTH_USERNAME){
                this.username = username;
            }
            else{
                throw  new UserException(USERNAME_CANT_BE_HIGHER_THAN_100);
            }
        }
        else{
            throw  new UserException(USERNAME_CANT_BE_EMPTY_USERNAME);
        }

    }

    private  void  setPassword (String password) throws UserException{

        if (!password.isEmpty() && password!=null) {
            if(password.length() >= MIN_LENGTH_PASSWORD) {
                this.password = password;
            }
            else{
                throw  new UserException(PASSWORD_CANT_BE_LESS_THAN_6);
            }
        }
        else{
            throw  new UserException(PASSWORD_CANT_BE_EMPTY_PASSWORD);
        }

    }

    private void verifyPasswordConfirmation(String confirmationPassword) throws UserException {
        if(confirmationPassword!=null && !confirmationPassword.isEmpty()) {
            if (!password.equals(confirmationPassword)) {
                throw new UserException(PASSWORD_ARE_NOT_EQUALS);
            }else{
                // Nothing to do
            }
        }
        else{
            throw new UserException(CONFIRM_PASSWORD_CANT_BE_EMPTY);
        }
    }

    private void setBirthDate (String birthDate) throws UserException, ParseException {
        if(!birthDate.isEmpty() && birthDate!=null){
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false);
                Date userDate = format.parse(birthDate);

                if (userDate.before(new Date())) {
                    this.birthDate = birthDate;
                }

                else{
                    throw new UserException(INVALID_BIRTH_DATE);
                }
            }
            catch (ParseException excecao){
                throw new UserException(INVALID_BIRTH_DATE);
            }
        }
        else{
            throw new UserException(BIRTH_DATE_CANT_BE_EMPTY);
        }
    }

    public int getIdUser(){
        return idUser;
    }

    public String getName(){
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getBirthDate(){
        return birthDate;
    }


}