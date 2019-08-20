package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

const val token_api: String = "1755ddda12372cf6db0b40695b5a3985d65172d6cdfc817156edd0eddf89c4d842073f92511b8b14"
const val user_agnet: String =
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36"

interface ApiHelper {

    @GET("book/book")
    fun getListTop(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String,
        @Query("order") order: String?,
        @Query("typeList") typeList: String?
    ): Call<ListStory>

    @GET("book/book")
    fun getListNewUpdate(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20,
        @Query("col") col: String = "modified",
        @Query("order") order: String = "DESC",
        @Query("typeList") typeList: String? = "category",
        @Query("arrayCategory") arrayCategory: String?
    ): Call<ListStory>

    @GET("book/category?col=name&order=ASC")
    fun getListCategory(@Header("token") token: String = token_api): Call<CategoryList>

    @GET("book/schedule")
    fun getSchduleStory(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("date") date: String,
        @Query("col") col: String = "time_post",
        @Query("order") order: String = "DESC"
    ): Call<ScheduleStoryList>

    @GET("book/history-read")
    fun getHistory(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String = "modified_date",
        @Query("order") order: String = "DESC",
        @Query("user_id") user_id: String?
    ): Call<ListStory>

    @POST("book/subscribe")
    fun setSubscribe(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("book_id") book_id: String,
        @Query("user_id") user_id: String
    ): Call<Subscribe>

    @GET("book/subscribe")
    fun getSubscribe(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String = "modified",
        @Query("order") order: String = "DESC",
        @Query("user_id") user_id: String?
    ): Call<ListStory>

    @GET("book/manga")
    fun getListChaps(
        @Header("token") token: String = token_api,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int?,
        @Query("col") col: String = "order",
        @Query("order") order: String = "DESC",
        @Query("book_id") book_id: String
    ): Call<ListChap>

    @GET("book/book")
    fun getStoryReading(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("id") book_id: String,
        @Query("user_id") user_id: String?
    ): Call<StoryRead>

    @GET("book/manga")
    fun getListImage(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("id") book_id: String,
        @Query("typeDetail") typeDetail: String? = "detail",
        @Query("order") chap: String?
    ): Call<StoryImage>

    @FormUrlEncoded
    @PUT("book/history-read")
    fun setHistory(
        @Header("token") token: String = token_api,
        @Query("id") book_id1: String,
        @Field("book_id") book_id2: String,
        @Field("user_id") user_id: String,
        @Field("chap_order") chap_order: String
    ): Call<ResponseBody>

    @GET("book/history-read")
    fun getHistoryReading(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("id") book_id1: String,
        @Query("book_id") book_id2: String,
        @Query("user_id") user_id: String
    ): Call<StatusRead>

    @GET("book/search")
    fun getListSearch(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10,
        @Query("typeList") col: String = "book",
        @Query("typeSearch") order: String = "search",
        @Query("search") search: String
    ): Call<ListStory>

    @FormUrlEncoded
    @PUT("user/register")
    fun sendForgotPassword(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("id") id1: Int = 0,
        @Field("email") email: String,
        @Field("captcha") captcha: String = "123456",
        @Field("word") word: String = "123456",
        @Field("id") id2: Int = 0
    ): Call<ForgotPassword>

    @FormUrlEncoded
    @POST("user/register")
    fun sendRegister(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password_confirm") password_confirm: String,
        @Field("captcha") captcha: String = "123456",
        @Field("word") word: String = "123456"
    ): Call<DataLogin>

    @FormUrlEncoded
    @POST("user/login")
    fun sendLogin(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<DataLogin>

    @FormUrlEncoded
    @PUT("user/user")
    fun sendChangePassWord(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("id") user_id: String,
        @Field("password_old") password_old: String,
        @Field("password_new") password_new: String,
        @Field("confirm_password_new") confirm_password_new: String,
        @Field("avatar") avatar: String?,
        @Field("changePassword") changePassword: String = "true",
        @Field("typeAction") typeAction: String = "edit"
    ): Call<ChangePassWord>

    @FormUrlEncoded
    @PUT("user/user")
    fun sendChangeInformation(
        @Header("token") token: String = token_api,
        @Header("User-Agent") Accept: String = user_agnet,
        @Query("id") user_id: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("gender") sex: String,
        @Field("avatar") avatar: String?,
        @Field("phone") phone: String?,
        @Field("changePassword") changePassword: String = "false",
        @Field("day") day: String,
        @Field("month") month: String,
        @Field("year") year: String,
        @Field("typeAction") typeAction: String = "edit"
    ): Call<ChangeInformation>
}

