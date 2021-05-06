package fr.test200.findme.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.Place
import fr.test200.findme.R
import fr.test200.findme.dataClass.Category
import fr.test200.findme.databinding.ProfileFragmentBinding
import fr.test200.findme.network.FindMeApi
import fr.test200.findme.utils.bottomNavBarIsVisible
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private var places : List<Place>? =  null

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runBlocking {
            launch {
                places = FindMeApi.APIService.getPlaceList().body()
            }
        }
        val bottomNavigation = requireActivity().findViewById<View>(R.id.activity_main_bottom_navigation) as BottomNavigationView?
        bottomNavBarIsVisible(bottomNavigation, true)

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
                createCardView(category)
            }
        })

        /*
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionLoginFragmentToProfile())
        }*/

        return binding.root
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

    private fun createCardView(category: Category){
        val inflater = LayoutInflater.from(this.context)
        val successCards = inflater.inflate(R.layout.success_card,null) as CardView

        successCards.findViewById<TextView>(R.id.card_name_category).text = category.name
        successCards.findViewById<ImageView>(R.id.card_success_drawable).setImageResource(getDrawable(category.name))
        successCards.findViewById<ImageView>(R.id.card_success_drawable)
        successCards.findViewById<TextView>(R.id.card_nb_success).text = getNbSuccessFormatted(category)


        val params = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
        params.setMargins(0, 10, 0, 10)
        successCards.layoutParams = params
        successCards.setOnClickListener {
            runBlocking {
                launch {
                    showDialog(category)
                }
            }

            Log.v("click", " identifiant : ${category.id}")
        }

        binding.listSuccessCard.addView(successCards)
    }

    private fun showDialog(category: Category) {
        val dialog = AlertDialog.Builder(this.context)
        val dialogView = layoutInflater.inflate(R.layout.popup_category, null, false)

        val infoSuccess = getInfoSuccess(category)
        val restSuccess = infoSuccess[0]?.let { infoSuccess[1]?.minus(it) }

        dialogView.findViewById<TextView>(R.id.popup_category_title).text = category.name
        dialogView.findViewById<TextView>(R.id.popup_category_nb_success).text = getNbSuccessFormatted(category)
        dialogView.findViewById<TextView>(R.id.popup_category_description).text = "Il en reste $restSuccess. Saurez-vous retrouver lequels ?"
        dialogView.findViewById<ImageView>(R.id.popup_category_success_drawable).setImageResource(getDrawable(category.name))

        val yesBtn = dialogView.findViewById<Button>(R.id.popup_category_see_monuments)

        dialog.setView(dialogView)

        val alertDialog = dialog.create()
        alertDialog.show()

        yesBtn.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    private fun getInfoSuccess(category: Category): Array<Int?> {
        val nbVisitedPlace = places?.filter { it.category_id == category.id && it.visited == true }?.count()
        val nbMaxPlaceByCategory = places?.filter { it.category_id == category.id }?.count()

        return arrayOf(nbVisitedPlace, nbMaxPlaceByCategory)
    }

    private fun getNbSuccessFormatted(category: Category): String {
        val infoSuccess = getInfoSuccess(category)

        return "${infoSuccess[0]} / ${infoSuccess[1]}"
    }
}