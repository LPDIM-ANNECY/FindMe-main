package fr.test200.findme.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.test200.findme.login.LoginViewModel

class MapViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}