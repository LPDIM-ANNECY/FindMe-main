package fr.test200.findme.itinerary_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.findme.dataClass.Place
import fr.test200.findme.network.FindMeApi
import kotlinx.coroutines.launch

class ItineraryListViewModel : ViewModel() {

    //region Data
    private val _listPlace = MutableLiveData<List<Place>>()
    val listPlace: LiveData<List<Place>>
        get() = _listPlace
    //endregion

    fun getPlaceList() {
        viewModelScope.launch {
            val isLogin = FindMeApi.APIService.getPlaceList()
            isLogin?.let {
                if (it.isSuccessful){
                    _listPlace.value = it.body()
                }
            }
        }
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}