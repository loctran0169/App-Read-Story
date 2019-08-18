package huuloc.uit.edu.truyenqq.activities.story

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.*
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelStoryFactory(val bookId: String, val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelStory(bookId, context) as T
    }
}

class ViewModelStory(val bookId: String, val context: Context) : ViewModel() {
    val share = MysharedPreferences(context).gẹtShare
    val user_id = share.getString(USER_ID, null)
    val refresh = MutableLiveData<Boolean>().apply { value = false }
    var loadChap = false
    var loadStory = false
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var Story = MutableLiveData<StoryRead>().apply {
        value = StoryRead("", "", "", "", "", 0L, "", "", "", "", "", "", mutableListOf(), "", FirstChap("", ""))
    }
    var isLoading = MutableLiveData<Boolean>().apply { value = false }
    var book_id = ""
    var listChap = MutableLiveData<List<Chap>>().apply { value = itemChap }
    var itemChap = mutableListOf<Chap>()
    var Subscribe = MutableLiveData<Subscribe>().apply { value = Subscribe(0, 0) }
    var isReaded = MutableLiveData<StatusRead>().apply { value = null }

    init {
        book_id = bookId
        loadListChap(bookId)
        loadStoryReading(bookId)
        if (user_id != null)
            loadHistoryReading(bookId)
    }

    fun loadListChap(_bookId: String) {
        compo.add(
            apiManager.getListChaps(0, null, _bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itemChap.clear()
                    itemChap.addAll(it.list)
                    listChap.value = itemChap
                    loadChap = true
                    if (loadStory)
                        refresh.value = false
                }, {

                })
        )
    }

    fun loadStoryReading(_bookId: String) {
        compo.add(
            apiManager.getStoryReading(_bookId, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Story.value = it
                    isLoading.value = true
                    loadStory = true
                    if (loadChap)
                        refresh.value = false
                }, {

                })
        )
    }

    fun loadHistoryReading(_bookId: String) {
        compo.add(
            apiManager.getHistoryReading(_bookId, user_id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isReaded.value = it
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