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

    public JSONObject searchAllPlaces()
    {
        return this.executeConsult("SELECT * FROM vw_place ORDER BY evaluate DESC");
    }

    public JSONObject searchTop5Places()
    {
        return this.executeConsult("SELECT * FROM vw_place ORDER BY evaluate DESC LIMIT 5");
    }
}
