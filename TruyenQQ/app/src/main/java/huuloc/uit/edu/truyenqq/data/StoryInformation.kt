package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class ListStory(
    @SerializedName("list")
    var list : List<StoryInformation>
)
data class StoryInformation(
    @Expose
    @SerializedName("name")
    var name : String,
    @Expose
    @SerializedName("id")
    var id : String,
    @Expose
    @SerializedName("image")
    val image : String,
    @Expose
    @SerializedName("current_episode")
    val episode : String,
    @Expose
    @SerializedName("modified")
    var modified : String,
    @Expose
    @SerializedName("array_category")
    val category: List<Category>
)