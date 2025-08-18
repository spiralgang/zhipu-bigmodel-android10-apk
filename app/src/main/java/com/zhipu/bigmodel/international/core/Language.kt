package com.zhipu.bigmodel.international.core

import java.util.Locale

/**
 * Language codes following ISO 639-1 standard
 */
enum class LanguageCode(val code: String, val displayName: String, val locale: Locale) {
    ENGLISH("en", "English", Locale.ENGLISH),
    CHINESE_SIMPLIFIED("zh", "简体中文", Locale.SIMPLIFIED_CHINESE),
    CHINESE_TRADITIONAL("zh-TW", "繁體中文", Locale.TRADITIONAL_CHINESE),
    RUSSIAN("ru", "Русский", Locale("ru")),
    KOREAN("ko", "한국어", Locale.KOREAN),
    JAPANESE("ja", "日本語", Locale.JAPANESE),
    SPANISH("es", "Español", Locale("es")),
    FRENCH("fr", "Français", Locale.FRENCH),
    GERMAN("de", "Deutsch", Locale.GERMAN),
    ITALIAN("it", "Italiano", Locale.ITALIAN),
    PORTUGUESE("pt", "Português", Locale("pt")),
    ARABIC("ar", "العربية", Locale("ar")),
    HINDI("hi", "हिन्दी", Locale("hi")),
    HEBREW("he", "עברית", Locale("he")),
    // Add more languages as needed
    AUTO_DETECT("auto", "Auto Detect", Locale.getDefault());

    companion object {
        fun fromCode(code: String): LanguageCode {
            return values().find { it.code == code } ?: AUTO_DETECT
        }
        
        fun fromLocale(locale: Locale): LanguageCode {
            return values().find { it.locale.language == locale.language } ?: AUTO_DETECT
        }
    }
}

/**
 * Region codes for geo-aware load balancing
 */
enum class RegionCode(val code: String, val displayName: String) {
    GLOBAL("global", "Global"),
    CHINA("cn", "China"),
    USA("us", "United States"),
    EUROPE("eu", "Europe"),
    RUSSIA("ru", "Russia"),
    KOREA("kr", "South Korea"),
    JAPAN("jp", "Japan"),
    CANADA("ca", "Canada"),
    ISRAEL("il", "Israel"),
    UK("uk", "United Kingdom")
}

/**
 * Cultural nuances that affect AI responses
 */
enum class CulturalNuance {
    FORMAL_LANGUAGE,
    CASUAL_LANGUAGE,
    BUSINESS_CONTEXT,
    EDUCATIONAL_CONTEXT,
    ENTERTAINMENT_CONTEXT,
    GAMING_CONTEXT,
    SOCIAL_MEDIA_CONTEXT,
    TECHNICAL_CONTEXT,
    LEGAL_COMPLIANCE,
    CONTENT_FILTERING
}

/**
 * Cultural context for localization
 */
data class CulturalContext(
    val language: LanguageCode,
    val region: RegionCode,
    val culturalNuances: Set<CulturalNuance>,
    val preferredProviders: List<String>
)