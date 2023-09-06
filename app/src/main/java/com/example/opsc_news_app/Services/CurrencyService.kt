package com.example.opsc_news_app.Services

import com.example.opsc_news_app.models.CurrencyModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CurrencyService {
    @Headers("X-Api-Key:DzC/pbxco2MvA8VCkXKObg==ojlINMm0mFaLJrG1")
    @GET("convertcurrency")
    fun getCurrency (@Query("have") have:String,@Query("want") want:String,@Query("amt") amount:Double):Observable<CurrencyModel>
}
class CurrencyBuilder{

    companion object {
        private val client = OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CurrencyService::class.java)

        fun buildService(): CurrencyService {
            return retrofit
        }
    }
}