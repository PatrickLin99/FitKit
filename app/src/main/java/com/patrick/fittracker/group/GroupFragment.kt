package com.patrick.fittracker.group

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.GroupFragmentBinding
import com.patrick.fittracker.databinding.HomeFragmentBinding

class GroupFragment : Fragment() {

    companion object {
        fun newInstance() = GroupFragment()
    }

    private lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.group_fragment, container, false)
        val binding = GroupFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
//        binding.viewModel = viewModel

        binding.muscleChestImage.setOnClickListener {
            it?.let {
                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
        }

        binding.muscleChestImage.setOnClickListener(onClickListener)
        binding.muscleBicepsImage.setOnClickListener(onClickListener)
        binding.muscleDeltoidsImage.setOnClickListener(onClickListener)
        binding.muscleLowerbackImage.setOnClickListener(onClickListener)
        binding.muscleAbsImage.setOnClickListener(onClickListener)
        binding.muscleUpperbackImage.setOnClickListener(onClickListener)
        binding.muscleFrontlegsImage.setOnClickListener(onClickListener)
        binding.muscleCalfImage.setOnClickListener(onClickListener)
        binding.muscleBacklegsImage.setOnClickListener(onClickListener)

        return  binding.root
    }


    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.muscle_chest_image -> viewModel.readData("chest")
            R.id.muscle_biceps_image -> viewModel.readData("biceps")
            R.id.muscle_deltoids_image -> viewModel.readData("deltoids")
            R.id.muscle_lowerback_image -> viewModel.readData("lowerback")
            R.id.muscle_abs_image -> viewModel.readData("abs")
            R.id.muscle_upperback_image -> viewModel.readData("upperback")
            R.id.muscle_frontlegs_image -> viewModel.readData("frontlegs")
            R.id.muscle_calf_image -> viewModel.readData("calf")
            R.id.muscle_backlegs_image -> viewModel.readData("backlegs")
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
