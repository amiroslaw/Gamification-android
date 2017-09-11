package xyz.miroslaw.gamification_android;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.menu.MenuActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateDeckTest {

    @Rule
    public ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void createDeckTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("Create Deck"),
                        childAtPosition(
                                withId(R.id.lv_menu_menuitems),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_createCard_next), withText("Next"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_createCard_name),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("main award"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_createCard_next), withText("Next"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_createCard_name),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("image"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_createCard_description),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("desc"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_createCard_next), withText("Next"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.et_change_name), isDisplayed()));
        appCompatEditText4.perform(replaceText("deck name"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_change_name_accept), withText("OK"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txt_deckList_name), withText("deck name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("deck name")));

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
}
