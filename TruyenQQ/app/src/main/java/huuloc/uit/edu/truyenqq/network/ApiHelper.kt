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
        @Query("order") order: String = "DESC",
        @Query("typeList") typeList: String = "category"
    ): Call<ListStory>

    @GET("/book/category?type_category=2&typeList=menu")
    fun getListCategory(@Header("token") token: String = token_api): Call<List<Category>>

    @FormUrlEncoded
    @POST("frontend/public/login")
    @Json
    fun loginTruyenQQ(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("Expire") expire: Int = 1
    ): Call<StatusLogin>


}

annotation class Html
annotation class Json

