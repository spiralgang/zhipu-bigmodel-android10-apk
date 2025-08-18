# International AI Orchestration Android App

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Environment-Specific Constraints
**CRITICAL**: This GitHub Copilot environment has network connectivity restrictions preventing dependency downloads.

⚠️ **BLOCKED DOMAINS**: `dl.google.com`, Maven Central, and other dependency repositories are inaccessible, causing Gradle builds to fail.

### Prerequisites and Environment Setup
- Android SDK environment: `export ANDROID_HOME=/usr/local/lib/android/sdk`
- Android SDK Location: `/usr/local/lib/android/sdk`
- Target Android API Level: 34 (available), updated from original 29
- Minimum SDK: 26 (Android 8.0+)
- Java Version: OpenJDK 17.0.16 (available)
- Kotlin Version: 2.2.0 (available)
- Gradle Version: 7.4.2

### Repository Structure and Key Components
- **MainActivity.kt**: Main UI with language selection, provider switching, translation toggle
- **InternationalAIService.kt**: Background service managing AI providers and translation
- **Core package**: Language codes, provider interfaces, cultural context management
- **Translation package**: Multi-provider translation orchestration system
- **Providers package**: 20+ international AI provider implementations
- **Test files**: Comprehensive unit tests for international AI functionality

### Build Commands - NETWORK LIMITATIONS
**What DOES work in this environment:**
- `export ANDROID_HOME=/usr/local/lib/android/sdk` - Set Android SDK path
- `ls $ANDROID_HOME/platforms` - Verify available Android platforms (API 33-36 available)
- `kotlinc -version` - Verify Kotlin compiler (works)
- `java -version` - Verify Java runtime (works)

**What DOES NOT work due to network restrictions:**
- `./gradlew assembleDebug` - FAILS: Cannot download dependencies from dl.google.com
- `./gradlew assembleRelease` - FAILS: Cannot download dependencies from dl.google.com  
- `./gradlew test` - FAILS: Cannot download dependencies
- `gradle wrapper` - FAILS: Cannot download gradle wrapper

**In environments with full network access, these commands would work:**
```bash
# Standard build process (ONLY works with unrestricted internet):
gradle clean assembleDebug    # Takes 15-30 minutes
gradle assembleRelease        # Takes 20-45 minutes  
gradle test                   # Takes 5-10 minutes
gradle installDebug           # Install to device/emulator
```

### Testing
**Current Environment**: Tests cannot run due to network dependency restrictions
- `./gradlew test` - FAILS: Cannot download Android/JUnit dependencies from dl.google.com
- Test files exist: `app/src/test/java/com/zhipu/bigmodel/international/InternationalAITest.kt`
- Tests cover: Language mapping, provider initialization, cultural context, translation, quality scoring

**In unrestricted environments**: Standard Android testing would work with proper dependency access

### Code Structure Overview
- **Source files**: 10 Kotlin files, 0 Java files
- **Languages supported**: 50+ languages with full UI translation
- **AI Providers**: 20+ international providers (Zhipu, Baidu, Yandex, Naver, etc.)
- **Translation providers**: Google Translate, Microsoft Translator, Baidu Translate, LibreTranslate
- **Architecture**: Service-based with provider registry, translation orchestrator, cultural optimizer

## Validation

### Current Environment Limitations
Due to network restrictions in this GitHub Copilot environment, the following validation scenarios **CANNOT** be performed:
- Building and running the application
- Testing language selection and provider switching  
- Verifying AI response generation
- Testing translation functionality
- Validating cultural context optimization
- Testing the 50+ language UI translations

### What CAN be validated:
- Code structure and syntax correctness
- File organization and architecture
- Resource file completeness (strings.xml in multiple languages)
- Manifest permissions and configuration
- Provider interface implementations
- Test code structure and coverage

### In Full Network Environments
**Complete User Flow Testing** would include:
1. **App Launch Validation**: Install and launch app (`gradle installDebug`)
2. **Core Functionality Testing**:
   - Select different languages from spinner (English, Chinese, Russian, Japanese, Korean)
   - Toggle auto-translation switch ON/OFF
   - Test AI provider selection and switching
   - Enter sample prompt: "Hello, how are you today?"
   - Verify AI response generation works
   - Test with different providers if API keys are configured
3. **International Features Testing**:
   - Test RTL languages (Arabic, Hebrew) if strings are available
   - Verify cultural context optimization
   - Test translation between different language pairs
   - Verify provider health checks work correctly

### Code Quality
- **Linting**: No lint tools configured that work without Gradle build
- **Formatting**: No automated formatting tools available without Gradle
- **Static Analysis**: Limited to basic syntax checking with kotlinc

