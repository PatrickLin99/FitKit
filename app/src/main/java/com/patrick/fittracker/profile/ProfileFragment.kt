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


        binding.imageView14.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalEditProfileFragment())
        }

        viewModel.getLoginInfoResult()
        viewModel.refresh()
        viewModel.UserInfo.observe(viewLifecycleOwner, Observer {
            Log.d("viewModel.UserInfo.","viewModel.UserInfo: $it")
            it?.let {

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

//        ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
//            refresh.observe(viewLifecycleOwner, Observer {
//                it?.let {
//                    viewModel.refresh()
//                    onRefreshed()
//                }
//            })
//        }

        return binding.root
    }

}
