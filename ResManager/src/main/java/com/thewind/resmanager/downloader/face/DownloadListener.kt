package com.thewind.resmanager.downloader.face

import com.thewind.resmanager.downloader.model.DownloadInfo

/**
 * @author: read
 * @date: 2022/12/18 下午9:26
 * @description:
 */
interface DownloadListener {
    fun onStart(downloadInfo: DownloadInfo)
    fun onProgress(downloadInfo: DownloadInfo)
    fun onPause(downloadInfo: DownloadInfo)
    fun onCancel(downloadInfo: DownloadInfo)
    fun onSuccess(downloadInfo: DownloadInfo)
    fun onFailed(downloadInfo: DownloadInfo)
}