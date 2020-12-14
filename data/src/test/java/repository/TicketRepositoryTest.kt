package repository

import com.android.data.TicketsDataSource
import com.android.data.mapper.TicketDataToDomainModelMapper
import com.android.data.model.DataResult
import com.android.data.model.DiscountDataModel
import com.android.data.model.TicketDataModel
import com.android.data.repository.TicketRepositoryImpl
import com.android.domain.TicketRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TicketRepositoryTest {

    @Mock
    lateinit var dataSource: TicketsDataSource

    @Mock
    lateinit var mapper: TicketDataToDomainModelMapper

    private lateinit var repository: TicketRepository

    @Before
    fun setup() {
        repository = TicketRepositoryImpl(dataSource, mapper)
    }

    @Test
    fun `When  repositoryGetTickets Then should call dataSource getTickets method`() {
        runBlockingTest {
            repository.getTickets()

            verify(dataSource).getTickets()
        }
    }

    @Test
    fun `Given dataSource emits right result When repository getTickets Then call mapper`() {
        runBlockingTest {
            whenever(dataSource.getTickets()).thenReturn(SUCCESS_FLOW)

            val flow = repository.getTickets()

            flow.collect { verify(mapper).mapTicketList(any()) }
        }
    }

    @Test
    fun `Given dataSource emits error When repository getTickets Then never call mapper`() {
        runBlockingTest {
            whenever(dataSource.getTickets()).thenReturn(ERROR_FLOW)

            val flow = repository.getTickets()

            flow.catch { verify(mapper, never()).mapTicketList(any()) }.collect()
        }
    }

    companion object {

        private val SUCCESS_FLOW = flow<DataResult<List<TicketDataModel>>> {
            emit(DataResult.Success(RESULT_LIST))
        }

        private val RESULT_LIST = listOf(TicketDataModel("id", "name", 0, 0, false, DiscountDataModel(1, ""), "image"))

        private val ERROR_FLOW = flow<DataResult<List<TicketDataModel>>> {
            emit(DataResult.Error(IOException()))
        }
    }
}