package com.example.test_a_e_o_n

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

class NetworkModule{
    private val baseUrl = "https://easypay.world/api-test/"
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private val contentType = "application/json".toMediaType()
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()
    val service: Api = retrofit.create()
}

@Serializable
data class UserInfo(
    @SerialName("login")
    val login:String,
    @SerialName("password")
    val password:String
)

@Serializable
data class LoginResult(
    @SerialName("success")
    val success:String = "true",
    @SerialName("response")
    val response:Token = Token(""),
    @SerialName("error")
    val error:Error = Error()
)

@Serializable
data class Token(
    @SerialName("token")
    val token:String = ""
)

@Serializable
data class Error(
    @SerialName("error_code")
    val errorCode:Int = 0,
    @SerialName("error_msg")
    val errorMsg:String = ""
)

@Serializable
data class PaymentsLoginResult(
    @SerialName("success")
    val success:String = "true",
    @SerialName("response")
    val response:List<Payment> = listOf(),
    @SerialName("error")//TODO
    val error:Error = Error()
)

@Serializable
data class Payment(
    @SerialName("id")
    val id:Int = 0,
    @SerialName("title")
    val title:String = ""
)

interface Api{
    @POST("login")
    suspend fun login(
        @Header("app-key") appKey:String,
        @Header("v") v:String,
        @Body userInfo: UserInfo
    ):LoginResult
    @GET("payments")
    suspend fun payments(
        @Header("app-key") appKey:String,
        @Header("v") v:String,
        @Header("token") token:String,
    ):PaymentsLoginResult
}