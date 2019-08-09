package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListHistoryRead(
    @Expose
    @SerializedName("list")
    var list : List<HistoryRead>
)
data class HistoryRead(
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("book_id")
    var id: String,
    @Expose
    @SerializedName("image")
    val image: String,
    @Expose
    @SerializedName("chap_order")
    val chap_order: String,
    @SerializedName("current_episode")
    val episode: String,
    @Expose
    @SerializedName("modified")
    var modified: String,
    @SerializedName("modified_date")
    var modified_date: String,
    @Expose
    @SerializedName("array_category")
    val category: List<Category>
)