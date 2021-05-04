package fr.test200.findme.profile

import androidx.lifecycle.*
import fr.test200.findme.dataClass.Category
import fr.test200.findme.network.FindMeApi
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    //region Event
    private val _eventTryConnection = MutableLiveData<Boolean>()
    val eventTryConnection: LiveData<Boolean>
        get() = _eventTryConnection
    //endregion

    private var _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>>
        get() = _allCategories

    init {
        _eventTryConnection.value = false
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            val categories = FindMeApi.APIService.getAllCategories()
            categories?.let {
                _allCategories.value = categories.body()
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