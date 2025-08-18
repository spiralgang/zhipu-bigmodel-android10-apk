# Free BigModel (Zhipu AI) Android 10 APK Project

**ALWAYS follow these instructions first and only fallback to additional search and context gathering if the information here is incomplete or found to be in error.**

This repository contains documentation and specifications for a lightweight Android 10 application that integrates with Zhipu BigModel API (GLM-4) for free text generation. The project is designed for rapid deployment with minimal setup.

## Repository Current State

**CRITICAL**: This repository currently contains documentation and code specifications only. The actual Android project structure has not been implemented yet.

Current contents:
- `README.md`: Project overview and user instructions
- `BigModel`: Comprehensive technical documentation with complete code examples
- `LICENSE`: MIT license file
- No actual Android project files (no `app/`, `gradle/`, source code, etc.)

## Working Effectively

### Initial Setup and Verification
When working with this repository, ALWAYS start by verifying the current state:

1. Check repository structure:
   ```bash
   ls -la
   find . -name "*.gradle*" -o -name "gradlew*" -o -name "AndroidManifest.xml"
   ```

2. If no Android project files exist:
   - This is expected in the current state
   - Reference the `BigModel` file for complete code specifications
   - Follow the "Creating Android Project Structure" section below

3. If Android project files do exist:
   - Follow the "Android Development Workflow" section below

### Creating Android Project Structure (If Not Present)

**NEVER CANCEL - Project creation may take 15+ minutes. Set timeout to 30+ minutes.**

If you need to implement the actual Android project based on the specifications:

1. Install Android development requirements:
   ```bash
   # Check current Java version (Java 11+ required for Android development)
   java -version
   
   # Install Java 11 if needed (requires sudo/root)
   sudo apt-get update && sudo apt-get install -y openjdk-11-jdk
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
   
   # Download and install Android SDK (requires internet access)
   # NOTE: Network access may be limited in some environments
   wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
   # If wget fails due to network restrictions:
   # - Use curl as alternative: curl -O https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
   # - Or manually download and transfer the SDK to the environment
   unzip commandlinetools-linux-8512546_latest.zip
   mkdir -p android-sdk/cmdline-tools/latest
   mv cmdline-tools/* android-sdk/cmdline-tools/latest/
   export ANDROID_HOME=$PWD/android-sdk
   export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools
   ```

2. Accept licenses and install required SDK components:
   ```bash
   yes | sdkmanager --licenses
   sdkmanager "platforms;android-29" "build-tools;29.0.3" "platform-tools"
   ```

3. Create the Android project structure following the specifications in the `BigModel` file:
   - Use the exact code provided in `BigModel` sections 1-8
   - Target SDK 29 (Android 10), minimum SDK 26
   - Include all dependencies specified in the documentation

### Android Development Workflow (If Project Files Exist)

**Build Requirements:**
- Java 11 or higher
- Android SDK with API level 29 (Android 10)
- Gradle 6.7+ (uses Gradle wrapper)

**NEVER CANCEL any build or test commands - Android builds can take 15-45 minutes. ALWAYS set timeouts to 60+ minutes.**

#### Building the Project

1. **CRITICAL**: Ensure Android SDK is properly configured:
   ```bash
   echo $ANDROID_HOME  # Should point to Android SDK directory
   echo $JAVA_HOME     # Should point to Java 11+
   ```

2. **Clean build** (takes 10-25 minutes, NEVER CANCEL):
   ```bash
   ./gradlew clean --timeout=60m
   ```

3. **Debug build** (takes 15-30 minutes, NEVER CANCEL):
   ```bash
   ./gradlew assembleDebug --timeout=60m
   ```

4. **Release build** (takes 20-45 minutes, NEVER CANCEL):
   ```bash
   ./gradlew assembleRelease --timeout=60m
   ```

5. **Find generated APKs**:
   ```bash
   find . -name "*.apk" -type f
   # Debug APK: app/build/outputs/apk/debug/app-debug.apk
   # Release APK: app/build/outputs/apk/release/app-release.apk
   ```

#### Testing

**NEVER CANCEL - Test suites may take 15-30 minutes to complete.**

1. **Unit tests** (if implemented):
   ```bash
   ./gradlew test --timeout=30m
   ```

2. **Android instrumented tests** (requires emulator, if implemented):
   ```bash
   ./gradlew connectedAndroidTest --timeout=45m
   ```

#### Code Quality

**ALWAYS run these before making any changes:**

1. **Lint check** (takes 5-10 minutes):
   ```bash
   ./gradlew lint --timeout=15m
   ```

2. **Check for API key placeholder**:
   ```bash
   grep -r "YOUR_FREE_API_KEY" . || echo "API key placeholder not found - good"
   ```

### Key Project Components

Based on the `BigModel` documentation, the project consists of:

**Core Files:**
- `app/src/main/java/com/zhipu/bigmodel/BigModelService.kt`: Main service handling API calls
- `app/src/main/java/com/zhipu/bigmodel/MainActivity.kt`: Primary UI activity
- `app/src/main/java/com/zhipu/bigmodel/BigModelApi.kt`: Retrofit API interface
- `app/src/main/res/layout/activity_main.xml`: Main UI layout
- `app/src/main/AndroidManifest.xml`: App configuration and permissions
- `app/build.gradle`: Module dependencies and build configuration
- `build.gradle`: Project-level configuration

