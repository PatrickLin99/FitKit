package com.patrick.fittracker.finish.classoption

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.ClassOptionFinishFragmentBinding
import com.patrick.fittracker.databinding.ClassOptionFragmentBinding
import com.patrick.fittracker.databinding.InnerRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlin.properties.Delegates

class ClassOptionFinishFragment : Fragment() {

    private val viewModel by viewModels<ClassOptionFinishViewModel> { getVmFactory() }
    private var sameFragment by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClassOptionFinishFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        sameFragment = true
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
