package id.co.blogspot.tutor93.bakingapps;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import id.co.blogspot.tutor93.bakingapps.main.MainListActivity;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by indraaguslesmana on 8/23/17.
 */
@RunWith(AndroidJUnit4.class)
public class FlowTest {
    @Rule
    public ActivityTestRule<MainListActivity> mMainListActivty = new ActivityTestRule<>(MainListActivity.class);

    @Test
    public void flowTest() {

        waiting(3000);  // need to wait API load

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.frame_ingridientandguide))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_list))
                .check(matches(isDisplayed())); // is recycler step displayed?

        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        waiting(4000);  // wait for video

        onView(withId(R.id.exo_play))
                .perform(click());
        waiting(9900);  // play video
    }

    private void waiting(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
