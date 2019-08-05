package huuloc.uit.edu.truyenqq.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.CategoryList
import huuloc.uit.edu.truyenqq.data.ScheduleStoryList
import huuloc.uit.edu.truyenqq.data.StoryInfo
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ViewModelHome : ViewModel() {
    val cal = Calendar.getInstance()

    val sLoadingNew = MutableLiveData<Boolean>().apply { value = false }
    val storyNew = MutableLiveData<List<StoryInformation>>().apply { value = mutableListOf() }

    val sLoadingStoryMale = MutableLiveData<Boolean>().apply { value = false }
    val storyStoryMale = MutableLiveData<List<StoryInfo>>().apply { value = mutableListOf() }

    val sLoadingStoryFemale = MutableLiveData<Boolean>().apply { value = false }
    val storyStoryFemale = MutableLiveData<List<StoryInfo>>().apply { value = mutableListOf() }

    val sLoadingSchedulers = MutableLiveData<ScheduleStoryList>().apply { value = ScheduleStoryList(mutableListOf()) }

    val sLoadingCategory = MutableLiveData<CategoryList>().apply { value = CategoryList(mutableListOf()) }

    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }

    init {
        loadNewStory()
        loadStoryMale()
        loadStoryFemale()
        loadSchedule()
        loadCategory()
    }

    fun loadNewStory() {
        sLoadingNew.value = false
        compo.add(
            apiManager.getListNewUpdate(0, 20, "list")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyNew.value = it
                    sLoadingNew.value = true
                }, {
                    sLoadingNew.value = false
                })
        )
    }

    fun loadStoryMale() {
        sLoadingStoryMale.value = false
        compo.add(
            apiManager.getListStory("truyen-con-trai.html")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyStoryMale.value = it
                    sLoadingStoryMale.value = true
                }, {
                    sLoadingStoryMale.value = false
                })
        )
    }

    fun loadStoryFemale() {
        sLoadingStoryFemale.value = false
        compo.add(
            apiManager.getListStory("truyen-con-gai.html")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyStoryFemale.value = it
                    sLoadingStoryFemale.value = true
                }, {
                    sLoadingStoryFemale.value = false
                })
        )
    }

    fun loadSchedule() {
        compo.add(
            apiManager.getSchedule("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DATE)}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sLoadingSchedulers.value = it
                }, {
                })
        )
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