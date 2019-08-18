package huuloc.uit.edu.truyenqq.fragments.book

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.LoadMoreObject
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.data.USER_ID
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelBookFactory(val conText: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelBook(conText) as T
    }
}

class ViewModelBook(conText: Context) : ViewModel() {
    val share = MysharedPreferences(conText).gẹtShare
    var load = MutableLiveData<Boolean>().apply { value = false }
    var offsetSub = 0
    var offsetHis = 0
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var subcribe = MutableLiveData<List<StoryInformation>>().apply { value = itemsSub }
    var itemsSub = mutableListOf<StoryInformation>()

    var history = MutableLiveData<List<StoryInformation>>().apply { value = itemsHis }
    var itemsHis = mutableListOf<StoryInformation>()

    val loadMoreSub = MutableLiveData<LoadMoreObject>()
    val loadMoreHis = MutableLiveData<LoadMoreObject>()

    init {
        loadSubsribe()
        loadHistory()
    }

    fun loadSubsribe() {
        val loadMore = itemsSub.isNotEmpty()
        compo.add(
            apiManager.getSubscribe(offsetSub, 20, share.getString(USER_ID, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!loadMore) {
                        itemsSub.clear()
                        itemsSub.addAll(it.list)
                        subcribe.value = itemsSub
                    } else {
                        val start = itemsSub.lastIndex
                        val end = start + it.list.size
                        itemsSub.addAll(it.list)
                        subcribe.value = itemsSub
                        this.loadMoreSub.value = LoadMoreObject(start, end)
                    }
                    offsetSub += 20
                }, {

                })
        )
    }

    fun loadHistory() {
        val loadMore = itemsHis.isNotEmpty()
        compo.add(
            apiManager.getHistory(offsetHis, 20, share.getString(USER_ID, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!loadMore) {
                        itemsHis.clear()
                        itemsHis.addAll(it.list)
                        history.value = itemsHis
                    } else {
                        val start = itemsHis.lastIndex
                        val end = start + it.list.size
                        itemsHis.addAll(it.list)
                        this.loadMoreHis.value = LoadMoreObject(start, end)
                    }
                    offsetHis += 20
                }, {
                    println("### lỗi")
                })
        )
    }
}