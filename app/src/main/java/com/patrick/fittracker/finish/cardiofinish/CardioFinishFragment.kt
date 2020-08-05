package com.patrick.fittracker.finish.cardiofinish

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlin.properties.Delegates

class CardioFinishFragment : Fragment() {

    private val viewModel by viewModels<CardioFinishViewModel> { getVmFactory() }
    private var sameFragment by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioFinishFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        sameFragment = true

        binding.cardioRecordName.text =
            CardioFinishFragmentArgs.fromBundle(requireArguments()).cardioKey

        Handler().postDelayed({
            if (sameFragment) {
                findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
            }
        }, 3000)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        sameFragment = false
    }
}
