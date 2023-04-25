package api

import api.model.User
import java.util.ArrayList


class ApiClient(private val baseUrl: String) {

    fun getUsers(): List<User> {
        val url = "$baseUrl/users"
        val response = executeGetRequest(url)
        return parseUsersFromResponse(response)
    }

    fun createUser(user: User): User {
        val url = "$baseUrl/users"
        val requestBody = serializeUserToRequestBody(user)
        val response = executePostRequest(url, requestBody)
        return parseUserFromResponse(response)
    }

    fun updateUser(user: User): User {
        val url = "$baseUrl/users/${user.id}"
        val requestBody = serializeUserToRequestBody(user)
        val response = executePutRequest(url, requestBody)
        return parseUserFromResponse(response)
    }

    fun deleteUser(userId: String) {
        val url = "$baseUrl/users/$userId"
        executeDeleteRequest(url)
    }

    // методы для выполнения HTTP-запросов
    private fun executeGetRequest(url: String): String {
        return ""
    }

    private fun executePostRequest(url: String, requestBody: String): String {
        return ""
    }

    private fun executePutRequest(url: String, requestBody: String): String {
        return ""
    }

    private fun executeDeleteRequest(url: String) {

    }

    // методы для обработки ответов
    private fun parseUsersFromResponse(response: String): List<User> {
        return listOf(User("1", "2", "3", 4))
    }

    private fun parseUserFromResponse(response: String): User {
        return User("1", "2", "3", 4)
    }

    // методы для работы с данными
    private fun serializeUserToRequestBody(user: User): String {
        return ""
    }

}
