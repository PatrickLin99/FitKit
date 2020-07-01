package com.patrick.fittracker.poseselect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.R

class PoseSelectFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = PoseSelectFragment()
    }

    private lateinit var viewModel: PoseSelectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pose_select_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PoseSelectViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
