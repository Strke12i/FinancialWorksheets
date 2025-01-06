package com.example.nubank_monitor

import com.google.gson.Gson
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.Call
import okhttp3.Callback
import okhttp3.RequestBody.Companion.toRequestBody

import java.io.IOException

class NubankNotificationListener : NotificationListenerService() {

    private val webhookUrl = "" // Webhook URL

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "nubank_notifications",
                "Nubank Notifications",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(this, "nubank_notifications")
                .setContentTitle("Serviço Ativo")
                .setContentText("Monitorando notificações do Nubank")
                .setSmallIcon(R.drawable.ic_notification)
                .build()

            startForeground(1, notification)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            if (it.packageName == "com.nu.production") {
                val extras = it.notification.extras
                val title = extras.getString("android.title")
                val text = extras.getString("android.text")

                Log.d("NubankListener", "Notificação recebida: $title - $text")
                sendNotificationToWebhook(title, text)
            }
        }
    }

    private fun sendNotificationToWebhook(title: String?, text: String?) {
        if (title == null || text == null) return

        val client = OkHttpClient()
        val gson = Gson()
        val requestData = mapOf(
            "title" to title,
            "text" to text
        )

        val jsonData = gson.toJson(requestData)
        val requestBody = jsonData.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(webhookUrl)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("NubankListener", "Erro ao enviar notificação para o webhook", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("NubankListener", "Notificação enviada com sucesso")
            }
        })
        }


}
