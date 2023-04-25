import api.ApiClient
import io.restassured.RestAssured.given
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import utils.TestConfigUtil
import utils.TestDataProvider

class P2PSmokeTests {

    lateinit var apiUrl: String
    lateinit var apiToken: String

    @BeforeClass
    fun setup() {
        apiUrl = TestConfigUtil.getProperty("api.url") ?: throw IllegalStateException("Value for key 'api.url' is missing in config.properties")
        apiToken = TestConfigUtil.getProperty("api.token") ?: throw IllegalStateException("Value for key 'api.token' is missing in config.properties")
    }

    @Test(priority = 1)
    fun testCreateObject() {
        val requestBody = mapOf(
            "name" to "Test Object",
            "description" to "This is a test object"
        )

        given()
            .contentType("application/json")
            .body(requestBody)
            .post("$apiUrl/object")
            .then()
            .statusCode(200)
            .extract().response()
            .let {
                assert(it.jsonPath().getString("name") == requestBody["name"])
                assert(it.jsonPath().getString("description") == requestBody["description"])
            }
    }

    @Test(priority = 2)
    fun testGetObject() {
        val objectId = "12345"

        given()
            .get("$apiUrl/object/$objectId")
            .then()
            .statusCode(200)
            .extract().response()
            .let {
                assert(it.jsonPath().getString("id") == objectId)
            }
    }

    @Test
    fun createUserTest() {
        val userData = TestDataProvider.getUserData("user1")
        val apiClient = ApiClient(apiUrl)
        val createUserResponse = apiClient.createUser(userData)
        // проверяем ответ
    }
}