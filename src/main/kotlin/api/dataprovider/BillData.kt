package api.dataprovider

import api.model.Amount
import api.model.Bill
import api.model.CustomFields
import api.model.Customer
import org.testng.annotations.DataProvider

class BillData {

    @DataProvider(name = "billForPayment")
    fun getBillForPayment(): Array<Bill> {
        return arrayOf(
            generatePositiveBill()
        )
    }

    fun generatePositiveBill(): Bill {
        val comment = "Test payment"
        val account = "454678"
        val email = "test@example.com"
        val phone = "78710009999"
        val currency = "RUB"
        val value = "1.0"
        val paySourcesFilter = "qw"
        val themeCode = "Yvan-YKaSh"
        val yourParamKey1 = "yourParam1"
        val yourParamValue1 = "64728940"
        val yourParamKey2 = "yourParam2"
        val yourParamValue2 = "order 678"
        val expirationDateTime = "2025-12-10T09:02:00+03:00"
        val amount = Amount("$currency", "$value")
        val customer = Customer(
            "$account",
            "$email",
            "$phone"
        )

        val customFields = CustomFields(
            "$paySourcesFilter",
            "$themeCode",
            hashMapOf(yourParamKey1 to yourParamValue1, yourParamKey2 to yourParamValue2)
        )
        return Bill(
            amount,
            comment,
            customFields,
            customer,
            "$expirationDateTime"
        )
    }
}