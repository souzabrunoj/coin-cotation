package br.com.souzabrunoj.coinquotation.utils

import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT = "yyyy-MM-dd"
private const val DATE_FORMATTER = "dd-MM-yyyy"

fun String.toLocaleDateFormat(): String {
    val locale = Locale.getDefault()
    val formatter = SimpleDateFormat(DATE_FORMATTER, locale)
    val parse = SimpleDateFormat(DATE_FORMAT, locale).parse(this)
    return if (isNotEmpty() && parse != null) formatter.format(parse) else " - "
}