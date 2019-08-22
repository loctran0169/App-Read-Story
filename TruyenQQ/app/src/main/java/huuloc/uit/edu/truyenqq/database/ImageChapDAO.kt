package huuloc.uit.edu.truyenqq.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageChapDAO {
    @Insert
    fun insertImage(vararg imageChap: ImageChap)

    @Insert
    fun insert(vararg storyChap: StoryChap)

    @Insert
    fun insert(vararg queue : QueueDownload)

    @Query("select *from ImageChap where bookId = :bookId and chapId=:idChap order by position asc")
    fun getAllImageWithId(bookId: String, idChap: String): LiveData<List<ImageChap>>

    @Query("select *from StoryChap")
    fun getAllDataStory(): LiveData<List<StoryChap>>

    @Query("select *from QueueDownload where bookId=:bookId")
    fun getDataQueueById(bookId: String): List<QueueDownload>

    @Query("select *from StoryChap where id = :bookId")
    fun getDataStory(bookId: String): LiveData<StoryChap>

    @Query("select distinct chapId from ImageChap where bookId = :bookId order by chapId ASC")
    fun countStory(bookId: String): LiveData<List<String>>

    @Query("select count(distinct chapId) from ImageChap where bookId = :bookId and chapId=:chapId")
    fun findChapId(bookId: String, chapId: String): String

    @Query("delete from ImageChap where bookId = :bookId and chapId = :idChap")
    fun deleteImageWithId(bookId: String, idChap: String)

    @Query("delete from QueueDownload where bookId = :bookId and chapId = :idChap")
    fun deleteQueue(bookId: String, idChap: String)

    @Query("delete from ImageChap")
    fun removeAll()

}