package api.endpoint.bill.reject

import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

class Reject(private val requestSpecification: RequestSpecification) {

    fun POST(): Response {
        return requestSpecification.log().all()
            .contentType(ContentType.JSON)
            .`when`()
            .post("/reject")
    }
}