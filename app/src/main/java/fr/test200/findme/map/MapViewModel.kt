package fr.test200.findme.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.findme.BuildConfig
import fr.test200.findme.dataClass.Place
import fr.test200.findme.network.GoogleMapApi
import kotlinx.coroutines.launch
import org.json.JSONArray


class MapViewModel : ViewModel() {

    private val _itinerary = MutableLiveData<JSONArray>()
    val itinerary: LiveData<JSONArray>
        get() = _itinerary

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    /*private fun getAllPlacesFromItinerary() {
        viewModelScope.launch {
            val places = FindMeApi.APIService.ge()
            categories.let {
                _allCategories.value = it.body()
            }
        }
    }*/

    /*fun getItineraryRequest(placeList: List<Place>) {
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
            val itinerary_array = GoogleMapApi.mapService.getItinerary(data);
            Log.d("test", itinerary_array.toString());
            itinerary_array?.let {
                if (it.isSuccessful){
                    //_itinerary.value = it.body();
                    createItinerary(it.body());
                }
            }
        }
    }*/

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