**Build Configuration:**
- Target SDK: 29 (Android 10)
- Minimum SDK: 26 (Android 8.0)
- Kotlin language
- Uses Retrofit, OkHttp, Coroutines, AndroidX libraries

**Permissions Required:**
- INTERNET: For API communication
- ACCESS_NETWORK_STATE: Network status checking
- FOREGROUND_SERVICE: Background API operations
- WAKE_LOCK: Prevent sleep during API calls

### API Configuration

**CRITICAL**: Before building, ensure proper API key configuration:

1. Register at [https://open.bigmodel.cn/](https://open.bigmodel.cn/)
2. Obtain free API key (1,000,000 tokens/month)
3. Replace `YOUR_FREE_API_KEY` in `BigModelService.kt`:
   ```kotlin
   private const val API_KEY = "your-actual-api-key-here"
   ```

### Validation Scenarios

**MANUAL VALIDATION REQUIREMENT**: After any changes, ALWAYS test these complete scenarios:

#### If Android Project Exists:

1. **Build Validation**:
   - Clean build completes successfully
   - No lint errors or warnings
   - APK generates without errors

2. **Functional Validation** (if you can install and run the APK):
   - App starts without crashes
   - Service connection established (status shows "BigModel service connected")
   - Text input accepts user prompt
   - Generate button triggers API call
   - Response displays properly (may show error if API key not configured)
   - App handles network errors gracefully

3. **Integration Test**:
   - Test with valid Zhipu API key
   - Verify actual text generation works
   - Check API quota usage
   - Validate "Powered by Zhipu AI" branding appears

#### If Only Documentation Exists:

1. **Documentation Validation**:
   - All code examples in `BigModel` file are syntactically correct
   - Build instructions are complete and accurate
   - API endpoints and data structures match Zhipu API documentation

### Build Time Expectations

**CRITICAL TIMING INFORMATION - NEVER CANCEL:**

- **First-time setup**: 30-45 minutes (SDK download and configuration)
- **Clean build**: 10-25 minutes
- **Incremental build**: 3-8 minutes  
- **Release build**: 20-45 minutes (includes optimization and signing)
- **Lint check**: 5-10 minutes
- **Full test suite**: 15-30 minutes

**ALWAYS set timeouts to at least 60 minutes for build commands and 30+ minutes for tests.**

### Common Tasks Reference

#### Check current repository status:
```bash
ls -la
# Expected files: README.md, BigModel, LICENSE, .git/
# If Android project exists: also app/, gradle/, gradlew, build.gradle, etc.
```

#### Validate documentation accuracy:
```bash
# Check if build commands in README match actual project structure
grep -n "gradlew" README.md BigModel

# Verify Android SDK version consistency
grep -A 5 -B 5 "compileSdk.*29" BigModel

# Count dependencies to ensure completeness  
grep -c "implementation.*retrofit" BigModel  # Should show 2
grep -c "import.*retrofit" BigModel         # Should show 6

# Check API endpoint configuration
grep -n "open.bigmodel.cn" BigModel README.md
```

#### Search for TODO items and placeholders:
```bash
grep -r "TODO\|FIXME\|YOUR_FREE_API_KEY\|HACK" . --exclude-dir=.git
```

### Troubleshooting

**Common Issues:**

1. **"Command not found: ./gradlew"**: Android project structure not implemented yet
   - Reference `BigModel` file for implementation guidance
   - This is expected in current repository state

2. **Build failures**: Ensure proper SDK configuration
   - Verify ANDROID_HOME and JAVA_HOME environment variables
   - Check SDK components are installed

3. **API errors**: Check API key configuration
   - Verify key is valid and not expired
   - Check network connectivity
   - Review Zhipu API documentation

4. **Long build times**: This is normal for Android projects
   - NEVER cancel builds that appear to hang
   - Gradle builds can take 45+ minutes on first run

### Documentation Structure Analysis

The `BigModel` file contains complete technical specifications in Chinese with the following sections:
1. **Project Configuration** (build.gradle) - Dependencies and SDK settings
2. **Android Manifest** - Permissions and app configuration  
3. **API Interface** (BigModelApi.kt) - Retrofit service definitions
4. **Background Service** (BigModelService.kt) - Core API integration logic
5. **Main Activity** (MainActivity.kt) - UI and service binding
6. **Layout XML** (activity_main.xml) - UI components and styling
7. **Resources** - Colors, drawables, styling
8. **Build Instructions** - Gradle commands and APK generation
9. **API Key Setup** - Zhipu AI registration and configuration
10. **Feature Table** - Comprehensive feature comparison
11. **Usage Instructions** - End-user guidance
12. **Branding Strategy** - Marketing and attribution requirements
13. **Technical Advantages** - Performance and architectural benefits  
14. **Extension Recommendations** - Future feature suggestions

### Working with Documentation

Since this repository currently contains comprehensive documentation:

1. **Always reference the `BigModel` file first** - contains complete technical specifications
2. **Use README.md for user-facing information** - installation and usage instructions
3. **Cross-reference code examples** - ensure consistency between documentation and any implemented code

### Important Notes

- **Free API quota**: 1,000,000 tokens/month per Zhipu account
- **Network requirement**: Stable internet connection required for API calls
- **Device compatibility**: Android 8.0+ (API 26+)
- **APK size**: Target < 5MB for optimal distribution
- **Branding requirement**: Must include "Powered by Zhipu AI" attribution

**REMEMBER**: This repository is currently documentation-focused. When actual Android development begins, follow the comprehensive specifications provided in the `BigModel` file and always validate builds with extended timeouts.