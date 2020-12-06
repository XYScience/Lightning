package com.redteamobile.lightning.data.remote.util

import android.text.TextUtils
import android.util.Log
import com.redteamobile.lightning.util.Util
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError

/**
 * @author SScience
 * @description
 * @email chentushen.science@gmail.com
 * @data 2019-09-28
 */
class UploadTest(
    private var uploadUrl: String?,
    private val uploadListener: UploadTestListener?
) : Thread() {

    private var speedTestSocket: SpeedTestSocket? = null

    companion object {
        private const val UPLOAD_URL =
            "http://ipv4.ikoula.testdebit.info/"
    }

    init {
        uploadUrl = if (TextUtils.isEmpty(uploadUrl)) UPLOAD_URL else uploadUrl
    }

    override fun run() {
        speedTestSocket = SpeedTestSocket()
        speedTestSocket?.socketTimeout = 5000
        Log.e(">>>>>", "UploadTest start")
        speedTestSocket?.addSpeedTestListener(
            object : ISpeedTestListener {

                override fun onCompletion(report: SpeedTestReport) {
                    uploadListener?.onAvgRateBit(Util.format2Mbps(report.transferRateBit))
                    Log.e(">>>>>", "UploadTest start")
                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                    val error = when (speedTestError) {
                        SpeedTestError.CONNECTION_ERROR -> SpeedTestException.NET_ERROR
                        SpeedTestError.SOCKET_TIMEOUT -> SpeedTestException.NET_ERROR
                        else -> SpeedTestException.UNKNOWN_ERROR
                    }
                    uploadListener?.onError(error)
                }

                override fun onProgress(percent: Float, uploadReport: SpeedTestReport) {
                    uploadListener?.onInstantRateBit(Util.format2Mbps(uploadReport.transferRateBit))
                }
            })
        speedTestSocket?.startFixedUpload(uploadUrl, 50000000, 15000, 100)
    }

    fun stopDownLoad() {
        speedTestSocket?.forceStopTask()
    }

    interface UploadTestListener {
        fun onInstantRateBit(instantRateBit: Double)
        fun onAvgRateBit(avgRateBit: Double)
        fun onError(error: SpeedTestException)
    }
}
