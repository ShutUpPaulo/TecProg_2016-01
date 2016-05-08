/**
 * File: RegisterFragment.java
 * Purpose: Search category in database
 */

package dao;

import android.app.Activity;

import org.json.JSONObject;

public class CategoryDAO extends DAO {

    public CategoryDAO(Activity currentActivity){
        super(currentActivity);
    }

    public JSONObject searchCategoryById(int idCategory){
        String consultQueryString =
                "SELECT nameCategory FROM tb_category WHERE idCategory = " + idCategory;

        JSONObject consultQuery = executeConsult(consultQueryString);

        return consultQuery;
    }
}