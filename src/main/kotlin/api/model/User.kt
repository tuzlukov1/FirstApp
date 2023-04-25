package api.model
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("id")
    val id: Int
)
