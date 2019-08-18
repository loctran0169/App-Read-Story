package huuloc.uit.edu.truyenqq.activities.reading

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.*
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelReadingFactory(val bookId: String, val chap: String, val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelReading(bookId, chap, context) as T
    }
}

class ViewModelReading(val bookId: String, var chap: String, val context: Context) : ViewModel() {
    val share = MysharedPreferences(context)
    val user_id = share.gẹtShare.getString(USER_ID, null)
    val compo: CompositeDisposable by lazy { CompositeDisposable() }
    val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<StoryImage>().apply { value = StoryImage("0.0", "0.0", "0.0", "0.0", mutableListOf()) }
    var listChap = MutableLiveData<ListChap>().apply { value = ListChap(mutableListOf()) }
    var position = MutableLiveData<Int>().apply { value = pos }
    var pos = 1

    init {
        getListChap()
        loadImage()
        if (user_id != null)
            setHistory()
    }

    fun loadImage() {
        compo.add(
            apiManager.getListImage(bookId, chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    story.value = it
                }, {

                })
        )
    }

    fun loadImageNextOrPrev(_chap: String) {
        val handler = Handler()
        this.chap = _chap
        handler.removeCallbacksAndMessages(null)
        handler.post {
            try {
                apiManager.getListImage(bookId, _chap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (story.value!!.order.toFloat() > it.order.toFloat())
                            pos++
                        else if (story.value!!.order.toFloat() < it.order.toFloat())
                            pos--
                        position.value = pos
                        story.value = it
                    }, {

                    })
            } catch (ex: Exception) {

            }
        }
    }

    fun setHistory() {
        compo.add(
            apiManager.setHistory(bookId, user_id!!, chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        )
    }

    fun getListChap() {
        compo.add(
            apiManager.getListChaps(0, null, bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listChap.value = it
                    findIndex(it.list)
                }, {

                })
        )
    }

    fun findIndex(list: List<Chap>) {
        for ((x, i) in list.withIndex()) {
            if (i.order == chap) {
                position.value = x
                pos = x
            }
        }
    }
}