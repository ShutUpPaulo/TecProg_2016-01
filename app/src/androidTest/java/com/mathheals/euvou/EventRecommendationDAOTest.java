package com.mathheals.euvou;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import dao.EventRecommendationDAO;

/**
 * Created by igor on 29/11/15.
 */
public class EventRecommendationDAOTest extends TestCase {
    public void testRecommendEvent(){
        EventRecommendationDAO eventRecommendationDAO = new EventRecommendationDAO();

        JSONObject jsonObject = eventRecommendationDAO.recommendEvents(3);

        boolean check;
        try {
            jsonObject.getJSONObject("0").getString("nameEvent");

            check = true;
        } catch (JSONException e) {
            check = false;

            e.printStackTrace();
        }

        assertTrue(check);
    }
}
