package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.*
import retrofit2.Call
import retrofit2.http.*

const val token_api: String = "1755ddda12372cf6db0b40695b5a3985d65172d6cdfc817156edd0eddf89c4d842073f92511b8b14"

interface ApiHelper {
    @GET("{url}")
    @Html
    fun getListTruyen(
        @Header("Cookie") cookie: String?,
        @Path("url") user: String,
        @Query("country") country: Int? = 1
    ): Call<List<StoryInfo>>

    @GET("book/book")
    fun getListTop(
        @Header("token") token: String = token_api,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String,
        @Query("order") order: String?,
        @Query("typeList") typeList: String?
    ): Call<ListStory>

    @GET("book/book")
    fun getListNewUpdate(
        @Header("token") token: String = token_api,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20,
        @Query("col") col: String = "modified",
        @Query("order") order: String = "DESC",
        @Query("typeList") typeList: String? = "category",
        @Query("arrayCategory") arrayCategory: String?
    ): Call<ListStory>

    @GET("book/category?type_category=2")
    fun getListCategory(@Header("token") token: String = token_api): Call<CategoryList>

    @FormUrlEncoded
    @POST("frontend/public/login")
    @Json
    fun loginTruyenQQ(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("Expire") expire: Int = 1
    ): Call<StatusLogin>

    @GET("book/schedule")
    fun getSchduleStory(
        @Header("token") token: String = token_api,
        @Query("date") date: String,
        @Query("col") col: String = "time_post",
        @Query("order") order: String = "DESC"
    ): Call<ScheduleStoryList>

    @GET("book/history-read")
    fun getHistory(
        @Header("token") token: String = token_api,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String = "modified_date",
        @Query("order") order: String = "DESC",
        @Query("user_id") user_id: String
    ): Call<ListHistoryRead>

    @POST("book/subscribe")
    fun setSubscribe(
        @Header("token") token: String = token_api,
        @Query("book_id") book_id: String,
        @Query("user_id") user_id: String
    ): Call<Subscribe>

    @GET("book/subscribe")
    fun getSubscribe(
        @Header("token") token: String = token_api,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String = "modified",
        @Query("order") order: String = "DESC",
        @Query("user_id") user_id: String
    ): Call<ListStory>

    @GET("book/manga")
    fun getListChaps(
        @Header("token") token: String = token_api,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("col") col: String = "order",
        @Query("order") order: String = "DESC",
        @Query("book_id") book_id: String
    ): Call<ListChap>

    @GET("book/book")
    fun getStoryReading(
        @Header("token") token: String = token_api,
        @Query("id") book_id: String,
        @Query("user_id") user_id: String?
    ): Call<StoryRead>

    @GET("book/manga")
    fun getListImage(
        @Header("token") token: String = token_api,
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
    ) :Call<StatusLogin>
}

annotation class Html
annotation class Json

