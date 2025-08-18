package com.zhipu.bigmodel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BigModelService : Service() {
    
    private val binder = LocalBinder()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private lateinit var bigModelApi: BigModelApi
    
    companion object {
        private const val API_BASE_URL = "https://open.bigmodel.cn/"
        private const val API_KEY = "YOUR_FREE_API_KEY"  // 替换为您的免费API密钥
    }
    
    inner class LocalBinder : Binder() {
        fun getService(): BigModelService = this@BigModelService
    }
    
    override fun onBind(intent: Intent?): IBinder = binder
    
    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        initRetrofit()
    }
    
    private fun startForegroundService() {
        val channelId = "bigmodel_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "BigModel Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("BigModel")
            .setContentText("AI service ready")
            .setSmallIcon(R.drawable.ic_bigmodel)
            .build()
            
        startForeground(1, notification)
    }
    
    private fun initRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        bigModelApi = retrofit.create(BigModelApi::class.java)
    }
    
    fun generateText(prompt: String, callback: (String) -> Unit) {
        coroutineScope.launch {
            try {
                val request = ChatCompletionRequest(
                    messages = listOf(
                        Message(role = "user", content = prompt)
                    )
                )
                
                val response = bigModelApi.generateText(
                    authToken = "Bearer $API_KEY",
                    request = request
                ).execute()
                
                if (response.isSuccessful) {
                    val content = response.body()?.choices?.firstOrNull()?.message?.content
                    callback(content ?: "No response")
                } else {
                    callback("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                callback("Error: ${e.message}")
            }
        }
    }
}