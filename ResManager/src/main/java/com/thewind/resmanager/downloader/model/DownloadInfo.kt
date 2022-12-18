package com.thewind.resmanager.downloader.model

/**
 * @author: read
 * @date: 2022/12/18 下午9:27
 * @description:
 */
class DownloadInfo {
    var url: String? = null
    var key: String? = null
    var fileName: String? = null
    var fileType = FileType.UN_KNOWN
    var totalSize: Long = 0
    var downloadedSize: Long = 0
    var startDownloadTime: Long = 0
    var endDownloadTime: Long = 0
    var saveDir: String? = null
    var filePath: String? = null
}

enum class FileType(val type: Int) {
    UN_KNOWN(0), CACHE(1), VALID(2)
}