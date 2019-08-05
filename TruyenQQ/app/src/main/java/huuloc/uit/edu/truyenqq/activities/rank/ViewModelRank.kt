package huuloc.uit.edu.truyenqq.activities.rank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelRank : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var storyDay = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }

    var storyWeek = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }

    var storyMonth = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }

    var storyLike = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }


    init {
        loadStoryDay()
        loadStoryWeek()
        loadStoryMonth()
        loadStoryLike()
    }

    private fun loadStoryDay() {
        compo.add(
            apiManager.geListTop(0, 20, "views_day", "DESC", "category")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyDay.value = it
                }, {

                })
        )
    }

    private fun loadStoryWeek() {
        compo.add(
            apiManager.geListTop(0, 20, "views_week")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyWeek.value = it
                }, {

                })
        )
    }

    private fun loadStoryMonth() {
        compo.add(
            apiManager.geListTop(0, 20, "views_month")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyMonth.value = it
                }, {

                })
        )
    }

    private fun loadStoryLike() {
        compo.add(
            apiManager.geListTop(0, 20, "like_book")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyLike.value = it
                }, {

                })
        )
    }
}