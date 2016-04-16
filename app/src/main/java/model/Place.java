package model;

import java.text.ParseException;
import java.util.ArrayList;

import exception.PlaceException;
/**
 * Created by geovanni on 09/09/15.
 */
public class Place {

    private int id;
    private String name;
    private ArrayList<String> comment;
    private Float evaluate;
    private Double longitude;
    private Double latitude;
    private String phone;
    private String operation;
    private String description;
    private String address;

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
        if(name.isEmpty()) throw new PlaceException(INVALID_NAME);
        this.name = name;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public void addComment(String comment) throws PlaceException {
        String INVALID_COMMENT = "Hey, o comentario não pode ser vazio";
        if(comment == null)
            throw new PlaceException(INVALID_COMMENT);
        if(comment.isEmpty())
            throw new PlaceException(INVALID_COMMENT);
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
        if(latitude.isEmpty()) throw new PlaceException(INVALID_LATITUDE);
        this.latitude = Double.parseDouble(latitude);
    }

    private void setLongitude(String longitude) throws ParseException, PlaceException {
        String INVALID_LONGITUDE = "Hey, sem a longitude não é possível encontrar o lugar";
        if(longitude.isEmpty()) throw new PlaceException(INVALID_LONGITUDE);
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
