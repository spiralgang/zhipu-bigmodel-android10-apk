package com.zhipu.bigmodel.international.core

/**
 * Prompt localizer for cultural context optimization
 */
class PromptLocalizer {
    
    private val culturalPromptMappings = mapOf(
        // Chinese providers optimization
        "baidu_ernie" to ChinesePromptOptimizer(),
        "alibaba_qianwen" to ECommercePromptOptimizer(),
        "tencent_hunyuan" to GamingPromptOptimizer(),
        "moonshot_kimi" to LongContextPromptOptimizer(),
        
        // Other regional optimizers
        "yandex_gpt" to RussianPromptOptimizer(),
        "naver_clova" to KoreanPromptOptimizer(),
        "rinna" to JapanesePromptOptimizer()
    )
    
    suspend fun localizePrompt(
        prompt: String, 
        targetProvider: AIProvider, 
        context: CulturalContext
    ): String {
        val optimizer = culturalPromptMappings[targetProvider.id.id]
        return optimizer?.optimizePrompt(prompt, context) ?: prompt
    }
}

/**
 * Base prompt optimizer interface
 */
interface PromptOptimizer {
    fun optimizePrompt(prompt: String, context: CulturalContext): String
}

/**
 * Chinese cultural context optimizer
 */
class ChinesePromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        var optimizedPrompt = prompt
        
        // Add formal language markers for Chinese context
        if (context.culturalNuances.contains(CulturalNuance.FORMAL_LANGUAGE)) {
            optimizedPrompt = "请以正式的语言风格回答：$optimizedPrompt"
        }
        
        // Add business context markers
        if (context.culturalNuances.contains(CulturalNuance.BUSINESS_CONTEXT)) {
            optimizedPrompt = "从商业角度分析：$optimizedPrompt"
        }
        
        // Add content filtering guidance
        if (context.culturalNuances.contains(CulturalNuance.CONTENT_FILTERING)) {
            optimizedPrompt += "（请确保回答符合相关法规要求）"
        }
        
        return optimizedPrompt
    }
}

/**
 * E-commerce focused optimizer (Alibaba Qianwen)
 */
class ECommercePromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        if (context.culturalNuances.contains(CulturalNuance.BUSINESS_CONTEXT)) {
            return "从电商业务角度：$prompt"
        }
        return prompt
    }
}

/**
 * Gaming context optimizer (Tencent Hunyuan)
 */
class GamingPromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        if (context.culturalNuances.contains(CulturalNuance.GAMING_CONTEXT)) {
            return "游戏场景下：$prompt"
        }
        return prompt
    }
}

/**
 * Long context optimizer (Moonshot Kimi)
 */
class LongContextPromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        // Moonshot Kimi excels at long context, so encourage detailed responses
        return "请提供详细的分析和解释：$prompt"
    }
}

/**
 * Russian cultural context optimizer
 */
class RussianPromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        var optimizedPrompt = prompt
        
        if (context.culturalNuances.contains(CulturalNuance.FORMAL_LANGUAGE)) {
            optimizedPrompt = "Ответьте в формальном стиле: $optimizedPrompt"
        }
        
        return optimizedPrompt
    }
}

/**
 * Korean cultural context optimizer
 */
class KoreanPromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        if (context.culturalNuances.contains(CulturalNuance.FORMAL_LANGUAGE)) {
            return "정중한 어조로 답변해 주세요: $prompt"
        }
        return prompt
    }
}

/**
 * Japanese cultural context optimizer
 */
class JapanesePromptOptimizer : PromptOptimizer {
    override fun optimizePrompt(prompt: String, context: CulturalContext): String {
        if (context.culturalNuances.contains(CulturalNuance.FORMAL_LANGUAGE)) {
            return "丁寧語でお答えください：$prompt"
        }
        return prompt
    }
}