package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.*
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


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
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.SECONDS)
            .connectTimeout(2, TimeUnit.SECONDS)
            .build()

        fun getHelperHtml(): Retrofit? {
            if (sInstance == null) {
                sInstance = Retrofit
                    .Builder()
                    .baseUrl("https://truyenqq.com/")
                    .addConverterFactory(HtmlOrJsonConverterFactory())
                    //.client(okHttpClient)
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

    fun getListCategory(): Single<CategoryList> {
        return buildRequest(_apiRestFull.getListCategory())
    }

    fun geListTop(
        _offset: Int,
        _limit: Int,
        _col: String,
        _order: String = "DESC",
        _typeList: String = "category"
    ): Single<ListStory> {
        return buildRequest(
            _apiRestFull.getListTop(
                offset = _offset,
                limit = _limit,
                col = _col,
                order = _order,
                typeList = _typeList
            )
        )
    }

    fun getListNewUpdate(_offset: Int, _col: String, _arrayCategory: String? = ""): Single<ListStory> {
        return buildRequest(_apiRestFull.getListNewUpdate(offset = _offset, col = _col, arrayCategory = _arrayCategory))
    }

    fun getSchedule(_date: String): Single<ScheduleStoryList> {
        return buildRequest(_apiRestFull.getSchduleStory(date = _date))
    }

    fun getHistory(_offset: Int, _limit: Int, _user_id: String): Single<ListHistoryRead> {
        return buildRequest(_apiRestFull.getHistory(offset = _offset, limit = _limit, user_id = _user_id))
    }

    fun setSubscribe(_book_id: String, _user_id: String): Single<Subscribe> {
        return buildRequest(_apiRestFull.setSubscribe(book_id = _book_id, user_id = _user_id))
    }

    fun getSubscribe(_offset: Int, _limit: Int, _user_id: String): Single<ListStory> {
        return buildRequest(_apiRestFull.getSubscribe(offset = _offset, limit = _limit, user_id = _user_id))
    }

    fun getListChaps(_offset: Int, _limit: Int, _book_id: String): Single<ListChap> {
        return buildRequest(_apiRestFull.getListChaps(offset = _offset, limit = _limit, book_id = _book_id))
    }

    fun getStoryReading(_id: String, _user_id: String?): Single<StoryRead> {
        return buildRequest(_apiRestFull.getStoryReading(book_id = _id, user_id = _user_id))
    }
    /*fun saveCookie(id: String) {
        putShare.putString("id", id)
        putShare.apply()
    }*/
}
