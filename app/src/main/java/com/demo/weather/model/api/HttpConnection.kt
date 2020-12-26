package com.demo.weather.model.api

import android.util.Log
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object HttpConnection {
    private val TAG = HttpConnection::class.java.simpleName
    private val TIME_OUT = 30*1000

    fun getRequest(url: URL): String? {
        val connect = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            readTimeout = TIME_OUT
            connectTimeout = TIME_OUT
        }

        try {
            // make GET request
            connect.connect()
            val inputStream = connect.inputStream
            inputStream?.bufferedReader().use {
                val response = StringBuffer()

                var inputLine = it?.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it?.readLine()
                }
                it?.close()
                Log.d(TAG, "Response : $response")
                if (response.isNotEmpty()) {
                    return response.toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connect.disconnect()
        }
        return null
    }
}