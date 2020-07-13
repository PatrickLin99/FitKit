package com.patrick.fittracker.finish.cardiofinish

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.CardioFinishFragmentBinding
import com.patrick.fittracker.databinding.CardioRecordFragmentBinding
import com.patrick.fittracker.databinding.CardioSelectionFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.cardio.CardioRecordFragmentArgs
import com.patrick.fittracker.record.cardio.CardioRecordViewModel

class CardioFinishFragment : Fragment() {


    private val viewModel by viewModels <CardioFinishViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioFinishFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        Handler().postDelayed({ findNavController().navigate(NavigationDirections.actionGlobalHomeFragment()) },3000)

        return binding.root
    }
}
