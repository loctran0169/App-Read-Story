package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class ListChap(
    @SerializedName("list")
    var list : List<Chap>
)
data class Chap(
    @Expose
    @SerializedName("id")
    val chap_id : String,
    @Expose
    @SerializedName("name")
    var name : String,
    @Expose
    @SerializedName("book_id")
    var book_id : String,
    @Expose
    @SerializedName("order")
    var order : String,
    @Expose
    @SerializedName("created")
    var created : String
)