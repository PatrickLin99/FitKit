package com.patrick.fittracker.analysis.test

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.MyApplication

import com.patrick.fittracker.R
import com.patrick.fittracker.analysis.AnalysisFragment
import com.patrick.fittracker.analysis.AnalysisViewModel
import com.patrick.fittracker.analysis.cardioanalysis.AnalysisCardioFragment
import com.patrick.fittracker.analysis.weight.AnalysisWeightFragment
import com.patrick.fittracker.databinding.AnalysisFragmentBinding
import com.patrick.fittracker.databinding.AnalysisTestFragmentBinding
import com.patrick.fittracker.ext.getVmFactory


class AnalysisTestFragment : Fragment() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager

    private val viewModel by viewModels<AnalysisTestViewModel> { getVmFactory() }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AnalysisTestFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionPagerAdapter =
            DemoCollectionPagerAdapter(
                childFragmentManager
            )
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionPagerAdapter
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(i: Int): Fragment {

        when (i) {
            0 -> return AnalysisWeightFragment()
            else -> return AnalysisCardioFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
//                return "TEST ${(position + 1)}"
        return when (position) {
            0 -> FitTrackerApplication.appContext.getString(R.string.weight)
            else -> FitTrackerApplication.appContext.getString(R.string.cardio)
        }
    }
}

private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
//                        val textView: TextView = view.findViewById(R.id.textView6)
//                        textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}
