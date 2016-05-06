package dao;

import android.app.Activity;

import org.json.JSONObject;

import model.Evaluation;

/**
 * Created by marlonmendes on 09/11/15.
 */
public class EvaluatePlaceDAO extends DAO{

    private static final String STRING_EMPTY = "";

    public EvaluatePlaceDAO() {

    }

    public EvaluatePlaceDAO(Activity currentActivity) {
        super(currentActivity);
    }

    public void evaluatePlace(Evaluation evaluation) {
        String evaluationQuery = STRING_EMPTY;

        String consultEvaluation = "SELECT * FROM evaluate_place WHERE idPlace = \"" +
                evaluation.getIdPlace() + "\" " + "AND idUser = \"" +
                evaluation.getIdUser() + "\"";

        JSONObject findEvaluation = executeConsult(consultEvaluation);

        if(findEvaluation==null) {
            evaluationQuery = "INSERT INTO evaluate_place(grade, idUser, idPlace) VALUES (\"" +
                    evaluation.getgrade() + "\"," + "\"" + evaluation.getIdUser() + "\"," +
                    "\"" + evaluation.getIdPlace() + "\")";
        }else{
            evaluationQuery = "UPDATE evaluate_place SET grade = \"" +
                    evaluation.getgrade() + "\" " + "WHERE idPlace = \"" + evaluation.getIdPlace() +
                    "\" AND idUser = \"" + evaluation.getIdUser() + "\"";
        }

        executeQuery(evaluationQuery);
    }


    public JSONObject searchPlaceEvaluation(int placeId, int userId) {
        String searchPlaceEvaluationQuery = "SELECT * FROM evaluate_place WHERE idUser = \"" +
                userId + "\" AND idPlace = " + placeId;
        return executeConsult(searchPlaceEvaluationQuery);
    }
}
