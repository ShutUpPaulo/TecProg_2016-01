package com.mathheals.euvou;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.login_user.LoginValidation;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.hamcrest.Matcher;
import org.junit.Before;

import dao.UserDAO;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by izabela on 04/11/15.
 */
public class RemoveUserControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    LoginUtility isLoged;
    private TestUtility setLogin;
    private UserDAO userDAO = new UserDAO();
    public RemoveUserControlTest() {
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        isLoged = new LoginUtility(getActivity());
    }

    public void testIfConfigureOptionIsDisplayedForUserLoggedOut() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Configurações")).check(doesNotExist());
    }

    public void testIfConfigureOptionIsDisplayedForUserLoggedIn() {
        if(!isLoged.hasUserLoggedIn()){
           setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Configurações")).check(matches(isDisplayed()));
    }

    public void testRemoveButton() {
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Configurações")).perform(click());
        onView(withText("Desativar conta")).check(matches(isDisplayed()));
        onView(withText("DESATIVAR")).check(matches(isDisplayed()));
        onView(withText("DESATIVAR")).perform(click());
        onView(withText("Sim")).check(matches(isDisplayed()));
        onView(withText("Não")).check(matches(isDisplayed()));
    }

    public void testRemoveConfirmationButton(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Configurações")).perform(click());
        onView(withText("DESATIVAR")).perform(click());
        onView(withText("Não")).perform(click());
        onView(withId(R.id.button_disable_account_confirmation_id)).check(matches(isDisplayed()));
        onView(withId(R.id.button_disable_account_confirmation_id)).check(matches(withText("DESATIVAR")));
    }

    public void testRemoveWithInvalidPasswordConfirmation(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Configurações")).perform(click());
        onView(withText("DESATIVAR")).perform(click());
        onView(withText("Não")).perform(click());
        onView(withId(R.id.edit_text_login_id)).perform(typeText("igodudu"));
        onView(withId(R.id.edit_text_password_id)).perform(typeText("1234567"));
        onView(withId(R.id.button_disable_account_confirmation_id)).perform(click());
        onView(withId(R.id.edit_text_password_id)).check(matches(hasErrorText("Ops, acho que você digitou a senha errada")));
    }

    public void testRemoveWithInvalidLoginConfirmation(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Configurações")).perform(click());
        onView(withText("DESATIVAR")).perform(click());
        onView(withText("Não")).perform(click());
        onView(withId(R.id.edit_text_login_id)).perform(typeText("izacris"));
        onView(withId(R.id.edit_text_password_id)).perform(typeText("123456"));
        onView(withId(R.id.button_disable_account_confirmation_id)).perform(click());
        onView(withId(R.id.edit_text_login_id)).check(matches(hasErrorText("Ops, acho que você digitou o login errado")));
    }

}
