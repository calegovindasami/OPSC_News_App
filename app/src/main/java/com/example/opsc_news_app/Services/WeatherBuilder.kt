package com.example.opsc_news_app.Services

import com.example.opsc_news_app.models.WeatherModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface IWeather {
    @GET("weather")
    fun getWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") apiKey: String, @Query("units") units: String): io.reactivex.Observable<WeatherModel>
}

class WeatherBuilder {
    companion object {
        private val client = OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
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