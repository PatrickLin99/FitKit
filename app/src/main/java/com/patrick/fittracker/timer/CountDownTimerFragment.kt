package com.patrick.fittracker.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.CountDownTimerFragmentBinding
import com.patrick.fittracker.ext.getVmFactory


class CountDownTimerFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<CountDownTimerViewModel> { getVmFactory() }
    lateinit var vibrator: Vibrator
    lateinit var vibrationEffect: VibrationEffect

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CountDownTimerFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.countingClock.visibility = View.GONE

        val builder = NotificationCompat.Builder(requireContext(), "testID")
            .setSmallIcon(R.drawable.circle_temp)
            .setContentTitle("FitKit計時結束")
            .setContentText("休息時間到囉，請進行下一組動作訓練")
            .setPriority(NotificationCompat.DEFAULT_VIBRATE)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSmallIcon(R.drawable.notification)

        viewModel.timeSecond.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.timeSecond.value?.let { timeMillis ->
                    binding.startCounting.setOnClickListener {
                        binding.enterTime.visibility = View.INVISIBLE
                        binding.countingClock.visibility = View.VISIBLE
                        binding.startCounting.visibility = View.GONE

                        object : CountDownTimer(timeMillis * 1000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
//                                binding.countingClock.setText("seconds remaining: " + millisUntilFinished / 1000)
                                binding.countingClock.text = "${millisUntilFinished.div(1000)}"

                            }

                            override fun onFinish() {
                                binding.countingClock.text = "完成"
//                                vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//                                vibrationEffect = VibrationEffect.createOneShot(3000, 250)
//                                vibrator.vibrate(vibrationEffect)

                                createNotificationChannel()
                                with(NotificationManagerCompat.from(requireContext())) {
                                    // notificationId is a unique int for each notification that you must define
                                    notify(0, builder.build())
                                }
                            }
                        }.start()
                    }
                }
            }
        })

        binding.dismissButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: String = "Name"
            val descriptionText: String = "Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("testID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
