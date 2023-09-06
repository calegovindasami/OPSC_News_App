package com.example.opsc_news_app.Services

import com.example.opsc_news_app.models.NewsModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface IArticle{
    @GET("top-headlines")
    fun getArticles(@Query("country") country: String, @Query("apiKey") apiKey:String): io.reactivex.Observable<NewsModel>

}
class NewsBuilder {
    companion object{
        private val client= OkHttpClient
            .Builder()
            .connectTimeout(timeout = 5,TimeUnit.MINUTES)
            .readTimeout(timeout = 2,TimeUnit.MINUTES)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(NewsBuilder.client)
            .build()
            .create((IArticle::class.java))

            fun buildService(): IArticle {
                return retrofit
            }
    }



}