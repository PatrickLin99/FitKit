package com.patrick.fittracker.group

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.databinding.GroupFragmentBinding

class GroupFragment : Fragment() {

    companion object {
        fun newInstance() = GroupFragment()
    }

//    private lateinit var viewModel: GroupViewModel

    val viewModel: GroupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        binding.muscleChestImage.setOnClickListener {
            it?.let {
                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
        }

        viewModel.navigateToPoseSelect.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("test navigateToPoseSelect", "$it")
                findNavController().navigate(NavigationDirections.actionGlobalPoseSelectFragment(it))
            }
        })





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
        viewModel.navigationToSelect(SelectedMuscleGroup())
        when (it.id) {
            R.id.muscle_chest_image -> {
                viewModel.readData("chest")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_biceps_image -> {
                viewModel.readData("biceps")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_deltoids_image -> {
                viewModel.readData("deltoids")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_lowerback_image -> {
                viewModel.readData("lowerback")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_abs_image -> {
                viewModel.readData("abs")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_upperback_image -> {
                viewModel.readData("upperback")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_frontlegs_image -> {
                viewModel.readData("frontlegs")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_calf_image -> {
                viewModel.readData("calf")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
            R.id.muscle_backlegs_image -> {
                viewModel.readData("backlegs")
//                findNavController().navigate(R.id.action_global_poseSelectFragment)
            }
        }
    }





//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
