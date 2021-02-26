package fr.test200.findme.login

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    //region Event
    private val _eventTryLogin = MutableLiveData<Boolean>()
    val eventTryLogin: LiveData<Boolean>
        get() = _eventTryLogin
    //endregion

    init {
        _eventTryLogin.value = false
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}