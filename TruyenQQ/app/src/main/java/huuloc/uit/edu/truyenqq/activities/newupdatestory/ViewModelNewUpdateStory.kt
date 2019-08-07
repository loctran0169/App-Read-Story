package huuloc.uit.edu.truyenqq.activities.newstory

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.CategoryList
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelNewUpdateStory : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }
    @SuppressLint("CheckResult")
    fun loadData(category: String?): LiveData<ListStory> {
        apiManager.getListNewUpdate(_offset = 0, _col = "modified",_arrayCategory = category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                story.value = it
            }, {

            })
        return story
    }

}