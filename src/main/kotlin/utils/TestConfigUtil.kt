package utils

import java.util.*

object TestConfigUtil {
    private val properties: Properties = Properties()

    init {
        val inputStream = javaClass.classLoader.getResourceAsStream("config.properties")
        properties.load(inputStream)
    }

    fun getProperty(key: String): String {
        return properties.getProperty(key)
    }
}