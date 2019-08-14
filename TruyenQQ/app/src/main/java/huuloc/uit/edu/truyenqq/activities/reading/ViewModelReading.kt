package huuloc.uit.edu.truyenqq.activities.reading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.ListChap
import huuloc.uit.edu.truyenqq.data.StoryImage
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelReadingFactory(val bookId: String, val chap: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelReading(bookId, chap) as T
    }
}

class ViewModelReading(val bookId: String, val chap: String) : ViewModel() {
    val compo: CompositeDisposable by lazy { CompositeDisposable() }
    val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<StoryImage>().apply { value = StoryImage("", "", "", mutableListOf()) }
    var listChap = MutableLiveData<ListChap>().apply { value = ListChap(mutableListOf()) }

    init {
        setHistory()
    }

    fun loadImage(): LiveData<StoryImage> {
        compo.add(
            apiManager.getListImage(bookId, chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    story.value = it
                }, {

                })
        )
        return story
    }

    fun setHistory() {
        compo.add(
            apiManager.setHistory(bookId, "24901", chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        )
    }

    fun getListChap() : LiveData<ListChap> {
        compo.add(
            apiManager.getListChaps(0, null, bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listChap.value = it
                }, {

                })
        )
        return listChap
    }
}