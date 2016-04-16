package com.mathheals.euvou;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by marlonmendes on 20/11/15.
 */
class TestUtility {

    public static void makeUserLogIn() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Entrar")).perform(click());
        onView(withId(R.id.usernameField)).perform(typeText("igodudu"));
        onView(withId(R.id.passwordField)).perform(typeText("123456"));
        onView(withText("Login")).perform(click());
    }

    public static void makeUserLogOut() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Sair")).perform(click());
    }

    public static void makeUserLogIn(String login, String password) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Entrar")).perform(click());
        onView(withId(R.id.usernameField)).perform(typeText(login));
        onView(withId(R.id.passwordField)).perform(typeText(password));
        onView(withText("Login")).perform(click());
    }

}
