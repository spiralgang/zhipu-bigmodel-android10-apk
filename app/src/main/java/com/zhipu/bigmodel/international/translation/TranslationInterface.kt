package com.zhipu.bigmodel.international.translation

import com.zhipu.bigmodel.international.core.AIResponse
import com.zhipu.bigmodel.international.core.LanguageCode

/**
 * Translation orchestrator interface
 */
interface TranslationOrchestrator {
    suspend fun detectLanguage(text: String): LanguageCode
    suspend fun translateUI(text: String, targetLang: LanguageCode): String
    suspend fun translateProviderResponse(response: AIResponse, userLang: LanguageCode): AIResponse
    suspend fun optimizePromptForProvider(prompt: String, providerId: String): String
}

/**
 * Base translation provider interface
 */
interface TranslationProvider {
    val providerId: String
    val supportedLanguages: Set<LanguageCode>
    val maxTextLength: Int
    
    suspend fun translate(
        text: String,
        fromLang: LanguageCode,
        toLang: LanguageCode
    ): Result<String>
    
    suspend fun detectLanguage(text: String): Result<LanguageCode>
    suspend fun checkHealth(): Boolean
}

/**
 * Translation cache interface for performance optimization
 */
interface TranslationCache {
    suspend fun get(key: String): String?
    suspend fun put(key: String, value: String, ttl: Long = 3600000) // 1 hour default
    suspend fun invalidate(key: String)
    suspend fun clear()
}

/**
 * Translation request data
 */
data class TranslationRequest(
    val text: String,
    val fromLang: LanguageCode,
    val toLang: LanguageCode,
    val context: String? = null,
    val priority: TranslationPriority = TranslationPriority.NORMAL
)

/**
 * Translation priority levels
 */
enum class TranslationPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}

/**
 * Translation result with metadata
 */
data class TranslationResult(
    val translatedText: String,
    val confidence: Float,
    val providerId: String,
    val fromLang: LanguageCode,
    val toLang: LanguageCode,
    val cached: Boolean,
    val translationTime: Long
)