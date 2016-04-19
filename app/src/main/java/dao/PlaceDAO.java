package dao;

import android.support.v4.app.FragmentActivity;

import org.json.JSONObject;

public class PlaceDAO extends DAO{

    public PlaceDAO(){
        
    }

    public PlaceDAO(FragmentActivity activity){
    }

    public JSONObject searchPlaceByPartName(String name){

        final String QUERY = "SELECT * FROM vw_place WHERE namePlace LIKE '%" + name + "%'";

        JSONObject placeByPartNameQueryResult = this.executeConsult(QUERY);

        return placeByPartNameQueryResult;
    }

    public JSONObject searchAllPlaces(){

        final String QUERY = "SELECT * FROM vw_place ORDER BY evaluate DESC";

        JSONObject searchAllPlacesQueryResult = this.executeConsult(QUERY)
                ;
        return searchAllPlacesQueryResult;
    }

    public JSONObject searchTop5Places(){

        final String QUERY = "SELECT * FROM vw_place ORDER BY evaluate DESC LIMIT 5";

        JSONObject searchTop5PlacesQueryResult =  this.executeConsult(QUERY);

        return searchTop5PlacesQueryResult;
    }
}
