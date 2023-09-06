package com.example.opsc_news_app.models

data class CurrencyModel(
    val new_amount: Double,
    val new_currency: String,
    val old_amount: Int,
    val old_currency: String
)