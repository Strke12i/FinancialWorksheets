package com.example.nubank_monitor


import android.text.TextUtils
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

import android.content.Context
import android.app.AppOpsManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings


class MainActivity : FlutterActivity() {
    private val CHANNEL = "notification_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                when (call.method) {
                    "startService" -> {
                        startForegroundService()
                        result.success(null)
                    }
                    "checkUsagePermission" -> {
                        result.success(checkNotificationAccess())
                    }
                    "requestUsagePermission" -> {
                        requestUsageStatsPermission()
                        result.success(null)
                    }
                    else -> {
                        result.notImplemented()
                    }
                }
            }
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "nubank_notifications",
                "Nubank Notifications",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "nubank_notifications")
            .setContentTitle("Serviço Ativo")
            .setContentText("Monitorando notificações do Nubank")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        val intent = Intent(this, NubankNotificationListener::class.java)
        startForegroundService(intent) // Inicia o serviço de notificação em primeiro plano
        (getSystemService(NotificationManager::class.java) as NotificationManager).notify(1, notification)
    }


    fun requestUsageStatsPermission() {
        try {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun checkNotificationAccess(): Boolean {
        val enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return !TextUtils.isEmpty(enabledListeners) && enabledListeners.contains(packageName)
    }



}
