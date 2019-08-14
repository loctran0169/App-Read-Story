package huuloc.uit.edu.truyenqq.activities.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.ListChap
import huuloc.uit.edu.truyenqq.data.StoryRead
import huuloc.uit.edu.truyenqq.data.Subscribe
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelStoryFactory(val bookId: String, val user_id: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelStory(bookId, user_id) as T
    }
}

class ViewModelStory(val bookId: String, val user_id: String?) : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var Story = MutableLiveData<StoryRead>().apply {
        value = StoryRead("", "", "", "", "", 0L, "", "", "", "","", "", mutableListOf(), "")
    }
    var book_id = ""
    var listChap = MutableLiveData<ListChap>().apply { value = ListChap(mutableListOf()) }
    var Subscribe = MutableLiveData<Subscribe>().apply { value = Subscribe(0, 0) }

    init {
        book_id = bookId
        loadListChap(bookId)
        loadStoryReading(bookId, user_id)
    }

    fun loadListChap(_bookId: String) {
        compo.add(
            apiManager.getListChaps(0, null, _bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listChap.value = it
                }, {

                })
        )
    }

    fun loadStoryReading(_bookId: String, _user_id: String?) {
        compo.add(
            apiManager.getStoryReading(_bookId, _user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Story.value = it
                }, {

                })
        )
    }

    fun loadSubscribe(): LiveData<Subscribe> {
        compo.add(
            apiManager.setSubscribe(bookId, user_id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Subscribe.value = it
                }, {

                })
        )
        return Subscribe
    }
}