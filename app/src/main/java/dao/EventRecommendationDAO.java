/*
 *File: EventRecommendationDAO.java
 * Purpose: allows the user to see the recommended events
 */

package dao;

import android.app.Activity;
import org.json.JSONObject;

public class EventRecommendationDAO extends DAO{

    /**
     * Required constructor to instantiate the class
     */
    public EventRecommendationDAO(){}

    /**
     * Required constructor to instantiate the class passing the current activity
     */
    public EventRecommendationDAO(Activity activity){
        super(activity);
    }

    /**
     * Recommends events to an user based on his last events participations.
     * @param idUser - The ID of an user
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject recommendEvents(int idUser){
        String QUERY =
                "SELECT DISTINCT V.idEvent, V.nameEvent,\n" +
                "(SELECT AVG(v.evaluate) FROM participate p \n" +
                "INNER JOIN vw_event v on v.idEvent = p.idEvent \n" +
                "WHERE v.nameCategory = V.nameCategory AND p.idUser = " + idUser +
                        "  GROUP BY v.nameCategory) grade,\n" +
                "SUM((SELECT COUNT(v.nameCategory) FROM participate p \n" +
                "INNER JOIN vw_event v on v.idEvent = p.idEvent \n" +
                "WHERE v.nameCategory = V.nameCategory AND p.idUser = " + idUser +
                        " GROUP BY v.nameCategory)) preference\n" +
                " FROM vw_event V \n" +
                "WHERE \n" +
                "V.dateTimeEvent BETWEEN now() and DATE_ADD(now(), INTERVAL 1 MONTH)\n" +
                "AND\n" +
                "nameCategory in(SELECT nameCategory FROM participate p \n" +
                "INNER JOIN vw_event v on v.idEvent = p.idEvent AND p.idUser = " + idUser + ")\n" +
                "GROUP BY V.idEvent\n" +
                "ORDER BY V.dateTimeEvent DESC,preference DESC, grade DESC\n" +
                "LIMIT 10";

        JSONObject querySearch = this.executeConsult(QUERY);

        assert  querySearch == this.executeConsult(QUERY);
        return querySearch;
    }
}
