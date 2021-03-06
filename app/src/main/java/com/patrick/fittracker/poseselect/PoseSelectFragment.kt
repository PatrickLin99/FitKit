package com.patrick.fittracker.poseselect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.databinding.PoseSelectFragmentBinding

class PoseSelectFragment : BottomSheetDialogFragment() {

    val viewModel: PoseSelectViewModel by lazy { ViewModelProvider(this).get(PoseSelectViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PoseSelectFragmentBinding.inflate(inflater, container,false)
        val movement = PoseSelectFragmentArgs.fromBundle(requireArguments()).muscleKey

        val viewModelFactory = PoseSelectViewModelFactory(movement)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(PoseSelectViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = PostSelectAdapter(PostSelectAdapter.OnClickListener{
            viewModel.navigateToRecord(it)
        })
        binding.muscleSelectPost.adapter = adapter
        movement.let {
            adapter.submitList(it.menu)
        }

        binding.titleMuscleGroup.text = movement.category
        binding.poseselectDown.setOnClickListener {
            dismiss()
        }

        viewModel.navigateToRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(NavigationDirections.actionGlobalRecordFragment(it))
            }
        })

        return binding.root
    }

}
