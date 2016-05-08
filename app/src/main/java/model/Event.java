/*
 * File: Event.java
 * Purpose: creates an instance to receive the information of events.
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
//confirmar restrições de tamanho
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

    public Event(int idOwner, String nameEvent, int eventEvaluation)
            throws EventException, ParseException{
        setIdOwner(idOwner);
        setNameEvent(nameEvent);
        setEvaluation(eventEvaluation);
    }

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

    public String getDateTimeEvent(){
        return dateTimeEvent;
    }

    private void setDateTimeEvent(String dateTimeEvent){
        this.dateTimeEvent=dateTimeEvent;
    }

    private void setDateTimeEvent(String date, String hour) throws ParseException, EventException{
        if(!date.isEmpty() && date!=null && !hour.isEmpty() && hour!=null){
            try{
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                formatDate.setLenient(false);
                Date eventDate = formatDate.parse(date);

                if(eventDate.after(new Date())){
                    String[] dateEventSplit = date.split("/");
                    date = dateEventSplit[2]+"-"+dateEventSplit[1]+"-"+dateEventSplit[0];
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
            if(date.isEmpty() || date==null){
                throw new EventException(EVENT_DATE_IS_EMPTY);
            }

            if(hour.isEmpty() || hour==null){
                throw new EventException(EVENT_HOUR_IS_EMPTY);
            }
        }

    }

    public Integer getEvaluation(){
        return evaluation;
    }

    private void setEvaluation(Integer evaluation) throws  EventException{
        if(evaluation >=1 && evaluation<=5){
            this.evaluation = evaluation;
        }else{
            throw new EventException(INVALID_EVALUATION);
        }

    }

    private void setPrice(String priceReal, String priceDecimal) throws EventException{
        if(priceReal!=null && priceDecimal!=null && !priceReal.isEmpty() && !priceDecimal.isEmpty()){
            Integer priceEventReal = Integer.parseInt(priceReal);
            Integer priceEventDecimal = Integer.parseInt(priceDecimal);

            Integer price = priceEventReal * 100 + priceEventDecimal;

            this.price=price;
        }else{
            if(priceReal==null || priceReal.isEmpty()){
                throw new EventException(PRICE_REAL_IS_EMPTY);
            }

            if(priceDecimal==null || priceDecimal.isEmpty()){
                throw new EventException(PRICE_DECIMAL_IS_EMPTY);
            }
        }
    }

    private void setPrice(Integer price){
        this.price=price;
    }

    public Integer getPrice(){
        return price;
    }

    public String getAddress(){
        return address;
    }

    private void setAddress(String adress) throws EventException{
        if(!(adress.isEmpty()) && adress!=null){
            this.address = adress;
        }else{
            throw new EventException(ADDRESS_IS_EMPTY);
        }

    }

    public int getIdEvent(){
        return idEvent;
    }

    private void setIdEvent(int idEvent){
        this.idEvent = idEvent;
    }

    public Double getLongitude(){
        return longitude;
    }

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

    public String getNameEvent(){
        return nameEvent;
    }

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

    public Double getLatitude(){
        return latitude;
    }

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

    private void setCategory(Vector<String> category) throws EventException{
        if(category!=null && !category.isEmpty()){
            this.category = category;
        }else{
            throw  new EventException(CATEGORY_IS_INVALID);
        }
    }

    public Vector<String> getCategory(){
        return category;
    }


    public int getIdOwner(){
        return idOwner;
    }

    private void setIdOwner(int idOwner){
        this.idOwner = idOwner;
    }
}