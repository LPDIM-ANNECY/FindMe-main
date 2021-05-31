package fr.test200.findme.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.test200.findme.R
import fr.test200.findme.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    private val viewModel: ProfileViewModel by viewModels{
        ProfileViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.profile_fragment,
            container,
            false
        )

        binding.profileViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //endregion

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        /*
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionLoginFragmentToProfile())
        }*/

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