package fr.test200.findme.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.findme.BuildConfig
import fr.test200.findme.dataClass.Place
import fr.test200.findme.network.FindMeApi
import fr.test200.findme.network.GoogleMapApi
import kotlinx.coroutines.launch
import org.json.JSONArray


class MapViewModel : ViewModel() {

    private val _itinerary = MutableLiveData<String>()
    val itinerary: LiveData<String>
        get() = _itinerary

    private val _allplaces = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>>
        get() = _allplaces

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    fun getAllPlacesFromItinerary(itineraryId: Int) {
        viewModelScope.launch {
            val places = FindMeApi.APIService.getPlacesByItinerary(itineraryId)
            places.let {
                _allplaces.value = it.body()
            }
        }
    }

    fun getItineraryRequest(placeList: List<Place>) {
        val data: MutableMap<String, String> = HashMap()
        var steps: String = "";

                data["origin"] = placeList.first().latitude.toString() + "," + placeList.first().longitude.toString()
        data["destination"] = placeList.last().latitude.toString() + "," + placeList.last().longitude.toString()
        data["mode"] = "walking"

        for (place in placeList) {
            if(place.id != 0 && place.id != placeList.size - 1) {
                steps += "via:" + place.latitude.toString() + "," + place.longitude.toString() + "|"
            }
        }
        steps.substring(steps.length - 1)

        data["waypoints"] = steps
        data["key"] = BuildConfig.GOOGLE_MAPS_API_KEY

        viewModelScope.launch {
            val itineraryArray = GoogleMapApi.mapService.getItinerary(data)
            Log.d("test", itineraryArray.toString())
            itineraryArray?.let {
                if (it.isSuccessful) {
                    it.body()?.let { it1 -> Log.d("test", it1.toString()) }
                    _itinerary.value = it.body().toString()
                }
            }
        }
    }

    fun JSONArray.forEachString(action: (String) -> Unit) {
        for (i in 0 until length()) {
            action(getString(i))
        }
    }

    fun createItinerary(jsonRequestResult: JSONArray?) {
        jsonRequestResult?.forEachString { item ->
            Log.d("item", item);
        }
    }
}