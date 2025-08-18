package com.zhipu.bigmodel.international

import com.zhipu.bigmodel.international.core.*
import com.zhipu.bigmodel.international.providers.ZhipuAIProvider
import com.zhipu.bigmodel.international.providers.BaiduErnieProvider
import com.zhipu.bigmodel.international.providers.YandexGPTProvider
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the International AI system
 */
class InternationalAITest {

    @Test
    fun testLanguageCodeMapping() {
        assertEquals(LanguageCode.ENGLISH, LanguageCode.fromCode("en"))
        assertEquals(LanguageCode.CHINESE_SIMPLIFIED, LanguageCode.fromCode("zh"))
        assertEquals(LanguageCode.RUSSIAN, LanguageCode.fromCode("ru"))
        assertEquals(LanguageCode.AUTO_DETECT, LanguageCode.fromCode("unknown"))
    }

    @Test
    fun testProviderInitialization() {
        val zhipuProvider = ZhipuAIProvider()
        assertEquals("zhipu_chatglm", zhipuProvider.id.id)
        assertTrue(zhipuProvider.supportsLanguage(LanguageCode.CHINESE_SIMPLIFIED))
        assertTrue(zhipuProvider.supportsRegion(RegionCode.CHINA))

        val baiduProvider = BaiduErnieProvider()
        assertEquals("baidu_ernie", baiduProvider.id.id)
        assertTrue(baiduProvider.supportsLanguage(LanguageCode.CHINESE_SIMPLIFIED))

        val yandexProvider = YandexGPTProvider()
        assertEquals("yandex_gpt", yandexProvider.id.id)
        assertTrue(yandexProvider.supportsLanguage(LanguageCode.RUSSIAN))
    }

    @Test
    fun testCulturalContext() {
        val context = CulturalContext(
            language = LanguageCode.CHINESE_SIMPLIFIED,
            region = RegionCode.CHINA,
            culturalNuances = setOf(CulturalNuance.FORMAL_LANGUAGE, CulturalNuance.BUSINESS_CONTEXT),
            preferredProviders = listOf("zhipu_chatglm", "baidu_ernie")
        )

        assertEquals(LanguageCode.CHINESE_SIMPLIFIED, context.language)
        assertEquals(RegionCode.CHINA, context.region)
        assertTrue(context.culturalNuances.contains(CulturalNuance.FORMAL_LANGUAGE))
        assertTrue(context.preferredProviders.contains("zhipu_chatglm"))
    }

    @Test
    fun testPromptLocalizer() {
        val localizer = PromptLocalizer()
        val provider = ZhipuAIProvider()
        val context = CulturalContext(
            language = LanguageCode.CHINESE_SIMPLIFIED,
            region = RegionCode.CHINA,
            culturalNuances = setOf(CulturalNuance.FORMAL_LANGUAGE),
            preferredProviders = listOf("zhipu_chatglm")
        )

        runBlocking {
            val localizedPrompt = localizer.localizePrompt("Hello world", provider, context)
            // Should return the same prompt since Zhipu provider doesn't have specific optimization
            assertTrue(localizedPrompt.isNotEmpty())
        }
    }

    @Test
    fun testQualityScoring() {
        val score = QualityScore(
            overall = 0.9f,
            languageAccuracy = 0.95f,
            culturalContext = 0.85f,
            responseTime = 0.8f,
            reliability = 0.9f
        )

        val weightedScore = score.getWeightedScore()
        assertTrue(weightedScore > 0.8f)
        assertTrue(weightedScore < 1.0f)
    }

    @Test
    fun testProviderHealthCheck() = runBlocking {
        val provider = ZhipuAIProvider()
        val healthStatus = provider.checkHealth()
        assertEquals(AccessStatus.AVAILABLE, healthStatus)
    }

    @Test
    fun testTokenUsageCalculation() {
        val usage = TokenUsage(
            promptTokens = 100,
            completionTokens = 150,
            totalTokens = 250
        )

        assertEquals(250, usage.totalTokens)
        assertEquals(100 + 150, usage.totalTokens)
    }

    @Test
    fun testEndpointPrioritization() {
        val provider = ZhipuAIProvider()
        val endpoint = provider.getOptimalEndpoint(RegionCode.CHINA)
        
        assertNotNull(endpoint)
        assertEquals(RegionCode.CHINA, endpoint?.region)
    }
}