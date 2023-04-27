package smoke

import api.dataprovider.BillData
import api.endpoint.bill.BillRequest
import api.endpoint.bill.reject.Reject
import api.enum.InvoiceStatus
import api.model.response.BillResponse
import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.eclipse.jetty.http.HttpStatus
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import utils.TestConfigUtil
import java.util.UUID

class QiwiBillTests {
    private lateinit var requestSpecification: RequestSpecification
    private val baseUrl = TestConfigUtil.getProperty("api.url")

    @BeforeClass
    fun setup() {
        RestAssured.baseURI = baseUrl
        RestAssured.basePath = "/partner/bill/v1/bills/${UUID.randomUUID()}"
        requestSpecification = given()
            .header("Authorization", "Bearer ${TestConfigUtil.getProperty("api.token")}")
    }

    @Test(
        priority = 0,
        dataProvider = "billForPayment",
        dataProviderClass = BillData::class)
    fun testCreateBill(bill: api.model.Bill) {
        val billInit = BillRequest(requestSpecification)
        val response: Response = billInit.PUT(bill)
        response.prettyPrint()
        val billResponse = Gson().fromJson(response.body.asString(), BillResponse::class.java)

        assertThat(HttpStatus.OK_200).isEqualTo(response.statusCode)

        SoftAssertions().apply {
            assertThat(InvoiceStatus.WAITING.toString()).isEqualTo(billResponse.status.value)
            assertThat(bill.amount.currency).isEqualTo(billResponse.amount.currency)
            assertThat(bill.amount.value.toDouble()).isEqualTo(billResponse.amount.value.toDouble())
            assertThat(bill.comment).isEqualTo(billResponse.comment)
            assertThat(bill.customFields.themeCode).isEqualTo(billResponse.customFields.themeCode)
        }.assertAll()
    }

    @Test(
        priority = 1,
        dependsOnMethods = ["testCreateBill"])
    fun testCancelBill() {
        val reject = Reject(requestSpecification)
        val response: Response = reject.POST()
        response.prettyPrint()

        assertThat(HttpStatus.OK_200).isEqualTo(response.statusCode)
    }

    @Test(
        priority = 2,
        dependsOnMethods = ["testCreateBill"])
    fun testCheckBillStatus() {
        val billRequest = BillRequest(requestSpecification)
        val response: Response = billRequest.GET()
        response.prettyPrint()
        val rejectResponse = Gson().fromJson(response.body.asString(), BillResponse::class.java)

        SoftAssertions().apply {
            assertThat(HttpStatus.OK_200).isEqualTo(response.statusCode)
            assertThat(InvoiceStatus.REJECTED).isEqualTo(rejectResponse.status)
        }.assertAll()
    }
}
