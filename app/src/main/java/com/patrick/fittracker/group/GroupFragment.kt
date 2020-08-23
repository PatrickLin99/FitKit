package com.patrick.fittracker.group

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.MainActivity
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.R
import com.patrick.fittracker.data.SelectedMuscleGroup
import com.patrick.fittracker.databinding.GroupFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.util.Util.getColor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.group_fragment.*

class GroupFragment() : Fragment() {

    var group: MuscleGroupTypeFilter = MuscleGroupTypeFilter.CHEST

    private val viewModel by viewModels <GroupViewModel> {getVmFactory(group)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.clickListener = onSelectedImageClickListener

//        (activity as AppCompatActivity).main_title_spannable_test.text = "FIT CHALLENGE"
//        val mainTitle = (activity as AppCompatActivity).main_title_spannable_test
//        val span: Spannable = SpannableString(mainTitle.getText())
//        span.setSpan(ForegroundColorSpan(getColor(R.color.colorAccent)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        mainTitle.setText(span)

        viewModel.navigateToPoseSelect.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalPoseSelectFragment(it))
            }
        })

        viewModel.selectMuscleType.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.getMuscleGroupResult(group = it)
                when (it) {
                    MuscleGroupTypeFilter.CHEST -> muscle_chest_color_image.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.BICEPS -> muscle_group_biceps_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.DELTOIDS -> muscle_group_deltoids_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.LOWERBACK -> muscle_group_lowerback_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.ABS -> muscle_group_abs_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.UPPERBACK -> muscle_group_uperback_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.FRONTLEGS -> muscle_group_frontlegs_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.CALF -> muscle_group_calf_color.visibility = View.VISIBLE
                    MuscleGroupTypeFilter.BACKLEGS -> muscle_group_backlegs_color.visibility = View.VISIBLE
                }
                viewModel.onMuscleSelected()
            }
        })

        return  binding.root
    }

    private val onSelectedImageClickListener = View.OnClickListener {
        it?.let {
            it.visibility = View.GONE
        }
    }

}
