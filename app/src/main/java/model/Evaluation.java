/*
 * File: Evaluation.java
 * Purpose: allows evaluate am user or a place
 */
package model;

public class Evaluation{

    private float grid;
    private int idPlace;
    private int idUser;

    public Evaluation(int idPlace, int idUser, float grid){
        setIdPlace(idPlace);
        setIdUser(idUser);
        setGrid(grid);
    }

    private void setGrid(float grid){
        this.grid = grid;
    }

    private  void setIdUser(int idUser){
        this.idUser = idUser;
    }

    private void setIdPlace(int idPlace){
        this.idPlace = idPlace;
    }

    public float getGrid(){
        return grid;
    }

    public int getIdPlace(){
        return idPlace;
    }

    public int getIdUser(){
        return idUser;
    }
}
