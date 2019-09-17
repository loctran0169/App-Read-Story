package huuloc.uit.edu.truyenqq.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.*
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ViewModelHome : ViewModel() {

    var isShow = MutableLiveData<Int>().apply { value = R.id.navHome }

    val cal = Calendar.getInstance()

    val storyNew = MutableLiveData<List<StoryInformation>>().apply { value = mutableListOf() }

    val storyStoryMale = MutableLiveData<List<StoryInformation>>().apply { value = mutableListOf() }

    val storyStoryFemale = MutableLiveData<List<StoryInformation>>().apply { value = mutableListOf() }

    val sLoadingSchedulers = MutableLiveData<ScheduleStoryList>().apply { value = ScheduleStoryList(mutableListOf()) }

    val sLoadingCategory = MutableLiveData<CategoryList>().apply { value = CategoryList(mutableListOf()) }

    val dataLogin = MutableLiveData<DataLogin>().apply { value = null }

    val slider = MutableLiveData<List<Slider>>().apply { value = mutableListOf() }

    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }

    init {
        if (storyNew.value.isNullOrEmpty())
            loadNewStory()
        if (storyStoryMale.value.isNullOrEmpty())
            loadStoryMale()
        if (storyStoryFemale.value.isNullOrEmpty())
            loadStoryFemale()
        loadSchedule()
        if (storyNew.value.isNullOrEmpty())
            loadCategory()
        getSlider()
    }

    fun refresh() {
        loadNewStory()
        loadStoryMale()
        loadStoryFemale()
        loadSchedule()
        loadCategory()
        getSlider()
    }

    fun loadNewStory() {
        compo.add(
            apiManager.getListNewUpdate(0, "modified")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyNew.value = it.list
                }, {
                })
        )
    }

    fun loadStoryMale() {
        compo.add(
            apiManager.getListNewUpdate(0, "modified", "26,27,30,31,32,41,43,47,48,50,57,85,97")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyStoryMale.value = it.list
                }, {
                })
        )
    }

    fun loadStoryFemale() {
        compo.add(
            apiManager.getListNewUpdate(0, "modified", "28,29,36,37,38,39,42,46,51,52,54,75,90,93")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    storyStoryFemale.value = it.list
                }, {
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

    fun getSlider() {
        compo.add(
            apiManager.getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    slider.value = it
                }, {

                })
        )
    }
}