package fr.test200.findme.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.R
import fr.test200.findme.databinding.RegisterFragmentBinding
import fr.test200.findme.utils.bottomNavBarIsVisible

class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterFragmentBinding

    private val viewModel: RegisterViewModel by viewModels{
        RegisterViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_fragment,
            container,
            false
        )

        binding.registerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //endregion

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        binding.btnConnection.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMapFragment())
        }

        //bottom nav bar
        val bottomNavigation = requireActivity().findViewById<View>(R.id.activity_main_bottom_navigation) as BottomNavigationView?
        bottomNavBarIsVisible(bottomNavigation,false)

        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun onBackPressed() {}
}