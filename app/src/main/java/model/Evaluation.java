/*
 * File: Evaluation.java
 * Purpose: allows evaluate am user or a place
 */
package model;

public class Evaluation{

    private static final float FLOAT_ZERO = 0;
    private static final int INT_ZERO = 0;

    private float grid = FLOAT_ZERO;
    private int idPlace = INT_ZERO;
    private int idUser = INT_ZERO;

    /**
     * Constructs the evaluation with the given information
     * @param idPlace - The ID of the place where the event will happen
     * @param idUser -  The ID of the user
     * @param grid - The grid of the event
     */
    public Evaluation(int idPlace, int idUser, float grid){
        setIdPlace(idPlace);
        setIdUser(idUser);
        setGrid(grid);

        assert getIdPlace() == idPlace;
        assert getIdUser() == idUser;
        assert getGrid() == grid;
    }

    /**
     * Sets the grid of the event
     * @param grid
     */
    private void setGrid(float grid){
        this.grid = grid;
    }

    /**
     * Sets the ID of the user
     * @param idUser
     */
    private  void setIdUser(int idUser){
        this.idUser = idUser;
    }

    /**
     * Sets the ID of the place that will be evaluated
     * @param idPlace
     */
    private void setIdPlace(int idPlace){
        this.idPlace = idPlace;
    }

    /**
     * Gets the grid
     * @return float - grid
     */
    public float getGrid(){
        return grid;
    }

    /**
     * Gets the ID of the place
     * @return int - The place ID
     */
    public int getIdPlace(){
        return idPlace;
    }

    /**
     * Gets the ID of the user
     * @return int - The user ID
     */
    public int getIdUser(){
        return idUser;
    }
}
