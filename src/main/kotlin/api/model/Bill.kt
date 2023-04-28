package api.model

data class Bill(
    val amount: Amount,
    val comment: String?,
    val customFields: CustomFields?,
    val customer: Customer?,
    val expirationDateTime: String
)