package com.thewind.spacecore.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.thewind.basic.base.BaseActivity

object PermissionHelper {

    private val perms = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE)

    fun requestPermission(activity: BaseActivity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                return
            }
            activity.startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:com.thewind.space")
                flags = flags.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } else {
            perms.forEach {
                if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(perms.toTypedArray(), 0)
                }
            }
        }

    }
}