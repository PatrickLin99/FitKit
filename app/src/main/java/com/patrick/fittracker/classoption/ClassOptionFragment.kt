package com.patrick.fittracker.classoption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.databinding.CardioSelectionFragmentBinding
import com.patrick.fittracker.databinding.ClassOptionFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlinx.android.synthetic.main.activity_main.*

class ClassOptionFragment : Fragment() {

    private val viewModel by viewModels<ClassOptionViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClassOptionFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = ClassOptionAdapter(ClassOptionAdapter.OnClickListener {
            viewModel.navigateToCardioRecord(it)
        })

        binding.recyclerViewClassOption.adapter = adapter
        viewModel.classOption.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigationToClassOptionRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.actionGlobalClassOptionRecordFragment(it)
                )
                viewModel.navigateToCardioRecordDone()
            }
        })

        return binding.root
    }

    override fun onResume() {
        (activity as AppCompatActivity).bottomNavView?.visibility = View.GONE
        super.onResume()
    }

    override fun onStop() {
        (activity as AppCompatActivity).bottomNavView?.visibility = View.VISIBLE
        super.onStop()
    }
}
