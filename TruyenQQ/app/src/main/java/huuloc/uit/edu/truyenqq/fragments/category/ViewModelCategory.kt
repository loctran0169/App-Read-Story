package huuloc.uit.edu.truyenqq.fragments.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.CategoryList
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelCategory : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val sLoadingCategory = MutableLiveData<CategoryList>().apply { value = CategoryList(mutableListOf()) }

    init {
        loadCategory()
    }

    fun loadCategory() {
        compo.add(
            apiManager.getListCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sLoadingCategory.value = it
                }, {
                })
        )
    }

}