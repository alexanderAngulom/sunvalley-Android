package com.alex.internettestsunvalley.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

object ConectionI {
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun macDevice(context:Context): String? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (wifiManager.isWifiEnabled) {
            val wifiInfo: WifiInfo? = wifiManager.connectionInfo
            val macAddress = wifiInfo?.macAddress
            return macAddress
        } else {
            return "WiFi está deshabilitado"

        }
    }

}