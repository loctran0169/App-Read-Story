package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.Category
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.data.StoryInfo
import huuloc.uit.edu.truyenqq.data.StoryInformation
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {

    private val _apiServices: ApiHelper by lazy {
        getHelperHtml()!!.create(ApiHelper::class.java)
    }
    private val _apiRestFull: ApiHelper by lazy {
        getHelperRestFull()!!.create(ApiHelper::class.java)
    }
    private val putShare by lazy {
        //context.getSharedPreferences("statusLogin", Context.MODE_PRIVATE).edit()
    }
    private val getShare by lazy {
        //context.getSharedPreferences("statusLogin", Context.MODE_PRIVATE)
    }

    companion object {

        private var sInstance: Retrofit? = null
        private var sRestFull: Retrofit? = null

        fun getHelperHtml(): Retrofit? {
            if (sInstance == null) {
                sInstance = Retrofit
                    .Builder()
                    .baseUrl("https://truyenqq.com/")
                    .addConverterFactory(HtmlOrJsonConverterFactory())
                    .build()
            }
            return sInstance
        }

        fun getHelperRestFull(): Retrofit? {
            if (sRestFull == null) {
                sRestFull = Retrofit
                    .Builder()
                    .baseUrl("https://api.qiqi.vn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return sRestFull
        }
    }

    private fun <T : Any> buildRequest(call: Call<T>): Single<T> {
        return Single.create {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    println("### 1" + it)
                    it.onSuccess(response.body()!!)
                }

                override fun onFailure(p0: Call<T>, response: Throwable) {
                    println("### 2$response")
                    it.onError(response)
                }
            })
        }
    }

    fun getListStory(url: String, country: Int? = 1): Single<List<StoryInfo>> {
        return buildRequest(_apiServices.getListTruyen("_qlg=90298_1f62cfd35d2745c4769db10b4ac0b508", url, country))
    }

    fun getListCategory(): Single<List<Category>> {
        return buildRequest(_apiRestFull.getListCategory())
    }

    fun geListTop(_offset: Int, _limit: Int, _col: String): Single<ListStory> {
        return buildRequest(_apiRestFull.getListTop(offset = _offset, limit = _limit, col = _col))
    }
    /*fun saveCookie(id: String) {
        putShare.putString("id", id)
        putShare.apply()
    }*/
}
