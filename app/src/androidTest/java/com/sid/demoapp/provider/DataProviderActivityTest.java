package com.sid.demoapp.provider;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.sid.demoapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DataProviderActivityTest {

    private DataProviderActivity activity;

    @Rule
    public ActivityTestRule<DataProviderActivity> mActivityTestRule = new ActivityTestRule<>(DataProviderActivity.class);

    @Before
    public void setUp() throws Exception {
        activity = mActivityTestRule.getActivity();
    }

    @Test
    public void checkDataEntry() throws Exception {
        activity.deleteData();
        activity.enterData("x");
        activity.enterData("z");
        activity.enterData("z");
        final int count = activity.deleteData();
        assertThat("Wrong number of records deleted", count, is(3));
    }

    @Test
    public void dataProviderActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.data_entry),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.data_entry),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("a"));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.action_data_submit),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.data_entry),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("b"));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.action_data_submit),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.data_entry),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("c"));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.action_data_submit),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.data_entry),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("b"));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.action_search),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.data_entry), withText("b"),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.data_entry), withText("b"),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText(""));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.action_delete),
                        withParent(allOf(withId(R.id.activity_data_provider),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

    }
}
