package fr.test200.findme.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConnectionViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectionViewModel::class.java)) {
            return ConnectionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}