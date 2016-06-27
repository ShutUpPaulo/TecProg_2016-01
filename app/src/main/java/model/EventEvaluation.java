/**
 * File: EventEvaluation.java
 * Purpose: Maintain event evaluations data
 */

package model;

import exception.EventEvaluationException;

public class EventEvaluation {
    private Float rating;
    private Integer userId;
    private Integer eventId;
    public static final String EVALUATION_IS_INVALID = "Hey, a avaliação deve estar entre 0 e 5";
    public static final String USER_ID_IS_INVALID = "O identificador do usuário é inválido";
    public static final String EVENT_ID_IS_INVALID = "O identificador do event é inválido";

    /**
     * Constructs event evaluation with given information
     * @param rating - Event rating
     * @param userId - ID Of the user who is evaluating the event
     * @param eventId - ID of the event evaluated
     * @throws EventEvaluationException
     */
    public EventEvaluation(Float rating, Integer userId, Integer eventId) throws EventEvaluationException {
        setRating(rating);
        setUserId(userId);
        setEventId(eventId);
    }

    /**
     * Gets event rating
     * @return Float - Event rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Sets event rating
     * @param rating - Event rating
     * @throws EventEvaluationException
     */
    private void setRating(Float rating) throws EventEvaluationException {
        if(rating>=0f && rating<=5f) {
            this.rating = rating;
        }
        else{
            throw new EventEvaluationException(EVALUATION_IS_INVALID);
        }
    }

    /**
     * Gets the ID of the user who is evaluating the event
     * @return Integer - ID of the user who is evaluating the event
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who is evaluating the event
     * @param userId - ID of the user who is evaluating the event
     * @throws EventEvaluationException
     */
    private void setUserId(Integer userId) throws EventEvaluationException {
        if(userId <= Integer.MAX_VALUE && userId >= 1) {
            this.userId = userId;
        }
        else{
            throw new EventEvaluationException(USER_ID_IS_INVALID);
        }
    }

    /**
     * Gets the ID of the event evaluated
     * @return Integer - ID of the event evaluated
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * Sets the ID of the event evaluated
     * @param eventId - ID of the event evaluated
     * @throws EventEvaluationException
     */
    private void setEventId(Integer eventId) throws EventEvaluationException {
        if(eventId <= Integer.MAX_VALUE && eventId >= 1) {
            this.eventId = eventId;
        }
        else{
            throw new EventEvaluationException(EVENT_ID_IS_INVALID);
        }
    }
}
