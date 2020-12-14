package mapper

import com.android.data.model.DataResult
import com.android.data.model.DiscountDataModel
import com.android.data.model.TicketDataModel
import com.android.data_api.mapper.TicketsApiToDataSourceMapperImpl
import com.android.data_api.model.ApiResult
import com.android.data_api.model.TicketApiModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TicketsApiToDataSourceMapperTest {

    private lateinit var mapper: TicketsApiToDataSourceMapperImpl

    @Before
    fun setUp() {
        mapper = TicketsApiToDataSourceMapperImpl()
    }

    @Test
    fun `Given ticketsList api response When map is invoked Then returns expected result`() {
        val actualValue = mapper.mapTicketListResult(TICKETS_RESPONSE)

        assertEquals(TICKETS_DATA_RESULT, actualValue)
    }

    @Test
    fun `Given api error response When map is invoked Then returns expected result`() {
        val actualValue = mapper.mapTicketListResult(TICKETS_ERROR_RESPONSE)

        assertEquals(TICKETS_DATA_ERROR_RESULT, actualValue)
    }

    companion object {

        private val TICKETS_RESPONSE = ApiResult.Success(TestModels.API_MODEL_LIST)

        private val TICKETS_DATA_RESULT = DataResult.Success(TestModels.DATA_MODEL_LIST)

        private val EXCEPTION = IOException("test")

        private val TICKETS_ERROR_RESPONSE = ApiResult.Error(EXCEPTION)
        private val TICKETS_DATA_ERROR_RESULT = DataResult.Error(EXCEPTION)
    }
}