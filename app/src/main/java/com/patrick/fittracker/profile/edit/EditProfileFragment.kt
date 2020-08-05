package com.patrick.fittracker.profile.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.UserManger
import com.patrick.fittracker.data.User
import com.patrick.fittracker.data.UserProfile
import com.patrick.fittracker.databinding.EditProfileFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.xw.repo.BubbleSeekBar


class EditProfileFragment : Fragment() {

    private val viewModel by viewModels<EditProfileViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = EditProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        var profileHeight: Double = 1.0
        var profileWeight: Double = 1.0
        var profileBodyFat: Long = 1

        viewModel.getLoginInfoResult()

        binding.seekBarHeight.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
//                Toast.makeText(requireContext(), "身高為$progress", Toast.LENGTH_LONG).show()
                profileHeight = progress.toDouble()
            }

            override fun getProgressOnActionUp(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float
            ) {
            }

            override fun getProgressOnFinally(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
            }
        }

        binding.seekBarWeight.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
//                Toast.makeText(requireContext(), "體重為$progress", Toast.LENGTH_LONG).show()
                profileWeight = progress.toDouble()
            }

            override fun getProgressOnActionUp(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float
            ) {
            }

            override fun getProgressOnFinally(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
            }
        }

        binding.seekBarBodyFat.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
//                Toast.makeText(requireContext(), "體脂肪為$progress", Toast.LENGTH_LONG).show()
                profileBodyFat = progress.toLong()
            }

            override fun getProgressOnActionUp(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float
            ) {
            }

            override fun getProgressOnFinally(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
            }
        }

        binding.updateInfoImage.setOnClickListener {

            UserManger.userData.userProfile?.info_height = profileHeight.toLong()
            UserManger.userData.userProfile?.info_weight = profileWeight.toLong()
            UserManger.userData.userProfile?.info_bodyFat = profileBodyFat

            val profileBMI: Double = profileWeight.times(10000).div(profileHeight * profileHeight)
            Log.d("aaaaaaaBMI", profileBMI.toString())

            viewModel.addUserInfo.value?.email = "${UserManger.userEmail}"
            viewModel.addUserInfo.value?.name = "${UserManger.userName}"
            viewModel.addUserInfo.value?.createdTime = UserManger.userData.createdTime
            viewModel.addUserInfo.value?.id = UserManger.userData.id

            viewModel.addUserInfo.value?.userProfile?.info_height = profileHeight.toLong()
            viewModel.addUserInfo.value?.userProfile?.info_weight = profileWeight.toLong()
            viewModel.addUserInfo.value?.userProfile?.info_bodyFat = profileBodyFat
            viewModel.addUserInfo.value?.userProfile?.info_BMI = profileBMI.toLong()


            viewModel.uploadProfileInfo(user = User(
                UserManger.userData.id,
                "${UserManger.userName}",
                "${UserManger.userEmail}",
                UserManger.userData.createdTime,
                UserManger.userData.userProfile?.info_image?.let { it1 ->
                    UserProfile(
                        "${UserManger.userID}",
                        UserManger.userData.createdTime,
                        "${UserManger.userName}",
                        profileWeight.toLong(),
                        profileHeight.toLong(),
                        profileBMI.toLong(),
                        profileBodyFat,
                        0,
                        "${UserManger.userImage}"
                    )
                }
            )
            )
            findNavController().navigate(NavigationDirections.actionGlobalProfileFragment())
        }


        return binding.root
    }

}