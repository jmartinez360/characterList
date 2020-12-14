package com.android.lidlapp.utils

import android.R.id
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher

class ClickItemViewAction(private val viewId: Int): ViewAction {

    override fun getDescription(): String {
        return "Clicks some viewHolder's view"
    }

    override fun getConstraints(): Matcher<View>? {
        return null
    }

    override fun perform(uiController: UiController?, view: View?) {
        val v: View = view!!.findViewById(viewId)
        v.performClick()
    }
}