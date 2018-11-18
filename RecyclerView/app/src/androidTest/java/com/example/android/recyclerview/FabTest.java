package com.example.android.recyclerview;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
/**
 * Recorded Espresso test for adding an item to a RecyclerView.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FabTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * This test clicks the floating action button and checks to see if a new
     * item has been added to the RecyclerView with the text "+ Word 20".
     */
    @Test
    public void fabTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(
                        withId(R.id.fab),
                        childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(
                        withId(R.id.word),
                //        withText("+ Word 20"),
                        childAtPosition(childAtPosition(withId(R.id.recyclerview), 13), 0),
                        isDisplayed()));
        textView.check(matches(withText("+ Word 20")));
    }

    /**
     * This test clicks the first item an checks that the result is
     * item has been added to the RecyclerView with the text "Clicked! Word 0+ Word 20".
     */
    @Test
    public void Recycler_ClickFirst() {

        final ViewInteraction viewInteraction = onView(withId(R.id.recyclerview));
        viewInteraction
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //        .perform(RecyclerViewActions.scrollToHolder(isFirst()),click());
        onView (allOf(withId(R.id.word),
                childAtPosition(childAtPosition(withId(R.id.recyclerview), 0), 0)))
                .check( matches(withText("Clicked! Word 0")));
    }

    /**
     * This test clicks the last item and checks that the result "Clicked! Word 19" is displayed.
     */
    @Test
    public void Recycler_ClickLast() {

        final ViewInteraction viewInteraction = onView(withId(R.id.recyclerview));
        viewInteraction
        //        .perform(RecyclerViewActions.actionOnItemAtPosition(19, click()));
        //        .perform(RecyclerViewActions.scrollToHolder(isLast()))
                .perform(RecyclerViewActions.actionOnHolderItem(isLast(),click()));

        onView (withText("Clicked! Word 19"))
                .check(matches(isDisplayed()));
    }


    /**
     * This test clicks the last item and checks that the result "Clicked! Word 10" is displayed.
     */
    @Test
    public void Recycler_ClickMiddle() {

        final ViewInteraction viewInteraction = onView(withId(R.id.recyclerview));
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));
           //     .perform(RecyclerViewActions.actionOnHolderItem(isInTheMiddle(),click()));

        onView (withText("Clicked! Word 10"))
                .check(matches(isDisplayed()));
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * Matches the {@link WordListAdapter.WordViewHolder}s in the middle of the list.
     */
    private static Matcher<WordListAdapter.WordViewHolder> isInTheMiddle() {
        return new TypeSafeMatcher<WordListAdapter.WordViewHolder>() {
            @Override
            protected boolean matchesSafely(WordListAdapter.WordViewHolder customHolder) {
                return customHolder.getIsInTheMiddle();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in the middle");

            }

        };
    }

    /**
     * Matches the {@link WordListAdapter.WordViewHolder}s in the middle of the list.
     */
    private static Matcher<WordListAdapter.WordViewHolder> isFirst() {
        return new TypeSafeMatcher<WordListAdapter.WordViewHolder>() {
            @Override
            protected boolean matchesSafely(WordListAdapter.WordViewHolder customHolder) {
                return customHolder.getIsFirst();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item is first");
            }
        };
    }

    /**
     * Matches the {@link WordListAdapter.WordViewHolder}s in the middle of the list.
     */
    private static Matcher<WordListAdapter.WordViewHolder> isLast() {
        return new TypeSafeMatcher<WordListAdapter.WordViewHolder>() {
            @Override
            protected boolean matchesSafely(WordListAdapter.WordViewHolder customHolder) {
                return customHolder.getIsLast();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item is last");
            }
        };
    }



}
