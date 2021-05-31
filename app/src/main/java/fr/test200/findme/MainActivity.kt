package fr.test200.findme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.utils.bottomNavBarIsVisible
import fr.test200.findme.network.FindMeApi


class MainActivity : AppCompatActivity()  {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController = findNavController(R.id.nav_host_fragment)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        bottomNavBarIsVisible(bottomNavigation,false)

        getUserInfo()

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_ways -> {
                    //return@setOnNavigationItemSelectedListener navigateFragmentId(bottomNavigation, it.itemId, R.id.profileFragment)
                }
                R.id.action_map -> {
                    //return@setOnNavigationItemSelectedListener navigateFragmentId(bottomNavigation, it.itemId, R.id.profileFragment)
                }
                R.id.action_profile -> {
                    return@setOnNavigationItemSelectedListener navigateFragmentId(bottomNavigation, it.itemId, R.id.profileFragment)
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
        /*bottomNavigation.setOnNavigationItemReselectedListener {

        }*/

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

    private fun navigateFragmentId(bottomNavigation : BottomNavigationView, idItem : Int, idFragment : Int): Boolean {
        if(bottomNavigation.selectedItemId != idItem) {
            navController.navigate(idFragment)
            return true
        }
        return false
    }

}