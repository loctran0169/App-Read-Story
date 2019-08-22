package huuloc.uit.edu.truyenqq.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "StoryChap")
data class StoryChap(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "name_orignal")
    @SerializedName("name_orignal")
    val name_orignal: String,
    @ColumnInfo(name = "like_book")
    @SerializedName("like_book")
    val like_book: Int,
    @ColumnInfo(name = "total_view")
    @SerializedName("total_view")
    val total_view: String,
    @ColumnInfo(name = "modified")
    @SerializedName("modified")
    val modified: String,
    @ColumnInfo(name = "info")
    @SerializedName("info")
    val info: String,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String,
    @ColumnInfo(name = "pending")
    @SerializedName("pending")
    val pending: String,
    @ColumnInfo(name = "subscribe")
    @SerializedName("subscribe")
    val subscribe: String,
    @ColumnInfo(name = "current_episode")
    @SerializedName("current_episode")
    val current_episode: String,
    @ColumnInfo(name = "total_subscribe")
    @SerializedName("total_subscribe")
    val total_subscribe: String,
    @ColumnInfo(name = "name_author")
    @SerializedName("name_author")
    val name_author: String
)
