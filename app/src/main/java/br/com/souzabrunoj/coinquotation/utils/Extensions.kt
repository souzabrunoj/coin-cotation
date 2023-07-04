package br.com.souzabrunoj.coinquotation.utils

import java.text.DecimalFormat

private val formatter2= DecimalFormat("##.##")
private val formatter3= DecimalFormat("##.###")

fun Double.roundToThreeDecimals() = formatter3.format(this).toString()

fun List<Double?>?.toDoubleFloatPairs(): List<Pair<String, Float>> {
    return this!!.map { d ->
        val f = d!!.toFloat()
        val s = d.toString()
        Pair(s, f)
    }
}