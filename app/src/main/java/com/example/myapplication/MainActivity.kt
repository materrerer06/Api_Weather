package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private val apiKey = "74f6bb6d6024f7a6e1049699f9a4dbe0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityNameField = findViewById<EditText>(R.id.cityNameField)
        val showButton = findViewById<Button>(R.id.showButton)
        val temperatureLabel = findViewById<TextView>(R.id.temperatureLabel)

        showButton.setOnClickListener {
            val city = cityNameField.text.toString()
            if (city.isNotEmpty()) {
                val weatherData = getWeather(city)
                if (weatherData != null) {
                    val temperature = weatherData?.get("temp")
                    if (temperature != null) {
                        temperatureLabel.text = "Temperatura w $city: $temperature °C"
                    } else {
                        temperatureLabel.text = "Nie udało się pobrać temperatury."
                    }
                } else {
                    temperatureLabel.text = "Nie udało się pobrać danych pogodowych dla miasta: $city."
                }
            } else {
                temperatureLabel.text = "Musisz podać nazwę miasta."
            }
        }
    }

    private fun getWeather(city: String): Map<String, Any>? {
        val encodedCity = URLEncoder.encode(city, "UTF-8")
        val urlString = "http://api.openweathermap.org/geo/1.0/direct?q=$encodedCity&limit=1&appid=$apiKey"
        val url = URL(urlString)

        return try {
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                val responseCode = responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    val type = object : TypeToken<Array<Map<String, Any>>>() {}.type
                    val locationData: Array<Map<String, Any>> = Gson().fromJson(jsonString, type)
                    if (locationData.isNotEmpty()) {
                        val location = locationData[0]
                        val lat = location["lat"]
                        val lon = location["lon"]
                        if (lat != null && lon != null) {
                            getWeatherData(lat as Double, lon as Double)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getWeatherData(lat: Double, lon: Double): Map<String, Any>? {
        val urlString = "http://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
        val url = URL(urlString)

        return try {
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                val responseCode = responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    Gson().fromJson(jsonString, Map::class.java) as Map<String, Any>?
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
