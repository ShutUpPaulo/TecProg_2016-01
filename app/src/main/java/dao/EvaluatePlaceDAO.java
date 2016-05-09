/**
 * File: EvaluatePlaceDAO.java
 * Purpose: Process an evaluation and register on database
 */

package dao;

import android.app.Activity;

import org.json.JSONObject;

import model.Evaluation;

public class EvaluatePlaceDAO extends DAO{

    private static final String STRING_EMPTY = "";

    /**
     * Required empty public constructor
     */
    public EvaluatePlaceDAO(){
    }

    /**
     * Constructs based on current Activity
     * @param currentActivity Activity used on construction
     */
    public EvaluatePlaceDAO(Activity currentActivity){
        super(currentActivity);
    }

    /**
     * Evaluates a place
     * @param evaluation Evaluation of the place made by user
     */
    public void evaluatePlace(Evaluation evaluation){
        assert evaluation != null;

        String evaluationQuery = STRING_EMPTY;

        String consultEvaluation = "SELECT * FROM evaluate_place WHERE idPlace = \"" +
                evaluation.getIdPlace() + "\" " + "AND idUser = \"" +
                evaluation.getIdUser() + "\"";

        JSONObject findEvaluation = executeConsult(consultEvaluation);

        if(findEvaluation != null){
            evaluationQuery = "UPDATE evaluate_place SET grade = \"" +
                    evaluation.getGrid() + "\" " + "WHERE idPlace = \"" + evaluation.getIdPlace() +
                    "\" AND idUser = \"" + evaluation.getIdUser() + "\"";
        }else{
            evaluationQuery = "INSERT INTO evaluate_place(grade, idUser, idPlace) VALUES (\"" +
                    evaluation.getGrid() + "\"," + "\"" + evaluation.getIdUser() + "\"," +
                    "\"" + evaluation.getIdPlace() + "\")";
        }

        executeQuery(evaluationQuery);
    }

    /**
     * Search place evaluation of an user
     * @param placeId Id of place evaluated
     * @param userId Id of user who evaluated the place
     * @return JSONObject based on the database consult
     */
    public JSONObject searchPlaceEvaluation(int placeId, int userId){
        assert placeId >= 0;
        assert userId >= 0;

        String searchPlaceEvaluationQuery = "SELECT * FROM evaluate_place WHERE idUser = \"" +
                userId + "\" AND idPlace = " + placeId;

        JSONObject consultJsonObject = executeConsult(searchPlaceEvaluationQuery);
        return consultJsonObject;
    }
}