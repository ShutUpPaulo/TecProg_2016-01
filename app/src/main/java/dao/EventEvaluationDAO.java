/**
 *  File: EventEvaluationDAO.java
 *  Purpose: allows to evaluate events, besides only search through the code to improve th
 *  e interest on a event.
 */

package dao;

import android.app.Activity;

import org.json.JSONObject;

import model.Evaluation;
import model.EventEvaluation;

public class EventEvaluationDAO extends DAO{

    /**
     * Required constructor to instantiate the class
     */
    public EventEvaluationDAO(){}

    /**
     * Required constructor to instantiate the class passing the current activity
     */
    public EventEvaluationDAO(Activity activity){
        super(activity);
    }

    /**
     * Saves an evaluation made in an Event at database
     * @param evaluation - The evaluation given to an Event
     */
    public void evaluateEvent(EventEvaluation evaluation){
        final String QUERY;

        JSONObject findEvaluation = searchEventEvaluation(evaluation.getEventId(),
                                    evaluation.getUserId());

        if(findEvaluation!=null){
            QUERY = "UPDATE participate SET grade = \"" +evaluation.getRating() + "\" " +
                    "WHERE idEvent = \"" + evaluation.getEventId() + "\" AND idUser = \"" +
                    evaluation.getUserId() + "\"";
        }else{
            QUERY = "INSERT INTO participate(grade, idUser, idEvent) VALUES (\"" +
                    evaluation.getRating() + "\"," +
                    "\"" + evaluation.getUserId() + "\"," +
                    "\"" + evaluation.getEventId() + "\")";

        }

        executeQuery(QUERY);
    }

    /**
     * Searches for an Event evaluated
     * @param eventId - The ID number of an Event
     * @param userId -  The ID number of an user
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject searchEventEvaluation(int eventId, int userId){
        final String QUERY = "SELECT * FROM participate WHERE idUser = \"" + userId
                            + "\" AND idEvent = " + eventId;

        JSONObject consultResult = this.executeConsult(QUERY);

        return consultResult;
    }
}
