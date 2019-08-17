package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.*
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


class ApiManager {

    private val _apiServices: ApiHelper by lazy {
        getHelperHtml()!!.create(ApiHelper::class.java)
    }
    private val _apiRestFull: ApiHelper by lazy {
        getHelperRestFull()!!.create(ApiHelper::class.java)
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
                    try {
                        it.onSuccess(response.body()!!)
                    }
                    catch (ex :Exception){

                    }
                }

                override fun onFailure(p0: Call<T>, response: Throwable) {
                    println("### $response")
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

    fun setHistory(_book_id: String, _user_id: String, _chap: String): Single<ResponseBody> {
        return buildRequest(
            _apiRestFull.setHistory(
                book_id1 = _book_id,
                book_id2 = _book_id,
                user_id = _user_id,
                chap_order = _chap
            )
        )
    }

    fun getHistory(_offset: Int, _limit: Int, _user_id: String): Single<ListStory> {
        return buildRequest(_apiRestFull.getHistory(offset = _offset, limit = _limit, user_id = _user_id))
    }

    fun setSubscribe(_book_id: String, _user_id: String): Single<Subscribe> {
        return buildRequest(_apiRestFull.setSubscribe(book_id = _book_id, user_id = _user_id))
    }

    fun getSubscribe(_offset: Int, _limit: Int, _user_id: String): Single<ListStory> {
        return buildRequest(_apiRestFull.getSubscribe(offset = _offset, limit = _limit, user_id = _user_id))
    }

    fun getListChaps(_offset: Int, _limit: Int?, _book_id: String): Single<ListChap> {
        return buildRequest(_apiRestFull.getListChaps(offset = _offset, limit = _limit, book_id = _book_id))
    }

    fun getStoryReading(_id: String, _user_id: String?): Single<StoryRead> {
        return buildRequest(_apiRestFull.getStoryReading(book_id = _id, user_id = _user_id))
    }

    fun getListImage(_book_id: String, _chap: String): Single<StoryImage> {
        return buildRequest(_apiRestFull.getListImage(book_id = _book_id, chap = _chap))
    }

    fun getListSearch(_search: String): Single<ListStory> {
        return buildRequest(_apiRestFull.getListSearch(search = _search))
    }

    fun getHistoryReading(bookId: String, userId: String): Single<StatusRead> {
        return buildRequest(_apiRestFull.getHistoryReading(book_id1 = bookId, book_id2 = bookId, user_id = userId))
    }

    fun sendForgotPassword(_email: String): Single<ForgotPassword> {
        return buildRequest(_apiRestFull.sendForgotPassword(email = _email))
    }

    fun sendRegister(_email: String, _password: String): Single<DataLogin> {
        return buildRequest(
            _apiRestFull.sendRegister(
                email = _email,
                username = _email.split("@")[0],
                password = _password,
                password_confirm = _password
            )
        )
    }

    fun sendLogin(_email: String, _password: String): Single<DataLogin> {
        return buildRequest(_apiRestFull.sendLogin(email = _email, password = _password))
    }

}
