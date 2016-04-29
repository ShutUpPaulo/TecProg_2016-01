package com.mathheals.euvou;

/**
 * Created by igor on 01/10/15.
 */

import junit.framework.TestCase;

import org.json.JSONException;

import java.text.ParseException;

import dao.UserDAO;
import exception.UserException;
import model.User;

public class UserDAOTest extends TestCase {
    private static final String ID_FIELD =  "idField";
    private static final String COLUMN_USER_ID = "idUser";
    private static final String COLUMN_USER_NAME = "nameUser";
    private static final String COLUMN_USER_LOGIN = "login";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "passwordUser";
    private static final String COLUMN_USER_BIRTHDATE = "birthDate";
    private static final String COLUMN_USER_STATE = "isActivity";

    public void testSave() throws ParseException, UserException {
            UserDAO userDAO = new UserDAO();
            User user;
        user = new User("marceloChavosaao","marceloChavosaao","marceloChavosao@euvou.com","marceloChavosao@euvou.com","123456","123456","11/09/2015");
        assertTrue(userDAO.saveUser(user).contains("Salvo"));
            userDAO.deleteByUsername("marceloChavosaao");
    }

    public void testDeleteByName() throws ParseException, UserException  {

        UserDAO userDAO = new UserDAO();
        User user = new User("Marcelo", "marceloChavosaoa", "galudo11cm@uol.com", "123456", "24/11/1969");
        if(!userDAO.saveUser(user).contains("Salvo"))
            assertTrue(false);
        assertTrue(userDAO.deleteByUsername("marceloChavosaoa").contains("Salvo"));
    }


    public void testDeleteById() throws ParseException, UserException, JSONException {

        UserDAO userDAO = new UserDAO();
        User user = new User("VIny", "viny", "viny@uol.com", "123456", "14/02/1995");
        if(!userDAO.saveUser(user).contains("Salvo"))
            assertTrue(false);
        int id = userDAO.searchUserByUsername("viny").getJSONObject("0").getInt("idUser");
        assertTrue(userDAO.deleteById(id).contains("Salvo"));
        userDAO.deleteByUsername("viny");
    }

    public void testeSearchUserById()
    {
        assertFalse(new UserDAO().searchUserById(3) == null);
    }

    public void testUpdateUser() throws ParseException, UserException, JSONException {
        UserDAO userDAO = new UserDAO();
        User user = new User(1,"Vinicius ppp", "umteste", "14/02/1995", "viny-pinheiro@hotmail.com",
                "viny-pinheiro@hotmail.com", "123456", "123456");
        if(!userDAO.saveUser(user).contains("Salvo")) {
            assertTrue(false);
            userDAO.deleteByUsername("umteste");
        }
        assertTrue(userDAO.update(user).contains("Salvo"));
        userDAO.deleteByUsername("umteste");

    }

    public void testDisableLogin() throws ParseException, UserException, JSONException {
        UserDAO userDAO = new UserDAO();
        User user = new User(1,"Vinicius Pinheiro", "umteste", "14/02/1995", "viny-pinheiro@hotmail.com",
                "viny-pinheiro@hotmail.com", "123456", "123456");
        if(!userDAO.saveUser(user).contains("Salvo")) {
            assertTrue(false);
            userDAO.deleteByUsername("umteste");
        }
        int id = userDAO.searchUserByUsername("umteste").getJSONObject("0").getInt("idUser");
        assertTrue(userDAO.disableUserById(id).contains("Salvo"));
        userDAO.deleteByUsername("umteste");

    }
    

}
