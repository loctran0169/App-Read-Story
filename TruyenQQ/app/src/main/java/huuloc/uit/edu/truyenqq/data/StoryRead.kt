package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StoryRead(
    @Expose
    @SerializedName("id")
    var id: String,
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("name_orignal")
    var name_orignal: String?,
    @Expose
    @SerializedName("like_book")
    var like_book: String?,
    @Expose
    @SerializedName("total_view")
    var total_view: String?,
    @Expose
    @SerializedName("modified")
    var modified: Long,
    @Expose
    @SerializedName("info")
    var info: String?,
    @Expose
    @SerializedName("image")
    val image: String,
    @Expose
    @SerializedName("pending")
    var pending: String?,
    @Expose
    @SerializedName("current_episode")
    var newChap: String?,
    @Expose
    @SerializedName("total_subscribe")
    val total_subscribe: String,
    @Expose
    @SerializedName("array_category")
    val category: List<Category>,
    @Expose
    @SerializedName("name_author")
    var author: String
)