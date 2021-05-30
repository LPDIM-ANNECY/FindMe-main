package fr.test200.findme.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import fr.test200.findme.Findme
import fr.test200.findme.dataClass.UserItineraryPost
import fr.test200.findme.itinerary_list.ItineraryListDirections
import fr.test200.findme.network.FindMeApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    val progressState: LiveData<Boolean> get() = _progressState
    private val _progressState = MutableLiveData<Boolean>()


    val navigation: LiveData<NavDirections?> get() = _navigation
    private val _navigation = MutableLiveData<NavDirections?>()

    init {
        _progressState.value = false
    }

    fun searchBarcode(barcode: String) {
        _progressState.value = true
        viewModelScope.launch {
            val userId = Findme.userRepository.currentUser.value?.id
            val qrcodeResponse = userId?.let { FindMeApi.APIService.addUserItinerary(barcode.toInt(), userId) }
            delay(1000)
            qrcodeResponse?.let {
                if (it.isSuccessful) {
                    _navigation.value = CameraFragmentDirections.actionCameraToItineraryListFragment().setQrcodeResponse("Code scanning succeful")
                }
                else {
                    _navigation.value = CameraFragmentDirections.actionCameraToItineraryListFragment().setQrcodeResponse("Error when scanning")
                }
            }
            _progressState.value = false
        }
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigation.value = null
    }

}