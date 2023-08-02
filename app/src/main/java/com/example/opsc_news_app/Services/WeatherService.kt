package com.example.opsc_news_app.Services

import android.database.Observable
import com.example.opsc_news_app.models.WeatherModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface IWeather {
    @GET("/onecall")
    fun getWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") apiKey: String): io.reactivex.Observable<WeatherModel>
}

class WeatherBuilder {
    companion object {
        private val client = OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create((IWeather::class.java))

        fun buildService(): IWeather {
            return retrofit
        }
    }
}