package dao;

import android.app.Activity;

import org.json.JSONObject;

import model.Place;

/**
 * Created by viny on 30/09/15.
 */
public class PlaceDAO extends DAO {

    public PlaceDAO(Activity currentActivity) {
        super(currentActivity);
    }

    public PlaceDAO(){};

    public JSONObject searchPlaceByPartName(String name)
    {
        return this.executeConsult("SELECT * FROM vw_place WHERE namePlace LIKE '%" + name + "%'");
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
