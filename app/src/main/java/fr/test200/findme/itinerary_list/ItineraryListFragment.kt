package fr.test200.findme.itinerary_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.test200.findme.R
import fr.test200.findme.dataClass.Place
import fr.test200.findme.databinding.ItineraryListFragmentBinding

class ItineraryList : Fragment() {

    private lateinit var binding: ItineraryListFragmentBinding

    private val args: ItineraryListArgs by navArgs()

    private val viewModel: ItineraryListViewModel by viewModels{
        ItineraryListViewModelFactory()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.itinerary_list_fragment,
            container,
            false
        )

        binding.itineraryListViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //endregion

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        binding.goToCamera.setOnClickListener {
            findNavController().navigate(ItineraryListDirections.actionItineraryListFragmentToCamera())
        }

        //region Observer
        viewModel.listPlace.observe(viewLifecycleOwner, {
            createPlaceCards(it)
        })
        //endregion

        //region args
        val qrcodeResponse = args.qrcodeResponse

        qrcodeResponse?.let {
            if (qrcodeResponse != "0") {
                Toast.makeText(this.context, qrcodeResponse, Toast.LENGTH_LONG).show()
            }
        }
        //endregion

        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun onBackPressed() {}

    private fun createPlaceCards(placeList: List<Place>) {
        val inflater = LayoutInflater.from(this.context)

        placeList.forEach {
            val placeCard = inflater.inflate(R.layout.place_card,null) as CardView

            placeCard.findViewById<TextView>(R.id.user_name).text = it.name
            //placeCard.findViewById<TextView>(R.id.user_status).text = it.score.toString()

            placeCard.cardElevation = 10F
            val params = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
            params.setMargins(20, 10, 20, 30)
            placeCard.layoutParams = params
            binding.listPlaceHighscore.addView(placeCard)
        }
    }
}

