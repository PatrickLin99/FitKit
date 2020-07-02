package com.patrick.fittracker.poseselect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.databinding.PoseSelectFragmentBinding

class PoseSelectFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = PoseSelectFragment()
    }

//    private lateinit var viewModel: PoseSelectViewModel
    val viewModel: PoseSelectViewModel by lazy {
    ViewModelProvider(this).get(PoseSelectViewModel::class.java)
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PoseSelectFragmentBinding.inflate(inflater, container,false)

        val movement = PoseSelectFragmentArgs.fromBundle(requireArguments()).muscleKey
        Log.d("test movement:", "$movement")

        val viewModelFactory = PoseSelectViewModelFactory(movement)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(PoseSelectViewModel::class.java)
        binding.viewModel = viewModel



        val adapter = PostSelectAdapter()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.muscleSelectPost.adapter = adapter
        movement?.let {
            Log.d("test","7654321 $it")
            adapter.submitList(it.menu)
        }


        viewModel.movementList.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("test","1234567 $it")
                adapter.submitList(it)
            }
        })






        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(PoseSelectViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
