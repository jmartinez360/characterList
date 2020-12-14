package com.android.lidlapp.utils

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.lidlapp.R
import com.google.android.material.appbar.SubtitleCollapsingToolbarLayout
import org.hamcrest.Matchers

class TicketsToolbarAssertion(private val mode: Int, private val activationCount: Int? = 0) : ViewAssertion {

    private val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        view as SubtitleCollapsingToolbarLayout
        when (mode) {
            TITLE_EXPANDED_MODE -> assertExpandedToolbarTitle(view)
            SUBTITLE_EXPANDED_MODE -> assertExpandedToolbarSubtitle(view)
        }
    }

    private fun assertExpandedToolbarTitle(toolbar: SubtitleCollapsingToolbarLayout) {
        val currentTitle = toolbar.title.toString()
        assertThat(currentTitle, Matchers.`is`(resources.getString(R.string.tickets_title)))
    }

    private fun assertExpandedToolbarSubtitle(toolbar: SubtitleCollapsingToolbarLayout) {
        val currentSubtitle = toolbar.subtitle.toString()
        assertThat(currentSubtitle, Matchers.`is`(resources.getQuantityString(R.plurals.activated_tickets, activationCount!!, activationCount)))
    }

    companion object {
        const val SUBTITLE_EXPANDED_MODE = 1
        const val TITLE_EXPANDED_MODE = 2
    }
}