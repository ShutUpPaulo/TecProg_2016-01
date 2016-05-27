package com.mathheals.euvou;

import android.test.ActivityInstrumentationTestCase2;
import com.mathheals.euvou.controller.home_page.HomePage;
import org.junit.Before;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by geovanni on 18/11/15.
 */
public class ShowPlaceRankingTest extends ActivityInstrumentationTestCase2<HomePage> {

    public ShowPlaceRankingTest()  {
        super(HomePage.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testIfTop5IsDisplayed(){
        onView(withId(R.id.listViewTop5Places)).check(matches(isDisplayed()));
        onView(withId(R.id.more)).check(matches(isDisplayed()));
        onView(withId(R.id.labelRankingPlace)).check(matches(isDisplayed()));

    }
    public void testIfPlaceRakingIsDisplayed(){
        testIfTop5IsDisplayed();
        onView(withId(R.id.more)).perform(click());
        onView(withId(R.id.listViewTop5Places)).check(matches(isDisplayed()));

    }

    public void testclickPlaceRakingIsDisplayed(){
        testIfTop5IsDisplayed();
        onView(withId(R.id.listViewTop5Places)).perform(click());

    }


}
