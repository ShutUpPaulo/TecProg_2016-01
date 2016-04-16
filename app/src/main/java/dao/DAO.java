package dao;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public abstract class DAO {

    private final String URLQUERY = "http://euvoutimedoamor.webcindario.com/query.php";
    private final String URLCONSULT = "http://euvoutimedoamor.webcindario.com/consult.php";

    private final int LIMITCONECTIONTIME = 15000;
    protected Activity currentActivity;

    public DAO(Activity currentActivity){
        this.currentActivity = currentActivity;
    }

    public DAO(){}

    private String query(String query,String urlQuery)
    {
        Consult consult = new Consult(query,urlQuery);
        consult.exec();

        long currentTime = Calendar.getInstance().getTime().getTime();
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
    public static boolean limitExceded(long timeLimit, long currentTime)
    {
        return (currentTime >= timeLimit);
    }
    protected String executeQuery(String query){
        return query(query, URLQUERY);
    }

    protected JSONObject executeConsult(String query)
    {
        String json;
        JSONObject jObject = null;
        try {
            json = query(query,URLCONSULT);
            jObject  = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jObject;
    }

}