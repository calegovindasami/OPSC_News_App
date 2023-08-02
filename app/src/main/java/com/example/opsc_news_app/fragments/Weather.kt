package com.example.opsc_news_app.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.opsc_news_app.R
import com.example.opsc_news_app.Services.WeatherBuilder
import com.example.opsc_news_app.models.WeatherModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val WEATHER_API_KEY = "7ca59a41f155a36695fb7a70d68e9c92"

/**
 * A simple [Fragment] subclass.
 * Use the [Weather.newInstance] factory method to
 * create an instance of this fragment.
 */
class Weather : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var locationClient: FusedLocationProviderClient
    private var longitude: Double? = null
    private var latitude: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getUserCords(view)

        return view
    }

    private fun onSuccess(view: View, weather: WeatherModel) {
        val txtTemperature = view.findViewById<TextView>(R.id.txtTemperature)
        txtTemperature.text = weather.main.temp.toString() + "°C"

        val iconCode = weather.weather[0].icon
        val imgUri = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
        val imgView = view.findViewById<ImageView>(R.id.imgWeather)
        imgView.setImageURI(Uri.parse(imgUri))
        Glide.with(requireContext()).load(Uri.parse(imgUri)).into(imgView)

    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_LONG).show()
        Log.e("Weather Service", t.message.toString())
    }

    private fun getUserCords(view: View) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
        }
        locationClient.lastLocation
            .addOnCompleteListener { location ->
                    latitude = location.result.latitude
                    longitude = location.result.longitude

                val compositeDisposable = CompositeDisposable()
                compositeDisposable.add(
                    WeatherBuilder.buildService().getWeather(latitude!!, longitude!!, WEATHER_API_KEY, "metric")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({response -> onSuccess(view, response)}, {t -> onFailure(t)}))
            }
    }

    fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Weather.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Weather().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}