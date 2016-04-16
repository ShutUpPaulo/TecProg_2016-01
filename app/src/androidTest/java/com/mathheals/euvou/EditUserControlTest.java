package com.mathheals.euvou;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.junit.Before;

import model.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by izabela on 04/11/15.
 */
public class EditUserControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    private LoginUtility isLoged;
    private TestUtility setLogin;
    private UiDevice device;
    private User user;


    public EditUserControlTest() {
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        isLoged = new LoginUtility(getActivity());
        device = UiDevice.getInstance(getInstrumentation());
    }

    public void testIfEditUserOptionIsDisplayedForUserLoggedOut() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).check((doesNotExist()));
    }

    public void testIfEditUserOptionIsDisplayedForUserLoggedIn() {
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).check(matches(isDisplayed()));
    }
    public void testUpdateUserButtonWithEmptyName(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.nameField)).perform(clearText());
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.nameField)).check(matches(hasErrorText(user.NAME_CANT_BE_EMPTY_NAME)));
    }

    public void testUpdateUserButtonWithEmptyDate(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.dateField)).perform(clearText());
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.dateField)).check(matches(hasErrorText(user.BIRTH_DATE_CANT_BE_EMPTY)));
    }

    public void testUpdateUserButtonWithInvalidDate(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.dateField)).perform(clearText());
        onView(withId(R.id.dateField)).perform(typeText("80/80/2012"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.dateField)).check(matches(hasErrorText(user.INVALID_BIRTH_DATE)));
    }


    public void testUpdateUserButtonWithEmptyMail(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.mailField)).perform(clearText());
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.mailField)).check(matches(hasErrorText(user.EMAIL_CANT_BE_EMPTY_EMAIL)));
    }

    public void testUpdateUserButtonWithInvalidMail(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.mailField)).perform(clearText());
        onView(withId(R.id.mailField)).perform(typeText("oi"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.mailField)).check(matches(hasErrorText(user.INVALID_EMAIL)));
    }

    public void testUpdateUserButtonWithEmptyMailConfirmation(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.confirmMailField)).check(matches(hasErrorText(user.EMAIL_CONFIRMATION_CANT_BE_EMPTY)));
    }

    public void testUpdateUserButtonWithInvalidMailConfirmation(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.confirmMailField)).perform(typeText("oi"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.mailField)).check(matches(hasErrorText(user.EMAIL_ARE_NOT_EQUALS)));
    }

    public void testUpdateUserButtonWithEmptyPassword(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.confirmMailField)).perform(typeText("igor-ribeiro@hotmail.com"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.passwordField)).check(matches(hasErrorText(user.PASSWORD_CANT_BE_EMPTY_PASSWORD)));
    }

    public void testUpdateUserButtonWithInvalidPassword(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.confirmMailField)).perform(typeText("igor-ribeiro@hotmail.com"));
        onView(withId(R.id.passwordField)).perform(typeText("1"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.passwordField)).check(matches(hasErrorText(user.PASSWORD_CANT_BE_LESS_THAN_6)));
    }

    public void testUpdateUserButtonWithEmptyPasswordConfirmation(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.confirmMailField)).perform(typeText("igor-ribeiro@hotmail.com"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.confirmPasswordField)).check(matches(hasErrorText(user.CONFIRM_PASSWORD_CANT_BE_EMPTY)));
    }

    public void testUpdateUserButtonWithInvalidPasswordConfirmation(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.confirmMailField)).perform(typeText("igor-ribeiro@hotmail.com"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
        onView(withId(R.id.confirmPasswordField)).perform(typeText("1234567"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.passwordField)).check(matches(hasErrorText(user.PASSWORD_ARE_NOT_EQUALS)));
    }

    public void testUpdateUserButtonWithValidInput(){
        if(!isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogIn();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Alterar cadastro")).perform(click());
        onView(withId(R.id.confirmMailField)).perform(typeText("igor-ribeiro@hotmail.com"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
        onView(withId(R.id.confirmPasswordField)).perform(typeText("123456"));
        UiObject marker = device.findObject(new UiSelector().textContains("Alterar"));
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
        onView(withId(R.id.labelRankingPlace)).check(matches(isDisplayed()));
    }

}
