package fr.test200.findme.onBoarding

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.R
import fr.test200.findme.databinding.OnBoardingFragmentBinding
import fr.test200.findme.profile.ProfileFragmentDirections
import fr.test200.findme.utils


class OnBoardingFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: OnBoardingFragmentBinding
    private lateinit var viewPager: ViewPager
    private lateinit var pager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.on_boarding_fragment,
                container,
                false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        viewPager = binding.viewPagerOnBoarding
        //endregion




        if (restorePrefData() != null && restorePrefData() == true) {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToConnectionFragment ())
        }

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        // Show or hide bottom nav bar
        val bottomNavigation = requireActivity().findViewById<View>(R.id.activity_main_bottom_navigation) as BottomNavigationView?
        utils.bottomNavBarIsVisible(bottomNavigation, false)

        binding.btnOnBoardingSuivant.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToConnectionFragment ())
            savePrefsData()
        }

        // 1 - Get ViewPager from layout
        pager = binding.viewPagerOnBoarding
        this.configureViewPager()
        binding.btnOnBoardingSuivant.setOnClickListener {
            this.ViewPagerSuivant()
        }

        binding.btnOnBoardingPrecedent.setOnClickListener {
            this.ViewPagerPrecedent()
        }


        return binding.root

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }


    private fun onBackPressed() {}

    private fun restorePrefData(): Boolean? {
        val pref: SharedPreferences? = context?.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref?.getBoolean("isIntroOpnend", false)
    }

    private fun savePrefsData() {
        val pref: SharedPreferences? = context?.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putBoolean("isIntroOpnend", true)
        editor?.commit()
    }

    private fun configureViewPager() {
        // 2 - Set Adapter PageAdapter and glue it together
        pager.adapter = object : PageAdapter(fragmentManager) {}
    }

    private fun ViewPagerSuivant() {
        var position : Int = pager.currentItem
        if (position < pager.childCount){
            position++
            pager.currentItem = position
        }else{
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToConnectionFragment())
        }
    }

    private fun ViewPagerPrecedent() {
        var position : Int = pager.currentItem
        if (position > 0){
            position--
            pager.currentItem = position
        }
    }



}