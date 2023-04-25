package utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import api.model.User
import java.io.File
object TestDataProvider {
    fun getUserData(userName: String): User {
        val testData = readJsonFile("json/testdata.json")
        val userJson = testData.get(userName).asJsonObject
        return Gson().fromJson(userJson, User::class.java)
    }

    private fun readJsonFile(fileName: String): JsonObject {
        val file = File(javaClass.classLoader.getResource(fileName).file)
        return Gson().fromJson(file.reader(), JsonObject::class.java)
    }
}