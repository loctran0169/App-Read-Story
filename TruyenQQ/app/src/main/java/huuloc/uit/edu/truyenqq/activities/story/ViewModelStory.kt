package huuloc.uit.edu.truyenqq.activities.story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.ListChap
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelStoryFactory(val bookId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelStory(bookId) as T
    }
}

class ViewModelStory(val bookId: String) : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var listChap = MutableLiveData<ListChap>().apply { value = ListChap(mutableListOf()) }

    init {
        println("### ${bookId}")
        loadListChap(bookId)
    }

    fun loadListChap(_bookId: String) {
        compo.add(
            apiManager.getListChaps(0, 20, _bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("### ${it.list.size}")
                    listChap.value = it
                }, {

                })
        )
    }
}