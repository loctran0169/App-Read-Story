package huuloc.uit.edu.truyenqq.fragments.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelBook : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val subcribe = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }

    init {
        loadSubsribe()
    }

    fun loadSubsribe() {
        compo.add(
            apiManager.getSubscribe(0, 20, "24901")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        subcribe.value=it
                }, {

                })
        )
    }
}