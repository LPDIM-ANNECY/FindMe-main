package fr.test200.findme.register

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    //region Event
    private val _eventTryRegister = MutableLiveData<Boolean>()
    val eventTryRegister: LiveData<Boolean>
        get() = _eventTryRegister
    //endregion

    init {
        _eventTryRegister.value = false
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}