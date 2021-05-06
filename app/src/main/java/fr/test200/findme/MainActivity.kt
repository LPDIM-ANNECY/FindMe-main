package fr.test200.findme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.utils.BottomNavBarBindNavigation
import fr.test200.findme.utils.bottomNavBarIsVisible


class MainActivity : AppCompatActivity()  {

    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController = findNavController(R.id.nav_host_fragment)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        bottomNavBarIsVisible(bottomNavigation,false)
        BottomNavBarBindNavigation(bottomNavigation)

    }



}