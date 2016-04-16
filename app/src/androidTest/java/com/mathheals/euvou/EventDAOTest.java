package com.mathheals.euvou;

import android.app.Activity;

import junit.framework.TestCase;

import org.json.JSONException;

import java.text.ParseException;
import java.util.Vector;

import dao.EventDAO;
import exception.EventException;
import model.Event;

/**
 * Created by viny on 30/10/15.
 */
public class EventDAOTest extends TestCase {


    public void testSearchEventByOwner() throws ParseException, EventException, JSONException {
        EventDAO eventDAO = new EventDAO();
        assertEquals(eventDAO.searchEventByOwner(-1), null);
    }

    public void testUpdateEvent() throws ParseException, EventException, JSONException{
        EventDAO eventDAO = new EventDAO();
        Vector<String> category = new Vector<>();
        category.add("Show");
        Event event = new Event(1, "Teste Abacate", 10010, "oi", "00/00/0000", "xablau", "0", "0", category);
        eventDAO.updateEvent(event);
        assertEquals(eventDAO.searchEventById(1).getJSONObject("0").getString("nameEvent"), "Teste Abacate");
        event = new Event(1, "Teste", 10010, "oi", "00/00/0000", "xablau", "0", "0", category);
        eventDAO.updateEvent(event);
    }


    public void testSearchEventByOwner2() throws ParseException, EventException, JSONException {
        EventDAO eventDAO = new EventDAO();
        assertFalse(eventDAO.searchEventByOwner(3).isEmpty());
    }


   public void testEventDelet() throws ParseException, EventException, JSONException {

        EventDAO eventDAO = new EventDAO();
        Vector<String> category = new Vector<>();
        category.add("Outros");

        Event event = new Event(3,"Evento Delete",10010, "FGA","14/02/2017","DESCRICAO", "12.20","78.41520",category);

        eventDAO.saveEvent(event);
        eventDAO.deleteEvent(3);

        assertNull(eventDAO.searchEventByName("Evento Delete"));

    }
}
