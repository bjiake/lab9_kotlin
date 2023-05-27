package com.example.lab9_1.data

import com.example.lab9_1.App
import com.example.lab9_1.data.local.CitySW
import com.example.lab9_1.data.local.PositionSW
import com.example.lab9_1.data.local.WeatherSW
import com.example.lab9_1.data.network.WeatherNW
import com.example.lab9_1.domain.WeatherData
import com.example.lab9_1.domain.model.City
import com.example.lab9_1.domain.model.Position
import com.example.lab9_1.domain.model.Weather

fun WeatherSW.toDomain() = Weather(
    temperature = temperature, pressure = pressure, dtTxt = dtTxt, iconURL = iconURL
)

fun WeatherNW.DataWeather.toDomain() = Weather(
    temperature = this.main.temp, pressure = this.main.pressure, dtTxt = this.dtTxt, iconURL = this.weather.first().icon
)

internal suspend fun WeatherNW.toDomain(): WeatherData = WeatherData(
    this.list.toDomain(),
    this.city.toDomain(),
    App.weatherDatabase.weatherDao().getPosition().toDomain()
)
internal fun List<WeatherNW.DataWeather>.toDomain(): List<Weather> = map {
    it.toDomain()
}
internal fun WeatherNW.City.toDomain(): City = City(
    cityName = name
)

@JvmName("toDomainWeatherSW")
internal fun List<WeatherSW>.toDomain(): List<Weather> = map {
    it.toDomain()
}
internal fun CitySW.toDomain(): City = City(
    cityName = cityName
)
fun PositionSW.toDomain(): Position = Position(lat, lon)


fun Weather.toEntity() = WeatherSW(
    temperature = temperature, pressure = pressure, dtTxt = dtTxt, iconURL = iconURL
)
internal fun List<WeatherNW.DataWeather>.toSW(): List<WeatherSW> = map {
    it.toSW()
}
internal fun WeatherNW.City.toSW(): CitySW = CitySW(
    cityName = name
)
fun Position.toSW(): PositionSW = PositionSW(lat, lon)
internal fun WeatherNW.DataWeather.toSW(): WeatherSW = WeatherSW(
    dtTxt = dtTxt,
    temperature = main.temp,
    pressure = main.pressure,
    iconURL = weather.first().icon
)

