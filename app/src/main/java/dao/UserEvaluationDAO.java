/**
 * File: UserEvaluationDAO.java
 * Purpose: Establish communication with the database to send and receive the user evaluation
 */

package dao;

import android.support.v4.app.FragmentActivity;
import org.json.JSONObject;
import model.UserEvaluation;

public class UserEvaluationDAO extends DAO{

    /**
     * Empty constructor required in DAO tests
     */
    public UserEvaluationDAO(){
    }

    /**
     * UserEvaluationDAO constructor
     * @param currentActivity - Current activity to show message of connection problem
     */
    public UserEvaluationDAO(FragmentActivity currentActivity){
        super(currentActivity);
    }

    /**
     * Saves an user evaluation at database
     * @param evaluation - Evaluation given to the user (>=0 ... <=5)
     */
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

        //The main flow is the user doesn't have an evaluation
        if(userEvaluationInDataBase == null){
            query = "INSERT INTO evaluate_user(grade, idUser, idUserEvaluated) VALUES (" +
                    "\"" + evaluation.getUserRating() + "\"," +
                    "\"" + evaluation.getIdUserLoggedIn() + "\"," +
                    "\"" + evaluation.getIdUserEvaluated() + "\")";
        }
        else{
            query = "UPDATE evaluate_user SET grade = \"" +evaluation.getUserRating() + "\" " +
                    "WHERE idUserEvaluated = \"" + evaluation.getIdUserEvaluated() + "\" " +
                    "AND idUser = \"" + evaluation.getIdUserLoggedIn() + "\"";

        }

        executeQuery(query);
    }

    /**
     * Searches the evaluation of an user, given by another user
     * @param userEvaluatedtId - Identifier of the user to be evaluated
     * @param userId - Identifier of the evaluator user
     * @return JSONObject - Data of the user evaluation given by the evaluator user
     */
    public JSONObject searchUserEvaluation(int userEvaluatedtId, int userId){

        assert userEvaluatedtId >= 1;
        assert userEvaluatedtId <= Integer.MAX_VALUE;
        assert userId >= 1;
        assert userId <=Integer.MAX_VALUE;

        final String QUERY = "SELECT * FROM evaluate_user WHERE idUser = \"" + userId
                + "\" AND idUserEvaluated = " + userEvaluatedtId;

        JSONObject userEvaluation = executeConsult(QUERY);

        return userEvaluation;
    }
}
