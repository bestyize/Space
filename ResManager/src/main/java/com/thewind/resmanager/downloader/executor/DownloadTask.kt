package com.thewind.resmanager.downloader.executor


import com.thewind.resmanager.downloader.face.DownloadListener
import com.thewind.resmanager.downloader.model.DownloadInfo
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author: read
 * @date: 2022/12/18 下午9:38
 * @description:
 */
class DownloadTask(var downloadInfo: DownloadInfo, var listener: DownloadListener?) : Runnable {

    override fun run() {
        listener?.onStart(downloadInfo)
        val file = File(downloadInfo.filePath)
        if (file.exists() && file.length() == downloadInfo.totalSize) {
            listener?.onSuccess(downloadInfo)
            return
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
                listener?.onProgress(downloadInfo)
            } while (len != -1)

            isr.close()
            conn.disconnect()
            raf.close()
            downloadInfo.endDownloadTime = System.currentTimeMillis()
            listener?.onSuccess(downloadInfo)
        } catch (e: java.lang.Exception) {
            downloadInfo.endDownloadTime = System.currentTimeMillis()
            listener?.onFailed(downloadInfo)
        }


    }
}