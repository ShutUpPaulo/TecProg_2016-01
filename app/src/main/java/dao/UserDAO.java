/**
 * File: UserDAO.java
 * Purpose: manage the users data at database, searching, updating amd deleting when is needed.
 */

package dao;

import android.app.Activity;
import org.json.JSONObject;
import model.User;

public class UserDAO extends DAO{

    public UserDAO(Activity currentActivity){
        super(currentActivity);
    }

    public UserDAO(){}

    public String save(User user){
        return this.executeQuery("INSERT INTO tb_user(nameUser, login,passwordUser,birthDate +" +
                " email)VALUES" + "(\"" + user.getName() + "\", \"" + user.getUsername() + "\", \""
                + user.getPassword() + "\"," + " STR_TO_DATE(\"" + user.getBirthDate() +
                "\",'%d/%m/%Y'),\"" + user.getEmail() + "\")");
    }

    public String searchUserById(int idUser){
        return this.executeConsult("SELECT * from vw_user WHERE idUser="+idUser+"").toString();
    }

    //This method is just used on the tests

    public String delete(String username){
        return this.executeQuery("DELETE FROM tb_user WHERE login=\"" + username + "\"");
    }

    public String delete(int idUser){
        return this.executeQuery("DELETE FROM tb_user WHERE idUser=\"" +idUser+ "\"");
    }

    public String update(User user){
        return this.executeQuery("UPDATE tb_user SET nameUser=\""+user.getName()+"\", " +
                "birthDate=STR_TO_DATE(\"" + user.getBirthDate() + "\",'%d/%m/%Y'), " +
                "email=\""+user.getEmail()+"\", passwordUser=\"" + user.getPassword() + "\"" +
                " WHERE idUser=\""+user.getIdUser()+"\"");
    }

    public String disableUserById(int idUser){
        return this.executeQuery("UPDATE tb_user SET isActivity=\"N\" WHERE idUser=" +idUser+ "");
    }

    public JSONObject searchUserByUsername(String username){
        return this.executeConsult("SELECT * FROM vw_user WHERE login=\"" + username + "\"");
    }

    public JSONObject searchUserByName(String name){
        return this.executeConsult("SELECT * FROM vw_user WHERE nameUser LIKE \"%" + name + "%\"");
    }
}