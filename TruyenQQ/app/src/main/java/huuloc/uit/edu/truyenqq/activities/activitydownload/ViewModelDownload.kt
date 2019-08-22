package huuloc.uit.edu.truyenqq.activities.activitydownload

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import huuloc.uit.edu.truyenqq.data.Chap
import huuloc.uit.edu.truyenqq.database.ImageChap
import huuloc.uit.edu.truyenqq.database.ImageChapRepository
import huuloc.uit.edu.truyenqq.database.ImageStorageManager
import huuloc.uit.edu.truyenqq.database.StoryChap
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.URL

class ViewModelDownloadFactory(val application: Application, val context: Context, val bookId: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelDownload(application, context, bookId) as T
    }
}

class ViewModelDownload(val application: Application, val context: Context, val bookId: String) : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val repo: ImageChapRepository by lazy {
        ImageChapRepository(application)
    }

    val select = mutableListOf<String>()
    val listChap = MutableLiveData<List<Chap>>().apply { value = mutableListOf() }
    val Story = MutableLiveData<StoryChap>().apply { value = null }
    val bitmap = MutableLiveData<Bitmap>().apply { value = null }

    init {
        loadListChap(bookId)
        loadStoryReading()
    }

    fun loadStoryReading() {
        compo.add(
            apiManager.getStoryImageChap(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("### load ${it.image}")
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

    fun downloadListChap(list: List<String>) {
        list.forEach { i ->
            apiManager.getListImage(bookId, i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.list != null) {
                        AsyncTaskLoad(context, i).execute(it.list)
                    }
                }, {

                })
        }
    }


    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskLoad internal constructor(val context: Context, val chap: String) :
        AsyncTask<List<String>, Void, Void>() {
        override fun doInBackground(vararg p0: List<String>?): Void? {
            if (repo.findChapId(bookId, chap) == "0") {
                println("### size${p0[0]!!.size} $chap")
                for ((i, s) in p0[0]!!.withIndex()) {
                    val bitmap = getBitmapFromURL(s)
                    if (bitmap != null) {
                        try {
                            ImageStorageManager.saveToInternalStorage(context, bitmap, "$bookId+$chap+$i")
                            repo.insert(
                                listOf(
                                    ImageChap(
                                        bookId = bookId,
                                        chapId = chap,
                                        position = i,
                                        name = "$bookId+$chap+$i"
                                    )
                                )
                            )
                            //println("### $bookId+$chap+$i ")
                        } catch (ex: Exception) {

                        }
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(context, "Tải xong chap $chap", Toast.LENGTH_SHORT).show()
        }
    }

    fun getBitmapFromURL(_url: String): Bitmap? {
        return try {
            val url = URL(_url)
            val connection = url.openConnection()
            connection.doInput = true
            connection.connect()
            val input = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            myBitmap
        } catch (ex: IOException) {
            null
        }
    }
}