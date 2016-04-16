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
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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

    public void testCategoriesCheckBox(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar Evento")).perform(click());
        onView(withId(R.id.optionShow)).perform(click());
        onView(withId(R.id.optionShow)).check(matches(isChecked()));
        onView(withId(R.id.optionCinema)).perform(click());
        onView(withId(R.id.optionCinema)).check(matches(isChecked()));
        onView(withId(R.id.optionEducation)).perform(click());
        onView(withId(R.id.optionEducation)).check(matches(isChecked()));
        onView(withId(R.id.optionExposition)).perform(click());
        onView(withId(R.id.optionExposition)).check(matches(isChecked()));
        onView(withId(R.id.optionMuseum)).perform(click());
        onView(withId(R.id.optionMuseum)).check(matches(isChecked()));
        onView(withId(R.id.optionOthers)).perform(click());
        onView(withId(R.id.optionOthers)).check(matches(isChecked()));
        onView(withId(R.id.optionSports)).perform(click());
        onView(withId(R.id.optionSports)).check(matches(isChecked()));
        onView(withId(R.id.optionParty)).perform(click());
        onView(withId(R.id.optionParty)).check(matches(isChecked()));
        onView(withId(R.id.optionTheater)).perform(click());
        onView(withId(R.id.optionTheater)).check(matches(isChecked()));
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

    public void testChoosePlaceOnMap() {
        final String SUCESSFULL_CHOICE_MESSAGE = "Local selecionado com sucesso";
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openRegisterEvent();
        onView(withId(R.id.eventLocal)).perform(click());
        onView(withId(R.id.map)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText(SUCESSFULL_CHOICE_MESSAGE))
                .inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    private void openRegisterEvent() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar Evento")).perform(click());
    }
}
