package com.mathheals.euvou;

import android.app.usage.UsageEvents;

import junit.framework.TestCase;

import java.text.ParseException;
import java.util.Vector;

import dao.EventDAO;
import exception.EventException;
import model.Event;

public class EventTest extends TestCase{
    private  Event event;

    public void testIfCategoryIsNull () {
        boolean ok = true;
        try{
            event = new Event(3,"Swing na casa do Miranda",  10010, "casa do miranda", "25/10/2016", "e trenzinho e eu fico no meio", "1", "2", null);
        }
        catch (EventException e){
            ok = false;
        }
        catch (ParseException e){
            ok = false;
        }

        assertFalse(ok);
    }

    public void testIfCategoryIsEmpty() {
        Vector<String> categories = new Vector<>();
        boolean ok = true;
        try {
            event = new Event(3,"Swing na casa do Miranda", 10010, "casa do miranda", "25/10/2016", "e trenzinho e eu fico no meio","1", "2", categories);
        } catch(EventException e) {
            ok = false;
        }
        catch (ParseException e){
            ok = false;
        }
        assertFalse(ok);
    }

    public void testIfCategoryIsNotNull () {
        boolean ok = true;
        Vector<String> categories = new Vector<String >();
        categories.add("Museus");
        try{
            event = new Event(3,"Swing na casa do Miranda", 10010, "casa do miranda", "25/10/2016", "e trenzinho e eu fico no meio", "1", "2", categories);
        }
        catch (EventException e){
            ok = false;
        }
        catch (ParseException e){
            ok = false;
        }

        assertTrue(ok);
    }

    public void testNameEmpty() throws ParseException {
        Vector<String> categories = new Vector<String >();
        categories.add("Educacao");
        boolean ok = true;
        try{
            event = new Event(3,"", 10010, "casa do miranda", "25/10/2016", "e trenzinho e eu fico no meio", "50.01","60.002", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }
    public void testNameIsNotEmpty()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Balada");
        boolean ok = true;
        try{
            event =  new Event(3,"Geovanni", 10010, "casa do miranda", "25/10/2016", "e trenzinho e eu fico no meio","21.4","30.2", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertTrue(ok);
    }
    public void testNameIsBiggerThanMax()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Museus");
        boolean ok = true;
        try
        {
            event = new Event(3,"Joãozinho da Silva Gosta da Dilma, venham conhecer esse jovem muito jovem", 10010, "casa do miranda", "25/10/2016", "e trenzinho e eu fico no meio","50.8","60.2", categories);

        } catch (EventException e) {
            e.printStackTrace();
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }
    /*public void testIfDateIsEmpty()  {
        boolean ok = true;
        Vector<String> categories = new Vector<String>();
        categories.add("Exposicao");
        try {
            event = new Event(3,"festa2", "", "festa top","34.0","34.0", categories);
        } catch (EventException e) {
            ok=false;
        }catch(ParseException ex)
        {
            ok=false;
        }
        assertFalse(ok);
    }*/

    public void testIfDescriptionIsEmpty()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Show");
        boolean ok = true;
        try{
            event = new Event(3,"FG Party", 10010, "casa do miranda", "25/10/2016", "","44.2","46.2", categories);
            ok = true;

        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok=false;
        }
        assertFalse(ok);
    }

    public void testIfDescriptionIsNotEmpty()
    {
        Vector<String> categories = new Vector<String>();
        categories.add("Cinema");
        boolean ok = true;
        try{

            event = new Event(3,"FG a Party",10010, "FGA","10/10/2016","Venha se perder com a gente!", "44.2","65.2", categories);
        }catch(EventException e){
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertTrue(ok);
    }

    public void testifDescriptionGoesOverTheMaximumValue()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Teatro");
        boolean ok = true;
        try
        {
            event = new Event(3,"FG a Party",10010, "FGA","10/10/2016","kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", "55.2","79.9", categories);

        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }

    public void testIfLatitudeIsUnderMinus90()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Esportes");
        boolean ok = true;
        try
        {
            event = new Event(3,"Evento teste",10010, "endereco", "12/12/2016", "Descrição","-90.9","140.2", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }

    public void testIfLatitudeIsAbove90()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Museus");
        boolean ok = true;
        try
        {
            event = new Event(3,"Evento teste",10010, "endereco", "12/12/2016", "Descrição","99.2","130.2", categories);
        }catch(EventException e)
        {
            ok = false;
        }
        catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }

    public void testIfLatitudeIsOk()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Museus");
        boolean ok = true;
        try
        {
            event = new Event(3,"Evento teste",10010, "endereco", "12/12/2016", "Descrição","-40.9","140.2", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertTrue(ok);
    }
    public void testIfLongitudeIsUnderMinus180()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Outros");
        boolean ok = true;
        try
        {
            event = new Event(3,"Evento teste",10010, "endereco", "12/12/2016", "Descrição","90.0","-181.1", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }

    public void testIfLongitudeIsAbove180()
    {
        Vector<String> categories = new Vector<String >();
        categories.add("Museus");
        boolean ok = true;
        try
        {
            event = new Event(3,"Evento teste",10010, "endereco", "12/12/2016", "Descrição","19.2","190.2", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException ex)
        {
            ok = false;
        }
        assertFalse(ok);
    }

    public void testIfLongitudeIsOk()
    {
        Vector<String> categories = new Vector<>();
        categories.add("Museus");
        boolean ok = true;
        try
        {
            event = new Event(3,"Evento teste", 10010, "endereco", "12/12/2016", "Descrição", "1", "2", categories);
        }catch(EventException e)
        {
            ok = false;
        }catch(ParseException parse) {
            ok = false;
        }

        assertTrue(ok);
    }

    /*public void testIfDateHasPassed()  {
        boolean ok = true;
        Vector<String> categories = new Vector<String>();
        categories.add("Exposicao");
        try {
            event = new Event(1, "festa2", "20/01/2000", "festa top","34.0","34.0", categories);
        } catch (EventException e) {
            ok=false;
        }catch(ParseException ex)
        {
            ok=false;
        }
        assertFalse(ok);
    }*/

    public void testEventConstructorWithId() throws ParseException, EventException {
        Vector<String> categories = new Vector<>();
        categories.add("Esporte");
        Event event = new Event(1,"nome",10010, "endereco", "14/02/2020 20:15:10","DESCRICAO", "12.20","78.41520", categories);
        assertEquals("nome",event.getNameEvent());
    }

}