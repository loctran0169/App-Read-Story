package huuloc.uit.edu.truyenqq.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ImageChap")
class ImageChap(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "bookId")
    val bookId: String,
    @ColumnInfo(name = "chapId")
    val chapId: String,
    @ColumnInfo(name = "position")
    val position: Int,
    @ColumnInfo(name = "name")
    val name: String
)