package br.com.souzabrunoj.coinquotation.utils

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(requireContext(), colorRes)

fun Fragment.getDrawable(@DrawableRes drawableRes: Int) =ContextCompat.getDrawable(requireContext(), drawableRes)