package com.zhipu.bigmodel

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    
    private lateinit var promptInput: EditText
    private lateinit var generateButton: Button
    private lateinit var responseText: TextView
    private lateinit var statusText: TextView
    
    private var bigModelService: BigModelService? = null
    private var isBound = false
    
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as BigModelService.LocalBinder
            bigModelService = binder.getService()
            isBound = true
            statusText.text = "BigModel service connected"
        }
        
        override fun onServiceDisconnected(arg0: ComponentName) {
            bigModelService = null
            isBound = false
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initUI()
        bindBigModelService()
    }
    
    private fun initUI() {
        promptInput = findViewById(R.id.promptInput)
        generateButton = findViewById(R.id.generateButton)
        responseText = findViewById(R.id.responseText)
        statusText = findViewById(R.id.statusText)
        
        generateButton.setOnClickListener {
            val prompt = promptInput.text.toString()
            if (prompt.isNotEmpty()) {
                generateText(prompt)
            } else {
                Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun bindBigModelService() {
        Intent(this, BigModelService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
    }
    
    private fun generateText(prompt: String) {
        if (isBound) {
            statusText.text = "Generating response..."
            generateButton.isEnabled = false
            
            bigModelService?.generateText(prompt) { response ->
                runOnUiThread {
                    responseText.text = response
                    statusText.text = "Response generated"
                    generateButton.isEnabled = true
                }
            }
        } else {
            Toast.makeText(this, "Service not connected", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}