package huuloc.uit.edu.truyenqq.network

import huuloc.uit.edu.truyenqq.data.StoryInfo
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.nio.charset.Charset

object JsoupConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        println(annotations.toString())
        return JsoupConverter(retrofit!!.baseUrl().toString())
    }
}

private class JsoupConverter(val baseUri: String) : Converter<ResponseBody, List<StoryInfo>?> {

    override fun convert(value: ResponseBody?): List<StoryInfo>? {
        val charset = value?.contentType()?.charset() ?: Charset.forName("UTF-8")

        val parser = when (value?.contentType().toString()) {
            "application/xml", "text/xml" -> Parser.xmlParser()
            else -> Parser.htmlParser()
        }
        var elements = Jsoup.parse(value?.byteStream(), charset.name(), baseUri, parser).select("div.story-item")
        var list = mutableListOf<StoryInfo>()
        if (elements != null) {
            for (item in elements) {
                val elementImage = item.getElementsByTag("a").first()
                if (item != null) {
                    list.add(
                        StoryInfo(
                            url = elementImage.attr("href"),
                            name = elementImage.getElementsByTag("img").attr("alt"),
                            image = elementImage.getElementsByTag("img").attr("src"),
                            time = item.getElementsByTag("span").first().ownText().substringBefore(" Tr")
                        )
                    )
                }
            }
        }
        return list
    }
}