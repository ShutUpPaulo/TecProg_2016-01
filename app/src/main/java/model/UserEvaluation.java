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

    public UserEvaluation(Float userRating, Integer idUserLoggedIn, Integer idUserEvaluated) throws UserEvaluationException {
        setUserRating(userRating);
        setIdUserLoggedIn(idUserLoggedIn);
        setIdUserEvaluated(idUserEvaluated);
    }

    public Float getUserRating() {
        return userRating;
    }

    private void setUserRating(Float userRating) throws UserEvaluationException {
        if(userRating >=0f && userRating <=5f) {
            this.userRating = userRating;
        }
        else{
            throw new UserEvaluationException(EVALUATION_IS_INVALID);
        }
    }

    public Integer getIdUserLoggedIn() {
        return idUserLoggedIn;
    }

    private void setIdUserLoggedIn(Integer idUserLoggedIn) throws UserEvaluationException {
        if(idUserLoggedIn <= Integer.MAX_VALUE && idUserLoggedIn >= 1) {
            this.idUserLoggedIn = idUserLoggedIn;
        }
        else{
            throw new UserEvaluationException(USER_ID_IS_INVALID);
        }
    }

    public Integer getIdUserEvaluated() {
        return idUserEvaluated;
    }

    private void setIdUserEvaluated(Integer idUserEvaluated) throws UserEvaluationException {
        if(idUserEvaluated <= Integer.MAX_VALUE && idUserEvaluated >= 1) {
            this.idUserEvaluated = idUserEvaluated;
        }
        else {
            throw new UserEvaluationException(USER_EVALUATED_ID_IS_INVALID);
        }
    }
}
