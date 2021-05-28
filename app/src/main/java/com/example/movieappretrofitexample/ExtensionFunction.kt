package com.example.movieappretrofitexample


import java.text.SimpleDateFormat
import java.util.*

fun Date.readableFormate(): String {

    val dateFormate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return dateFormate.format(this)
}

