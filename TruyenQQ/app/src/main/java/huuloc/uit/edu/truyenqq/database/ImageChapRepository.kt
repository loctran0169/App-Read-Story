package huuloc.uit.edu.truyenqq.database

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ImageChapRepository(val application: Application) {

    private val ImageChapDAO: ImageChapDAO? = AppDataBase.get(application)?.ImageChapDAO()

    fun insert(imageChap: List<ImageChap>) {
        //AsyncTaskProcessInsert(ImageChapDAO).execute(*imageChap.toTypedArray())
        ImageChapDAO?.insertImage(*(imageChap.toTypedArray()))
    }

    fun insertStory(story: List<StoryChap>) {
        AsyncTaskProcessInsertStory(ImageChapDAO).execute(*story.toTypedArray())
    }

    fun insertQueue(imageChap: List<QueueDownload>) {
        AsyncTaskProcessInsertQueue(ImageChapDAO).execute(*imageChap.toTypedArray())
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

    fun getDataQueueById(bookId: String): List<QueueDownload>? {
        return ImageChapDAO?.getDataQueueById(bookId)
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

    fun deleteQueue(booid: String, chapId: String) {
        AsyncTaskProcessDeleteQueue(ImageChapDAO, booid, chapId).execute()
    }

    fun deleteBook(bookId: String) {
        AsyncTaskProcessDeleteBook(bookId).execute()
    }

    fun deleteAll() {
        AsyncTaskProcessDeleteAll(ImageChapDAO).execute()
    }

    private class AsyncTaskProcessInsert internal constructor(val dao: ImageChapDAO?) :
        AsyncTask<ImageChap, Void, Void>() {
        override fun doInBackground(vararg imageChap: ImageChap): Void? {
            dao?.insertImage(*(imageChap.toMutableList()).toTypedArray())
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

    private class AsyncTaskProcessInsertQueue internal constructor(val dao: ImageChapDAO?) :
        AsyncTask<QueueDownload, Void, Void>() {
        override fun doInBackground(vararg imageChap: QueueDownload): Void? {
            dao?.insert(*(imageChap.toMutableList()).toTypedArray())
            return null
        }

    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskProcessDelete internal constructor(
        val dao: ImageChapDAO?,
        val bookId: String,
        val chapId: String
    ) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean? {
            dao?.getAllImageWithIdNow(bookId,chapId)?.forEachIndexed { index, imageChap ->
                ImageStorageManager.deleteImageFromInternalStorage(application,imageChap.name)
            }
            dao?.deleteImageWithId(bookId, chapId)
            return ImageChapDAO?.countChapWithId(bookId)!!.isNullOrEmpty()
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if(result == true)
                AsyncTaskProcessDeleteBook(bookId).execute()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskProcessDeleteBook internal constructor(val bookId: String) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            ImageChapDAO?.deleteBook(bookId)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            ImageStorageManager.deleteImageFromInternalStorage(application,bookId)
        }
    }

    private class AsyncTaskProcessDeleteQueue internal constructor(
        val dao: ImageChapDAO?,
        val bookId: String,
        val chapId: String
    ) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            dao?.deleteQueue(bookId, chapId)
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