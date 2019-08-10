package com.sid.demoapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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