package com.patrick.fittracker.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
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
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : Fragment() {

    private val viewModel by viewModels <HomeViewModel> {getVmFactory()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.cardViewSelfTraining.setOnClickListener {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalGroupFragment())
            }
        }

        binding.cardViewClassOption.setOnClickListener {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalClassOptionFragment())
            }
        }

        binding.cardViewCardio.setOnClickListener {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalCardioSelectionFragment())
            }
        }

        binding.cardViewLocation.setOnClickListener{
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalLocationFragment())
            }
        }


        return binding.root
    }

}
