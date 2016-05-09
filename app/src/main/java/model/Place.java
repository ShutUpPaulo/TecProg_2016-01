/**
 * File: Place.java
 * Purpose: Maintain place data
 */

package model;

import java.text.ParseException;
import java.util.ArrayList;

import exception.PlaceException;

public class Place{

    private static final String STRING_EMPTY = "";
    private static final double DOUBLE_ZERO = 0.0;
    private static final float FLOAT_ZERO = 0.0f;
    private static final int INT_ZERO = 0;

    private int id = INT_ZERO;
    private String name = STRING_EMPTY;
    private ArrayList<String> comment;
    private Float evaluate = FLOAT_ZERO;
    private Double longitude = DOUBLE_ZERO;
    private Double latitude = DOUBLE_ZERO;
    private String phone = STRING_EMPTY;
    private String operation = STRING_EMPTY;
    private String description = STRING_EMPTY;
    private String address = STRING_EMPTY;

    /**
     * Constructs place with given information
     * @param name Place name
     * @param evaluate Place evaluate
     * @param longitude Place longitude
     * @param latitude Place latitude
     * @param operation Place operation
     * @param description Place description
     * @param address Place address
     * @param phone Place phone
     * @throws PlaceException
     * @throws ParseException
     */
    public Place(String name, String evaluate, String longitude, String latitude,
                 String operation, String description, String address, String phone)
            throws PlaceException, ParseException{
        setName(name);
        setEvaluate(evaluate);
        setLongitude(longitude);
        setLatitude(latitude);
        setOperation(operation);
        setDescription(description);
        setAddress(address);
        setPhone(phone);
        comment = new ArrayList<>();
    }

    /**
     * Constructs place with given information and id
     * @param id Place id
     * @param name Place name
     * @param evaluate Place evaluate
     * @param longitude Place longitude
     * @param latitude Place latitude
     * @param operation Place operation
     * @param description Place description
     * @param address Place address
     * @param phone Place phone
     * @throws PlaceException
     * @throws ParseException
     */
    public Place(int id, String name, String evaluate, String longitude, String latitude,
        String operation, String description, String address, String phone)
            throws PlaceException, ParseException{
        setId(id);
        setName(name);
        setEvaluate(evaluate);
        setLongitude(longitude);
        setLatitude(latitude);
        setOperation(operation);
        setDescription(description);
        setAddress(address);
        setPhone(phone);
    }

    /**
     * Sets place address
     * @param address New place address
     */
    private void setAddress(String address){
        this.address = address;
    }

    /**
     * Returns place name
     * @return Place name
     */
    public String getPlaceName(){
        return name;
    }

    /**
     * Sets place name
     * @param name New place name
     * @throws PlaceException
     */
    private void setName(String name) throws PlaceException{
        String INVALID_NAME = "Hey, nome invalido";

        if(!name.isEmpty()){
           //nothing to do
        }else{
            throw new PlaceException(INVALID_NAME);
        }

        this.name = name;
    }

    /**
     * Returns list of comments
     * @return List of place's comments
     */
    public ArrayList<String> getComment(){
        return comment;
    }

    /**
     * Adds a comment in Place data
     * @param comment Comment to be added
     * @throws PlaceException
     */
    public void addComment(String comment) throws PlaceException{
        final String INVALID_COMMENT = "Hey, o comentario não pode ser vazio";

        if(comment != null){
            //nothing to do
        }else{
            throw new PlaceException(INVALID_COMMENT);
        }

        if(!comment.isEmpty()){
            //nothing to do
        }else{
            throw new PlaceException(INVALID_COMMENT);
        }

        this.comment.add(comment);
    }

    /**
     * Returns place longitude
     * @return Place longitude
     */
    public Double getPlaceLongitude(){
        return longitude;
    }

    /**
     * Returns place latitude
     * @return Place latitude
     */
    public Double getPlacetLatitude(){
        return latitude;
    }

    /**
     * Returns place address
     * @return Place address
     */
    public String getPlaceAddress(){
        return address;
    }

    /**
     * Sets place latitude
     * @param latitude New place latitude
     * @throws ParseException
     * @throws PlaceException
     */
    private void setLatitude(String latitude) throws ParseException, PlaceException{
        final String INVALID_LATITUDE = "Hey, sem a latitude não é possível encontrar o lugar";

        if(!latitude.isEmpty()){
            //nothing to do
        }else{
            throw new PlaceException(INVALID_LATITUDE);
        }

        this.latitude = Double.parseDouble(latitude);
    }

    /**
     * Sets place longitude
     * @param longitude New place longitude
     * @throws ParseException
     * @throws PlaceException
     */
    private void setLongitude(String longitude) throws ParseException, PlaceException{
        final String INVALID_LONGITUDE = "Hey, sem a longitude não é possível encontrar o lugar";

        if(!longitude.isEmpty()){
            //nothing to do
        }else{
            throw new PlaceException(INVALID_LONGITUDE);
        }

        this.longitude = Double.parseDouble(longitude);
    }

    /**
     * Sets place evaluate
     * @param evaluate New place evaluate
     * @throws NumberFormatException
     */
    private void setEvaluate(String evaluate) throws NumberFormatException{
        if(!evaluate.equals("null")){
            this.evaluate = Float.parseFloat(evaluate);
        }else{
            this.evaluate = FLOAT_ZERO;
        }
    }

    /**
     * Sets place operation
     * @param operation New place operation
     */
    private void setOperation(String operation){
        this.operation = operation;
    }

    /**
     * Sets place description
     * @param description New place description
     */
    private void setDescription(String description){
        this.description = description;
    }

    /**
     * Returns place operation
     * @return Place operation
     */
    public String getOperation(){
        return operation;
    }

    /**
     * Returns place evaluate
     * @return Place evaluate
     */
    public Float getEvaluate(){
        return evaluate;
    }

    /**
     * Returns place description
     * @return Place description
     */
    public String getPlaceDescription(){
        return description;
    }

    /**
     * Returns place phone
     * @return Place phone
     */
    public String getPlacePhone(){
        return phone;
    }

    /**
     * Sets place phone
     * @param phone New place phone
     */
    private void setPhone(String phone){
        this.phone = phone;
    }

    /**
     * Returns place id
     * @return Place id
     */
    public int getId(){
        return id;
    }

    /**
     * Sets place id
     * @param id New place id
     */
    private void setId(int id){
        this.id = id;
    }
}
