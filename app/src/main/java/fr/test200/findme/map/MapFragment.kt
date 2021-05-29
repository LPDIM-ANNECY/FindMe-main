package fr.test200.findme.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.test200.findme.Place
import fr.test200.findme.databinding.MapFragmentBinding
import fr.test200.findme.R
import fr.test200.findme.RadiusType


class MapFragment : Fragment(), OnMapReadyCallback  {
    private lateinit var binding: MapFragmentBinding
    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null

    private val viewModel: MapViewModel by viewModels{
        MapViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.map_fragment,
            container,
            false
        )

        binding.mapViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //endregion


        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.let{
            mapFragment?.getMapAsync(this)
        }

        val placelistahah: MutableList<Place> = mutableListOf();
        val place = Place(1, "truc", 45.919312596446204,6.15764283942438, 1, RadiusType.Large, false, 1)
        val place2 = Place(2, "rthf", 45.92213102764319,6.15249647809534, 1, RadiusType.Large, false, 1)
        val place3 = Place(3, "trdqzsfuc", 45.9244343170087,6.1554796502963836, 1, RadiusType.Large, false, 1)
        val place4 = Place(4, "qff", 45.92131284183732,6.1534800513713686, 1, RadiusType.Large, false, 1)
        placelistahah.add(place);
        placelistahah.add(place2);
        placelistahah.add(place3);
        placelistahah.add(place4);

        viewModel.getItinerary(placelistahah)

        //region Observer
        viewModel.itinerary.observe(viewLifecycleOwner, {
            Log.d("TAG", it)
        })

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun onBackPressed() {}
}