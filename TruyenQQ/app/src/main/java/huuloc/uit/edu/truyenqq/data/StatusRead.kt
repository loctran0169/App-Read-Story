package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class StatusRead(
    @SerializedName("status")
    var status : String,
    @SerializedName("data")
    var data : StatusReadInfor
)
data class StatusReadInfor(
    @SerializedName("book_id")
    var book_id : String,
    @SerializedName("modified_date")
    var modified_date : String,
    @SerializedName("chap_order")
    var chap_order : String
)