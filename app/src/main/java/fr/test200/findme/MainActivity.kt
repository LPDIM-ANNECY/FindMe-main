package fr.test200.findme

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.login.LoginFragmentDirections
import fr.test200.findme.network.FindMeApi
import fr.test200.findme.utils.BottomNavBarBindNavigation
import fr.test200.findme.utils.BottomNavBarIsVisible
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.coroutineScope


class MainActivity : AppCompatActivity()  {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController = findNavController(R.id.nav_host_fragment)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        BottomNavBarIsVisible(bottomNavigation,false)
        BottomNavBarBindNavigation(bottomNavigation)

        getUserInfo()
    }

    private fun getUserInfo() {
        lifecycleScope.launchWhenCreated {
            val user = FindMeApi.APIService.getUserById()
            user?.let {
                if (it.isSuccessful){
                    Findme.userRepository.setUser(it.body()?.get(0))
                }
            }
        }
    }

}