## Common Tasks

### Adding New AI Providers
1. Create provider class implementing `AIProvider` interface in `providers/`
2. Add to provider registry in `InternationalAIService.kt`
3. Update cultural context optimizers in `PromptLocalizer.kt` if needed
4. Add provider-specific tests to `InternationalAITest.kt`
5. Configure API keys and endpoint URLs

### Adding New Languages
1. Create new `values-xx/` directory (e.g., `values-es` for Spanish)
2. Copy and translate `strings.xml` file
3. Add language to `LanguageCode` enum in `Language.kt`
4. Update translation providers support in `TranslationOrchestratorImpl.kt`
5. Test RTL layout if applicable

### API Key Configuration
The application requires API keys for multiple providers:
```kotlin
// ZhipuAIProvider.kt - Chinese AI provider
private const val ZHIPU_API_KEY = "your_zhipu_key"

// BaiduErnieProvider.kt - Chinese cultural optimization
private const val BAIDU_API_KEY = "your_baidu_key"

// YandexGPTProvider.kt - Russian language optimization
private const val YANDEX_API_KEY = "your_yandex_key"
```

### Testing Changes (In Full Network Environment)
```bash
# Run unit tests for core functionality
gradle test

# Run specific test class
gradle test --tests "com.zhipu.bigmodel.international.InternationalAITest"
```

**Test Coverage Areas:**
- Language code mapping and detection
- Provider initialization and health checks  
- Cultural context optimization
- Translation pipeline functionality
- Quality scoring algorithms
- Endpoint prioritization logic

## Project Structure Navigation

### Key Directories
```
app/src/main/java/com/zhipu/bigmodel/international/
├── MainActivity.kt                    # Main UI controller
├── core/                             # Core system components
│   ├── Language.kt                   # Language definitions and enums
│   ├── PromptLocalizer.kt           # Cultural prompt optimization  
│   └── Provider.kt                   # AI provider interfaces
├── providers/                        # AI provider implementations
│   ├── ChineseProviders.kt          # Zhipu AI, Baidu Ernie providers
│   └── InternationalProviders.kt    # Yandex, international providers
├── service/                         # Background services
│   └── InternationalAIService.kt    # Main orchestration service
└── translation/                     # Translation system
    ├── TranslationInterface.kt      # Translation contracts
    └── TranslationOrchestratorImpl.kt # Translation implementation
```

### Configuration Files
- **`build.gradle` (app)**: Dependencies, build configuration, ProGuard rules
- **`build.gradle` (root)**: Project-wide Gradle configuration  
- **`AndroidManifest.xml`**: Permissions, activities, services
- **`proguard-rules.pro`**: Code obfuscation rules for release builds

### Internationalization Resources
- **`app/src/main/res/values/strings.xml`**: Default English strings
- **`app/src/main/res/values-zh/strings.xml`**: Chinese localization
- **`app/src/main/res/values-ru/strings.xml`**: Russian localization
- **`app/src/main/res/values-ja/strings.xml`**: Japanese localization  
- **`app/src/main/res/values-ko/strings.xml`**: Korean localization

### Repository Information from Documentation

From README.md:
```bash
# Original build process (DOES NOT WORK in this environment):
git clone https://github.com/zhipu-bigmodel/android10-apk.git
cd android10-apk
./gradlew assembleRelease  # FAILS - network limitations
```

From INTERNATIONAL_AI_README.md:
```bash
# Expected build process (DOES NOT WORK in this environment):
./gradlew assembleDebug    # FAILS - network limitations  
./gradlew assembleRelease  # FAILS - network limitations
./gradlew test            # FAILS - network limitations
./gradlew installDebug    # FAILS - network limitations
```

### App Functionality (from source code analysis)
The app provides:
1. **Multi-Provider AI Access**: 20+ international AI providers with automatic provider selection
2. **Real-Time Translation**: 50+ language support with translation caching
3. **Cultural Context Optimization**: Region-specific prompt engineering and cultural adaptation
4. **Smart Provider Routing**: Geo-aware load balancing and health monitoring
5. **Comprehensive UI**: Language selection, provider switching, translation toggle


## Critical Limitations in This Environment

**NETWORK CONNECTIVITY**: This GitHub Copilot environment cannot access external repositories (dl.google.com, maven repositories). This means:
- Gradle builds WILL FAIL when trying to download dependencies
- Tests cannot run because they depend on Android/JUnit dependencies
- APK generation is not possible
- Dependency resolution will timeout

