package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose

data class StoryInfo(
    var url: String,
    var name: String?,
    var image: String,
    var time: String,
    var chap: String? = null,
    var category: List<String>? = emptyList()
)