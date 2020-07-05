package com.patrick.fittracker.cardio.selection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.databinding.CardioSelectionFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class CardioSelectionFragment : Fragment() {


//    private lateinit var viewModel: CardioSelectionViewModel
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
//                Log.d("getCardio result 00000", "$it")
                adapter.submitList(it)
            }
        })

        viewModel.navigationToCardioRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalCardioRecordFragment(it))
            }
        })

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(CardioSelectionViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
