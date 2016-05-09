/**
 * File: UserEvaluation.java
 * Purpose: Maintain user evaluations data
 */

package model;

import exception.UserEvaluationException;

public class UserEvaluation {
    private Float userRating;
    private Integer idUserLoggedIn;
    private Integer idUserEvaluated;
    public static final String EVALUATION_IS_INVALID = "Hey, a avaliação deve estar entre 0 e 5";
    public static final String USER_ID_IS_INVALID = "O identificador do usuário é inválido";
    public static final String USER_EVALUATED_ID_IS_INVALID = "O identificador do usuário avaliado é inválido";

    /**
     * Constructs user evaluation with given information
     * @param userRating - Rating chosen by the user logged in
     * @param idUserLoggedIn - ID of the user logged in
     * @param idUserEvaluated - ID of the user evaluated
     * @throws UserEvaluationException
     */
    public UserEvaluation(Float userRating, Integer idUserLoggedIn, Integer idUserEvaluated) throws UserEvaluationException{
        setUserRating(userRating);
        setIdUserLoggedIn(idUserLoggedIn);
        setIdUserEvaluated(idUserEvaluated);
    }

    /**
     * Gets the rating chosen by the user logged in
     * @return Float - Rating chosen by the user logged in
     */
    public Float getUserRating(){
        return userRating;
    }

    /**
     * Sets the rating chosen by the user logged in
     * @param userRating - Rating chosen by the user logged in
     * @throws UserEvaluationException
     */
    private void setUserRating(Float userRating) throws UserEvaluationException{
        if(userRating >=0f && userRating <=5f){
            this.userRating = userRating;
        }
        else{
            throw new UserEvaluationException(EVALUATION_IS_INVALID);
        }
    }

    /**
     * Gets the ID of the user logged in
     * @return Integer - ID of the user logged in
     */
    public Integer getIdUserLoggedIn(){
        return idUserLoggedIn;
    }

    /**
     * Sets the ID of the user logged in
     * @param idUserLoggedIn - ID of the user logged in
     * @throws UserEvaluationException
     */
    private void setIdUserLoggedIn(Integer idUserLoggedIn) throws UserEvaluationException{
        if(idUserLoggedIn <= Integer.MAX_VALUE && idUserLoggedIn >= 1){
            this.idUserLoggedIn = idUserLoggedIn;
        }
        else{
            throw new UserEvaluationException(USER_ID_IS_INVALID);
        }
    }

    /**
     * Gets the ID Of the user evaluated
     * @return Integer - ID of the user evaluated
     */
    public Integer getIdUserEvaluated(){
        return idUserEvaluated;
    }

    /**
     * Sets the ID Of the user evaluated
     * @param idUserEvaluated - ID of the user evaluated
     * @throws UserEvaluationException
     */
    private void setIdUserEvaluated(Integer idUserEvaluated) throws UserEvaluationException{
        if(idUserEvaluated <= Integer.MAX_VALUE && idUserEvaluated >= 1){
            this.idUserEvaluated = idUserEvaluated;
        }
        else{
            throw new UserEvaluationException(USER_EVALUATED_ID_IS_INVALID);
        }
    }
}
