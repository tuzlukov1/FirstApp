package api.endpoint.bill

import api.model.Bill
import com.google.gson.Gson
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

class BillRequest(private val requestSpecification: RequestSpecification) {

    fun PUT(uuid: String,bill: Bill): Response {
        val paymentJson = Gson().toJson(bill)

        return requestSpecification
            .contentType(ContentType.JSON)
            .accept("application/json")
            .body(paymentJson)
            .put("/$uuid")
    }

    fun GET(uuid: String): Response {
        return requestSpecification
            .contentType(ContentType.JSON)
            .`when`()
            .get("/$uuid")
    }
}
