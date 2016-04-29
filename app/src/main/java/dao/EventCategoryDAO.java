/**
 * File: EventCategoryDAO.java
 * Purpose: Establish communication with the database to send and receive the event categories
 */

package dao;

import android.app.Activity;
import org.json.JSONObject;

public class EventCategoryDAO extends DAO{

    public EventCategoryDAO(Activity currentActivity){
        super(currentActivity);
    }

    public JSONObject searchCategoriesByEventId(int idEvent){

        assert idEvent >= 1;
        assert idEvent <= Integer.MAX_VALUE;

        final String QUERY = "SELECT idCategory FROM event_category WHERE idEvent = " + idEvent;

        JSONObject eventCategoriesQueryResult = this.executeConsult(QUERY);

        return eventCategoriesQueryResult;
    }
}