package com.sid.demoapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnListView), withText("List View"),
                        withParent(allOf(withId(R.id.btnContainer),
                                withParent(withId(R.id.relative_layout)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withClassName(is("android.support.v7.widget.RecyclerView")),
                        withParent(allOf(withId(R.id.fragment_container),
                                withParent(withId(R.id.relative_layout)))),
                        isDisplayed()));
        recyclerView.perform(swipeUp(), swipeUp(), swipeDown(), swipeDown());

    }
}
