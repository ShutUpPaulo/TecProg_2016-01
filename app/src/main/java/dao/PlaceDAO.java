package dao;

import org.json.JSONObject;

public class PlaceDAO extends DAO{

    public PlaceDAO(){
        
    }

    public JSONObject searchPlaceByPartName(String name){

        final String QUERY = "SELECT * FROM vw_place WHERE namePlace LIKE '%" + name + "%'";

        JSONObject placeByPartNameQueryResult = this.executeConsult(QUERY);

        return placeByPartNameQueryResult;
    }
}