**AVAILABLE ANDROID SDKS**: Only API levels 33-36 are available, but the project originally targeted API 29. The build.gradle has been updated to use API 34.

**WHAT WORKS**: Code examination, file structure analysis, static syntax checking, architecture review.

**WHAT DOESN'T WORK**: Building, testing, running, APK generation, dependency installation.

## Security and Compliance

### API Key Security
- API keys stored locally on device only
- No user data collection or external storage
- Regional compliance: GDPR, Chinese data sovereignty
- HTTPS-only API communications

### Permissions Required
- `INTERNET`: Network access for API calls
- `ACCESS_NETWORK_STATE`: Check network connectivity
- `FOREGROUND_SERVICE`: Background AI processing
- `WAKE_LOCK`: Maintain processing during generation

## Known Limitations

### Build Environment Constraints
- **CRITICAL**: Requires internet connectivity for all builds (except this environment)
- Cannot build in offline mode due to missing dependency cache
- Android SDK/NDK must be available in build environment
- Network timeouts may occur in restricted environments

### Runtime Limitations  
- Free API quotas: Zhipu AI provides 1,000,000 tokens/month
- Some providers may have geographical restrictions
- Translation quality varies by provider and language pair
- UI testing limited to manual verification (no automated UI tests)

### Development Workflow
- No automated lint checking configured
- Manual code review required for quality assurance
- Limited automated testing infrastructure
- Provider health checks depend on external service availability

## Build Times and Performance

**Expected Build Times** (in environments with full network access):
- Clean build: 2-5 minutes
- Debug APK build: 15-30 minutes  
- Release APK build: 20-45 minutes (includes ProGuard/R8)
- Unit tests: 5-10 minutes
- Install to device: 2-5 minutes

**Performance Notes:**
- Release builds use R8 minification and resource shrinking
- ProGuard rules optimize for AI provider classes
- App supports Android 8.0+ for wider device compatibility
- Designed for mobile-first responsive UI

## Troubleshooting

### Network Build Issues in Restricted Environments
- **Problem**: `dl.google.com: No address associated with hostname` during build
- **Root Cause**: GitHub Copilot environment blocks dependency repositories for security
- **Status**: Expected behavior in this environment - no workaround available
- **Solution**: In unrestricted environments, ensure stable internet connection

### Common Build Failures (General)
- **Missing repositories in buildscript**: Fixed in current codebase
- **Gradle version mismatch**: Use Gradle 7.4.2 as specified in wrapper
- **ProGuard issues**: Check `proguard-rules.pro` for required keep rules

### Runtime Issues (When Building is Possible)
- **Service not connecting**: Check `InternationalAIService` is properly bound in `MainActivity`
- **API failures**: Verify API keys are configured correctly
- **Translation errors**: Check network connectivity and provider availability
- **UI language not updating**: Restart app after language change

## Working with Source Code

When making changes to this codebase:
1. Focus on code structure and logic changes
2. Verify Kotlin syntax with `kotlinc -classpath . -no-stdlib <file>.kt` for basic checks
3. Examine test files to understand expected behavior
4. Review resource files for UI changes
5. Check manifest for permission requirements
6. Always update API keys in provider implementations when deploying

## References

- **Zhipu BigModel API**: [https://open.bigmodel.cn/dev/api](https://open.bigmodel.cn/dev/api)
- **Android Developer Guide**: Standard Android development practices apply
- **Kotlin Coroutines**: Used extensively for async operations
- **Retrofit/OkHttp**: Network layer implementation
- **Project Documentation**: See `README.md` and `INTERNATIONAL_AI_README.md`

## Repository Information from Documentation

From README.md:
```bash
# Original build process (DOES NOT WORK in this environment):
git clone https://github.com/zhipu-bigmodel/android10-apk.git
cd android10-apk
./gradlew assembleRelease  # FAILS - network limitations
```

From INTERNATIONAL_AI_README.md:
```bash
# Expected build process (DOES NOT WORK in this environment):
./gradlew assembleDebug    # FAILS - network limitations  
./gradlew assembleRelease  # FAILS - network limitations
./gradlew test            # FAILS - network limitations
./gradlew installDebug    # FAILS - network limitations
```

### App Functionality (from source code analysis)
The app provides:
1. **Multi-Provider AI Access**: 20+ international AI providers with automatic provider selection
2. **Real-Time Translation**: 50+ language support with translation caching
3. **Cultural Context Optimization**: Region-specific prompt engineering and cultural adaptation
4. **Smart Provider Routing**: Geo-aware load balancing and health monitoring
5. **Comprehensive UI**: Language selection, provider switching, translation toggle