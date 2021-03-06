package com.patrick.fittracker.finish

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.databinding.FinishRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlin.properties.Delegates

class FinishRecordFragment : Fragment() {

    private val viewModel by viewModels<FinishRecordViewModel> { getVmFactory() }
    var sameFragment by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FinishRecordFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        sameFragment = true

        Handler().postDelayed({
            if (sameFragment) {
                findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
            }

        }, 3000)

        binding.backToHome.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
        }

        val recordName = FinishRecordFragmentArgs.fromBundle(requireArguments()).recordKey
        binding.recordMuscleName.text = recordName

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        sameFragment = false
    }

}
