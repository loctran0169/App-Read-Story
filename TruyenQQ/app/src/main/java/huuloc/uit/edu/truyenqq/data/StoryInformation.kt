package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListStory(
    @SerializedName("list")
    var list: List<StoryInformation>
)

data class StoryInformation(
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("id")
    var id: String,
    @Expose
    @SerializedName("book_id")
    var book_id: String?,
    @Expose
    @SerializedName("image")
    val image: String,
    @Expose
    @SerializedName("like_book")
    val like_book: String,
    @Expose
    @SerializedName("total_view")
    val total_view: String,
    @Expose
    @SerializedName("current_episode")
    val episode: String,
    @Expose
    @SerializedName("chap_order")
    val chap_order: String?,
    @Expose
    @SerializedName("modified")
    var modified: String,
    @Expose
    @SerializedName("total_subscribe")
    val total_subscribe: String,
    @Expose
    @SerializedName("array_category")
    val category: List<Category>
)