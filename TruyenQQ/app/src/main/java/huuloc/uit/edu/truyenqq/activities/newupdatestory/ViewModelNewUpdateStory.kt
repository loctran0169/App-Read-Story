package huuloc.uit.edu.truyenqq.activities.newstory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelNewUpdateStory : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<List<StoryInformation>>().apply { value = mutableListOf() }

    init {
        loadData()
    }

    fun loadData() {
        compo.add(
            apiManager.getListNewUpdate(0, 20, "list")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    story.value = it
                }, {
                })
        )
    }
}