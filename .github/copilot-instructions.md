
# International AI Assistant Android App

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Repository Bootstrap and Build Process
- **CRITICAL**: This project requires stable internet connectivity for all build operations.
- **NEVER CANCEL** any build commands - Android builds can take 15-45 minutes depending on system performance. Set timeout to 60+ minutes.
- **Always check network connectivity first**: `ping google.com` before attempting builds.

**Essential Build Steps:**
```bash
# 1. Ensure you have the correct directory
cd /home/runner/work/zhipu-bigmodel-android10-apk/zhipu-bigmodel-android10-apk

# 2. Clean and build debug APK (takes 15-30 minutes, NEVER CANCEL)
gradle clean assembleDebug

# 3. Build release APK (takes 20-45 minutes, NEVER CANCEL)  
gradle assembleRelease

# 4. Run unit tests (takes 5-10 minutes, NEVER CANCEL)
gradle test

# 5. Install debug APK to connected device/emulator
gradle installDebug
```

**Build Timeout Requirements:**
- `gradle clean`: Set timeout to 300 seconds (5 minutes)
- `gradle assembleDebug`: Set timeout to 3600 seconds (60 minutes)
- `gradle assembleRelease`: Set timeout to 3600 seconds (60 minutes) 
- `gradle test`: Set timeout to 1800 seconds (30 minutes)
- `gradle installDebug`: Set timeout to 900 seconds (15 minutes)

### Development Environment Setup
- **Target Platform**: Android 10 (API 29), minimum Android 8.0 (API 26)
- **Language**: Kotlin with Java 8 compatibility
- **Build System**: Gradle 7.4.2 with Android Gradle Plugin 7.1.3
- **IDE Support**: Configured for Android Studio with view binding enabled

### API Key Configuration
Before building, API keys must be configured in the source code:
- **ZhipuAIProvider.kt**: Add `ZHIPU_API_KEY` constant
- **BaiduErnieProvider.kt**: Add `BAIDU_API_KEY` constant  
- **YandexGPTProvider.kt**: Add `YANDEX_API_KEY` constant
- Register for free API keys at respective provider websites

## Validation Scenarios

**CRITICAL**: Always run complete end-to-end testing after making changes:

### Complete User Flow Testing
1. **App Launch Validation**:
   ```bash
   # Install and launch app
   gradle installDebug
   # Manually verify app launches without crashes
   ```

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

### Build Validation Steps
- Always run `gradle clean` before major builds
- **NEVER skip ProGuard validation**: Release builds use minification
- Test both debug and release builds
- Verify APK outputs at: `app/build/outputs/apk/debug/` and `app/build/outputs/apk/release/`

### Code Quality Validation
- No built-in lint commands found - manual code review required
- Maintain >80% test coverage as per project guidelines
- Follow Kotlin coding conventions
- Test all new provider integrations thoroughly

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

## Common Tasks and Commands

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

### Testing Changes
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

## Build Times and Performance

**Expected Build Times** (varies by system):
- Clean build: 2-5 minutes
- Debug APK build: 15-30 minutes  
- Release APK build: 20-45 minutes (includes ProGuard)
- Unit tests: 5-10 minutes
- Install to device: 2-5 minutes

**Performance Notes:**
- Release builds use R8 minification and resource shrinking
- ProGuard rules optimize for AI provider classes
- App supports Android 8.0+ for wider device compatibility
- Designed for mobile-first responsive UI

## Troubleshooting

### Network Build Issues
- **Problem**: `No address associated with hostname` during build
- **Solution**: Ensure stable internet connection, check firewall settings
- **Workaround**: Cannot use `--offline` mode due to missing cached dependencies

### Common Build Failures
- **Missing repositories in buildscript**: Fixed in current codebase
- **Gradle version mismatch**: Use Gradle 7.4.2 as specified in wrapper
- **ProGuard issues**: Check `proguard-rules.pro` for required keep rules

### Runtime Issues
- **Service not connecting**: Check `InternationalAIService` is properly bound in `MainActivity`
- **API failures**: Verify API keys are configured correctly
- **Translation errors**: Check network connectivity and provider availability
- **UI language not updating**: Restart app after language change

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
- **CRITICAL**: Requires internet connectivity for all builds
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

## References

- **Zhipu BigModel API**: [https://open.bigmodel.cn/dev/api](https://open.bigmodel.cn/dev/api)
- **Android Developer Guide**: Standard Android development practices apply
- **Kotlin Coroutines**: Used extensively for async operations
- **Retrofit/OkHttp**: Network layer implementation
- **Project Documentation**: See `README.md` and `INTERNATIONAL_AI_README.md`

