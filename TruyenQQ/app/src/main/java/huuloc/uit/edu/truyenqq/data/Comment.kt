package huuloc.uit.edu.truyenqq.data

import com.google.gson.annotations.SerializedName

data class ListComment(
    @SerializedName("list")
    var list: List<Comment>
)

data class Comment(
    @SerializedName("id")
    var id: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("parent_id")
    var parent_id: String,
    @SerializedName("level")
    var level: String,
    @SerializedName("created_by")
    var created_by: String,
    @SerializedName("user_parent")
    var user_parent: String,
    @SerializedName("name_comment")
    var name_comment: String,
    @SerializedName("email_comment")
    var email_comment: String,
    @SerializedName("total_like")
    var total_like: String,
    @SerializedName("avatar")
    var avatar: String?
)