package dao;

import android.app.Activity;

import org.json.JSONObject;

import model.UserEvaluation;

/**
 * Created by igor on 20/11/15.
 */
public class UserEvaluationDAO extends DAO{
    public UserEvaluationDAO() {}

    public UserEvaluationDAO(Activity activity) {
        super(activity);
    }

    public void evaluateUser(UserEvaluation evaluation) {
        final String QUERY;

        JSONObject findEvaluation = searchUserEvaluation(evaluation.getUserEvaluatedId(), evaluation.getUserId());

        if(findEvaluation==null) {
            QUERY = "INSERT INTO evaluate_user(grade, idUser, idUserEvaluated) VALUES (\"" + evaluation.getRating() + "\"," +
                    "\"" + evaluation.getUserId() + "\"," +
                    "\"" + evaluation.getUserEvaluatedId() + "\")";
        }else{
            QUERY = "UPDATE evaluate_user SET grade = \"" +evaluation.getRating() + "\" " +
                    "WHERE idUserEvaluated = \"" + evaluation.getUserEvaluatedId() + "\" " +
                    "AND idUser = \"" + evaluation.getUserId() + "\"";
        }

        executeQuery(QUERY);
    }

    public JSONObject searchUserEvaluation(int userEvaluatedtId, int userId) {
        final String QUERY = "SELECT * FROM evaluate_user WHERE idUser = \"" + userId
                + "\" AND idUserEvaluated = " + userEvaluatedtId;
        return executeConsult(QUERY);
    }
}
