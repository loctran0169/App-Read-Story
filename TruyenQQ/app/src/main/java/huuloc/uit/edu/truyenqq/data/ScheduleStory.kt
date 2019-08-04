package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName
data class ScheduleStoryList(
    @SerializedName("list")
    var list : List<ScheduleStory>?
)
data class ScheduleStory(
    @SerializedName("id")
    var id : String,
    @SerializedName("name")
    var name : String,
    @SerializedName("time_post")
    val time_post : String,
    @SerializedName("status")
    var status : String
)