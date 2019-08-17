package huuloc.uit.edu.truyenqq.activities.rank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.data.LoadMoreObject
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelRank : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var offsetDay =0
    var offsetWeek =0
    var offsetMonth =0
    var offsetLike =0
    var storyDay = MutableLiveData<List<StoryInformation>>().apply { value = itemsDay }
    var itemsDay = mutableListOf<StoryInformation>()

    var storyWeek = MutableLiveData<List<StoryInformation>>().apply { value = itemsWeek }
    var itemsWeek = mutableListOf<StoryInformation>()

    var storyMonth = MutableLiveData<List<StoryInformation>>().apply { value =itemsMonth }
    var itemsMonth = mutableListOf<StoryInformation>()

    var storyLike = MutableLiveData<List<StoryInformation>>().apply { value = itemsLike }
    var itemsLike = mutableListOf<StoryInformation>()

    val loadMoreDay = MutableLiveData<LoadMoreObject>()
    val loadMoreWeek = MutableLiveData<LoadMoreObject>()
    val loadMoreMonth = MutableLiveData<LoadMoreObject>()
    val loadMoreLike = MutableLiveData<LoadMoreObject>()

    fun loadDay(category: String?) {
        val loadMore = itemsDay.isNotEmpty()
        compo.add(
            apiManager.geListTop(offsetDay, 20, category!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!loadMore) {
                        itemsDay.clear()
                        itemsDay.addAll(it.list)
                        storyDay.value = itemsDay
                    } else {
                        val start = itemsDay.lastIndex
                        val end = start + it.list.size
                        itemsDay.addAll(it.list)
                        this.loadMoreDay.value = LoadMoreObject(start, end)
                    }
                    offsetDay += 20
                }, {

                })
        )
    }

    fun loadWeek(category: String?) {
        val loadMore = itemsWeek.isNotEmpty()
        compo.add(
            apiManager.geListTop(offsetWeek, 20, category!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!loadMore) {
                        itemsWeek.clear()
                        itemsWeek.addAll(it.list)
                        storyWeek.value = itemsWeek
                    } else {
                        val start = itemsWeek.lastIndex
                        val end = start + it.list.size
                        itemsWeek.addAll(it.list)
                        this.loadMoreWeek.value = LoadMoreObject(start, end)
                    }
                    offsetWeek += 20
                }, {

                })
        )
    }

    fun loadMonth(category: String?) {
        val loadMore = itemsMonth.isNotEmpty()
        compo.add(
            apiManager.geListTop(offsetMonth, 20, category!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!loadMore) {
                        itemsMonth.clear()
                        itemsMonth.addAll(it.list)
                        storyMonth.value = itemsMonth
                    } else {
                        val start = itemsMonth.lastIndex
                        val end = start + it.list.size
                        itemsMonth.addAll(it.list)
                        this.loadMoreMonth.value = LoadMoreObject(start, end)
                    }
                    offsetMonth += 20
                }, {

                })
        )
    }

    fun loadLike(category: String?) {
        val loadMore = itemsLike.isNotEmpty()
        compo.add(
            apiManager.geListTop(offsetLike, 20, category!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!loadMore) {
                        itemsLike.clear()
                        itemsLike.addAll(it.list)
                        storyLike.value = itemsLike
                    } else {
                        val start = itemsLike.lastIndex
                        val end = start + it.list.size
                        itemsLike.addAll(it.list)
                        this.loadMoreLike.value = LoadMoreObject(start, end)
                    }
                    offsetLike += 20
                }, {

                })
        )
    }
}