package com.zhipu.bigmodel.international

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zhipu.bigmodel.international.core.LanguageCode
import com.zhipu.bigmodel.international.core.QueryType
import com.zhipu.bigmodel.international.service.InternationalAIService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var languageSpinner: Spinner
    private lateinit var providerSpinner: Spinner
    private lateinit var promptInput: EditText
    private lateinit var generateButton: Button
    private lateinit var responseText: TextView
    private lateinit var statusText: TextView
    private lateinit var translationToggle: Switch
    private lateinit var providerInfo: TextView
    
    // Service binding
    private var aiService: InternationalAIService? = null
    private var isBound = false
    
    // Current settings
    private var selectedLanguage = LanguageCode.AUTO_DETECT
    private var translationEnabled = true
    
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as InternationalAIService.LocalBinder
            aiService = binder.getService()
            isBound = true
            updateStatus("International AI service connected")
            loadAvailableProviders()
        }
        
        override fun onServiceDisconnected(arg0: ComponentName) {
            aiService = null
            isBound = false
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initUI()
        bindAIService()
    }
    
    private fun initUI() {
        // Initialize UI components
        languageSpinner = findViewById(R.id.languageSpinner)
        providerSpinner = findViewById(R.id.providerSpinner)
        promptInput = findViewById(R.id.promptInput)
        generateButton = findViewById(R.id.generateButton)
        responseText = findViewById(R.id.responseText)
        statusText = findViewById(R.id.statusText)
        translationToggle = findViewById(R.id.translationToggle)
        providerInfo = findViewById(R.id.providerInfo)
        
        setupLanguageSpinner()
        setupUI()
    }
    
    private fun setupLanguageSpinner() {
        val languages = listOf(
            LanguageCode.AUTO_DETECT,
            LanguageCode.ENGLISH,
            LanguageCode.CHINESE_SIMPLIFIED,
            LanguageCode.CHINESE_TRADITIONAL,
            LanguageCode.RUSSIAN,
            LanguageCode.KOREAN,
            LanguageCode.JAPANESE,
            LanguageCode.SPANISH,
            LanguageCode.FRENCH,
            LanguageCode.GERMAN,
            LanguageCode.ARABIC,
            LanguageCode.HEBREW
        )
        
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            languages.map { it.displayName }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter
        
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedLanguage = languages[position]
                aiService?.setUserLanguage(selectedLanguage)
                updateUI()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun setupUI() {
        generateButton.setOnClickListener {
            val prompt = promptInput.text.toString()
            if (prompt.isNotEmpty()) {
                generateText(prompt)
            } else {
                Toast.makeText(this, getString(R.string.enter_prompt), Toast.LENGTH_SHORT).show()
            }
        }
        
        translationToggle.setOnCheckedChangeListener { _, isChecked ->
            translationEnabled = isChecked
            updateStatus(
                if (isChecked) getString(R.string.translation_enabled)
                else getString(R.string.translation_disabled)
            )
        }
    }
    
    private fun bindAIService() {
        Intent(this, InternationalAIService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
    }
    
    private fun loadAvailableProviders() {
        if (!isBound) return
        
        val providers = aiService?.getAvailableProviders() ?: return
        val providerNames = providers.map { "${it.name} (${it.supportedRegions.first().displayName})" }
        
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            providerNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        providerSpinner.adapter = adapter
        
        providerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position < providers.size) {
                    val provider = providers[position]
                    updateProviderInfo(provider.description)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun generateText(prompt: String) {
        if (!isBound) {
            Toast.makeText(this, getString(R.string.service_not_connected), Toast.LENGTH_SHORT).show()
            return
        }
        
        updateStatus(getString(R.string.generating_response))
        generateButton.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val result = aiService?.generateText(
                    prompt = prompt,
                    targetLanguage = if (translationEnabled) selectedLanguage else LanguageCode.AUTO_DETECT,
                    queryType = QueryType.GENERAL_CHAT
                )
                
                result?.fold(
                    onSuccess = { response ->
                        responseText.text = response.content
                        updateStatus("${getString(R.string.response_from)} ${response.providerId.id} (${response.responseTime}ms)")
                        updateProviderInfo("Tokens: ${response.usage.totalTokens}, Language: ${response.language.displayName}")
                    },
                    onFailure = { error ->
                        responseText.text = getString(R.string.error_occurred, error.message)
                        updateStatus(getString(R.string.generation_failed))
                    }
                )
            } catch (e: Exception) {
                responseText.text = getString(R.string.error_occurred, e.message)
                updateStatus(getString(R.string.generation_failed))
            } finally {
                generateButton.isEnabled = true
            }
        }
    }
    
    private fun updateStatus(message: String) {
        runOnUiThread {
            statusText.text = message
        }
    }
    
    private fun updateProviderInfo(info: String) {
        runOnUiThread {
            providerInfo.text = info
        }
    }
    
    private fun updateUI() {
        // Update UI elements based on selected language
        title = when (selectedLanguage) {
            LanguageCode.CHINESE_SIMPLIFIED -> "国际AI助手"
            LanguageCode.CHINESE_TRADITIONAL -> "國際AI助手"
            LanguageCode.RUSSIAN -> "Международный ИИ"
            LanguageCode.KOREAN -> "국제 AI 어시스턴트"
            LanguageCode.JAPANESE -> "国際AIアシスタント"
            LanguageCode.SPANISH -> "Asistente AI Internacional"
            LanguageCode.FRENCH -> "Assistant IA International"
            LanguageCode.GERMAN -> "Internationaler KI-Assistent"
            else -> "International AI Assistant"
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