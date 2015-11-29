package com.mathheals.euvou;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.junit.Before;

import model.Event;
import model.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by izabela on 29/11/15.
 */
public class EventRegistrationControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    private LoginUtility isLoged;
    private TestUtility setLogin;
    private UiDevice device;
    private Event event;

    public EventRegistrationControlTest(){
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        isLoged = new LoginUtility(getActivity());
        device = UiDevice.getInstance(getInstrumentation());
    }

    public void testRegisterEventButtonWithEmptyAddress(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar Evento")).perform(click());
        onView(withId(R.id.eventName)).perform(typeText("Cine Drive-In"));
        onView(withId(R.id.eventDate)).perform(typeText("12/12/2015"));
        onView(withId(R.id.eventHour)).perform(typeText("20:00"));
        onView(withId(R.id.optionCinema)).perform(click());
        onView(withId(R.id.eventPriceReal)).perform(typeText("05"));
        onView(withId(R.id.eventPriceDecimal)).perform(typeText("00"));
        onView(withText("Cadastrar")).perform(scrollTo());
        UiObject marker = device.findObject(new UiSelector().textContains("Cadastrar"));
        try {
            marker.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.eventAddress)).check(matches(hasErrorText(event.ADDRESS_IS_EMPTY)));
    }


}