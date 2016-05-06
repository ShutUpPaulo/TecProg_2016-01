package model;

import java.text.ParseException;
import java.util.ArrayList;

import exception.PlaceException;
/**
 * Created by geovanni on 09/09/15.
 */
public class Place {

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

    public Place(String name, String evaluate, String longitude, String latitude,
                 String operation, String description, String address, String phone) throws PlaceException, ParseException {
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

    public Place(int id, String name, String evaluate, String longitude, String latitude,
        String operation, String description, String address, String phone) throws PlaceException, ParseException {
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

    private void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) throws PlaceException {
        String INVALID_NAME = "Hey, nome invalido";

        if(name.isEmpty()){
            throw new PlaceException(INVALID_NAME);
        }else{
           //nothing to do
        }

        this.name = name;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public void addComment(String comment) throws PlaceException {
        String INVALID_COMMENT = "Hey, o comentario não pode ser vazio";

        if(comment == null) {
            throw new PlaceException(INVALID_COMMENT);
        }else{
            //nothing to do
        }

        if(comment.isEmpty()) {
            throw new PlaceException(INVALID_COMMENT);
        }else{
            //nothing to do
        }

        this.comment.add(comment);
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    private void setLatitude(String latitude) throws ParseException, PlaceException{
        String INVALID_LATITUDE = "Hey, sem a latitude não é possível encontrar o lugar";

        if(latitude.isEmpty()){
            throw new PlaceException(INVALID_LATITUDE);
        }else{
            //nothing to do
        }

        this.latitude = Double.parseDouble(latitude);
    }

    private void setLongitude(String longitude) throws ParseException, PlaceException {
        String INVALID_LONGITUDE = "Hey, sem a longitude não é possível encontrar o lugar";

        if(longitude.isEmpty()){
            throw new PlaceException(INVALID_LONGITUDE);
        }else{
            //nothing to do
        }

        this.longitude = Double.parseDouble(longitude);
    }

    private void setEvaluate(String evaluate) throws NumberFormatException{
        if(evaluate.equals("null")) {
            this.evaluate = 0.0F;
        } else {
            this.evaluate = Float.parseFloat(evaluate);
        }
    }

    private void setOperation(String operation) {
        this.operation = operation;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public Float getEvaluate() {
        return evaluate;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

}
