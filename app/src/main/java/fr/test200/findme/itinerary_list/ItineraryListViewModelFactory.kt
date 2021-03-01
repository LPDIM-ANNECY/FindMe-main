package fr.test200.findme.itinerary_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.test200.findme.connection.ConnectionViewModel

class ItineraryListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItineraryListViewModel::class.java)) {
            return ItineraryListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}