package com.patrick.fittracker.analysis.cardioanalysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.databinding.AnalysisCardioFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class AnalysisCardioFragment : Fragment() {

    private val viewModel by viewModels<AnalysisCardioViewModel> { getVmFactory() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AnalysisCardioFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = AnalysisCardioAdapter(AnalysisCardioAdapter.OnClickListener{
            it.let {
                findNavController().navigate(NavigationDirections.actionGlobalCardioChartFragment(it))
            }
        })
        binding.recyclerviewCardioRecord.adapter = adapter

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.distinctBy { it.name })
            }
        })

        return binding.root
    }
}
