package fr.test200.findme.profile

import androidx.lifecycle.*
import fr.test200.findme.dataClass.Category
import fr.test200.findme.dataClass.Place
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


    private var _allPlacesByCategory = MutableLiveData<List<Place>>()
    val allPlacesByCategory: LiveData<List<Place>>
        get() = _allPlacesByCategory

    private var _allPlacesByUser = MutableLiveData<List<Place>>()
    val allPlacesByUser: LiveData<List<Place>>
        get() = _allPlacesByUser

    init {
        _eventTryConnection.value = false
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            val categories = FindMeApi.APIService.getAllCategories()
            categories.let {
                _allCategories.value = it.body()
            }
        }
    }

    fun getAllPlacesByCategory(name : String) {
        viewModelScope.launch {
            val places = FindMeApi.APIService.getAllPlacesByCategory(name)
            println("PLAAAAAAACES " + places.body()?.size.toString())
            places.let {
                _allPlacesByCategory.value = it.body()
            }
        }
    }

    fun getAllPlacesByUser(id : Int) {
        viewModelScope.launch {
            val places = FindMeApi.APIService.getPlaceList(id)
            places.let {
                _allPlacesByUser.value = it.body()
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