package huuloc.uit.edu.truyenqq.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageChapDAO {
    @Insert
    fun insert(vararg imageChap: ImageChap)

    @Query("select *from ImageChap where bookId = :idChap order by position asc")
    fun getAllImageWithId(idChap: String): LiveData<List<ImageChap>>

    @Query("delete from ImageChap where bookId = :bookId and chapId = :idChap")
    fun deleteImageWithId(bookId: String, idChap: String)

    @Query("delete from ImageChap")
    fun removeAll()

}