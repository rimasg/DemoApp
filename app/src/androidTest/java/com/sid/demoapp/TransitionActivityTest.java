package com.sid.demoapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Okis on 2016.10.27.
 */
@RunWith(AndroidJUnit4.class)
public class TransitionActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void startTransitionActivity() throws Exception {
        onView(withId(R.id.menu_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        pressBack();
        onView(withText("Other Fragment")).perform(click());
        onView(withText("Translation")).perform(click());
    }
}