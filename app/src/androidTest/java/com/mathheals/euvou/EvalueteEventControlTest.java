package com.mathheals.euvou;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.junit.Before;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by izabela on 15/11/15.
 */
public class EvalueteEventControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    LoginUtility isLoged;

    public EvalueteEventControlTest() {
            super(HomePage.class);
            }

    @Before
    public void setUp() throws Exception {
            super.setUp();
            getActivity();
            isLoged = new LoginUtility(getActivity());
    }

    public void testIfEditUserOptionIsDisplayedForUserLoggedOut() {
        if(isLoged.hasUserLoggedIn()){
            isLoged.setUserLogOff();
        }
        searchForEventUsedForTest();
        closeSoftKeyboard();
        assertFalse(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE).matches(onView(withId(R.id.ratingBar))));
    }

    public void testIfEvaluetationIsDisplayedForUserLoggedIn() {
        if (!isLoged.hasUserLoggedIn()) {
            isLoged.setUserLogIn(3);
        }
        searchForEventUsedForTest();
        closeSoftKeyboard();
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
    }

    public void testEvaluateSetInRatingBar(){
        boolean result;

        if (!isLoged.hasUserLoggedIn()) {
            isLoged.setUserLogIn(3);
        }

        searchForEventUsedForTest();
        closeSoftKeyboard();
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

    private void searchForEventUsedForTest(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.radio_events)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("t"), pressKey(66));
        onData(hasToString(containsString("Teste")))
                .inAdapterView(withId(R.id.events_list)).atPosition(0)
                .perform(click());
    }
}
