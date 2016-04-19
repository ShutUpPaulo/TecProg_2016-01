package dao;

import android.app.Activity;

import org.json.JSONObject;


public class EventCategoryDAO extends DAO{

    public EventCategoryDAO(Activity currentActivity){
        super(currentActivity);
    }

    public JSONObject searchCategoriesByEventId(int idEvent){

        final String QUERY = "SELECT idCategory FROM event_category WHERE idEvent = " + idEvent;

        JSONObject eventCategoriesQueryResult = this.executeConsult(QUERY);

        return eventCategoriesQueryResult;
    }
}