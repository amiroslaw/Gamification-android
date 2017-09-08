package xyz.miroslaw.gamification_android.menu;


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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeckManagerTest {

    @Rule
    public ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void deckManagerTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("Deck Manager"),
                        childAtPosition(
                                withId(R.id.lv_menu_menuitems),
                                2),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_list_add), withText("Add deck"),
                        withParent(allOf(withId(R.id.fragment_deckManager),
                                withParent(withId(R.id.fragment_deckManager)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_createCard_name),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("large"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_createCard_description),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("desc1"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_createCard_next), withText("Next"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_createCard_description),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("desc2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.et_createCard_name),
                        withParent(allOf(withId(R.id.fragment_createCard),
                                withParent(withId(R.id.fragment_createCard)))),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("medium"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_createCard_next), withText("Next"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.et_change_name), isDisplayed()));
        appCompatEditText5.perform(replaceText("deck1"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_change_name_accept), withText("OK"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txt_deckList_name), withText("deck1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("deck1")));


        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btn_change_name_accept), withText("OK"), isDisplayed()));
        appCompatButton9.perform(click());

        pressBack();

        pressBack();

        pressBack();

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Deck Manager"),
                        childAtPosition(
                                withId(R.id.lv_menu_menuitems),
                                2),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.item_duplicate), withContentDescription("Duplicate"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.et_change_name), isDisplayed()));
        appCompatEditText8.perform(replaceText("deck2"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_change_name_accept), withText("OK"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.txt_deckList_name), withText("deck"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        0),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("deck")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txt_list_number), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("2")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.txt_list_number), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("2")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.txt_deckList_name), withText("deck2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("deck2")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.txt_list_number), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_deckList),
                                        1),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("2")));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.item_delete), withContentDescription("Delete"), isDisplayed()));
        actionMenuItemView2.perform(click());

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
