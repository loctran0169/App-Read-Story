package huuloc.uit.edu.truyenqq.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class HtmlOrJsonConverterFactory : Converter.Factory() {
    val _html = JsoupConverterFactory
    val _json = GsonConverterFactory.create()
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation.toString() == "@"+Html::class.java.name+"()") {
                return _html.responseBodyConverter(type, annotations, retrofit)
            }
            if (annotation.toString() == "@"+Json::class.java.name+"()") {
                return _json.responseBodyConverter(type, annotations, retrofit)
            }
        }
        return null
    }
}