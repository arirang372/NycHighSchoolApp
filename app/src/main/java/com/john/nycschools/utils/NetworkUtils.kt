package com.john.nycschools.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 *  Check if the internet is available
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}
