package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class Subscribe(
    @SerializedName("success")
    var success : Int,
    @SerializedName("status")
    var status : Int
)