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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.login.LoginFragmentDirections
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity()  {

    private lateinit var navController: NavController

    //Allow redirection to others fragments when bottom nav bar is pressed
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_ways -> {
                //navController.navigate(R.id.itineraires)
                return@OnNavigationItemSelectedListener true

            }
            R.id.action_map -> {
                //navController.navigate(R.id.carte)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                //navController.navigate(R.id.profile)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController = findNavController(R.id.nav_host_fragment)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        bottomNavigation.visibility = GONE
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }



}