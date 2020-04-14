package com.office14.coffeedose.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.coffeedose.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CoffeeDoseEspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(CoffeeDoseActivity::class.java)

    @Test
    fun on_clear_order_details_empty_message_text_is_visible() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.orderFragment), withContentDescription("Корзина"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bttm_nav),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.title), withText("Очистить корзину"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tv_empty_order_details), withText("Список заказов пуст")
            )
        )
        textView.check(matches(isDisplayed()))
    }

    @Test
    fun on_add_single_order_then_single_order_detail_exists_and_approve_button_avaliable() {

        val catalogItem = onView(
            childAtPosition(
                withId(R.id.drinks_rv),0
            )
        )

        catalogItem.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.addButton),
                childAtPosition(
                    allOf(
                        withId(R.id.view_addins_root),
                        childAtPosition(
                            withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.orderFragment), withContentDescription("Корзина"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bttm_nav),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val orderDetailsItem = onView(
            childAtPosition(
                withId(R.id.rv_order_details),0
            )
        )
        orderDetailsItem.check(matches(isDisplayed()))

        val imageButton = onView(
            allOf(
                withId(R.id.confirmButton),
                childAtPosition(
                    allOf(
                        withId(R.id.rl_content),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))
    }

    @Test
    fun on_catalog_item_click_then_bottom_dialog_visible() {
        val catalogItem = onView(
            childAtPosition(
                withId(R.id.drinks_rv),
                0
            )
        )
        catalogItem.perform(click())

        val relativeLayout = onView(
            withId(R.id.view_addins_root)
        )
        relativeLayout.check(matches(isDisplayed()))
    }

    @Test
    fun on_go_to_order_details_then_empty_list_message_visible() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.orderFragment), withContentDescription("Корзина"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bttm_nav),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.title), withText("Очистить корзину"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tv_empty_order_details), withText("Список заказов пуст")
            )
        )
        textView.check(matches(isDisplayed()))
    }

    @Test
    fun on_add_two_items_and_delete_ony_by_swipe_then_last_item_visible() {
        val catalogItem = onView(
            childAtPosition(
                withId(R.id.drinks_rv),
                0
            )
        )
        catalogItem.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.addButton),
                childAtPosition(
                    allOf(
                        withId(R.id.view_addins_root),
                        childAtPosition(
                            withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val catalogItem2 = onView(
            childAtPosition(
                withId(R.id.drinks_rv),
                0
            )
        )
        catalogItem2.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.addButton),
                childAtPosition(
                    allOf(
                        withId(R.id.view_addins_root),
                        childAtPosition(
                            withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.orderFragment), withContentDescription("Корзина"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bttm_nav),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val viewToSwipe = onView(
            childAtPosition(
                withId(R.id.rv_order_details),
                0
            )
        )
        viewToSwipe.perform(ViewActions.swipeLeft())

        val orderDetailsItem = onView(
            childAtPosition(
                withId(R.id.rv_order_details),
                0
            )
        )
        orderDetailsItem.check(ViewAssertions.matches(isDisplayed()))
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
