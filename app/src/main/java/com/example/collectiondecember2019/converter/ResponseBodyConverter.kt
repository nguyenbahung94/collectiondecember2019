package vn.aipacific.aihealth.simplifier.converter

import com.example.collectiondecember2019.MyCustomApplication
import com.example.collectiondecember2019.converter.BaseResponse
import com.example.collectiondecember2019.converter.NetException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset

class ResponseBodyConverter<T>(
    private val converter: Converter<ResponseBody, BaseResponse<T>>,
    private val type: Type
) : Converter<ResponseBody, T> {

    private val gson = Gson()

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T? {
        responseBody.use { rb ->
            val rawData = readRawString(responseBody)
            val response = converter.convert(rb)!!
            return if (response.valid()) {
                if (response.isSuccess) {
                    response.value
                } else {
                    val code = response.code!!
                    val context = MyCustomApplication.applicationContext()
                    val message = "parse code to message here"
                    throw NetException(code, message, response.message)
                }
            } else {
                return parseRawJson(rawData, type)
            }
        }
    }

    private fun <T> parseRawJson(rawData: String, type: Type): T? {
        return try {
            gson.fromJson(rawData, type)
        } catch (jsonE: JsonSyntaxException) {
            null
        }
    }

    private fun readRawString(responseBody: ResponseBody): String {
        val source: BufferedSource = responseBody.source()
        source.request(Long.MAX_VALUE)
        val buffer: Buffer = source.buffer()
        return buffer.clone().readString(Charset.forName("UTF-8"))
    }
}
