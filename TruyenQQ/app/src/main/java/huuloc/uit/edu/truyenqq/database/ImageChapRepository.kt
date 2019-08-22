package huuloc.uit.edu.truyenqq.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ImageChapRepository(application: Application) {

    private val ImageChapDAO: ImageChapDAO? = AppDataBase.get(application)?.ImageChapDAO()

    fun insert(imageChap: List<ImageChap>) {
        AsyncTaskProcessInsert(ImageChapDAO).execute(*imageChap.toTypedArray())
    }

    fun insertStory(story: List<StoryChap>) {
        AsyncTaskProcessInsertStory(ImageChapDAO).execute(*story.toTypedArray())
    }

    fun getAllImageWithId(bookId: String, idChap: String): LiveData<List<ImageChap>>? {
        return ImageChapDAO?.getAllImageWithId(bookId, idChap)
    }

    fun getDataStory(bookId: String): LiveData<StoryChap>? {
        return ImageChapDAO?.getDataStory(bookId)
    }

    fun getAllDataStory(): LiveData<List<StoryChap>>? {
        return ImageChapDAO?.getAllDataStory()
    }

    fun countStory(bookId: String): LiveData<List<String>>? {
        return ImageChapDAO?.countStory(bookId)
    }

    fun findChapId(bookId: String, chapId: String): String? {
        return ImageChapDAO?.findChapId(bookId, chapId)
    }

    fun deleteImageWithId(booid: String, chapId: String) {
        AsyncTaskProcessDelete(ImageChapDAO, booid, chapId).execute()
    }

    fun deleteAll() {
        AsyncTaskProcessDeleteAll(ImageChapDAO).execute()
    }

    private class AsyncTaskProcessInsert internal constructor(val dao: ImageChapDAO?) :
        AsyncTask<ImageChap, Void, Void>() {
        override fun doInBackground(vararg imageChap: ImageChap): Void? {
            dao?.insert(*(imageChap.toMutableList()).toTypedArray())
            return null
        }
    }

    private class AsyncTaskProcessInsertStory internal constructor(val dao: ImageChapDAO?) :
        AsyncTask<StoryChap, Void, Void>() {
        override fun doInBackground(vararg imageChap: StoryChap): Void? {
            dao?.insert(*(imageChap.toMutableList()).toTypedArray())
            return null
        }
    }

    private class AsyncTaskProcessDelete internal constructor(
        val dao: ImageChapDAO?,
        val bookId: String,
        val chapId: String
    ) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            dao?.deleteImageWithId(bookId, chapId)
            return null
        }

    }

    private class AsyncTaskProcessDeleteAll internal constructor(val dao: ImageChapDAO?) :
        AsyncTask<ImageChap, Void, Void>() {
        override fun doInBackground(vararg contact: ImageChap): Void? {
            dao?.removeAll()
            return null
        }
    }
}