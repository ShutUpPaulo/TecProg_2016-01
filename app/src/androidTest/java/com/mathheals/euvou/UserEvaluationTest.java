package com.mathheals.euvou;

import junit.framework.TestCase;

import java.text.ParseException;

import exception.UserEvaluationException;
import exception.UserException;
import model.User;
import model.UserEvaluation;

/**
 * Created by izabela on 24/11/15.
 */
public class UserEvaluationTest extends TestCase{

    private UserEvaluation userEvaluation;

    public void testUserEvaluationWithValidParameters() {
        boolean isUserEvaluationValid;

        try {
           userEvaluation = new UserEvaluation(3f, 3, 1);
            isUserEvaluationValid = true;
        } catch (UserEvaluationException e) {
           isUserEvaluationValid = false;
        }

        assertTrue(isUserEvaluationValid);

    }

    public void testUserEvaluationWithInvalidUserId() {
        boolean isUserEvaluationValid;

        try {
            userEvaluation = new UserEvaluation(3f, -3, 1);
            isUserEvaluationValid = true;
        } catch (UserEvaluationException e) {
            isUserEvaluationValid = false;
        }

        assertFalse(isUserEvaluationValid);

    }

    public void testUserEvaluationWithInvalidUserEvaluatedId() {
        boolean isUserEvaluationValid;

        try {
            userEvaluation = new UserEvaluation(3f, 3, -1);
            isUserEvaluationValid = true;
        } catch (UserEvaluationException e) {
            isUserEvaluationValid = false;
        }

        assertFalse(isUserEvaluationValid);

    }

    public void testUserEvaluationWithNegativeRating() {
        boolean isUserEvaluationValid;

        try {
            userEvaluation = new UserEvaluation(3f, -3, 1);
            isUserEvaluationValid = true;
        } catch (UserEvaluationException e) {
            isUserEvaluationValid = false;
        }

        assertFalse(isUserEvaluationValid);

    }

    public void testUserEvaluationWithEvaluationBiggerThanFive() {
        boolean isUserEvaluationValid;

        try {
            userEvaluation = new UserEvaluation(6f, 3, 1);
            isUserEvaluationValid = true;
        } catch (UserEvaluationException e) {
            isUserEvaluationValid = false;
        }

        assertFalse(isUserEvaluationValid);

    }



}
