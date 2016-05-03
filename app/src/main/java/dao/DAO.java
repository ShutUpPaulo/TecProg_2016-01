package dao;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public abstract class DAO {

    private final int LIMIT_CONNECTION_TIME = 15000;
    private final String URL_QUERY = "http://euvou.esy.es/query.php";
    private final String URL_CONSULT = "http://euvou.esy.es/consult.php";

    private Activity currentActivity;

    public DAO(Activity currentActivity){
        this.currentActivity = currentActivity;
    }

    public DAO(){

    }

    private String query(String query,String urlQuery)
    {
        Consult consult = new Consult(query,urlQuery);
        consult.exec();

        long currentTime = Calendar.getInstance().getTime().getTime();

        long timeLimit = currentTime + LIMIT_CONNECTION_TIME;
        while(!consult.getIsDoing() && currentTime < timeLimit) {
            currentTime = Calendar.getInstance().getTime().getTime();
        }

        if(limitExceded(timeLimit,currentTime)) {
            Toast.makeText(currentActivity,"Problema de conexão com o servidor (verifique se esta conectado a internet)", Toast.LENGTH_LONG).show();
            return null;
        }else{
            //nothing to do
        }


        return consult.getResult();
    }
    public static boolean limitExceded(long timeLimit, long currentTime){

        boolean isLimitExceeded = false;

        if(currentTime >= timeLimit) {
            isLimitExceeded = true;
        }else{
            //nothing to do
        }

        return isLimitExceeded;
    }

    protected String executeQuery(String query){
        String executedQuery = query(query, URL_QUERY);
        return executedQuery;
    }

    protected JSONObject executeConsult(String query)
    {
        String consultJson;
        JSONObject jsonObject = null;
        try {
            consultJson = query(query, URL_CONSULT);
            jsonObject  = new JSONObject(consultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}