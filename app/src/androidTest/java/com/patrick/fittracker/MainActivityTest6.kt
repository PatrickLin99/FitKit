package com.patrick.fittracker


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest6 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @Test
    fun mainActivityTest6() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_calendar),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_profile),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        bottomNavigationItemView2.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.navigation_analysis),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        bottomNavigationItemView3.perform(click())

        val bottomNavigationItemView4 = onView(
            allOf(
                withId(R.id.navigation_home),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        bottomNavigationItemView4.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.card_view_self_training),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        cardView.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.muscle_chest_image),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("android.widget.FrameLayout")),
//                        0
//                    ),
//                    0
//                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        appCompatImageView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.muscle_select_post)
//                childAtPosition(
//                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
//                    4
//                )
            )
        )
        Thread.sleep(8000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.set_add_button),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.add_tools),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        Thread.sleep(8000)
        appCompatImageView2.perform(click())

        val appCompatImageView3 = onView(
            allOf(
                withId(R.id.add_record),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.add_tools),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        Thread.sleep(8000)
        appCompatImageView3.perform(click())

        val appCompatImageView4 = onView(
            allOf(
                withId(R.id.count_down_timer),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(8000)
        appCompatImageView4.perform(click())

        val appCompatImageView5 = onView(
            allOf(
                withId(R.id.dismiss_button),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        appCompatImageView5.perform(click())

        val appCompatButton = onView(
            allOf(
                withId(R.id.finish_record), withText("紀錄完成"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.myNavHostFragment),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val bottomNavigationItemView5 = onView(
            allOf(
                withId(R.id.navigation_analysis),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        bottomNavigationItemView5.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
