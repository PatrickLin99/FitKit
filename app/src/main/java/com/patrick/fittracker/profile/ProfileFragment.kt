package com.patrick.fittracker.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.MainViewModel
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.databinding.ProfileFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        binding.editButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalEditProfileFragment())
        }

        viewModel.getLoginInfoResult()
        viewModel.refresh()
        viewModel.UserInfo.observe(viewLifecycleOwner, Observer {
            Log.d("viewModel.UserInfo.","viewModel.UserInfo: $it")
            it?.let {

            }
        })

        binding.lowBodyFat.visibility = View.GONE
        binding.fitBodyFat.visibility = View.GONE
        binding.fitBodyFat.visibility = View.GONE
        binding.superLowBodyFat.visibility = View.GONE

        viewModel.UserInfo.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.getLoginInfoResult()
//                viewModel.refresh()
                when (viewModel.UserInfo.value?.userProfile?.info_bodyFat) {
                    in 1..8.toLong() -> binding.superLowBodyFat.visibility = View.VISIBLE
                    in 8..12.toLong() -> binding.lowBodyFat.visibility = View.VISIBLE
                    in 13..20.toLong() -> binding.fitBodyFat.visibility = View.VISIBLE
                    in 20..50.toLong() -> binding.highBodyFat.visibility = View.VISIBLE
                }

            }
        })


        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.layoutSwipeRefreshHome.isRefreshing = it
            }
        })


        return binding.root
    }

}
