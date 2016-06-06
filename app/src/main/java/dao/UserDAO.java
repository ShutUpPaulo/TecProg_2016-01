/**
 * File: UserDAO.java
 * Purpose: manage the users data at database, searching, updating amd deleting when is needed.
 */

package dao;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;
import model.User;

public class UserDAO extends DAO{

    /**
     * Required constructor to instantiate the class passing the current activity
     */
    public UserDAO(Activity currentActivity){
        super(currentActivity);
    }

    /**
     * Required constructor to instantiate the class
     */
    public UserDAO(){}

    /**
     * Saves an user on the database
     * @param user
     * @return String - Returns a text confirming if the query was executed with success
     */
    public String saveUser(User user){

        assert user.getName() != null;
        assert user.getPassword() != null;
        assert user.getIdUser() >= 1;
        assert user.getIdUser() <=Integer.MAX_VALUE;

        final String QUERY = "INSERT INTO tb_user(nameUser, login,passwordUser,birthDate +" +
                " email)VALUES" + "(\"" + user.getName() + "\", \"" + user.getUsername() + "\", \""
                + user.getPassword() + "\"," + " STR_TO_DATE(\"" + user.getBirthDate() +
                "\",'%d/%m/%Y'),\"" + user.getEmail() + "\")";

        String queryStatus = this.executeQuery(QUERY);

        return queryStatus;
    }

    /**
     * Allows to search an user by his Id number
     * @param idUser - The id number of an user
     * @return String - Returns a text confirming if the query was executed with success
     */
    public String searchUserById(int idUser){
        final String QUERY = "SELECT * from vw_user WHERE idUser="+idUser+"";

        String queryStatus = this.executeConsult(QUERY).toString();

        return queryStatus;
    }


    /**
     * Removes an User at database using his username as param to find the  specified user
     * @param username - The name of the user
     * @return String - Returns a text confirming if the query was executed with success
     */
    public String deleteUserByUsername(String username){
        final String QUERY = "DELETE FROM tb_user WHERE login=\"" + username + "\"";

        String queryStatus = this.executeQuery(QUERY);

        return queryStatus;
    }

    /**
     * Removes an User at database using his Id as param to find the  specified user
     * @param idUser - The id number of an user
     * @return String - Returns a text confirming if the query was executed with success
     */
    public String deleteUserById(int idUser){
        final String QUERY = "DELETE FROM tb_user WHERE idUser=\"" +idUser+ "\"";

        String queryStatus = this.executeQuery(QUERY);

        return queryStatus;
    }

    /**
     * Updates the user's data on the database
     * @param user
     * @return String - Returns a text confirming if the query was executed with success
     */
    public String updateUser(User user){

        assert user.getName() != null;
        assert user.getPassword() != null;
        assert user.getIdUser() >= 1;
        assert user.getIdUser() <=Integer.MAX_VALUE;

        final String QUERY = "UPDATE tb_user SET nameUser=\""+user.getName()+"\", " +
                "birthDate=STR_TO_DATE(\"" + user.getBirthDate() + "\",'%d/%m/%Y'), " +
                "email=\""+user.getEmail()+"\", passwordUser=\"" + user.getPassword() + "\"" +
                " WHERE idUser=\""+user.getIdUser()+"\"";

        String queryStatus = this.executeQuery(QUERY);

        return queryStatus;
    }

    /**
     * Disables an user from the database using his id
     * @param idUser -  The id number of an user
     * @return String - Returns a text confirming if the query was executed with success
     */
    public String disableUserById(int idUser){
        final String QUERY = "UPDATE tb_user SET isActivity=\"N\" WHERE idUser=" +idUser+ "";

        String queryStatus = this.executeQuery(QUERY);

        return queryStatus;
    }

    /**
     * Searches an user at database by his username
     * @param username - The name of an user
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject searchUserByUsername(String username){
        final String QUERY = "SELECT * FROM vw_user WHERE login=\"" + username + "\"";

        JSONObject userData = this.executeConsult(QUERY);

        return userData;
    }

    /**
     * Searches an user at database using his name
     * @param name - name of the user
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject searchUserByName(String name){
        final String QUERY = "SELECT * FROM vw_user WHERE nameUser LIKE \"%" + name + "%\"";

        JSONObject userData = this.executeConsult(QUERY);

        return userData;
    }
}