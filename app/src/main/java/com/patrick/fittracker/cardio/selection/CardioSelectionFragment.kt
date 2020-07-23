package com.patrick.fittracker.cardio.selection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.databinding.CardioSelectionFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlinx.android.synthetic.main.cardio_selection_fragment.*

class CardioSelectionFragment : Fragment() {


    private val viewModel by viewModels <CardioSelectionViewModel> {getVmFactory()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioSelectionFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter =
            CardioSelectionAdapter(CardioSelectionAdapter.OnClickListener{
                viewModel.navigateToCardioRecord(it)
            })
        binding.recyclerCardioSelection.adapter = adapter

        viewModel.cardio.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)

                //indoor checked
                binding.filterIndoor.setOnCheckedChangeListener { buttonView, isChecked ->
                    adapter.submitList(it.filter { it.cardio_unknown == "室內" })
                    binding.filterOutdoor.isChecked = false
                    if (!isChecked) {
                        adapter.submitList(it)
                    }
                }

                //outdoor checked
                binding.filterOutdoor.setOnCheckedChangeListener { buttonView, isChecked ->
                    adapter.submitList(it.filter { it.cardio_unknown == "戶外"})
                    binding.filterIndoor.isChecked = false
                    if (!isChecked){
                        adapter.submitList(it)
                    }
                }

                //relax
                binding.filterRelax.setOnCheckedChangeListener { buttonView, isChecked ->
                    adapter.submitList(it.filter { it.cardio_unknown == "輕鬆" })
                    binding.filterHighBurn.isChecked = false
                    if (!isChecked){
                        adapter.submitList(it)
                    }
                }

                binding.filterHighBurn.setOnCheckedChangeListener { buttonView, isChecked ->
                    adapter.submitList(it.filter { it.cardio_unknown == "高強度" })
                    binding.filterRelax.isChecked = false
                    if (!isChecked) {
                        adapter.submitList(it)
                    }
                }

                binding.filterCombine.setOnCheckedChangeListener { buttonView, isChecked ->
                    adapter.submitList(it.filter { it.cardio_unknown == "綜合" })
                    if (!isChecked){
                        adapter.submitList(it)
                    }
                }
            }
        })

        viewModel.navigationToCardioRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalCardioRecordFragment(it))
                viewModel.navigateToCardioRecordDone()
            }
        })


        return binding.root
    }


}
