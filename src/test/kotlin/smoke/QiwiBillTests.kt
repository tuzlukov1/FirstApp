package smoke

import api.dataprovider.BillData
import api.endpoint.bill.BillRequest
import api.endpoint.bill.reject.Reject
import api.enum.InvoiceStatus
import api.model.Bill
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
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import utils.TestConfigUtil
import java.util.UUID

class QiwiBillTests {
    private lateinit var requestSpecification: RequestSpecification
    private val baseUrl = TestConfigUtil.getProperty("api.url")
    private lateinit var uuid: UUID

    @BeforeClass
    fun setup() {
        RestAssured.baseURI = baseUrl
        RestAssured.basePath = "/partner/bill/v1/bills"
    }

    @BeforeMethod
    fun generateBill() {
        uuid = UUID.randomUUID()
        requestSpecification = given()
            .header("Authorization", "Bearer ${TestConfigUtil.getProperty("api.token")}")
    }

    @Test(
        dataProvider = "billForPayment",
        dataProviderClass = BillData::class)
    fun testCreateBill(bill: Bill) {
        val billInit = BillRequest(requestSpecification)
        val responseFromCreateBill: Response = billInit.PUT("$uuid",bill)
        val billResponse = Gson().fromJson(responseFromCreateBill.body.asString(), BillResponse::class.java)

        assertThat(HttpStatus.OK_200).isEqualTo(responseFromCreateBill.statusCode)

        SoftAssertions().apply {
            assertThat(InvoiceStatus.WAITING.toString()).isEqualTo(billResponse.status.value)
            assertThat(bill.amount.currency).isEqualTo(billResponse.amount.currency)
            assertThat(bill.amount.value.toDouble()).isEqualTo(billResponse.amount.value.toDouble())
            assertThat(bill.comment).isEqualTo(billResponse.comment)
            assertThat(bill.customFields?.themeCode).isEqualTo(billResponse.customFields.themeCode)
        }.assertAll()
    }

    @Test(
        dataProvider = "billForPayment",
        dataProviderClass = BillData::class)
    fun testCancelBill(bill: Bill) {
        val billInit = BillRequest(requestSpecification)
        val responseFromCreateBill: Response = billInit.PUT("$uuid",bill)
        assertThat(HttpStatus.OK_200).isEqualTo(responseFromCreateBill.statusCode)

        val reject = Reject(requestSpecification)
        val responseFromRejectBill: Response = reject.POST("$uuid")

        assertThat(HttpStatus.OK_200).isEqualTo(responseFromRejectBill.statusCode)

        val responseFromGetBill: Response = billInit.GET("$uuid")

        val rejectResponse = Gson().fromJson(responseFromGetBill.body.asString(), BillResponse::class.java)

        SoftAssertions().apply {
            assertThat(HttpStatus.OK_200).isEqualTo(responseFromGetBill.statusCode)
            assertThat(InvoiceStatus.REJECTED.toString()).isEqualTo(rejectResponse.status.value)
        }.assertAll()
    }

    @Test(
        dataProvider = "billForPayment",
        dataProviderClass = BillData::class)
    fun testCreateBillInvalidToken(bill: Bill) {
        requestSpecification = given()
            .header("Authorization", "Bearer UNVALID")

        val billInit = BillRequest(requestSpecification)
        val responseFromCreateBill: Response = billInit.PUT("$uuid",bill)

        assertThat(HttpStatus.UNAUTHORIZED_401).isEqualTo(responseFromCreateBill.statusCode)
    }

    @Test()
    fun testGetFakeUUID() {
        val billInit = BillRequest(requestSpecification)
        val responseFromGetBill: Response = billInit.GET("$uuid")

        assertThat(HttpStatus.NOT_FOUND_404).isEqualTo(responseFromGetBill.statusCode)
    }

}
