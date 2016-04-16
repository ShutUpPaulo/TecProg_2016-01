package com.mathheals.euvou;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import dao.EventEvaluationDAO;
import dao.UserEvaluationDAO;
import exception.EventEvaluationException;
import exception.UserEvaluationException;
import model.EventEvaluation;
import model.UserEvaluation;

/**
 * Created by izabela on 24/11/15.
 */
public class UserEvaluationDAOTest extends TestCase {

    public void testIfRatingIsBeingSavedCorrectly() {
        final Float RATING = 3.5F;
        final Integer USER_ID = 3;
        final Integer EVALUATED_USER_ID = 1;

        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO();
        try {
            userEvaluationDAO.evaluateUser(new UserEvaluation(RATING, USER_ID, EVALUATED_USER_ID));
        } catch (UserEvaluationException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = userEvaluationDAO.searchUserEvaluation(EVALUATED_USER_ID, USER_ID);

        try {
            Float rating = new Float(jsonObject.getJSONObject("0").getString("grade"));
            assertEquals(rating, RATING);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  testIfUserIdIsBeingSavedCorrectly() {
        final Float RATING = 3.5F;
        final Integer USER_ID = 3;
        final Integer EVALUATED_USER_ID = 1;

        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO();
        try {
            userEvaluationDAO.evaluateUser(new UserEvaluation(RATING, USER_ID, EVALUATED_USER_ID));
        } catch (UserEvaluationException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = userEvaluationDAO.searchUserEvaluation(EVALUATED_USER_ID, USER_ID);

        try {
            Integer userId = jsonObject.getJSONObject("0").getInt("idUser");
            assertEquals(userId, USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  testIfEvaluatedUserIdIsBeingSavedCorrectly() {
        final Float RATING = 3.5F;
        final Integer USER_ID = 3;
        final Integer EVALUATED_USER_ID = 1;

        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO();
        try {
            userEvaluationDAO.evaluateUser(new UserEvaluation(RATING, USER_ID, EVALUATED_USER_ID));
        } catch (UserEvaluationException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = userEvaluationDAO.searchUserEvaluation(EVALUATED_USER_ID, USER_ID);

        try {
            Integer evaluatedUserId = jsonObject.getJSONObject("0").getInt("idUserEvaluated");

            assertEquals(evaluatedUserId, EVALUATED_USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
