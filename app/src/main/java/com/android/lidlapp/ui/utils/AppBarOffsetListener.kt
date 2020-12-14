package com.android.lidlapp.ui.utils

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

interface AppbarChangedListener {
    fun onAppBarCollapsed()
    fun onAppBarExpanded()
    fun onAppBarIdled()
}

class AppBarOffsetListener(private val stateChangesCallback: AppbarChangedListener) :
    AppBarLayout.OnOffsetChangedListener {

    private var _currentState = IDLE
    val currentState: Int
        get() = _currentState


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        when {
            isExpanded(verticalOffset) -> { notifyAppBarIsExpanded() }

            isCollapsed(verticalOffset, appBarLayout) -> { notifyAppBarIsCollapsed() }

            else -> { notifyAppBarIdled() }
        }
    }

    private fun isCollapsed(
        verticalOffset: Int,
        appBarLayout: AppBarLayout?
    ) = abs(verticalOffset) >= appBarLayout?.totalScrollRange ?: 0

    private fun isExpanded(verticalOffset: Int) = verticalOffset == 0

    private fun notifyAppBarIsExpanded() {
        if (_currentState != EXPANDED) {
            _currentState = EXPANDED
            stateChangesCallback.onAppBarExpanded()
        }
    }

    private fun notifyAppBarIsCollapsed() {
        if (_currentState != COLLAPSED) {
            _currentState = COLLAPSED
            stateChangesCallback.onAppBarCollapsed()
        }
    }

    private fun notifyAppBarIdled() {
        if (_currentState != IDLE) {
            _currentState = IDLE
            stateChangesCallback.onAppBarIdled()
        }
    }

    companion object {
        const val EXPANDED = 0
        const val COLLAPSED = 1
        const val IDLE = 2
    }
}