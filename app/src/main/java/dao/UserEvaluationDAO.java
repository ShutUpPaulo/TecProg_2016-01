/**
 * File: UserEvaluationDAO.java
 * Purpose: Establish communication with the database to send and receive the user evaluation
 */

package dao;

import org.json.JSONObject;
import model.UserEvaluation;

public class UserEvaluationDAO extends DAO{

    public UserEvaluationDAO(){

    }

    public void evaluateUser(UserEvaluation evaluation){

        assert evaluation.getIdUserEvaluated() >=1;
        assert evaluation.getIdUserLoggedIn() >=1;
        assert evaluation.getIdUserEvaluated() <= Integer.MAX_VALUE;
        assert evaluation.getIdUserLoggedIn() <= Integer.MAX_VALUE;
        assert evaluation.getUserRating() >= 0F;
        assert evaluation.getUserRating() <= 5F;

        JSONObject userEvaluationInDataBase = searchUserEvaluation(evaluation.getIdUserEvaluated(),
                evaluation.getIdUserLoggedIn());

        String query;

        if(userEvaluationInDataBase!=null){
            query = "UPDATE evaluate_user SET grade = \"" +evaluation.getUserRating() + "\" " +
                    "WHERE idUserEvaluated = \"" + evaluation.getIdUserEvaluated() + "\" " +
                    "AND idUser = \"" + evaluation.getIdUserLoggedIn() + "\"";
        }
        else{
            query = "INSERT INTO evaluate_user(grade, idUser, idUserEvaluated) VALUES (" +
                    "\"" + evaluation.getUserRating() + "\"," +
                    "\"" + evaluation.getIdUserLoggedIn() + "\"," +
                    "\"" + evaluation.getIdUserEvaluated() + "\")";
        }

        executeQuery(query);
    }

    public JSONObject searchUserEvaluation(int userEvaluatedtId, int userId){

        assert userEvaluatedtId >= 1;
        assert userEvaluatedtId <= Integer.MAX_VALUE;
        assert userId >= 1;
        assert userId <=Integer.MAX_VALUE;

        final String QUERY = "SELECT * FROM evaluate_user WHERE idUser = \"" + userId
                + "\" AND idUserEvaluated = " + userEvaluatedtId;

        return executeConsult(QUERY);
    }
}
