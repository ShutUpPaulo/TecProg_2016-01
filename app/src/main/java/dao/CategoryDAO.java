package dao;

import android.app.Activity;

import org.json.JSONObject;

/**
 * Created by julliana on 28/10/15.
 */
public class CategoryDAO extends DAO {

    public CategoryDAO(Activity currentActivity){
        super(currentActivity);
    }

    public JSONObject searchCategoryById(int idCategory){
        JSONObject consultQuery = executeConsult("SELECT nameCategory FROM tb_category WHERE idCategory = " + idCategory);
        return consultQuery;
    }
}