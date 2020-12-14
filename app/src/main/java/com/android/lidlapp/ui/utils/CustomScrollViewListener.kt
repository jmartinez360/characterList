package com.android.lidlapp.ui.utils

import android.view.View

interface ScrollViewCallback {

    fun onFirstViewPartiallyVisible()
    fun onFirstViewCompletelyVisible()
}

class CustomScrollViewListener(
    private val callback: ScrollViewCallback,
    private val yFirstView: Float
) :
    View.OnScrollChangeListener {

    private var currentState = FIRST_VIEW_COMPLETELY_VISIBLE

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {

        if (scrollY > yFirstView) {
            setState(FIRST_VIEW_PARTIALLY_VISIBLE)
        } else {
            setState(FIRST_VIEW_COMPLETELY_VISIBLE)
        }
    }

    private fun setState(newState: Int) {
        if (newState != currentState) {
            currentState = newState
            informState()
        }
    }

    private fun informState() {
        if (currentState == FIRST_VIEW_COMPLETELY_VISIBLE) {
            callback.onFirstViewCompletelyVisible()
        } else {
            callback.onFirstViewPartiallyVisible()
        }
    }

    companion object {
        private const val FIRST_VIEW_COMPLETELY_VISIBLE = 0
        private const val FIRST_VIEW_PARTIALLY_VISIBLE = 1
    }
}