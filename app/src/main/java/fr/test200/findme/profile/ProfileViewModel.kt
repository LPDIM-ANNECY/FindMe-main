package fr.test200.findme.profile

import androidx.lifecycle.*
import fr.test200.findme.dataClass.Category
import fr.test200.findme.dataClass.Place
import fr.test200.findme.network.FindMeApi
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private var _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>>
        get() = _allCategories


    private var _allPlacesByCategory = MutableLiveData<List<Place>>()
    val allPlacesByCategory: LiveData<List<Place>>
        get() = _allPlacesByCategory

    private var _allPlacesByUser = MutableLiveData<List<Place>>()
    val allPlacesByUser: LiveData<List<Place>>
        get() = _allPlacesByUser

    private var _place = MutableLiveData<Place>()
    val place: LiveData<Place>
        get() = _place



    init {
        getAllCategories()
    }

    private fun getAllCategories() {
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

    fun getPlace(id: Int) {
        viewModelScope.launch {
            val place = FindMeApi.APIService.getPlace(id)
            place.let {
                _place.value = it.body()?.get(0)
            }
        }
    }
}