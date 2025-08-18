# International AI Orchestration Android App

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Prerequisites and Environment Setup
- Set Android SDK environment: `export ANDROID_HOME=/usr/local/lib/android/sdk`
- Android SDK Location: `/usr/local/lib/android/sdk`
- Required Android API Level: 34 (available), Target was originally 29 but updated to 34
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
**CRITICAL**: This environment has network connectivity issues preventing dependency downloads.

**What DOES work:**
- `export ANDROID_HOME=/usr/local/lib/android/sdk` - Set Android SDK path
- `ls $ANDROID_HOME/platforms` - Verify available Android platforms (API 33-36 available)
- `kotlinc -version` - Verify Kotlin compiler (works)
- `java -version` - Verify Java runtime (works)

**What DOES NOT work due to network limitations:**
- `./gradlew assembleDebug` - FAILS: Cannot download dependencies from dl.google.com
- `./gradlew assembleRelease` - FAILS: Cannot download dependencies from dl.google.com  
- `./gradlew test` - FAILS: Cannot download dependencies
- `gradle wrapper` - FAILS: Cannot download gradle wrapper

**IMPORTANT**: Manual gradlew script has been created but Gradle builds will fail due to network restrictions preventing dependency downloads.

### Testing
- `./gradlew test` - FAILS due to network limitations - cannot download Android/JUnit dependencies
- Test files exist: `app/src/test/java/com/zhipu/bigmodel/international/InternationalAITest.kt`
- Tests cover: Language mapping, provider initialization, cultural context, translation, quality scoring

### Code Structure Overview
- **Source files**: 10 Kotlin files, 0 Java files
- **Languages supported**: 50+ languages with full UI translation
- **AI Providers**: 20+ international providers (Zhipu, Baidu, Yandex, Naver, etc.)
- **Translation providers**: Google Translate, Microsoft Translator, Baidu Translate, LibreTranslate
- **Architecture**: Service-based with provider registry, translation orchestrator, cultural optimizer

## Validation

### Manual Validation Scenarios (Cannot be performed due to build limitations)
Due to network restrictions, the following validation scenarios CANNOT be performed:
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

### Code Quality
- **Linting**: No lint tools configured that work without Gradle build
- **Formatting**: No automated formatting tools available without Gradle
- **Static Analysis**: Limited to basic syntax checking with kotlinc

## Common Tasks

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

### Key File Locations
- **Main Activity**: `app/src/main/java/com/zhipu/bigmodel/international/MainActivity.kt`
- **AI Service**: `app/src/main/java/com/zhipu/bigmodel/international/service/InternationalAIService.kt`
- **Core Interfaces**: `app/src/main/java/com/zhipu/bigmodel/international/core/`
- **Provider Implementations**: `app/src/main/java/com/zhipu/bigmodel/international/providers/`
- **Translation System**: `app/src/main/java/com/zhipu/bigmodel/international/translation/`
- **Tests**: `app/src/test/java/com/zhipu/bigmodel/international/InternationalAITest.kt`
- **Main Layout**: `app/src/main/res/layout/activity_main.xml`
- **Strings**: `app/src/main/res/values/strings.xml` (and values-zh/, values-ru/, values-ja/)

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

### Directory Structure Output
```
ls -la /home/runner/work/zhipu-bigmodel-android10-apk/zhipu-bigmodel-android10-apk/
total 84
drwxr-xr-x 5 runner docker  4096 Aug 18 03:54 .
drwxr-xr-x 3 runner docker  4096 Aug 18 03:53 ..
drwxr-xr-x 7 runner docker  4096 Aug 18 03:54 .git
-rw-r--r-- 1 runner docker  1020 Aug 18 03:54 .gitignore
-rw-r--r-- 1 runner docker 17703 Aug 18 03:54 BigModel
-rw-r--r-- 1 runner docker  7825 Aug 18 03:54 IMPLEMENTATION_SUMMARY.md
-rw-r--r-- 1 runner docker 10899 Aug 18 03:54 INTERNATIONAL_AI_README.md
-rw-r--r-- 1 runner docker  1067 Aug 18 03:54 LICENSE
-rw-r--r-- 1 runner docker  4854 Aug 18 03:54 README.md
drwxr-xr-x 3 runner docker  4096 Aug 18 03:54 app
-rw-r--r-- 1 runner docker   353 Aug 18 03:54 build.gradle
drwxr-xr-x 3 runner docker  4096 Aug 18 03:54 gradle
-rw-r--r-- 1 runner docker    57 Aug 18 03:54 settings.gradle
```

## Critical Limitations in This Environment

**NETWORK CONNECTIVITY**: This environment cannot access external repositories (dl.google.com, maven repositories). This means:
- Gradle builds WILL FAIL when trying to download dependencies
- Tests cannot run because they depend on Android/JUnit dependencies
- APK generation is not possible
- Dependency resolution will timeout

**AVAILABLE ANDROID SDKS**: Only API levels 33-36 are available, but the project originally targeted API 29. The build.gradle has been updated to use API 34.

**WHAT WORKS**: Code examination, file structure analysis, static syntax checking, architecture review.

**WHAT DOESN'T WORK**: Building, testing, running, APK generation, dependency installation.

## Troubleshooting Build Issues

If you encounter `dl.google.com: No address associated with hostname` errors, this is expected in this environment due to network restrictions. There is no workaround - building requires internet access to download Android Gradle Plugin and other dependencies.

## Working with Source Code

When making changes to this codebase:
1. Focus on code structure and logic changes
2. Verify Kotlin syntax with `kotlinc -classpath . -no-stdlib <file>.kt` for basic checks
3. Examine test files to understand expected behavior
4. Review resource files for UI changes
5. Check manifest for permission requirements
6. Always update API keys in provider implementations when deploying