package api.model.response

import api.model.Amount
import api.model.CustomFields
import api.model.Customer
import api.model.Status

data class BillResponse(
    val amount: Amount,
    val billId: String,
    val comment: String,
    val creationDateTime: String,
    val customFields: CustomFields,
    val customer: Customer,
    val expirationDateTime: String,
    val payUrl: String,
    val siteId: String,
    val status: Status
)