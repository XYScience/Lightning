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
class DownloadTest(
    private var downloadUrl: String?,
    private val downloadListener: DownloadTestListener?
) : Thread() {

    private var speedTestSocket: SpeedTestSocket? = null

    companion object {
        private const val DOWNLOAD_URL =
            "http://ipv4.ikoula.testdebit.info/1M.iso"
    }

    init {
        downloadUrl = if (TextUtils.isEmpty(downloadUrl)) DOWNLOAD_URL else downloadUrl
    }

    override fun run() {
        speedTestSocket = SpeedTestSocket()
        speedTestSocket?.socketTimeout = 5000
        Log.e(">>>>>", "DownloadTest start")
        speedTestSocket?.addSpeedTestListener(
            object : ISpeedTestListener {

                override fun onCompletion(report: SpeedTestReport) {
                    downloadListener?.onAvgRateBit(Util.format2Mbps(report.transferRateBit))
                    Log.e(">>>>>", "DownloadTest end")
                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                    val error = when (speedTestError) {
                        SpeedTestError.CONNECTION_ERROR -> SpeedTestException.NET_ERROR
                        SpeedTestError.SOCKET_TIMEOUT -> SpeedTestException.NET_ERROR
                        else -> SpeedTestException.UNKNOWN_ERROR
                    }
                    downloadListener?.onError(error)
                }

                override fun onProgress(percent: Float, downloadReport: SpeedTestReport) {
                    downloadListener?.onInstantRateBit(Util.format2Mbps(downloadReport.transferRateBit))
                }
            })
        speedTestSocket?.startFixedDownload(downloadUrl, 18000, 100)
    }

    fun stopDownLoad() {
        speedTestSocket?.forceStopTask()
    }

    interface DownloadTestListener {
        fun onInstantRateBit(instantRateBit: Double)
        fun onAvgRateBit(avgRateBit: Double)
        fun onError(error: SpeedTestException)
    }
}
