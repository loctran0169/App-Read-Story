package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class Slider(
    @SerializedName("id")
    var id: String,
    @SerializedName("book_id")
    var bookId: String,
    @SerializedName("img")
    var image: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("status")
    var status: String

)