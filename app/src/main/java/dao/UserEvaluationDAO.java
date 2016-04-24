package dao;

import org.json.JSONObject;

import model.UserEvaluation;

public class UserEvaluationDAO extends DAO{

    public UserEvaluationDAO(){

    }

    public void evaluateUser(UserEvaluation evaluation){

        assert evaluation.getUserEvaluatedId() >=1;
        assert evaluation.getUserId() >=1;
        assert evaluation.getUserEvaluatedId() <= Integer.MAX_VALUE;
        assert evaluation.getUserId() <= Integer.MAX_VALUE;
        assert evaluation.getRating() >= 0F;
        assert evaluation.getRating() <= 5F;

        JSONObject findEvaluation = searchUserEvaluation(evaluation.getUserEvaluatedId(),
                evaluation.getUserId());

        final String QUERY;

        if(findEvaluation!=null){
            QUERY = "UPDATE evaluate_user SET grade = \"" +evaluation.getRating() + "\" " +
                    "WHERE idUserEvaluated = \"" + evaluation.getUserEvaluatedId() + "\" " +
                    "AND idUser = \"" + evaluation.getUserId() + "\"";
        }
        else{
            QUERY = "INSERT INTO evaluate_user(grade, idUser, idUserEvaluated) VALUES (" +
                    "\"" + evaluation.getRating() + "\"," +
                    "\"" + evaluation.getUserId() + "\"," +
                    "\"" + evaluation.getUserEvaluatedId() + "\")";
        }

        executeQuery(QUERY);
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
