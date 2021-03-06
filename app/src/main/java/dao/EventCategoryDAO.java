/**
 * File: EventCategoryDAO.java
 * Purpose: Establish communication with the database to send and receive the event categories
 */

package dao;

import android.app.Activity;
import org.json.JSONObject;

public class EventCategoryDAO extends DAO{

    /**
     * Constructor to instantiate an EventCategoryDAO object
     * @param currentActivity - Current activity to show message of connection problem
     */
    public EventCategoryDAO(Activity currentActivity){
        super(currentActivity);
    }

    /**
     * Searches the categories of an event with a given identifier
     * @param idEvent - Identifier of the event
     * @return JSONObject - Data of the categories of the event
     */
    public JSONObject searchCategoriesByEventId(int idEvent){

        assert idEvent >= 1;
        assert idEvent <= Integer.MAX_VALUE;

        final String QUERY = "SELECT idCategory FROM event_category WHERE idEvent = " + idEvent;

        JSONObject eventCategoriesQueryResult = this.executeConsult(QUERY);

        return eventCategoriesQueryResult;
    }
}