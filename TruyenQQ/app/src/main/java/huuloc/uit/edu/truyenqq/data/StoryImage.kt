package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class StoryImage(
    @SerializedName("order")
    var order: String,
    @SerializedName("next")
    var next: String,
    @SerializedName("prev")
    var prev: String,
    @SerializedName("listPhoto")
    var list: List<String>
)