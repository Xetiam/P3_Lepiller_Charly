package com.openclassrooms.entrevoisins.neighbour_list;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.AddNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ViewNeighbourDetailActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    @Rule
    public ActivityScenarioRule<ListNeighbourActivity> mActivityRule = new ActivityScenarioRule<>(ListNeighbourActivity.class);

    private ListNeighbourActivity mActivity;

    @Before
    public void setUp() {
        Intents.init();
        mActivityRule.getScenario().onActivity(activity -> mActivity = activity);
        assertThat(mActivity, notNullValue());
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myNeighboursList_shouldOpenDetailView() {
        //Check that on click on an element of the recyclerview open ViewNeighbourActivity
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(ViewNeighbourDetailActivity.class.getName()));

    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void myNeighboursList_shouldOpenAddNeighbour() {
        //Check that on click on an element of the recyclerview open AddNeighbourActivity
        onView(allOf(isDisplayed(), withId(R.id.add_neighbour)))
                .perform(click());
        intended(hasComponent(AddNeighbourActivity.class.getName()));

    }

    @Test
    public void myNeighboursList_shouldLoadNeighbourOnClick() {
        //Check that when open ViewNeighbourActivity neighbour is correctly loaded
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(isDisplayed(), withId(R.id.neighbourName)))
                .check(matches(withText("Caroline")));
    }

    @Test
    public void myNeighboursList_shouldGoToFavoriteList() {
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(isDisplayed(), withId(R.id.fav)))
                .perform(click());
        onView(allOf(isDisplayed(), withId(R.id.toolbar)))
                .perform(click());

        onView(allOf(withText("Favorites"), isDescendantOfA(withId(R.id.tabs))))
                .perform(click());
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .check(withItemCount(1));
    }
}