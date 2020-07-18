package com.patrick.fittracker.heatmap

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.HeatMapFragmentBinding
import com.patrick.fittracker.databinding.WeightChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.linechart.cardiochart.CardioChartFragmentArgs
import com.patrick.fittracker.linechart.cardiochart.CardioChartViewModel

class HeatMapFragment : Fragment() {

    private val viewModel by viewModels<HeatMapViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HeatMapFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel





        return binding.root
    }



}
