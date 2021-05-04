package fr.test200.findme.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.test200.findme.R
import fr.test200.findme.dataClass.Category
import fr.test200.findme.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

        viewModel.allCategories.observe(viewLifecycleOwner, {
            it.forEach { category ->
                createCardView(category.name)
            }
        })

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

    private fun getDrawable(categoryName: String): Int {
        return when (categoryName) {
            "RELIGIEUX" -> R.drawable.church
            "PONTS" -> R.drawable.bridge
            "MARCHÉS" -> R.drawable.market
            else -> R.drawable.market
        }
    }

    private fun createCardView(categoryName: String){
        val inflater = LayoutInflater.from(this.context)
        val successCards = inflater.inflate(R.layout.success_card,null) as CardView

        successCards.findViewById<TextView>(R.id.card_name_category).text = categoryName
        successCards.findViewById<ImageView>(R.id.card_success_drawable).setImageResource(getDrawable(categoryName))

        successCards.cardElevation = 10F
        val params = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
        params.setMargins(0, 10, 0, 10)
        successCards.layoutParams = params

        binding.listSuccessCard.addView(successCards)
    }
}