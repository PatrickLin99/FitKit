package com.patrick.fittracker.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.EditProfileFragmentBinding
import com.patrick.fittracker.databinding.ProfileFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.profile.edit.EditProfileViewModel

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.imageView14.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalEditProfileFragment())
        }






        return binding.root
    }

}
