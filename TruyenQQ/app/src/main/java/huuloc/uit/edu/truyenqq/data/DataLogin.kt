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

data class ChangeInformation(
    @SerializedName("data")
    var data: DataLogin?,
    @SerializedName("success")
    var success: String?
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
    @SerializedName("gender")
    var sex: String?,
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
    var password: String?,
    @SerializedName("username")
    var username: String?
)

data class ErrorChangePassWord(
    @SerializedName("password_old")
    var password_old: String?,
    @SerializedName("confirm_password_new")
    var confirm_password_new: String?
)