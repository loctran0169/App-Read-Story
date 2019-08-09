package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class CategoryList(
    @SerializedName("list")
    var list: List<Category>
)
data class Category(
    @Expose
    @SerializedName("id")
    var id : String,
    @Expose
    @SerializedName("name")
    var name : String,
    @Expose
    @SerializedName("info")
    var info : String
)