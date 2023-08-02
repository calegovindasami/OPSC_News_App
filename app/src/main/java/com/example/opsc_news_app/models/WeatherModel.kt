package com.example.opsc_news_app.models

data class WeatherModel(
    val alerts: List<Alert> = listOf(),
    val current: Current = Current(),
    val daily: List<Any> = listOf(),
    val hourly: List<Any> = listOf(),
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val minutely: List<Any> = listOf(),
    val timezone: String = "",
    val timezone_offset: Int = 0
) {
    data class Alert(
        val description: String = "",
        val end: Int = 0,
        val event: String = "",
        val sender_name: String = "",
        val start: Int = 0,
        val tags: List<Any> = listOf()
    )

    data class Current(
        val clouds: Int = 0,
        val dew_point: Double = 0.0,
        val dt: Int = 0,
        val feels_like: Double = 0.0,
        val humidity: Int = 0,
        val pressure: Int = 0,
        val sunrise: Int = 0,
        val sunset: Int = 0,
        val temp: Double = 0.0,
        val uvi: Double = 0.0,
        val visibility: Int = 0,
        val weather: List<Weather> = listOf(),
        val wind_deg: Int = 0,
        val wind_gust: Double = 0.0,
        val wind_speed: Double = 0.0
    ) {
        data class Weather(
            val description: String = "",
            val icon: String = "",
            val id: Int = 0,
            val main: String = ""
        )
    }
}