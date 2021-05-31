package fr.test200.findme

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.itinerary_list.ItineraryListDirections
import fr.test200.findme.profile.ProfileFragment

object utils {

    fun bottomNavBarIsVisible(bottomNavigation : BottomNavigationView?, isVisible : Boolean = true){

        bottomNavigation?.let{
            if (isVisible){
                it.visibility = View.VISIBLE
            }else{
                it.visibility = View.GONE
            }
        }
    }

}