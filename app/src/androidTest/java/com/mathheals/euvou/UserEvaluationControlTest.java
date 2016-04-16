package com.mathheals.euvou;

import android.app.Activity;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.uiautomator.UiDevice;
import android.test.ActivityInstrumentationTestCase2;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.junit.Before;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by izabela on 23/11/15.
 */
public class UserEvaluationControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    private LoginUtility isLoged;

    public UserEvaluationControlTest(){
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        isLoged = new LoginUtility(getActivity());
    }

    public void testIfRatingBarIsAvailableForLoggedOutUser() {
        if(isLoged.hasUserLoggedIn()) {
            TestUtility.makeUserLogOut();
        }
        searchForUserUsedForTest();
        onView(withId(R.id.ratingBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

    }

    public void testIfRatingBarIsAvailableForLoggedInUser() {
        if(!isLoged.hasUserLoggedIn()) {
            TestUtility.makeUserLogIn();
        }
        searchForUserUsedForTest();
        onView(withId(R.id.ratingBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    public void testIfRatingBarIsSetingEvaluation() {
        boolean result;
        if(!isLoged.hasUserLoggedIn()) {
            TestUtility.makeUserLogIn();
        }
        searchForUserUsedForTest();
        try {
            int[] ratingNumbersForTest = new int[]{1, 3, 5};

            for(Integer ratingNumber : ratingNumbersForTest)
                onView(withId(R.id.ratingBar)).perform(new SetRating(ratingNumber));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = true;
        } catch (PerformException performException) {
            result = false;
        }
        assertTrue(result);

    }

    private void searchForUserUsedForTest(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.radio_people)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("t"), pressKey(66));
        onData(hasToString(containsString("Igor Duarte")))
                .inAdapterView(withId(R.id.events_list)).atPosition(0)
                .perform(click());
        closeSoftKeyboard();
    }
}
