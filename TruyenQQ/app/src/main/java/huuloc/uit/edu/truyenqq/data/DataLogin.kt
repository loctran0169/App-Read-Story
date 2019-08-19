package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class ChangePassWord(
    @SerializedName("data")
    var data: DataLogin?,
    @SerializedName("success")
    var success: String?,
    @SerializedName("error")
    var error: ErrorChangePassWord?
)

data class DataLogin(
    @SerializedName("error")
    var error: Error?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("avatar")
    var avatar: String?,
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

data class ErrorChangePassWord(
    @SerializedName("password_old")
    var password_old: String?,
    @SerializedName("confirm_password_new")
    var confirm_password_new: String?
)