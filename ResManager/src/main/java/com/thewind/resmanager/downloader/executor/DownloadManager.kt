package com.thewind.resmanager.downloader.executor

import android.util.Log
import com.thewind.spacecore.formater.JSON
import java.io.BufferedInputStream
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/**
 * @author: read
 * @date: 2022/12/18 下午10:00
 * @description:
 */
private const val TAG = "[ResManager]DownloadManager"
class DownloadManager private constructor() {
    private val executor = Executors.newSingleThreadExecutor()

    fun submit(task: DownloadTask) {
        Log.i(TAG, "submit, task = ${JSON.toJson(task)}")
        executor.submit(task)
    }

    fun execute(task: DownloadTask) {
        executor.execute(task)
    }

    fun syncDown(task: DownloadTask): Boolean {
        val downloadInfo = task.downloadInfo
        val file = File(downloadInfo.filePath)
        if (file.exists() && file.length() == downloadInfo.totalSize) {
            return true
        }
        file.delete()
        val url = URL(downloadInfo.url)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connectTimeout = 10000
        conn.readTimeout = 10000
        downloadInfo.downloadedSize = 0
        downloadInfo.startDownloadTime = System.currentTimeMillis()
        downloadInfo.totalSize = conn.contentLength.toLong()
        try {
            val isr = BufferedInputStream(conn.inputStream)
            val raf = RandomAccessFile(downloadInfo.filePath, "rwd")
            val bytes = ByteArray(4096)
            var downloadedSize = 0
            var len: Int = -1
            do {
                len = isr.read(bytes)
                raf.write(bytes, 0 , len)
                downloadedSize += len
            } while (len != -1)

            isr.close()
            conn.disconnect()
            raf.close()
            downloadInfo.endDownloadTime = System.currentTimeMillis()
            return true
        } catch (e: java.lang.Exception) {
            downloadInfo.endDownloadTime = System.currentTimeMillis()
        }
        return false
    }


    companion object {
        fun getInstance(): DownloadManager = Inner.INSTANCE

        object Inner {
            val INSTANCE = DownloadManager()
        }
    }
}