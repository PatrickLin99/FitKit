package com.patrick.fittracker.group

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.R
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.databinding.GroupFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class GroupFragment() : Fragment() {

    var group: MuscleGroupTypeFilter = MuscleGroupTypeFilter.CHEST


    private val viewModel by viewModels <GroupViewModel> {getVmFactory(group)}

//    val viewModel: GroupViewModel by lazy {
//        ViewModelProvider(this).get(GroupViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        viewModel.navigateToPoseSelect.observe(viewLifecycleOwner, Observer {
            it?.let {
//                Log.d("test navigateToPoseSelect", "$it")
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
//        viewModel.navigationToSelect(SelectedMuscleGroup())
        when (it.id) {
            R.id.muscle_chest_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.CHEST)
            }
            R.id.muscle_biceps_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.BICEPS)
            }
            R.id.muscle_deltoids_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.DELTOIDS)
            }
            R.id.muscle_lowerback_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.LOWERBACK)
            }
            R.id.muscle_abs_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.ABS)
            }
            R.id.muscle_upperback_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.UPPERBACK)
            }
            R.id.muscle_frontlegs_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.FRONTLEGS)
            }
            R.id.muscle_calf_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.CALF)
            }
            R.id.muscle_backlegs_image -> {
                viewModel.getMuscleGroupResult(group = MuscleGroupTypeFilter.BACKLEGS)
            }
        }
    }





//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
