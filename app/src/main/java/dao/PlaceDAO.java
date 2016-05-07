/**
 * File: PlaceDAO.java
 * Purpose: Establish communication with the database to send and receive data of Brasilia's places
 */

package dao;

import org.json.JSONObject;

public class PlaceDAO extends DAO{

    public PlaceDAO(){
    }

    public JSONObject searchPlaceByPartName(String name){

        assert name.isEmpty() == false;

        final String QUERY = "SELECT * FROM vw_place WHERE namePlace LIKE '%" + name + "%'";

        JSONObject placeByPartNameQueryResult = this.executeConsult(QUERY);

        return placeByPartNameQueryResult;
    }

    public JSONObject searchAllPlaces(){

        final String QUERY = "SELECT * FROM vw_place ORDER BY evaluate DESC";

        JSONObject searchAllPlacesQueryResult = this.executeConsult(QUERY);

        return searchAllPlacesQueryResult;
    }

    public JSONObject searchTop5Places(){

        final String QUERY = "SELECT * FROM vw_place ORDER BY evaluate DESC LIMIT 5";

        JSONObject searchTop5PlacesQueryResult =  this.executeConsult(QUERY);

        return searchTop5PlacesQueryResult;
    }
}
