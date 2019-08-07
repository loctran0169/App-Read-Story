package huuloc.uit.edu.truyenqq.activities.newstory

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.network.ApiHelper
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelNewStory : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }
    var story2 = MutableLiveData<ListStory>().apply { value = ListStory(mutableListOf()) }

    init {
        loadData()
    }

    fun result(_offset: Int): LiveData<ListStory> {
        if (story2.value?.list.isNullOrEmpty())
            loadStory(_offset)
        return story2
    }

    @SuppressLint("CheckResult")
    fun loadStory(_offset: Int) {
        val sRestFull = Retrofit
            .Builder()
            .baseUrl("https://api.qiqi.vn/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = sRestFull.create(ApiHelper::class.java)
        val call = api.getListTop(offset = _offset, limit = 20, col = "created", order = "DESC", typeList = "category")
        call.enqueue(object : Callback<ListStory> {
            override fun onFailure(call: Call<ListStory>, t: Throwable) {

            }

            override fun onResponse(call: Call<ListStory>, response: Response<ListStory>) {
                story2.value = response.body()
            }
        })
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