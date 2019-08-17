package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class DataLogin(
    @SerializedName("error")
    var error: Error?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("first_name")
    var first_name: String?,
    @SerializedName("last_name")
    var last_name: String?,
    @SerializedName("birthday_string")
    var birthday_string: String?,
    @SerializedName("phone")
    var phone: String?
)
data class Error(
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?
)