package com.mathheals.euvou;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.test.ActivityInstrumentationTestCase2;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.junit.Before;

import java.text.ParseException;
import java.util.Vector;

import dao.EventDAO;
import dao.UserDAO;
import exception.EventException;
import model.Event;
import model.User;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by izabela on 29/11/15.
 */
public class RemoveEventControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    private LoginUtility isLoged;
    private TestUtility setLogin;
    private UiDevice device;
    private User user;
    private EventDAO eventDao = new EventDAO();

    public RemoveEventControlTest() {
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        isLoged = new LoginUtility(getActivity());
        device = UiDevice.getInstance(getInstrumentation());
    }

    public void testIfRemoveEventOptionIsDisplayedForUserLoggedOut(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Meus Eventos")).check(doesNotExist());
    }

    public void testRemoveEventButton() throws ParseException, EventException {

        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Meus Eventos")).perform(click());

        onData(hasToString(containsString("Teste")))
                .inAdapterView(withId(R.id.eventList)).atPosition(0)
                .perform(click());
        onView(withId(R.id.editRemoveButton)).perform(click());
        onView(withId(R.id.removeEvent)).perform(scrollTo());
        onView(withId(R.id.removeEvent)).perform(click());
        onView(withText("Deletado com sucesso")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Event event = new Event(1,3,"Teste","2015-12-20 14:00:00",10010,"oi","xablau","0","0");
        new EventDAO().saveEventWithId(event);
    }
}
