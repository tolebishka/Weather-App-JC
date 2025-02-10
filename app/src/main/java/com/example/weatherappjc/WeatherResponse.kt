package com.example.weatherappjc

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Float,
    val humidity : Int,
    val feels_like: Float
)

data class Weather(
    val description: String
)

data class Wind(
    val speed: Float
)