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
    private static final String SELECT_EVALUATE_PLACE_COMMAND =
            "SELECT * FROM evaluate_place WHERE ";

    private static final String UPDATE_EVALUATE_PLACE_COMMAND =
            "UPDATE evaluate_place SET ";

    private static final String INSERT_EVALUATE_PLACE_COMMAND =
            "INSERT INTO evaluate_place(grade, idUser, idPlace) VALUES (";



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

        String consultEvaluation =
                getConsultEvaluationQuery(evaluation.getIdUser(), evaluation.getIdPlace());

        JSONObject findEvaluation = executeConsult(consultEvaluation);

        if(findEvaluation != null){
            evaluationQuery = getUpdateEvaluationQuery(evaluation);
        }else{
            evaluationQuery = getInsertEvaluationQuery(evaluation);
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

        String searchPlaceEvaluationQuery = getConsultEvaluationQuery(userId, placeId);
        JSONObject consultJsonObject = executeConsult(searchPlaceEvaluationQuery);
        return consultJsonObject;
    }

    /**
     * Returns string of update query of a given evaluation
     * @param evaluation Evaluation to be updated
     * @return String of the database command of update
     */
    private String getUpdateEvaluationQuery(Evaluation evaluation){
        final String gradeValue = "grade = \"" + evaluation.getGrid() + "\" ";
        final String idPlaceCondition = "WHERE idPlace = \"" + evaluation.getIdPlace() + "\"";
        final String idUserCondition = "AND idUser = \"" + evaluation.getIdUser() + "\"";

        final String updateEvaluationQuery =
                UPDATE_EVALUATE_PLACE_COMMAND  + gradeValue + idPlaceCondition + idUserCondition;

        return updateEvaluationQuery;
    }

    /**
     * Returns string of update query of a given evaluation
     * @param evaluation Evaluation to be updated
     * @return String of the database command of update evaluation
     */
    private String getInsertEvaluationQuery(Evaluation evaluation){
        final String gradeValue = "\"" + evaluation.getGrid() + "\",";
        final String userValue = "\"" + evaluation.getIdUser() + "\",";
        final String placeValue = "\"" + evaluation.getIdPlace() + "\")";

        final String insertEvaluationQuery =
                INSERT_EVALUATE_PLACE_COMMAND + gradeValue + userValue + placeValue;

        return insertEvaluationQuery;
    }

    /**
     * Returns string of consult query of a given evaluation
     * @param idUser id of user of the evaluation
     * @param idPlace id of place of the evaluation
     * @return String of the database command of select evaluation
     */
    private String getConsultEvaluationQuery(int idUser, int idPlace){
        final String idPlaceCondition = "idPlace = \"" + idPlace + "\" ";
        final String idUserCondition = "AND idUser = \"" + idUser + "\"";


        final String consultEvaluationQuery =
                SELECT_EVALUATE_PLACE_COMMAND + idPlaceCondition + idUserCondition;

        return consultEvaluationQuery;
    }
}