/**
 * File: RegisterFragment.java
 * Purpose: Search category in database
 */

package dao;

import android.app.Activity;

import org.json.JSONObject;

public class CategoryDAO extends DAO{

    private static final String SELECT_NAME_CATEGORY_COMMAND =
            "SELECT nameCategory FROM tb_category WHERE ";

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
        String consultQueryString = getConsultQueryString(idCategory);

        JSONObject consultQuery = executeConsult(consultQueryString);

        return consultQuery;
    }

    /**
     * Returns the consult query string of a given category
     * @param idCategory id of category searched
     * @return string of the database command of consult category
     */
    private String getConsultQueryString(int idCategory){
        final String idCategoryValue = "idCategory = " + idCategory;

        final String consultQueryString = SELECT_NAME_CATEGORY_COMMAND + idCategoryValue;
        return consultQueryString;
    }
}