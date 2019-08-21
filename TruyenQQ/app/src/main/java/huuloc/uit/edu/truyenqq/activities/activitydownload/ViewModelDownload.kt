package huuloc.uit.edu.truyenqq.activities.activitydownload

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.Chap
import huuloc.uit.edu.truyenqq.database.ImageChap
import huuloc.uit.edu.truyenqq.database.ImageChapRepository
import huuloc.uit.edu.truyenqq.database.ImageStorageManager
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
    val listResult = MutableLiveData<List<Boolean>>().apply { value = mutableListOf() }

    init {
        loadListChap(bookId)
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


    fun downloadList(chap: String) {
        compo.add(
            apiManager.getListImage(bookId, chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.list != null) {
                        AsyncTaskLoad(context, chap).execute(it.list)
                    }
                }, {

                })
        )
    }

    inner class AsyncTaskLoad internal constructor(val context: Context, val chap: String) :
        AsyncTask<List<String>, Void, List<Boolean>>() {
        override fun doInBackground(vararg p0: List<String>?): List<Boolean>? {
            val list = mutableListOf<Boolean>()
            p0[0]!!.forEachIndexed { i, s ->
                val bitmap = getBitmapFromURL(s)
                if (bitmap != null) {
                    val str = ImageStorageManager.saveToInternalStorage(context, bitmap, "$bookId+$chap+$i")
                    repo.insert(listOf(ImageChap(bookId = bookId, chapId = chap, position = i, name = "$bookId+$chap+$i")))
                }
                else{

                }
            }
            return null
        }

        override fun onPostExecute(result: List<Boolean>?) {
            super.onPostExecute(result)
            Toast.makeText(context,"Download is Finish",Toast.LENGTH_SHORT).show()
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