package br.com.souzabrunoj.coinquotation.utils

import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator

fun View.show(visibility: Int = View.VISIBLE) {
    this.visibility = visibility
}

fun View.hide(visibility: Int = View.GONE) {
    this.visibility = visibility
}

fun View.animateWithAlpha(alpha: Float, duration: Long = 500, action: () -> Unit): ViewPropertyAnimator =
    animate()
        .alpha(alpha)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction { action.invoke() }

fun View.hideWithFade(visibility: Int = View.GONE, duration: Long = 500) {
    val action = { hide(visibility) }
    animateWithAlpha(0f, duration, action)
        .scaleX(0f)
        .scaleY(0f)
        .start()
}

fun View.showWithFade(duration: Long = 500) {
    val action = {
        alpha = 0f
        show()
    }
    animateWithAlpha(1f, duration, action)
        .scaleX(1f)
        .scaleY(1f)
        .start()
}