package fr.test200.findme.profile

import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.test200.findme.Findme
import fr.test200.findme.R
import fr.test200.findme.dataClass.Category
import fr.test200.findme.dataClass.Place
import fr.test200.findme.dataClass.User
import fr.test200.findme.databinding.ProfileFragmentBinding
import fr.test200.findme.network.FindMeApi
import fr.test200.findme.utils.bottomNavBarIsVisible
import kotlinx.android.synthetic.main.card_category_places.view.*
import kotlinx.android.synthetic.main.popup_category_places.view.*
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
                places = FindMeApi.APIService.getPlaceList(1).body()
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

        Findme.userRepository.currentUser.observe(viewLifecycleOwner, {
           displayUserInfo(it)
        })

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        viewModel.allCategories.observe(viewLifecycleOwner, {
            it.forEach { category ->
                places?.let { it1 -> createCardView(category, it1) }
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

    private fun createCardView(category: Category, places: List<Place>){
        val inflater = LayoutInflater.from(this.context)
        val successCards = inflater.inflate(R.layout.success_card, null) as CardView

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
                    showDialogTrophy(category)
                }
            }
        }

        binding.listSuccessCard.addView(successCards)
    }

    private fun showDialogTrophy(category: Category) {
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

            viewModel.getAllPlacesByCategory(category.name)
            viewModel.allPlacesByCategory.observe(viewLifecycleOwner, {
                Log.d("allPlacesByCategory", it?.size.toString() )
                it?.let { it1 ->
                    showDialogPlaces(category, it1)
                }
            })

        }

    }

    private fun displayUserInfo(user : User) {
        try {
            val picture = user.picture.split(',')[1]
            val imageBytes = Base64.decode(picture, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.imageAvatar.setImageBitmap(decodedImage)
        } catch(e: IllegalArgumentException) {
            Log.d("Base64 avatar", "error format")
        }

        binding.userNameProfile.text = user.pseudo


        viewModel.getAllPlacesByUser(user.id)
        viewModel.allPlacesByUser.observe(viewLifecycleOwner, {
            val nbVisitedPlace = places?.filter { place -> place.visited == true }?.count()
            val nbMaxPlaceByCategory = places?.size

            binding.nbTrophee.text = "${nbVisitedPlace.toString()} / ${nbMaxPlaceByCategory.toString()}"
        })
    }

    private fun displayPlacesCard(view: View, places: List<Place>) {
        val inflater = LayoutInflater.from(context)

        places.forEach {
            val placeCard = inflater.inflate(R.layout.card_category_places, null) as CardView

            placeCard.popup_category_place_title.text = it.name
            placeCard.popup_category_place_description.text = it.description?.substring(0, 50) + "..."

            val visited = this.places?.filter { place -> place.id == it.id }?.get(0)?.visited
            if(visited == true)
            {
                placeCard.card_category_places_lock.visibility = View.INVISIBLE
                placeCard.card_category_places_lock_background.visibility = View.INVISIBLE
            } else {
                placeCard.popup_category_place_see_more.visibility = View.INVISIBLE
            }


            if(it.picture?.length!! > 0) {
                try {
                    val picture = it.picture?.split(',')?.get(1)
                    val imageBytes = Base64.decode(picture, Base64.DEFAULT)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    placeCard.card_category_places_image.setImageBitmap(decodedImage)
                } catch(e: IllegalArgumentException) {
                    Log.d("Base64 avatar", "error format")
                }
            }


            view.list_category_places.addView(placeCard)
        }
    }

    private var alertDialog : AlertDialog? = null
    private fun showDialogPlaces(category: Category, places : List<Place>) {
        alertDialog?.dismiss()
        val dialog = AlertDialog.Builder(this.context)
        val dialogView = layoutInflater.inflate(R.layout.popup_category_places, null, false)

        dialogView.findViewById<TextView>(R.id.popup_category_places_title).text = category.name.toLowerCase()

        displayPlacesCard(dialogView, places)
        dialog.setView(dialogView)

        alertDialog = dialog.create()
        alertDialog?.show()
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