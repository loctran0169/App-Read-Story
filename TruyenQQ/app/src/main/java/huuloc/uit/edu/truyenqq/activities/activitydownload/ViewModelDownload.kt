package huuloc.uit.edu.truyenqq.activities.activitydownload

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import huuloc.uit.edu.truyenqq.data.Chap
import huuloc.uit.edu.truyenqq.database.*
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.services.ServiceDownload
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelDownloadFactory(val application: Application, val context: Context, val bookId: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelDownload(application, context, bookId) as T
    }
}

class ViewModelDownload(val application: Application, val context: Context, val bookId: String) : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    private val ImageChapDAO: ImageChapDAO? = AppDataBase.get(application)?.ImageChapDAO()
    private val repo: ImageChapRepository by lazy { ImageChapRepository(application) }

    var select = mutableListOf<String>()
    var select_temp = mutableListOf<String>()
    val downloaded = mutableListOf<String>()
    val listChap = MutableLiveData<List<Chap>>().apply { value = mutableListOf() }
    val Story = MutableLiveData<StoryChap>().apply { value = null }
    val bitmap = MutableLiveData<Bitmap>().apply { value = null }

    init {
        loadDownloaded()
        loadListChap(bookId)
        loadStoryReading()
    }

    fun loadStoryReading() {
        compo.add(
            apiManager.getStoryImageChap(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Story.value = it
                    Glide.with(context)
                        .asBitmap()
                        .load("http://i.mangaqq.com/ebook/190x247/" + it.image + "?thang=t515")
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                bitmap.value = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {

                            }
                        })
                }, {

                })
        )
    }

    fun loadListChap(_bookId: String) {
        compo.add(
            apiManager.getListChaps(0, null, _bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listChap.value = it.list
                }, {

                })
        )
    }

    fun insertStory() {
        repo.insertStory(listOf(Story.value!!))
    }

    fun loadDownloaded(){
        repo.countStory(bookId)?.observeForever {
            downloaded.addAll(it)
            select.clear()
            select_temp.addAll(it)
        }
    }

    fun saveAndRunServiceDownload(list: List<String>) {
        AsyncTaskProcessInsert(list).execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskProcessInsert internal constructor(val list: List<String>) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg imageChap: Void): Void? {
            list.forEach {
                repo.insertQueue(listOf(QueueDownload(bookId = bookId, chapId = it)))
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val inten = Intent(application, ServiceDownload::class.java)
            val bundle = Bundle()
            bundle.putString("bookId", bookId)
            bundle.putInt("require", list.size)
            inten.putExtras(bundle)
            context.startService(inten)
        }
    }

}