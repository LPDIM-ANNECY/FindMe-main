package fr.test200.findme.map

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.findme.BuildConfig
import fr.test200.findme.Place
import fr.test200.findme.R
import fr.test200.findme.network.FindMeApi
import fr.test200.findme.network.GoogleMapApi
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _itinerary = MutableLiveData<String>()
    val itinerary: LiveData<String>
        get() = _itinerary

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    fun getItinerary(placeList: List<Place>) {
        val data: MutableMap<String, String> = HashMap()
        var steps: String = "";

        data["origin"] = placeList.first().latitude.toString() + "," + placeList.first().longitude.toString()
        data["destination"] = placeList.last().latitude.toString() + "," + placeList.last().longitude.toString()
        data["mode"] = "walking"

        for (place in placeList) {
            if(place.id != 0 && place.id != placeList.size - 1) {
                steps += "via:" + place.latitude.toString() + "/" + place.longitude.toString() + "|"
            }
        }
        steps.substring(steps.length - 1)

        data["waypoints"] = steps
        data["key"] = BuildConfig.GOOGLE_MAPS_API_KEY

        viewModelScope.launch {
            val isLogin = GoogleMapApi.mapService.getItinerary(data)
            isLogin?.let {
                if (it.isSuccessful){
                    _itinerary.value = it.body()
                }
            }
        }
    }
}