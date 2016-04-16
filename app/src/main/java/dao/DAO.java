package dao;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public abstract class DAO {

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
        int LIMITCONECTIONTIME = 15000;
        long timeLimit = currentTime + LIMITCONECTIONTIME;
        while(!consult.getIsDoing() && currentTime < timeLimit) {
            currentTime = Calendar.getInstance().getTime().getTime();
        }

        if(limitExceded(timeLimit,currentTime)) {
            Toast.makeText(currentActivity,"Problema de conexão com o servidor (verifique se esta conectado a internet)", Toast.LENGTH_LONG).show();
            return null;
        }


        return consult.getResult();
    }
    public static boolean limitExceded(long timeLimit, long currentTime){

        boolean isLimitExceded = false;

        if(currentTime >= timeLimit) {
            isLimitExceded = true;
        }

        return isLimitExceded;
    }

    protected String executeQuery(String query){
        String URLQUERY = "http://euvou.esy.es/query.php";
        return query(query, URLQUERY);
    }

    protected JSONObject executeConsult(String query)
    {
        String json;
        JSONObject jObject = null;
        try {
            String URLCONSULT = "http://euvou.esy.es/consult.php";
            json = query(query, URLCONSULT);
            jObject  = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jObject;
    }

}