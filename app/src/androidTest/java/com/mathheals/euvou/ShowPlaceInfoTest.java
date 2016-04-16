package com.mathheals.euvou;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.RatingBar;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.hamcrest.Matcher;
import org.junit.Before;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by marlonmendes on 12/11/15.
 */

public class ShowPlaceInfoTest extends ActivityInstrumentationTestCase2<HomePage>{

    private static final String SELECTED_PLACE_NAME = "Parque Ecológico do Tororó";
    private static final int USER_LOGGED_OUT = -1;
    private UiDevice device;
    private boolean isUserLoggedIn;

    public ShowPlaceInfoTest() {
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Activity activity = getActivity();
        setIsUserLoggedIn(new LoginUtility(activity).getUserId() != USER_LOGGED_OUT);
        device = UiDevice.getInstance(getInstrumentation());
    }

    public void testShowPlaceStarting() {
        startShowPlaceInfoForSettedUpPlace();
        onView(withId(R.id.address)).check(matches(isDisplayed()));
    }

    public void testShowMapForSelectedPlace() {
        startShowPlaceInfoForSettedUpPlace();
        onView(withId(R.id.button_show_map)).perform(click());
        onView(withId(R.id.fragment_show_place_info_map)).check(matches(isDisplayed()));
    }

    public void testHideMapForSelectedPlace() {
        startShowPlaceInfoForSettedUpPlace();
        onView(withId(R.id.button_show_map)).perform(click());
        onView(withId(R.id.button_hide_map)).perform(click());
        onView(withId(R.id.fragment_show_place_info_map)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    public void testIfRatingBarIsAvailableForLoggedOutUser() {
        if(isUserLoggedIn) {
            TestUtility.makeUserLogOut();
            isUserLoggedIn = false;
        }
        startShowPlaceInfoForSettedUpPlace();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ratingBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    public void testIfRatingBarIsAvailableForLoggedInUser() {
        boolean result;
        if(!isUserLoggedIn) {
            TestUtility.makeUserLogIn();
            isUserLoggedIn = true;
        }
        startShowPlaceInfoForSettedUpPlace();
        try {int[] ratingNumbersForTest = new int[]{1, 3, 5};

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

    private void clickOnTodosPlaceCategory() {
        onView(withContentDescription("Navigate up")).perform(click());
        onData(hasToString(containsString("")))
                .inAdapterView(withId(R.id.left_drawer_list)).atPosition(0)
                .perform(click());
    }

    private void startShowPlaceInfoForSettedUpPlace() {
        clickOnTodosPlaceCategory();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UiObject marker = device.findObject(new UiSelector().descriptionContains(SELECTED_PLACE_NAME));
        try {
            marker.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setIsUserLoggedIn(boolean isUserLoggedIn) {
        this.isUserLoggedIn = isUserLoggedIn;
    }
}