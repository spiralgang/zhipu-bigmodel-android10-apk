package com.zhipu.bigmodel.international.core

/**
 * Provider identification
 */
data class ProviderId(val id: String)

/**
 * Access status for API endpoints
 */
enum class AccessStatus {
    AVAILABLE,
    RATE_LIMITED,
    QUOTA_EXCEEDED,
    BLOCKED,
    ERROR,
    UNKNOWN
}

/**
 * Quality score for provider performance in specific contexts
 */
data class QualityScore(
    val overall: Float,
    val languageAccuracy: Float,
    val culturalContext: Float,
    val responseTime: Float,
    val reliability: Float
) {
    fun getWeightedScore(): Float {
        return (overall * 0.3f + languageAccuracy * 0.25f + 
                culturalContext * 0.2f + responseTime * 0.15f + 
                reliability * 0.1f)
    }
}

/**
 * API quota information
 */
data class QuotaInfo(
    val dailyLimit: Long,
    val monthlyLimit: Long,
    val currentUsage: Long,
    val resetTime: Long,
    val requestsPerMinute: Int
)

/**
 * Endpoint configuration for geo-routing
 */
data class Endpoint(
    val url: String,
    val region: RegionCode,
    val priority: Int,
    val healthStatus: AccessStatus
)

/**
 * AI response container
 */
data class AIResponse(
    val content: String,
    val providerId: ProviderId,
    val language: LanguageCode,
    val usage: TokenUsage,
    val responseTime: Long,
    val metadata: Map<String, Any> = emptyMap()
)

/**
 * Token usage information
 */
data class TokenUsage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int
)

/**
 * Query types for optimized routing
 */
enum class QueryType {
    GENERAL_CHAT,
    CODE_GENERATION,
    TRANSLATION,
    CREATIVE_WRITING,
    TECHNICAL_ANALYSIS,
    BUSINESS_QUERY,
    EDUCATIONAL_CONTENT,
    GAMING_CONTENT,
    LEGAL_QUERY
}

/**
 * Base AI Provider interface
 */
interface AIProvider {
    val id: ProviderId
    val name: String
    val description: String
    val supportedLanguages: Set<LanguageCode>
    val supportedRegions: Set<RegionCode>
    val endpoints: List<Endpoint>
    val quotaInfo: QuotaInfo
    
    suspend fun generateText(
        prompt: String,
        language: LanguageCode = LanguageCode.AUTO_DETECT,
        context: CulturalContext? = null
    ): Result<AIResponse>
    
    suspend fun checkHealth(): AccessStatus
    suspend fun getQuotaStatus(): QuotaInfo
    fun supportsLanguage(language: LanguageCode): Boolean
    fun supportsRegion(region: RegionCode): Boolean
    fun getOptimalEndpoint(region: RegionCode): Endpoint?
}