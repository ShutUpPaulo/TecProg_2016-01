package com.mathheals.euvou;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;
import com.mathheals.euvou.controller.home_page.HomePage;
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.junit.Before;

import dao.UserDAO;
import model.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by izabela on 03/11/15.
 */
public class UserRegistrationControlTest extends ActivityInstrumentationTestCase2<HomePage> {

    private LoginUtility isLoged;
    private TestUtility setLogin;
    private UiDevice device;
    private User user;
    private UserDAO userDao = new UserDAO();

    public UserRegistrationControlTest() {
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        isLoged = new LoginUtility(getActivity());
        device = UiDevice.getInstance(getInstrumentation());
    }

    public void testRegisterOptionInActionBarMenu() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).check(matches(withText("Cadastrar")));
    }
    
    public void testIfNameLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.userNameLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.userNameLabel)).check(matches(withText("Nome")));
    }

    public void testNameField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).check(matches(isDisplayed()));
        onView(withId(R.id.nameField)).perform(typeText("Ju Almeida"));
        onView(withId(R.id.dateField)).perform(click());
        onView(withId(R.id.nameField)).check(matches(withText("Ju Almeida")));
    }

    public void testIfDateLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.birthDateLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.birthDateLabel)).check(matches(withText("Data de Nascimento")));
    }

    public void testDateField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.dateField)).check(matches(isDisplayed()));
        onView(withId(R.id.dateField)).perform(typeText("22/12/2000"));
        onView(withId(R.id.mailField)).perform(click());
        onView(withId(R.id.dateField)).check(matches(withText("22/12/2000")));
    }

    public void testIfMailLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.userMailLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.userMailLabel)).check(matches(withText("E-mail")));
    }

    public void testMailField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.mailField)).check(matches(isDisplayed()));
        onView(withId(R.id.mailField)).perform(typeText("oi-xau@voltei.com"));
        onView(withId(R.id.confirmMailField)).perform(click());
        onView(withId(R.id.mailField)).check(matches(withText("oi-xau@voltei.com")));
    }

    public void testIfMailConfirmationLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.confirmUserMailLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmUserMailLabel)).check(matches(withText("Confirme o e-mail")));
    }

    public void testMailConfirmationField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.confirmMailField)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi-xau@voltei.com"));
        onView(withId(R.id.loginField)).perform(click());
        onView(withId(R.id.confirmMailField)).check(matches(withText("oi-xau@voltei.com")));
    }

    public void testIfLoginLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.userLoginLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.userLoginLabel)).check(matches(withText("Login")));
    }

    public void testLoginField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.loginField)).check(matches(isDisplayed()));
        onView(withId(R.id.loginField)).perform(typeText("oioioi123"));
        onView(withId(R.id.passwordField)).perform(click());
        onView(withId(R.id.loginField)).check(matches(withText("oioioi123")));
    }

    public void testIfPasswordLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.userPasswordLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.userPasswordLabel)).check(matches(withText("Senha")));
    }

    public void testPasswordField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.passwordField)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordField)).perform(typeText("1234567"));
        onView(withId(R.id.confirmPasswordField)).perform(click());
        onView(withId(R.id.passwordField)).check(matches(withText("1234567")));
    }

    public void testIfConfirmPasswordLabelIsCorrect() {
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.userConfirmPasswordLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.userConfirmPasswordLabel)).check(matches(withText("Confirme a senha")));
    }

    public void testConfirmPasswordField(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.confirmPasswordField)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPasswordField)).perform(typeText("1234567"));
        onView(withId(R.id.passwordField)).perform(click());
        onView(withId(R.id.confirmPasswordField)).check(matches(withText("1234567")));
    }

    public void testRegisterUserButtonWithEmptyName(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
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
        onView(withId(R.id.nameField)).check(matches(hasErrorText(user.NAME_CANT_BE_EMPTY_NAME)));
    }

    public void testRegisterUserButtonWithEmptyDate(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
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
        onView(withId(R.id.dateField)).check(matches(hasErrorText(user.BIRTH_DATE_CANT_BE_EMPTY)));
    }

    public void testRegisterUserButtonWithInvalidDate(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("80/80/2012"));
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
        onView(withId(R.id.dateField)).check(matches(hasErrorText(user.INVALID_BIRTH_DATE)));
    }


    public void testRegisterUserButtonWithEmptyMail(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
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
        onView(withId(R.id.mailField)).check(matches(hasErrorText(user.EMAIL_CANT_BE_EMPTY_EMAIL)));
    }

    public void testRegisterUserButtonWithInvalidMail(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi"));
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
        onView(withId(R.id.mailField)).check(matches(hasErrorText(user.INVALID_EMAIL)));
    }

    public void testRegisterUserButtonWithEmptyMailConfirmation(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
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
        onView(withId(R.id.confirmMailField)).check(matches(hasErrorText(user.EMAIL_CONFIRMATION_CANT_BE_EMPTY)));
    }

    public void testRegisterUserButtonWithInvalidMailConfirmation(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi"));
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
        onView(withId(R.id.mailField)).check(matches(hasErrorText(user.EMAIL_ARE_NOT_EQUALS)));
    }

    public void testRegisterUserButtonWithEmptyLogin(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
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
        onView(withId(R.id.loginField)).check(matches(hasErrorText(user.USERNAME_CANT_BE_EMPTY_USERNAME)));
    }

    public void testRegisterUserButtonWithExistentLogin(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.loginField)).perform(typeText("igodudu"));
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
        onView(withId(R.id.loginField)).check(matches(hasErrorText(user.USERNAME_EXISTENT)));
    }

    public void testRegisterUserButtonWithEmptyPassword(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.loginField)).perform(typeText("izacris"));
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
        onView(withId(R.id.passwordField)).check(matches(hasErrorText(user.PASSWORD_CANT_BE_EMPTY_PASSWORD)));
    }

    public void testRegisterUserButtonWithInvalidPassword(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.loginField)).perform(typeText("izacris"));
        onView(withId(R.id.passwordField)).perform(typeText("1"));
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
        onView(withId(R.id.passwordField)).check(matches(hasErrorText(user.PASSWORD_CANT_BE_LESS_THAN_6)));
    }

    public void testRegisterUserButtonWithEmptyPasswordConfirmation(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.loginField)).perform(typeText("izacris"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
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
        onView(withId(R.id.confirmPasswordField)).check(matches(hasErrorText(user.CONFIRM_PASSWORD_CANT_BE_EMPTY)));
    }

    public void testRegisterUserButtonWithInvalidPasswordConfirmation(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.loginField)).perform(typeText("izacris"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
        onView(withId(R.id.confirmPasswordField)).perform(typeText("1234567"));
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
        onView(withId(R.id.passwordField)).check(matches(hasErrorText(user.PASSWORD_ARE_NOT_EQUALS)));
    }

    public void testRegisterUserButtonWithValidInput(){
        if(isLoged.hasUserLoggedIn()){
            setLogin.makeUserLogOut();
        }
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Cadastrar")).perform(click());
        onView(withId(R.id.nameField)).perform(typeText("iza"));
        onView(withId(R.id.dateField)).perform(typeText("12/12/2012"));
        onView(withId(R.id.mailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.confirmMailField)).perform(typeText("oi@xau.com"));
        onView(withId(R.id.loginField)).perform(typeText("izacris"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
        onView(withId(R.id.confirmPasswordField)).perform(typeText("123456"));
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
        onView(withId(R.id.usernameField)).check(matches(isDisplayed()));
        userDao.deleteUserById(1);
    }
}


