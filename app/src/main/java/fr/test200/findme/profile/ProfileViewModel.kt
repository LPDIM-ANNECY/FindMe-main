package fr.test200.findme.profile

import androidx.lifecycle.*

class ProfileViewModel : ViewModel() {

    //region Event
    private val _eventTryConnection = MutableLiveData<Boolean>()
    val eventTryConnection: LiveData<Boolean>
        get() = _eventTryConnection
    //endregion

    init {
        _eventTryConnection.value = false
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}