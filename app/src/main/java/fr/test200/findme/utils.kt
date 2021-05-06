package fr.test200.findme

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

object utils {

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

    fun bottomNavBarIsVisible(bottomNavigation : BottomNavigationView?, isVisible : Boolean){

        bottomNavigation?.let{
            if (isVisible){
                it.visibility = View.VISIBLE
            }else{
                it.visibility = View.GONE
            }
        }
    }

    fun BottomNavBarBindNavigation(bottomNavigation : BottomNavigationView?){
        bottomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}