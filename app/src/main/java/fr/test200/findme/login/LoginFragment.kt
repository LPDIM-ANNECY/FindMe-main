package fr.test200.findme.login

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
import fr.test200.findme.databinding.LoginFragmentBinding
import fr.test200.findme.utils.bottomNavBarIsVisible


class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding



    private val viewModel: LoginViewModel by viewModels{
        LoginViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )


        binding.loginViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //endregion

        binding.btnConnection.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToConnectionFragment())
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        // Show or hide bottom nav bar
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