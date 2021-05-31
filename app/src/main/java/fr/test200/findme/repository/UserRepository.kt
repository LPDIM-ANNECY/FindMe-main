package fr.test200.findme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.test200.findme.dataClass.User

class UserRepository {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    fun setUser(user: User?) {
        _currentUser.postValue(user!!)
    }

}