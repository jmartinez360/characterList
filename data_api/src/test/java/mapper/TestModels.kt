package mapper

import com.android.data.model.DiscountDataModel
import com.android.data.model.TicketDataModel
import com.android.data_api.model.DiscountApiModel
import com.android.data_api.model.TicketApiModel

object TestModels {

    val API_MODEL_LIST = listOf(
        TicketApiModel(id = "1",
        name = "name",
        image = "",
        unlockDate = 0L,
        relatedProducts = emptyList(),
        productCode = 0,
        limit = 0,
        exchangeLimit = 0,
        company = "company",
        endDate = 0L,
        activated = false,
        discount = DiscountApiModel(1, ""),
        description = "")
    )

    val DATA_MODEL_LIST = listOf(
        TicketDataModel(id = "1",
            name = "name",
            image = "",
            unlockDate = 0L,
            endDate = 0L,
            activated = false,
            discount = DiscountDataModel(1, "")
        )
    )
}