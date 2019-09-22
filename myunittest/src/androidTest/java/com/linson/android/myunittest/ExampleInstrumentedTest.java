package com.linson.android.myunittest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest
{
    private Context mContext;

    public  ExampleInstrumentedTest()
    {
        mContext=InstrumentationRegistry.getTargetContext();
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class,true,false);

    @Test
    public void useAppContext()
    {
        assertEquals("com.linson.android.myunittest", mContext.getPackageName());
    }

    @Test
    public void testClick()
    {
        String str="hi";
        onView(withId(R.id.editText)).check(matches(isDisplayed()));
       // onView(withId(R.id.editText)).perform(typeText(str),closeSoftKeyboard());
       // onView(withId(R.id.button)).perform(click());
        //onView(withId(R.id.welcome)).check(matches(withText(str)));
    }
}