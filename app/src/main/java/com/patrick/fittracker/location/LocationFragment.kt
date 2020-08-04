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

//            map.addMarker(
//                MarkerOptions()
//                    .position(LatLng(25.042496, 121.564919))
//                    .title("AppWorks School")
//            )
            viewModel.gymList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    val locationSize = viewModel.gymList.value?.results?.size?.minus(1) ?: 0
                    for (i in 0..locationSize) {
                        map.addMarker(
                            (viewModel.gymList.value?.results?.get(i)?.geometry?.location?.lat?.toDouble())?.let { it1 ->
                                viewModel.gymList.value?.results?.get(i)?.geometry?.location?.lng?.toDouble()
                                    ?.let { it2 ->
                                        LatLng(
                                            it1, it2
                                        )
                                    }
                            }?.let { it2 ->
                                MarkerOptions().position(it2)
                                    .title(viewModel.gymList.value?.results?.get(i)?.name)
                            }
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


    fun permission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        } }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startIntent()
                Toast.makeText(requireContext(),"bbbbbbbbbbbbbbbb",Toast.LENGTH_LONG).show()

            } else {
//                val snackBar = Snackbar.make(thisView, "無定位功能無法執行程序", Snackbar.LENGTH_INDEFINITE)
//                snackBar.setAction("OK", object : View.OnClickListener {
//                    override fun onClick(v: View?) {
//                        snackBar.setText("aaaaaaaa")
////                        snackBar.dismiss()
//                    }
//                })
//                    .setActionTextColor(Color.LTGRAY)
//                    .show()
            }
        }
    }




    fun locationManager(){
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        var isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

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


//            UserManger.currentLocation.longitude = 121.565023
//            UserManger.currentLocation.latitude = 25.042994

        Log.d("Usermanager.currentlocation","${UserManger.currentLocation.latitude},${UserManger.currentLocation.longitude}")
        if(oriLocation != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(oriLocation!!.latitude, oriLocation!!.longitude), 15.4f))
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.042994,121.565023), 15.0f))

            UserManger.currentLocation.latitude = oriLocation!!.latitude
            UserManger.currentLocation.longitude = oriLocation!!.longitude
        }
    }

    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if(oriLocation == null) {
                oriLocation = location
            }
            //keep CameraOn
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15.4f))

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
//        locationManager()
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

        binding.recyclerViewLocationList.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            viewModel.onGalleryScrollChange(
                binding.recyclerViewLocationList.layoutManager, linearSnapHelper
            )
        }


            viewModel.snapPosition.observe(viewLifecycleOwner, Observer {
                adapter.selectedPosition.value
                val cameraLat = viewModel.detailResult.value?.get(it)?.geometry?.location?.lat?.toDouble()
                val cameraLng = viewModel.detailResult.value?.get(it)?.geometry?.location?.lng?.toDouble()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(cameraLat!!, cameraLng!!), 15.4f))

                Log.d("nnnnnnnnaaaaaaaaa","${viewModel.snapPosition.value }")
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
