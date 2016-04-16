package com.mathheals.euvou;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.RatingBar;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

/**
 * Created by marlonmendes on 20/11/15.
 */
public class SetRating implements ViewAction {

    public SetRating(Integer rating) {
        setRating(rating);
    }
    private Integer rating;
    @Override
    public Matcher<View> getConstraints() {
        Matcher<View> isRatingBarConstraint = isAssignableFrom(RatingBar.class);
        return isRatingBarConstraint;
    }

    @Override
    public String getDescription() {
        return "Custom view action to set rating.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        RatingBar ratingBar = (RatingBar) view;
        ratingBar.setRating(getRating());
    }

    private Integer getRating() {
        return rating;
    }

    private void setRating(Integer rating) {
        this.rating = rating;
    }
}