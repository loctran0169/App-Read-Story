package huuloc.uit.edu.truyenqq.activities.newstory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelNewStory : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }

    init {
        loadData()
    }

    fun loadData() {
        compo.add(
            apiManager.geListTop(0, 20, "created")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    story.value = it
                }, {

                })
        )
    }
}