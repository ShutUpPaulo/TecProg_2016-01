/**
 * File: DAO.java
 * Purpose: Process database queries
 */

package dao;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public abstract class DAO {

    private static final int LIMIT_CONNECTION_TIME = 15000;
    private static final String STRING_EMPTY = "";
    private static final String URL_QUERY = "http://euvou.esy.es/query.php";
    private static final String URL_CONSULT = "http://euvou.esy.es/consult.php";
    private static final String CONNECTION_PROBLEM_MESSAGE =
            "Problema de conexão com o servidor (verifique se esta conectado a internet)";

    private Activity currentActivity;

    public DAO(Activity currentActivity){
        this.currentActivity = currentActivity;
    }

    public DAO(){

    }

    private String processQuery(String query,String urlQuery) {
        assert query != null;
        assert urlQuery != null;

        Consult consult = new Consult(query,urlQuery);
        consult.exec();

        boolean isConnectionTimedOut = testConnectionTime(consult);

        String consultAnswer = STRING_EMPTY;
        if(!isConnectionTimedOut){
            consultAnswer = consult.getResult();
        }else{
            consultAnswer = null;
        }

        return consultAnswer;
    }
    public static boolean limitExceeded(long timeLimit, long currentTime){
        assert timeLimit >= 0;
        assert currentTime >= 0;

        boolean isLimitExceeded;

        if (currentTime < timeLimit) {
            isLimitExceeded = false;
        } else {
            isLimitExceeded = true;
        }

        return isLimitExceeded;
    }

    protected String executeQuery(String query){
        assert query != null;

        String executedQuery = processQuery(query, URL_QUERY);
        assert executedQuery != null;

        return executedQuery;
    }

    protected JSONObject executeConsult(String query) {
        assert query != null;

        String consultJson = STRING_EMPTY;

        JSONObject jsonObject = null;

        try {
            consultJson = processQuery(query, URL_CONSULT);
            assert consultJson != null;

            jsonObject  = new JSONObject(consultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private boolean testConnectionTime(Consult consult){
        long currentTime = Calendar.getInstance().getTime().getTime();
        assert currentTime >= 0;

        long timeLimit = currentTime + LIMIT_CONNECTION_TIME;
        assert timeLimit >= 0;

        while(!consult.getIsDoing() && currentTime < timeLimit) {
            currentTime = Calendar.getInstance().getTime().getTime();
        }

        boolean isConnectionTimedOut = false;
        if(limitExceeded(timeLimit,currentTime)) {
            Toast.makeText(currentActivity,CONNECTION_PROBLEM_MESSAGE, Toast.LENGTH_LONG).show();
            isConnectionTimedOut = true;
        }else{
            isConnectionTimedOut = false;
        }

        return isConnectionTimedOut;
    }

}