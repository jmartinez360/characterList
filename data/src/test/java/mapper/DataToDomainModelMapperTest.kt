package mapper

import com.android.data.mapper.TicketDataToDomainModelMapper
import com.android.data.mapper.TicketDataToDomainModelMapperImpl
import com.android.data.model.DiscountDataModel
import com.android.data.model.TicketDataModel
import com.android.domain.model.DiscountDomainModel
import com.android.domain.model.TicketDomainModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class DataToDomainModelMapperTest {

    private lateinit var mapper: TicketDataToDomainModelMapper

    @Before
    fun setUp() {
        mapper = TicketDataToDomainModelMapperImpl()
    }

    @Test
    fun `Given ticketsDataModelList When mapList is invoked Then returns expected result`() {
        val actualValue = mapper.mapTicketList(TICKET_DATA_LIST)

        TestCase.assertEquals(TICKET_DOMAIN_LIST, actualValue)
    }

    companion object {

        private val TICKET_DATA_LIST = listOf(
            TicketDataModel("id", "name", 0L, 0L, false, DiscountDataModel(1, ""), "image"),
            TicketDataModel( "id2","name2", 0L, 0L, false, DiscountDataModel(1, ""), "image")
        )

        private val TICKET_DOMAIN_LIST = listOf(
            TicketDomainModel("id", "name", 0L, 0L, false, DiscountDomainModel(1, ""), "image"),
            TicketDomainModel("id2", "name2", 0L, 0L, false, DiscountDomainModel(1, ""), "image")
        )
    }
}