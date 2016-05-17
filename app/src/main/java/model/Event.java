/*
 * File: Event.java
 * Purpose: creates an instance to receive the information of events.
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import exception.EventException;

public class Event{

    public static final String EVENT_NAME_CANT_BE_EMPTY_NAME = "Hey, acho que você está " +
            "esquecendo de nos informar o nome do evento.";
    public static final String NAME_CANT_BE_GREATER_THAN_50 = "Hey, você ultrapassou o número de " +
            "caracteres permitido para o nome do evento, tente novamente.";
    public static final String DESCRIPTION_CANT_BE_EMPTY = "Hey, acho que você esqueceu de " +
            "informar a descrição do evento.";
    public static final String DESCRIPTION_CANT_BE_GREATER_THAN = "Hey, o máximo de caracteres" +
            " para descrever um evento é de 500 caracteres";
    private static final String LATITUDE_IS_INVALID = "Hey, você inseriu um número inválido," +
            " a latitude deve ser maior que -90 e menor que 90!";
    private static final String LONGITUDE_IS_INVALID = "Hey, você inseriu um número inválido," +
            " a longitude deve ser maior que -180 e menor que 180";
    private static final String LONGITUDE_IS_EMPTY = "Hey, você deixou a longitude em branco..." +
            " preenche ela aí, vai!";
    private static final String LANTITUDE_IS_EMPTY = "Hey, você deixou a longitude em branco... " +
            "preenche ela aí, vai!";
    private static final String INVALID_EVALUATION = "Hey, você deve avaliar um evento com notas" +
            " de 1 a 5!";
    public static final String ADDRESS_IS_EMPTY = "Hey, você esqueceu de nos informar o endereço" +
            " do evento!";
    public static final String INVALID_EVENT_DATE = "Hey, você informou uma data errada, pay" +
            " attention guy!";
    public static final String EVENT_DATE_IS_EMPTY = "Hey, você esqueceu de informar a data do" +
            " evento, cuidado!";
    public static final String PRICE_REAL_IS_EMPTY = "Hey, você esqueceu de informar a parte" +
            " real do preço";
    public static final String PRICE_DECIMAL_IS_EMPTY = "Hey, você esqueceu de informar a parte" +
            " decimal do preço";
    public static final String INVALID_EVENT_HOUR = "Hey, você informou uma hora inválida";
    public static final String EVENT_HOUR_IS_EMPTY = "Hey, você esqueceu de informar a hora";
    private static final String CATEGORY_IS_INVALID = "Hey, você esqueceu de informar a categoria" +
            " do evento, preenche ela aí vai!";


    private int idEvent;
    private String nameEvent;
    private String dateTimeEvent;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private Integer evaluation;
    private Integer price;
    private Vector<String> category;

    private static final int MAX_LENGTH_NAME = 50;
    private static final int MAX_LENGTH_DESCRIPTION = 500;
    private int idOwner;

    /**
     * Constructs the event with the given informations
     * @param idOwner - The ID of the user that created the event
     * @param nameEvent - Name of the event
     * @param date - Date of the event
     * @param hour - THe hour when the event starts
     * @param priceReal - The price that need to be paid to go to the event
     * @param priceDecimal - The cents that complements the price of the event
     * @param address - The location of the event
     * @param description - Information about what the event is
     * @param latitude - Latitude of the location, used to set position on a map
     * @param longitude - Longitude  of the location, used to set position on a map
     * @param category - Type of the event, ex.: movies, party, exihibition
     * @throws EventException
     * @throws ParseException
     */
    public Event(int idOwner, String nameEvent, String date, String hour, String priceReal,
                 String priceDecimal, String address, String description, String latitude,
                 String longitude, Vector<String> category) throws EventException, ParseException{
        setIdOwner(idOwner);
        setNameEvent(nameEvent);
        setDateTimeEvent(date, hour);
        setPrice(priceReal, priceDecimal);
        setAddress(address);
        setDescription(description);
        setLatitude(latitude);
        setLongitude(longitude);
        setCategory(category);
    }

    /**
     * Constructs the event with the given informations
     * @param idOwner - The ID of the user that created the event
     * @param nameEvent - Name of the event
     * @param dateTimeEvent - The time (day and hour) of the event
     * @param price - The price of the event
     * @param address - The location of the event
     * @param description - Information about what the event is
     * @param latitude - Latitude of the location, used to set position on a map
     * @param longitude - Longitude  of the location, used to set position on a map
     * @param category - Type of the event, ex.: movies, party, exihibition
     * @throws EventException
     * @throws ParseException
     */
    public Event(int idOwner, String nameEvent, String dateTimeEvent, Integer price,
                 String address, String description, String latitude, String longitude,
                 Vector<String> category) throws EventException, ParseException{
        setIdOwner(idOwner);
        setNameEvent(nameEvent);
        setDateTimeEvent(dateTimeEvent);
        setPrice(price);
        setAddress(address);
        setDescription(description);
        setLatitude(latitude);
        setLongitude(longitude);
        setCategory(category);
    }

    /**
     * Constructs the event with the given informations
     * @param idOwner - The ID of the user that created the event
     * @param nameEvent - Name of the event
     * @param eventEvaluation - The evaluation given to an event
     * @throws EventException
     * @throws ParseException
     */
    public Event(int idOwner, String nameEvent, int eventEvaluation)
            throws EventException, ParseException{
        setIdOwner(idOwner);
        setNameEvent(nameEvent);
        setEvaluation(eventEvaluation);
    }

    /**
     * Constructs the event with the given informations
     * @param idEvent - The ID of the event
     * @param idOwner - The ID of the user that created the event
     * @param nameEvent - Name of the event
     * @param dateTimeEvent - The time (day and hour) of the event
     * @param price - The price of the event
     * @param address - The location of the event
     * @param description - Information about what the event is
     * @param latitude - Latitude of the location, used to set position on a map
     * @param longitude - Longitude  of the location, used to set position on a map
     * @throws EventException
     * @throws ParseException
     */
    public Event(int idEvent, int idOwner, String nameEvent, String dateTimeEvent,
                 Integer price, String address, String description, String latitude,
                 String longitude) throws EventException, ParseException{
        setIdEvent(idEvent);
        setIdOwner(idOwner);
        setNameEvent(nameEvent);
        setDateTimeEvent(dateTimeEvent);
        setPrice(price);
        setAddress(address);
        setDescription(description);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Constructs the event with the given informations
     * @param idEvent - The ID of the event
     * @param nameEvent - Name of the event
     * @param address - The location of the event
     * @param price - The price of the event
     * @param dateTimeEvent - The time (day and hour) of the event
     * @param description - Information about what the event is
     * @param latitude - Latitude of the location, used to set position on a map
     * @param longitude - Longitude  of the location, used to set position on a map
     * @param category - Type of the event, ex.: movies, party, exihibition
     * @throws EventException
     * @throws ParseException
     */
    public Event(int idEvent, String nameEvent, Integer price, String address, String dateTimeEvent,
                 String description,String latitude, String longitude, Vector<String> category)
            throws EventException, ParseException{
        setIdEvent(idEvent);
        setNameEvent(nameEvent);
        setAddress(address);
        setPrice(price);
        setDateTimeEvent(dateTimeEvent);
        setDescription(description);
        setLatitude(latitude);
        setLongitude(longitude);
        setCategory(category);
    }

    /**
     * Gets the date and time of the event
     * @return String - The date and time of the event
     */
    public String getDateTimeEvent(){
        return dateTimeEvent;
    }

    /**
     * Sets the date and time of the event
     * @param dateTimeEvent - The date (day and hour) of the event
     */
    private void setDateTimeEvent(String dateTimeEvent){
        this.dateTimeEvent=dateTimeEvent;
    }

    /**
     * Sets the date and time of the event, validating and formating then
     * @param date - The date (day, month and year) of the event
     * @param hour - The hour of the event
     * @throws ParseException
     * @throws EventException
     */
    private void setDateTimeEvent(String date, String hour) throws ParseException, EventException{
        if(!date.isEmpty() && date != null && !hour.isEmpty() && hour != null){
            try{
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                formatDate.setLenient(false);
                Date eventDate = formatDate.parse(date);

                if(eventDate.after(new Date())){
                    String[] dateEventSplit = date.split("/");
                    date = dateEventSplit[2] + "-" + dateEventSplit[1] + "-" + dateEventSplit[0];
                }else{
                    throw new EventException(INVALID_EVENT_DATE);
                }
            }catch(ParseException exception){
                throw new EventException(INVALID_EVENT_DATE);
            }

            try{
                SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
                formatHour.setLenient(false);
                formatHour.parse(hour);

                this.dateTimeEvent = date + " " + hour;
            }catch(ParseException exception){
                throw new EventException(INVALID_EVENT_HOUR);
            }
        }else{
            if(date.isEmpty() || date == null){
                throw new EventException(EVENT_DATE_IS_EMPTY);
            }else if(hour.isEmpty() || hour == null){
                throw new EventException(EVENT_HOUR_IS_EMPTY);
            }
        }
    }

    /**
     * Gets the evaluation given to an event
     * @return int - evaluation of the event
     */
    public Integer getEvaluation(){
        return evaluation;
    }

    /**
     * Sets the evaluation to an event
     * @param evaluation
     * @throws EventException
     */
    private void setEvaluation(Integer evaluation) throws  EventException{
        if(evaluation >=1 && evaluation<=5){
            this.evaluation = evaluation;
        }else{
            throw new EventException(INVALID_EVALUATION);
        }

    }

    /**
     * Sets the price of the event, validanting and formating then
     * @param priceReal
     * @param priceDecimal
     * @throws EventException
     */
    private void setPrice(String priceReal, String priceDecimal) throws EventException{
        if(priceReal!=null && priceDecimal!=null && !priceReal.isEmpty() && !priceDecimal.isEmpty()){
            Integer priceEventReal = Integer.parseInt(priceReal);
            Integer priceEventDecimal = Integer.parseInt(priceDecimal);

            Integer price = priceEventReal * 100 + priceEventDecimal;

            this.price=price;
        }else{
            if(priceReal==null || priceReal.isEmpty()){
                throw new EventException(PRICE_REAL_IS_EMPTY);
            }else if(priceDecimal==null || priceDecimal.isEmpty()){
                throw new EventException(PRICE_DECIMAL_IS_EMPTY);
            }
        }
    }

    /**
     * Sets the total price of the event
     * @param price
     */
    private void setPrice(Integer price){
        this.price=price;
    }

    /**
     * Gets the total price of the event
     * @return int - price of the event
     */
    public Integer getPrice(){
        return price;
    }

    /**
     * Gets the address (location) of the event
     * @return String - address of the event
     */
    public String getAddress(){
        return address;
    }

    /**
     * Sets the address of the event
     * @param adress
     * @throws EventException
     */
    private void setAddress(String adress) throws EventException{
        if(!(adress.isEmpty()) && adress!=null){
            this.address = adress;
        }else{
            throw new EventException(ADDRESS_IS_EMPTY);
        }

    }

    /**
     * Gets the ID of the event
     * @return int - The event ID
     */
    public int getIdEvent(){
        return idEvent;
    }

    /**
     * Sets the ID of the event
     * @param idEvent
     */
    private void setIdEvent(int idEvent){
        this.idEvent = idEvent;
    }

    /**
     * Gets the longitude of the event location
     * @return double - longitude  of the event
     */
    public Double getLongitude(){
        return longitude;
    }

    /**
     * Sets the longitude of the event, validating it
     * @param longitude
     * @throws EventException
     */
    private void setLongitude(String longitude) throws EventException{
        if(!(longitude.toString().isEmpty()) && longitude!=null){
            Double longitudeDouble = Double.parseDouble(longitude);
            if(longitudeDouble >= -180 && longitudeDouble <= 180){
                this.longitude = longitudeDouble;

            }else{
                throw  new EventException(LONGITUDE_IS_INVALID);
            }
        }else{
            throw new EventException(LONGITUDE_IS_EMPTY);
        }

    }

    /**
     * Gets the name of the event
     * @return String - the event name
     */
    public String getNameEvent(){
        return nameEvent;
    }

    /**
     * Sets the name of the event, validating it
     * @param nameEvent
     * @throws EventException
     */
    private void setNameEvent(String nameEvent) throws EventException{
        if(!nameEvent.isEmpty() && nameEvent!= null){

            if(nameEvent.length() <= MAX_LENGTH_NAME){
                this.nameEvent = nameEvent;
            }else{
                throw new EventException(NAME_CANT_BE_GREATER_THAN_50);
            }
        }else{
            throw new EventException(EVENT_NAME_CANT_BE_EMPTY_NAME);
        }
    }

    /**
     * Gets the description of the event
     * @return String - description of the event
     */
    public String getDescription(){
        return description;
    }

    private void setDescription(String description) throws EventException{
        if(!description.isEmpty() && description !=null){
            if(description.length() < MAX_LENGTH_DESCRIPTION){
                this.description = description;
            }else{
                throw new EventException(DESCRIPTION_CANT_BE_GREATER_THAN);
            }
        }else{
            throw new EventException(DESCRIPTION_CANT_BE_EMPTY);
        }

    }

    /**
     * Gets the latitude of the event
     * @return double - latitude of the event
     */
    public Double getLatitude(){
        return latitude;
    }

    /**
     * Sets the latitude of the event, validanting it
     * @param latitude
     * @throws EventException
     */
    private void setLatitude(String latitude) throws EventException{
        if(!(latitude.toString().isEmpty()) && latitude!=null){
            Double latitudeDouble = Double.parseDouble(latitude);
            if(latitudeDouble >= -90 && latitudeDouble <= 90){
                this.latitude = latitudeDouble;
            }else{
                throw  new EventException(LATITUDE_IS_INVALID);
            }
        }else{
            throw  new EventException(LANTITUDE_IS_EMPTY);
        }
    }

    /**
     * Sets the category (type) of the event
     * @param category
     * @throws EventException
     */
    private void setCategory(Vector<String> category) throws EventException{
        if(category!=null && !category.isEmpty()){
            this.category = category;
        }else{
            throw  new EventException(CATEGORY_IS_INVALID);
        }
    }

    /**
     * Gets the category of the event
     * @return Vector <String> -  The category of the event
     */
    public Vector<String> getCategory(){
        return category;
    }

    /**
     * Gets the ID of the creator of the event
     * @return int - ID of the owner (user)
     */
    public int getIdOwner(){
        return idOwner;
    }

    /**
     * Sets the ID of the creator of the event
     * @param idOwner
     */
    private void setIdOwner(int idOwner){
        this.idOwner = idOwner;
    }
}