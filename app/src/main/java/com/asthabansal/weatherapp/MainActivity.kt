package com.asthabansal.weatherapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.asthabansal.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private var apiKey = "74f6bb6d6024f7a6e1049699f9a4dbe0"
    private var cityName = "warsaw,pl"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.sendButton.setOnClickListener {
            cityName = activityMainBinding.cityEditText.text.toString()
            WeatherTask().execute()
        }

        WeatherTask().execute()
    }

    inner class WeatherTask : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            activityMainBinding.loader.visibility = View.VISIBLE
            activityMainBinding.mainContainerRelativeLayout.visibility = View.GONE
            activityMainBinding.tvError.visibility = View.GONE
        }

        override fun doInBackground(vararg params: Void?): String? {
            var response: String? = null
            try {
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$cityName&units=metric&appid=$apiKey")
                response = url.readText(Charsets.UTF_8)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!result.isNullOrEmpty()) {
                try {
                    val jsonObj = JSONObject(result)
                    val main = jsonObj.getJSONObject("main")
                    val sys = jsonObj.getJSONObject("sys")
                    val wind = jsonObj.getJSONObject("wind")
                    val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                    val updatedAt = jsonObj.getLong("dt")
                    val updatedText = "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt * 1000))
                    val temp = main.getString("temp") + "°C"
                    val tempMin = "Min temp: " + main.getString("temp_min") + "°C"
                    val tempMax = "Max temp: " + main.getString("temp_max") + "°C"
                    val pressure = main.getString("pressure")
                    val humidity = main.getString("humidity")
                    val sunrise = sys.getLong("sunrise")
                    val sunset = sys.getLong("sunset")
                    val windSpeed = wind.getString("speed")
                    val weatherDescription = weather.getString("description")
                    val address = jsonObj.getString("name") + ", " + sys.getString("country")
                    val bg = R.id.background
                    when {
                        weatherDescription.contains("clouds", ignoreCase = true) -> {
                            activityMainBinding.background.setBackgroundResource(R.drawable.clouds)

                        }

                        weatherDescription.contains("rain", ignoreCase = true) -> {
                            activityMainBinding.background.setBackgroundResource(R.drawable.rain)

                        }
                        weatherDescription.contains("sky", ignoreCase = true) -> {
                            activityMainBinding.background.setBackgroundResource(R.drawable.darksky)

                        }

                        else -> {
                            activityMainBinding.background.setBackgroundColor(
                                ContextCompat.getColor(
                                    this@MainActivity,
                                    R.color.grey
                                )
                            )
                        }
                    }


                    activityMainBinding.address.text = address
                    activityMainBinding.updatedAt.text = updatedText
                    activityMainBinding.status.text = weatherDescription.capitalize(Locale.getDefault())
                    activityMainBinding.temp.text = temp
                    activityMainBinding.tempMin.text = tempMin
                    activityMainBinding.tempMax.text = tempMax
                    activityMainBinding.tvSunriseTime.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                    activityMainBinding.tvSunsetTime.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                    activityMainBinding.tvWindTime.text = windSpeed
                    activityMainBinding.tvPressureTime.text = pressure
                    activityMainBinding.tvHumidityTime.text = humidity

                    activityMainBinding.loader.visibility = View.GONE
                    activityMainBinding.tvError.visibility = View.GONE
                    activityMainBinding.mainContainerRelativeLayout.visibility = View.VISIBLE
                } catch (e: Exception) {
                    e.printStackTrace()
                    activityMainBinding.loader.visibility = View.GONE
                    activityMainBinding.tvError.visibility = View.VISIBLE
                }
            } else {
                activityMainBinding.loader.visibility = View.GONE
                activityMainBinding.tvError.visibility = View.VISIBLE
            }
        }
    }
}
//projekt by mateusz cembala