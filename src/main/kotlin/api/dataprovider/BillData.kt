package api.dataprovider

import api.model.Amount
import api.model.Bill
import api.model.CustomFields
import api.model.Customer
import org.testng.annotations.DataProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BillData {

    @DataProvider(name = "billForPayment")
    fun billForPayment(): Array<Bill> {
        val currentDateTime = LocalDateTime.now()
        val expiredDateTime = currentDateTime.minusDays(1)
        val expiredDate = expiredDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
        val featureDateTime = currentDateTime.plusDays(3)
        val featureDate = featureDateTime.format(DateTimeFormatter.ISO_DATE_TIME)

        val comment = "Test payment"
        val amount = Amount("RUB", "1.0")
        val customer = Customer(
            "454678",
            "test@example.com",
            "78710009999"
        )

        val customFields = CustomFields(
            "qw",
            "Yvan-YKaSh",
            "64728940",
            "order 678"
        )

        return arrayOf(
            Bill(
                amount,
                comment,
                customFields,
                customer,
                expiredDate
            ),
            Bill(
                amount,
                comment,
                customFields,
                customer,
                featureDate
            )
        )
    }
}