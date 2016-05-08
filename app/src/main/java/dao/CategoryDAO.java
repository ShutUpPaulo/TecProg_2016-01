/**
 * File: RegisterFragment.java
 * Purpose: Search category in database
 */

package dao;

import android.app.Activity;

import org.json.JSONObject;

public class CategoryDAO extends DAO{

    /**
     * Constructs based on current activity
     * @param currentActivity Activity used on construction
     */
    public CategoryDAO(Activity currentActivity){
        super(currentActivity);
    }

    /**
     * Searches category by Id
     * @param idCategory Id of searched category
     * @return JSONObject based on executed database consult
     */
    public JSONObject searchCategoryById(int idCategory){
        String consultQueryString =
                "SELECT nameCategory FROM tb_category WHERE idCategory = " + idCategory;

        JSONObject consultQuery = executeConsult(consultQueryString);

        return consultQuery;
    }
}