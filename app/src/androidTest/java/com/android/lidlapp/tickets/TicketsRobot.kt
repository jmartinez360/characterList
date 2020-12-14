package com.android.lidlapp.tickets

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.android.lidlapp.R
import com.android.lidlapp.ui.recycler.viewholder.TicketViewHolder
import com.android.lidlapp.utils.ClickItemViewAction
import com.android.lidlapp.utils.RecyclerViewItemsCountAssertion
import com.android.lidlapp.utils.TicketsToolbarAssertion

fun ticketsRobot(func: TicketsRobot.() -> Unit) = TicketsRobot().apply { func() }

class TicketsRobot {

    fun assertTicketListIsDisplayed() = apply {
        onView(ticketsListMatcher)
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun assertTicketListSizeIsRight(count: Int) {
        onView(ticketsListMatcher).check(
            RecyclerViewItemsCountAssertion(count)
        )
    }

    fun assertToolbarHasMainTitle() {
        onView(toolbarMatcher).check(
            TicketsToolbarAssertion(TicketsToolbarAssertion.TITLE_EXPANDED_MODE)
        )
    }

    fun assertToolbarSubtitleShowsRightCount(activatedTicketsCount: Int) {
        onView(toolbarMatcher).check(
            TicketsToolbarAssertion(TicketsToolbarAssertion.SUBTITLE_EXPANDED_MODE, activatedTicketsCount)
        )
    }

    fun activateTicket(position: Int) {
        onView(ticketsListMatcher).perform(
            RecyclerViewActions.actionOnItemAtPosition<TicketViewHolder>(position, ClickItemViewAction(R.id.activateButton)))
    }

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    companion object {
        private val ticketsListMatcher = withId(R.id.recycler)
        private val toolbarMatcher = withId(R.id.collapsingToolbar)
    }
}