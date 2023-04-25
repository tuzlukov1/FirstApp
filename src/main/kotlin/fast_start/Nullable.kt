package fast_start

private val city: String? = null
private val country: String? = "Russia"
private val street: String? = ""

fun main() {
    val result = (city?.length?:0) + (country?.length?:0) + (street?.length?:0)
    println(result)
}