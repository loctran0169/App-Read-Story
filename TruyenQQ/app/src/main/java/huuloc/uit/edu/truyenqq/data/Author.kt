package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Author(
    @Expose
    @SerializedName("id")
    var id : String,
    @Expose
    @SerializedName("name")
    var name : String
)