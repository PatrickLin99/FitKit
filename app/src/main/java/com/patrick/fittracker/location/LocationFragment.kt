package com.patrick.fittracker.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.patrick.fittracker.R
import com.patrick.fittracker.UserManger
import com.patrick.fittracker.databinding.LocationFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlinx.android.synthetic.main.location_fragment.*

class LocationFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<LocationViewModel> { getVmFactory() }
    private lateinit var  googleMap: GoogleMap
    var oriLocation : Location? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it

            viewModel.gymList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    viewModel.insertMarkerValue()
                    val locationSize = it.results?.size ?: 0
                    for (i in 0 until locationSize) {
//                        map.addMarker(
//                            (viewModel.gymList.value?.results?.get(i)?.geometry?.location?.lat?.toDouble())?.let { lat ->
//                                viewModel.gymList.value?.results?.get(i)?.geometry?.location?.lng?.toDouble()
//                                    ?.let { lng ->
//                                        LatLng(
//                                            lat, lng
//                                        )
//                                    }
//                            }?.let { position ->
//                                MarkerOptions().position(position)
//                                    .title(viewModel.gymList.value?.results?.get(i)?.name)
//                            }
//                        ).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.gym_icon))
                        map.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    viewModel.locationLat[i].toDouble(),
                                    viewModel.locationLng[i].toDouble()
                                )
                            ).title(viewModel.locationTitle[i])
                        ).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.gym_icon))
                    }
                }
            })

            permission()

            locationManager()
            if (UserManger.currentLocation.latitude != null) {
                viewModel.gymList.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        map.isMyLocationEnabled = true
                    }
                })
            }
        }
    }


    private fun permission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        } }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startIntent()
                Toast.makeText(requireContext(),"bbbbbbbbbbbbbbbb",Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun locationManager(){
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!(isGPSEnabled || isNetworkEnabled))
//            Snackbar.make(thisView, "目前無開啟任何定位功能", Snackbar.LENGTH_LONG).show()
        else
            try {
                if (isGPSEnabled ) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0L, 0f, locationListener)
                    oriLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
                else if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        0L, 0f, locationListener)
                    oriLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                }
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }
        UserManger.currentLocation.latitude = oriLocation?.latitude
        UserManger.currentLocation.longitude = oriLocation?.longitude
        viewModel.getLocationListResult(key = getString(R.string.google_maps_key), location = "${UserManger.currentLocation.latitude},${UserManger.currentLocation.longitude}",radius = 800, language = "zh-TW", keyword = "健身")

        if(oriLocation != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(oriLocation!!.latitude, oriLocation!!.longitude), 15.4f))

            UserManger.currentLocation.latitude = oriLocation!!.latitude
            UserManger.currentLocation.longitude = oriLocation!!.longitude
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if(oriLocation == null) {
                oriLocation = location
            }
        }
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LocationFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        permission()
        binding.mapView.visibility = View.GONE

        binding.findNearGym.setOnClickListener {
            binding.mapView.visibility = View.VISIBLE
//            permission()
            viewModel.getLocationListResult(key = getString(R.string.google_maps_key), location = "${UserManger.currentLocation.latitude},${UserManger.currentLocation.longitude}",radius = 600, language = "zh-TW", keyword = "健身")
            mapView.getMapAsync(this)
            mapView.onResume()
        }

        val adapter = GymLocationAdapter(GymLocationAdapter.OnClickListener{

        })
        binding.recyclerViewLocationList.adapter = adapter

        val linearSnapHelper = LinearSnapHelper().apply {
            attachToRecyclerView(binding.recyclerViewLocationList)
        }

        binding.recyclerViewLocationList.setOnScrollChangeListener { _, _, _, _, _ ->
            viewModel.onGalleryScrollChange(
                binding.recyclerViewLocationList.layoutManager, linearSnapHelper
            )
        }

            viewModel.snapPosition.observe(viewLifecycleOwner, Observer {
                adapter.selectedPosition.value
//                val cameraLat = viewModel.detailResult.value?.get(it)?.geometry?.location?.lat?.toDouble()
//                val cameraLng = viewModel.detailResult.value?.get(it)?.geometry?.location?.lng?.toDouble()
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(cameraLat ?:0.0, cameraLng ?:0.0), 15.4f))
                viewModel.detailResult.value?.get(it)?.geometry?.location?.let { location ->
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.lat?.toDouble() ?:0.0, location.lng?.toDouble() ?:0.0), 15.4f))
                }

            })

        viewModel.detailResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.getLocationResult()

        return binding.root
    }

}
