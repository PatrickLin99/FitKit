package com.patrick.fittracker.profile

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.patrick.fittracker.FitTrackerApplication
import com.patrick.fittracker.R


class ProfileAvatarOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        view.clipToOutline = true
        val radius = FitTrackerApplication.instance.resources.getDimensionPixelSize(R.dimen.radius_profile_avatar)
        outline.setOval(0, 0, radius, radius)
    }
}

class CardioSelectionOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        view.clipToOutline = true
        val radius = FitTrackerApplication.instance.resources.getDimensionPixelSize(R.dimen.image_avatar)
        outline.setRoundRect(view.left, 0, view.right, view.bottom + radius, radius.toFloat())

    }
}