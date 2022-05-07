package com.mabnets.videouploaderapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import net.gotev.uploadservice.UploadServiceConfig

class App : ContentProvider() {

    companion object {
        // ID of the notification channel used by upload service. This is needed by Android API 26+
        // but you have to always specify it even if targeting lower versions, because it's handled
        // by AndroidX AppCompat library automatically
        const val notificationChannelID = "TestChannel"
    }


    fun dependencies(): MutableList<Class<out Initializer<*>>> {
        TODO("Not yet implemented")
    }

    override fun onCreate(): Boolean {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                notificationChannelID,
                "TestApp Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = context!!.applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        UploadServiceConfig.initialize(
            context = context as Application,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )
        return true;
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}