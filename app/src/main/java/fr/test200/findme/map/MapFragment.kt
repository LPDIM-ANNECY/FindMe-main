package fr.test200.findme.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.test200.findme.databinding.MapFragmentBinding
import fr.test200.findme.R


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