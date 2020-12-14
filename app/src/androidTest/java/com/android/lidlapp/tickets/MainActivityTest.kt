package com.android.lidlapp.tickets

import android.view.View
import android.widget.ProgressBar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.lidlapp.R
import com.android.lidlapp.di.generateTestAppComponents
import com.android.lidlapp.ui.activity.MainActivity
import com.android.lidlapp.ui.view.ActivationButton
import com.android.lidlapp.utils.SuccessTicketsCallDispatcher
import com.android.lidlapp.utils.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @get:Rule
    val mockWebServer = MockWebServer()

    private var ticketProgressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null


    @Before
    fun beforeTestsRun() {
        loadKoinModules(generateTestAppComponents(mockWebServer.url("/").toString()))
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        unloadKoinModules(generateTestAppComponents(mockWebServer.url("/").toString()))
        IdlingRegistry.getInstance().unregister(ticketProgressBarGoneIdlingResource)
    }


    @Test
    fun ticketList_showsRightItemSize_afterSuccesResponse() {
        mockWebServer.dispatcher = SuccessTicketsCallDispatcher()

        ticketsRobot {
            assertTicketListIsDisplayed()
            assertTicketListSizeIsRight(ITEM_COUNT)
        }
    }

    @Test
    fun ticketList_shouldShowRightToolbar_IfNotScrolled() {
        mockWebServer.dispatcher = SuccessTicketsCallDispatcher()

        ticketsRobot {
            assertToolbarHasMainTitle()
            assertToolbarSubtitleShowsRightCount(ACTIVATED_TICKETS_DEF)
        }
    }

    @Test
    fun ticketList_shouldShowRightToolbar_WhenNewTicketIsActivated() {
        mockWebServer.dispatcher = SuccessTicketsCallDispatcher()

        activityScenario.onActivity {
            idleViewHolderProgressBar(0, it)
        }


        ticketsRobot {
            activateTicket(0)
            waitForCondition(ticketProgressBarGoneIdlingResource)
            assertToolbarSubtitleShowsRightCount(ACTIVATED_TICKETS_DEF + 1)
        }
    }

    private fun idleViewHolderProgressBar(position: Int, activity: MainActivity) {
        val recycler = activity.findViewById(R.id.recycler) as RecyclerView
        val viewHolder = recycler[position]
        val container = viewHolder.findViewById(R.id.container) as ConstraintLayout
        val button = container.findViewById(R.id.activateButton) as ActivationButton
        val progress = button.findViewById<ConstraintLayout>(R.id.container)
            .findViewById<ProgressBar>(R.id.activationProgress)

        ticketProgressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(progress, View.GONE)
    }

    companion object {
        private const val ITEM_COUNT = 10
        private const val ACTIVATED_TICKETS_DEF = 2
    }
}