package data

import com.android.data.TicketsDataSource
import com.android.data_api.api.TicketsApiService
import com.android.data_api.data.RemoteTicketsDataSource
import com.android.data_api.mapper.ApiToDataSourceMapper
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RemoteTicketsDataSourceTest {

    @Mock
    lateinit var apiService: TicketsApiService

    @Mock
    lateinit var mapper: ApiToDataSourceMapper

    private lateinit var dataSource: TicketsDataSource

    @Before
    fun setUp() {
        dataSource = RemoteTicketsDataSource(apiService, mapper)
    }

    @Test
    fun `when getTickets then apiService is invoked`() {
        runBlockingTest {
            whenever(apiService.getTickets()).thenReturn(Response.success(listOf()))

            dataSource.getTickets()

            verify(apiService, times(1)).getTickets()
        }
    }
}