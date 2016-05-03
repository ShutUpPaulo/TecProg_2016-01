package dao;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public abstract class DAO {

    private final int LIMIT_CONNECTION_TIME = 15000;
    private final String URL_QUERY = "http://euvou.esy.es/query.php";
    private final String URL_CONSULT = "http://euvou.esy.es/consult.php";
    private final String CONNECTION_PROBLEM_MESSAGE =
            "Problema de conexão com o servidor (verifique se esta conectado a internet)";

    private Activity currentActivity;

    public DAO(Activity currentActivity){
        this.currentActivity = currentActivity;
    }

    public DAO(){

    }

    private String processQuery(String query,String urlQuery) {
        Consult consult = new Consult(query,urlQuery);
        consult.exec();

        long currentTime = Calendar.getInstance().getTime().getTime();

        long timeLimit = currentTime + LIMIT_CONNECTION_TIME;

        while(!consult.getIsDoing() && currentTime < timeLimit) {
            currentTime = Calendar.getInstance().getTime().getTime();
        }

        if(limitExceeded(timeLimit,currentTime)) {
            Toast.makeText(currentActivity,CONNECTION_PROBLEM_MESSAGE, Toast.LENGTH_LONG).show();
            return null;
        }else{
            //nothing to do
        }

        return consult.getResult();
    }
    public static boolean limitExceeded(long timeLimit, long currentTime){
        boolean isLimitExceeded = false;

        if(currentTime >= timeLimit) {
            isLimitExceeded = true;
        }else{
            //nothing to do
        }

        return isLimitExceeded;
    }

    protected String executeQuery(String query){
        String executedQuery = processQuery(query, URL_QUERY);
        return executedQuery;
    }

    protected JSONObject executeConsult(String query) {
        String consultJson;
        JSONObject jsonObject = null;
        try {
            consultJson = processQuery(query, URL_CONSULT);
            jsonObject  = new JSONObject(consultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}