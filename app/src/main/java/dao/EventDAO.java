/**
 * File: EventDAO.java
 * Purpose: Manage the events data at database, searching, updating and deleting when is needed.
 */

package dao;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Vector;

import exception.EventException;
import model.Event;

/**
 * Created by geovanni on 10/10/15.
 */
public class EventDAO extends DAO {

    /**
     * Required constructor to instantiate the class passing the current activity
     */
    public EventDAO(Activity currentActivity){
        super(currentActivity);
    }

    /**
     * Required constructor to instantiate the class
     */
    public EventDAO(){

    }

    /**
     * Saves an event on the DataBase
     * @param event - Object with event data
     */
    public void saveEvent(Event event){
        assert event != null;

        executeQuery("insert into tb_event(nameEvent, idOwner, price, address, dateTimeEvent,description,longitude,latitude) VALUES('" +
                event.getNameEvent() + "', '" + event.getIdOwner() + "', '" + event.getPrice() + "', '" + event.getAddress() + "','" + event.getDateTimeEvent() + "','" + event.getDescription() + "'," +
                "" + event.getLongitude() + "," + event.getLatitude() + ")");

        Vector<String> categories = event.getCategory();
        JSONObject jsonObject = executeConsult("SELECT idEvent FROM tb_event WHERE nameEvent = \"" + event.getNameEvent() + "\"");
        int idEvent = 0;
        try {
            idEvent = jsonObject.getJSONObject("0").getInt("idEvent");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i<categories.size(); i++){
            String query = "INSERT INTO event_category(idEvent, idCategory) VALUES(\"" + idEvent + "\", " +
                    "(SELECT idCategory FROM tb_category WHERE nameCategory = \""+categories.get(i)+"\"))";

            executeQuery(query);
        }

    }

    /**
     * Deletes an event from the DataBase
     * @param idEvent - ID of the event to be deleted
     * @return String - A text confirming if the query was executed with success
     */
    public  String deleteEvent(int idEvent){
        assert idEvent >= 1;

        return this.executeQuery("DELETE FROM tb_event WHERE idEvent ="+idEvent);
    }

    /**
     * Updates an event on the DataBase
     * @param event - Object with event data
     */
    public void updateEvent(Event event)
    {
        assert event != null;

        executeQuery("UPDATE tb_event SET price=\"" + event.getPrice() + "\", address=\"" + event.getAddress() + "\", " +
                "nameEvent=\""+event.getNameEvent()+"\", "+"dateTimeEvent=\""+event.getDateTimeEvent()+
                "\", "+"description=\""+event.getDescription()+"\", "+"longitude=\""+event.getLongitude()+"\", " +
                " "+" latitude=\""+event.getLatitude()+ "\" WHERE idEvent = " + event.getIdEvent());

        executeQuery("deleteUserById from event_category where idEvent ="+event.getIdEvent());

        for (String category : event.getCategory()) {
            String query = "INSERT INTO event_category VALUES("+event.getIdEvent() +","
                    + "(SELECT idCategory FROM tb_category WHERE namecategory = '"+category+"'));";

            executeQuery(query);
        }

    }

    /**
     * Searches events by name
     * @param eventName - Name of the event to be searched
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject searchEventByName(String eventName){
        assert eventName != null;

        return this.executeConsult("SELECT * FROM vw_event WHERE nameEvent LIKE'%"+eventName+"%'");
    }

    /**
     * Searches events regrouping them by name
     * @param eventName - Name of the event to be searched
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject searchEventByNameGroup(String eventName)
    {
        assert eventName != null;

        return this.executeConsult("SELECT * FROM tb_event WHERE nameEvent LIKE'%"+eventName+"%' GROUP BY idEvent");
    }

    /**
     * Searches events by ID
     * @param idEvent - ID of the event to be searched
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject searchEventById(int idEvent){
        assert idEvent >= 1;

        return this.executeConsult("SELECT * FROM tb_event WHERE idEvent = " + idEvent);
    }

    /**
     * Searches events by owner
     * @param owner - ID of the owner of the events to be searched
     * @return Vector <Event> - Returns a vector of events with the events found
     * @throws JSONException
     * @throws ParseException
     * @throws EventException
     */
    public Vector<Event> searchEventByOwner(int owner) throws JSONException, ParseException, EventException {
        assert owner >= 1;

        JSONObject json = this.executeConsult("SELECT * FROM tb_event WHERE idOwner=" + owner + " GROUP BY idEvent");

        if(json == null) {
            return null;
        }else{
            // Nothing to do
        }

        Vector<Event> events = new Vector<>();

        for (int i = 0; i < json.length(); i++)
        {

            Event event = new Event(json.getJSONObject(""  + i).getInt("idEvent"),
                    json.getJSONObject(""  + i).getInt("idOwner"),
                    json.getJSONObject("" + i).getString("nameEvent"),
                    json.getJSONObject("" + i).getString("dateTimeEvent"),
                    json.getJSONObject("" + i).getInt("price"),
                    json.getJSONObject("" + i).getString("address"),
                    json.getJSONObject(""  + i).getString("description"),
                    json.getJSONObject("" + i).getString("latitude"),
                    json.getJSONObject("" + i).getString("longitude")
            );
            events.add(event);
        }

        return events;
    }

    /**
     * Marks the user's participation in the event
     * @param idUser - ID of the user who will participate of the event
     * @param idEvent - ID of the event
     * @return String - A text confirming if the query was executed with success
     */
    public String markParticipate(int idUser, int idEvent) {
        assert idUser >= 1;
        assert idEvent >= 1;

        return this.executeQuery("INSERT INTO participate(idEvent, idUser) VALUES(" + idEvent + "," + idUser + ");");
    }

    /**
     * Checks if the user passed already marked participation in the event
     * @param idUser - ID of the user
     * @param idEvent - ID of the event
     * @return JSONObject - Returns a JSONObject with the results of the consult
     */
    public JSONObject verifyParticipate(int idUser, int idEvent) {
        assert idUser >= 1;
        assert idEvent >= 1;

        return this.executeConsult("SELECT * FROM participate WHERE idEvent=" + idEvent + " AND idUser=" + idUser);
    }

    /**
     * Marks off the participation of the user in the event
     * @param idUser - ID of the user
     * @param idEvent - ID of the event
     * @return String - A text confirming if the query was executed with success
     */
    public String markOffParticipate(int idUser, int idEvent) {
        return this.executeQuery("DELETE FROM participate WHERE idEvent=" + idEvent + " AND idUser=" + idUser);
    }

    /**
     * Saves an event on the DataBase
     * @param event - Object with event data
     */
    public void saveEventWithId(Event event)
    {
        assert event != null;

        executeQuery("insert into tb_event(idEvent,nameEvent, idOwner, price, address, dateTimeEvent,description,longitude,latitude) VALUES('" +
                event.getIdEvent() + "', '" + event.getNameEvent() + "', '" + event.getIdOwner() + "', '" + event.getPrice() + "', '" + event.getAddress() + "','" + event.getDateTimeEvent() + "','" + event.getDescription() + "'," +
                "" + event.getLongitude() + "," + event.getLatitude() + ")");

        Vector<String> categories = event.getCategory();
        JSONObject jsonObject = executeConsult("SELECT idEvent FROM tb_event WHERE nameEvent = \"" + event.getNameEvent() + "\"");
        int idEvent = 0;
        try {
            idEvent = jsonObject.getJSONObject("0").getInt("idEvent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
