package com.patrick.fittracker.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.HomeFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class HomeFragment : Fragment() {

//    private lateinit var viewModel: HomeViewModel
    private val viewModel by viewModels <HomeViewModel> {getVmFactory()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
//        binding.viewModel = viewModel

        binding.viewSelfTraining.setOnClickListener {
            it?.let {
                binding.viewSelfTraining.setBackgroundColor(Color.parseColor("#3aacba"))
                binding.mainSelfTraining.setTextColor(Color.parseColor("#383838"))
                findNavController().navigate(NavigationDirections.actionGlobalGroupFragment())
            }
        }

        binding.viewSelect.setOnClickListener {
            it?.let {
                binding.viewSelect.setBackgroundColor(Color.parseColor("#3aacba"))
                binding.mainSelect.setTextColor(Color.parseColor("#383838"))
                findNavController().navigate(NavigationDirections.actionGlobalClassOptionFragment())
            }
        }

        binding.viewCardio.setOnClickListener {
            it?.let {
                binding.viewCardio.setBackgroundColor(Color.parseColor("#3aacba"))
                binding.mainCardio.setTextColor(Color.parseColor("#383838"))
                findNavController().navigate(NavigationDirections.actionGlobalCardioSelectionFragment())
            }
        }
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
