/**
 * File: PlaceDAO.java
 * Purpose: Establish communication with the database to send and receive data of Brasilia's places
 */

package dao;

import android.app.Activity;
import org.json.JSONObject;

import model.Place;

public class PlaceDAO extends DAO{

    /**
     * Constructor to instantiate a PlaceDAO object
     * @param currentActivity - Current activity to show message of connection problem
     */
    public PlaceDAO(Activity currentActivity){
        super(currentActivity);
    }

    /**
     * Empty constructor required for tests
     */
    public PlaceDAO(){

    }

    /**
     * Searches places by part of name
     * @param name - Part of the name to be searched on places name
     * @return JSONObject - Data of the places found
     */
    public JSONObject searchPlaceByPartName(String name){

        assert name != null;
        assert name.isEmpty() == false;

        final String QUERY = "SELECT * FROM vw_place WHERE namePlace LIKE '%" + name + "%'";

        JSONObject placeByPartNameQueryResult = this.executeConsult(QUERY);

        return placeByPartNameQueryResult;
    }

    /**
     * Searches all places available in database
     * @return JSONObject - Data of the places found
     */
    public JSONObject searchAllPlaces(){

        final String QUERY = "SELECT * FROM vw_place ORDER BY evaluate DESC";

        JSONObject searchAllPlacesQueryResult = this.executeConsult(QUERY);

        return searchAllPlacesQueryResult;
    }

    /**
     * Searches the five events with better evaluation
     * @return JSONObject - Data of the five events found
     */
    public JSONObject searchTop5Places(){

        final String QUERY = "SELECT * FROM vw_place ORDER BY evaluate DESC LIMIT 5";

        JSONObject searchTop5PlacesQueryResult =  this.executeConsult(QUERY);

        return searchTop5PlacesQueryResult;
    }
}
