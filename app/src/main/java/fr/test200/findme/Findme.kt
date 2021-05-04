package fr.test200.findme

import android.app.Application
import fr.test200.findme.repository.UserRepository

class Findme : Application() {

    companion object {
        val userRepository = UserRepository()
    }

    override fun onCreate() {
        super.onCreate()
    }
}