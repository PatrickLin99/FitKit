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

        val viewModelFactory = PoseSelectViewModelFactory(movement)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(PoseSelectViewModel::class.java)
        binding.viewModel = viewModel


        val adapter = PostSelectAdapter(PostSelectAdapter.OnClickListener{
            viewModel.navigateToRecord(it)
        })
        binding.lifecycleOwner = viewLifecycleOwner
        binding.muscleSelectPost.adapter = adapter
        movement?.let {
            Log.d("test","7654321 $it")
            adapter.submitList(it.menu)
        }


        binding.textView16.text = movement.category

        viewModel.navigateToRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
//                Log.d("test navigateToRecord", "$it")
                this.findNavController().navigate(NavigationDirections.actionGlobalRecordFragment(it))
            }
        })

//        viewModel.navigateToRecordTwo.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                Log.d("test safe args", "$it")
//                adapter.submitList(it.menu)
//                this.findNavController().navigate(NavigationDirections.actionGlobalRecordFragment(it))
//            }
//        })

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(PoseSelectViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
