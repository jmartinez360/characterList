package com.android.lidlapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.domain.model.DiscountDomainModel
import com.android.domain.model.TicketDomainModel
import com.android.domain.usecase.ActivateTicket
import com.android.domain.usecase.DeactivateTicket
import com.android.domain.usecase.GetTickets
import com.android.lidlapp.MainCoroutineScopeRule
import com.android.lidlapp.mapper.DomainToModelMapper
import com.android.lidlapp.model.DiscountModel
import com.android.lidlapp.model.TicketModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.lastValue
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TicketListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    private lateinit var getTickets: GetTickets

    @Mock
    private lateinit var activateTicket: ActivateTicket

    @Mock
    private lateinit var deactivateTicket: DeactivateTicket

    @Mock
    private lateinit var mapper: DomainToModelMapper

    @Mock
    private lateinit var ticketsObserver: Observer<List<TicketModel>>

    @Mock
    private lateinit var activatedTicketsCountObserver: Observer<Int>

    @Mock
    private lateinit var activatedTicketObserver: Observer<String>

    @Mock
    private lateinit var deactivatedTicketObserver: Observer<String>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    @Captor
    private lateinit var booleanCaptor: ArgumentCaptor<Boolean>

    @Captor
    private lateinit var ticketsCaptor: ArgumentCaptor<List<TicketModel>>

    @Captor
    private lateinit var activatedTicketsCaptor: ArgumentCaptor<Int>

    @Captor
    private lateinit var ticketIdCaptor: ArgumentCaptor<String>

    private lateinit var viewModel: TicketListViewModel

    @Before
    fun setup() {
        viewModel = TicketListViewModelImpl(getTickets, activateTicket, deactivateTicket, mapper)

        viewModel.ticketListLiveData.observeForever(ticketsObserver)
        viewModel.activatedTicketsCountLiveData.observeForever(activatedTicketsCountObserver)
        viewModel.activatedTicketLiveData.observeForever(activatedTicketObserver)
        viewModel.deactivatedTicketLiveData.observeForever(deactivatedTicketObserver)
        viewModel.loadingLiveData.observeForever(loadingObserver)
        viewModel.errorLiveData.observeForever(errorObserver)
    }

    @Test
    fun `When getTicketList Then should execute getTickets useCase`() {
        runBlockingTest {
            viewModel.getTicketList()
            verify(getTickets).execute()
        }
    }

    @Test
    fun `Given getTicketList is invoked Then should show loading`() {
        viewModel.getTicketList()

        verify(loadingObserver).onChanged(eq(BaseViewModel.SHOW))
    }

    @Test
    fun `Given getTicketList return success result Then should hide loading`() {
        runBlockingTest {
            setupSuccessFlow()

            viewModel.getTicketList()

            verify(loadingObserver, times(2)).onChanged(booleanCaptor.capture())
            assertEquals(BaseViewModel.HIDE, booleanCaptor.lastValue)
        }
    }

    @Test
    fun `Given getTicketList returns error Then should hide loading`() {
        runBlockingTest {
            setupErrorFlow()

            viewModel.getTicketList()

            verify(loadingObserver, times(2)).onChanged(booleanCaptor.capture())
            assertEquals(BaseViewModel.HIDE, booleanCaptor.lastValue)
        }
    }

    @Test
    fun `Given getTicketList returns error Then should show error`() {
        runBlockingTest {
            setupErrorFlow()

            viewModel.getTicketList()

            verify(errorObserver).onChanged(booleanCaptor.capture())
            assertEquals(BaseViewModel.SHOW, booleanCaptor.value)
        }
    }

    @Test
    fun `Given getTicketList returns success Then never should show error`() {
        runBlockingTest {
            setupSuccessFlow()

            viewModel.getTicketList()

            verify(errorObserver, never()).onChanged(any())
        }
    }

    @Test
    fun `Given getTicketList returns success Then should show right result`() {
        runBlockingTest {
            setupSuccessFlow()
            whenever(mapper.mapToTicketModelList(any())).thenReturn(RIGHT_TICKET_MODEL_LIST)

            viewModel.getTicketList()

            verify(ticketsObserver).onChanged(ticketsCaptor.capture())
            assertEquals(RIGHT_TICKET_MODEL_LIST, ticketsCaptor.value)
        }
    }

    @Test
    fun `Given getTicketList returns success Then should show right activated tickets count`() {
        runBlockingTest {
            setupSuccessFlow()
            whenever(mapper.mapToTicketModelList(any())).thenReturn(RIGHT_TICKET_MODEL_LIST)

            viewModel.getTicketList()

            verify(activatedTicketsCountObserver).onChanged(activatedTicketsCaptor.capture())
            assertEquals(RIGHT_ACTIVATED_TICKETS_COUNT, activatedTicketsCaptor.value)
        }
    }

    @Test
    fun `Given deactivatedTicket When activateDeactivateTicket invoked Then should inform about activated ticket`() {
        runBlockingTest {
            setupSuccessTicketActivationFlow()

            viewModel.activateDeactivateTicket(DEACTIVATED_TICKET)

            verify(activatedTicketObserver).onChanged(ticketIdCaptor.capture())
            assertEquals("id2", ticketIdCaptor.value)
        }
    }

    @Test
    fun `Given activatedTicket When activateDeactivateTicket invoked Then should inform about deactivated ticket`() {
        runBlockingTest {
            setupSuccessTicketDeactivationFlow()

            viewModel.activateDeactivateTicket(ACTIVATED_TICKET)

            verify(deactivatedTicketObserver).onChanged(ticketIdCaptor.capture())
            assertEquals("id1", ticketIdCaptor.value)
        }
    }

    private suspend fun setupSuccessFlow() {
        val ticketsChannel = ConflatedBroadcastChannel<List<TicketDomainModel>>()
        ticketsChannel.offer(TICKET_DOMAIN_LIST)
        whenever(getTickets.execute()).thenReturn(ticketsChannel.asFlow())
    }

    private suspend fun setupErrorFlow() {
        val flow = flow<List<TicketDomainModel>> {
            throw IOException("test")
        }
        whenever(getTickets.execute()).thenReturn(flow)
    }

    private suspend fun setupSuccessTicketActivationFlow() {
        val flow = flow {
            emit("id2")
        }
        whenever(activateTicket.execute(any())).thenReturn(flow)
    }

    private suspend fun setupSuccessTicketDeactivationFlow() {
        val flow = flow {
            emit("id1")
        }
        whenever(deactivateTicket.execute(any())).thenReturn(flow)
    }

    companion object {
        private val TICKET_DOMAIN_LIST = listOf(
            TicketDomainModel("id", "name2", 0L, 0L, true, DiscountDomainModel(1,""), ""),
            TicketDomainModel("id2", "name2", 0L, 0L, false, DiscountDomainModel(1,""), "")
        )

        private val RIGHT_TICKET_MODEL_LIST = listOf(
            TicketModel("id", "name2", 0L, 0L, true, DiscountModel(1,""), ""),
            TicketModel("id2", "name3", 0L, 0L, false, DiscountModel(1,""), "")
        )

        private const val RIGHT_ACTIVATED_TICKETS_COUNT = 1
        private val ACTIVATED_TICKET = TicketModel("id1", "activated", 0L, 0L, true, DiscountModel(1,""), "")
        private val DEACTIVATED_TICKET = TicketModel("id2", "deactivated", 0L, 0L, false, DiscountModel(1,""), "")
    }
}