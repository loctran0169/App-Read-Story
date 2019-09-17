package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.*
import huuloc.uit.edu.truyenqq.database.StoryChap
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {

    private val _apiRestFull: ApiHelper by lazy {
        getHelperRestFull()!!.create(ApiHelper::class.java)
    }

    companion object {

        private var sRestFull: Retrofit? = null


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
                    } catch (ex: Exception) {

                    }
                }

                override fun onFailure(p0: Call<T>, response: Throwable) {
                    it.onError(response)
                }
            })
        }
    }

    private fun <Bitmap : Any> buildImage(call: Call<Bitmap>): Single<Bitmap> {
        return Single.create {
            call.enqueue(object : Callback<Bitmap> {
                override fun onResponse(call: Call<Bitmap>, response: Response<Bitmap>) {
                    try {
                        it.onSuccess(response.body()!!)
                    } catch (ex: Exception) {

                    }
                }

                override fun onFailure(p0: Call<Bitmap>, response: Throwable) {
                    it.onError(response)
                }
            })
        }
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

    fun getHistory(_offset: Int, _limit: Int, _user_id: String?): Single<ListStory> {
        return buildRequest(_apiRestFull.getHistory(offset = _offset, limit = _limit, user_id = _user_id))
    }

    fun setSubscribe(_book_id: String, _user_id: String): Single<Subscribe> {
        return buildRequest(_apiRestFull.setSubscribe(book_id = _book_id, user_id = _user_id))
    }

    fun getSubscribe(_offset: Int, _limit: Int, _user_id: String?): Single<ListStory> {
        return buildRequest(_apiRestFull.getSubscribe(offset = _offset, limit = _limit, user_id = _user_id))
    }

    fun getListChaps(_offset: Int, _limit: Int?, _book_id: String): Single<ListChap> {
        return buildRequest(_apiRestFull.getListChaps(offset = _offset, limit = _limit, book_id = _book_id))
    }

    fun getStoryReading(_id: String, _user_id: String?): Single<StoryRead> {
        return buildRequest(_apiRestFull.getStoryReading(book_id = _id, user_id = _user_id))
    }

    fun getStoryImageChap(_id: String): Single<StoryChap> {
        return buildRequest(_apiRestFull.getStoryImageChap(book_id = _id))
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

    fun sendChangePassWord(
        userId: String,
        old: String,
        new1: String,
        new2: String,
        _avatar: String?
    ): Single<ChangePassWord> {
        return buildRequest(
            _apiRestFull.sendChangePassWord(
                user_id = userId,
                password_old = old,
                password_new = new1,
                confirm_password_new = new2,
                avatar = _avatar
            )
        )
    }

    fun sendChangeInformation(
        user_id: String,
        first_name: String,
        last_name: String,
        sex: String,
        avatar: String?,
        phone : String?,
        day: String,
        month: String,
        year: String
    ): Single<ChangeInformation> {
        return buildRequest(
            _apiRestFull.sendChangeInformation(
                user_id = user_id,
                first_name = first_name,
                last_name = last_name,
                sex = sex,
                avatar = avatar,
                phone = phone,
                day = day,
                month = month,
                year = year
            )
        )
    }

    fun postComment(
        bookId: String,
        userId: String,
        name : String,
        email : String,
        content: String,
        level : String,
        type_book: String,
        status: String,
        user_parent: String
    ): Single<Success> {
        return buildRequest(
            _apiRestFull.postComment(
                book_id = bookId,
                user_id = userId,
                name = name,
                email = email,
                content = content,
                level = level,
                type_book = type_book,
                status = status,
                user_parent = user_parent
            )
        )
    }
    fun getListComment(bookId: String, offset : Int): Single<ListComment> {
        return buildRequest(_apiRestFull.getListComment(book_id = bookId, offset = offset))
    }

    fun getSlider():Single<List<Slider>> {
        return buildRequest(_apiRestFull.getSlider())
    }
}
