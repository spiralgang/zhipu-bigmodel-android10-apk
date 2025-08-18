# Zhipu BigModel Android 10 APK - Developer Instructions

Always reference these instructions first and fallback to search or additional investigation only when you encounter information that does not match or is incomplete compared to what is documented here.

## Repository Structure and Purpose

This repository is a **documentation and specification repository** for a Zhipu BigModel Android 10 APK project. **CRITICAL**: This repository does NOT contain actual Android source code, build files, or a working Android project structure.

### What This Repository Contains:
- `README.md`: High-level project description and quick start guide
- `BigModel`: Detailed technical specifications with complete code examples and architecture
- `LICENSE`: MIT license file
- `.github/`: GitHub configuration and this instruction file

### What This Repository Does NOT Contains:
- No Android project structure (`app/`, `src/`, `build.gradle` files)
- No gradlew scripts or build system
- No actual Kotlin/Java source code files
- No XML layout files  
- No CI/CD workflows or build automation

## Working Effectively

### Understanding the Documentation
- **Always read `README.md` first** for project overview and quick start concepts
- **Reference the `BigModel` file** for complete technical specifications and code examples
- The `BigModel` file contains detailed Android project code including:
  - Complete `build.gradle` configuration
  - Full Kotlin source code for MainActivity, BigModelService, API interfaces
  - XML layout files and resource definitions
  - Manifest configuration
  - Build and deployment instructions

## Environment Setup Requirements

**EXACT INSTALLATION STEPS for Android Development:**

### Required SDKs and Tools
```bash
# Java Development Kit (required for Android development)
# Install OpenJDK 17 (recommended for current Android projects)
sudo apt-get update
sudo apt-get install openjdk-17-jdk

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc

# Android Studio installation (Linux)
wget https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2023.3.1.18/android-studio-2023.3.1.18-linux.tar.gz
tar -xzf android-studio-2023.3.1.18-linux.tar.gz
sudo mv android-studio /opt/
sudo ln -sf /opt/android-studio/bin/studio.sh /usr/local/bin/android-studio

# Set Android environment variables
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin

# Add to bashrc for persistence
echo 'export ANDROID_HOME=$HOME/Android/Sdk' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/emulator' >> ~/.bashrc  
echo 'export PATH=$PATH:$ANDROID_HOME/platform-tools' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin' >> ~/.bashrc
```

### Required Android SDK Components
Install these exact components via Android Studio SDK Manager or command line:
- **Android SDK Platform 29** (Android 10.0) - Required for target SDK
- **Android SDK Platform 26** (Android 8.0) - Required for minimum SDK  
- **Android SDK Build Tools 33.0.0** or latest
- **Android Emulator** (for testing)
- **Android SDK Platform-Tools** (includes ADB)
- **Android SDK Command-line Tools**

### Verification Commands
```bash
# Verify Java installation
java -version  # Should show OpenJDK 17

# Verify Android SDK
adb version    # Should show Android Debug Bridge version

# List installed SDK platforms
sdkmanager --list | grep "Android API"

# Verify required components are installed
sdkmanager --list | grep "build-tools;33"
sdkmanager --list | grep "platforms;android-29"
sdkmanager --list | grep "platforms;android-26"
```

### Creating the Android Project from Specifications

**CRITICAL**: You cannot build or run anything in this repository directly. To create an actual working Android project:

2. **Create new Android project**:
   ```bash
   # Launch Android Studio
   android-studio
   
   # Or create from command line:
   mkdir BigModelAndroid
   cd BigModelAndroid
   
   # In Android Studio: File > New > New Project
   # Use these EXACT settings:
   # - Template: Empty Activity
   # - Name: BigModel
   # - Package: com.zhipu.bigmodel  
   # - Language: Kotlin
   # - Minimum SDK: API 26 (Android 8.0)
   # - Build configuration language: Gradle (Kotlin DSL)
   ```

3. **Implement the specifications**:
   - Copy code examples from the `BigModel` file
   - Replace `YOUR_FREE_API_KEY` in BigModelService.kt with actual Zhipu AI API key
   - Follow the exact package structure and dependencies listed
   - Use provided XML layouts and resource files

4. **API Key Setup**:
   - Register at https://open.bigmodel.cn/
   - Create free API key (1,000,000 tokens/month quota)
   - Never commit API keys to version control

### Build Process (For Created Project)

Once you create the actual Android project from specifications:

```bash
# Clean build - typically takes 30-60 seconds
./gradlew clean

# Build debug APK for development - NEVER CANCEL: Takes 3-8 minutes on first build
./gradlew assembleDebug
# Set timeout to 15+ minutes for this command

# Build release APK - NEVER CANCEL: Takes 5-15 minutes depending on machine, includes ProGuard
./gradlew assembleRelease  
# Set timeout to 30+ minutes for this command

# Run lint checks - takes 2-5 minutes
./gradlew lint
# Set timeout to 10+ minutes for this command

# Find generated APK at:
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk

# Install on connected device (requires USB debugging enabled)
adb devices  # Verify device is connected
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Testing Commands (For Created Project)

```bash
# Run unit tests - NEVER CANCEL: Takes 2-10 minutes depending on test suite
./gradlew test
# Set timeout to 20+ minutes for this command

# Run connected Android tests (requires physical device or emulator) 
# NEVER CANCEL: Takes 5-20 minutes depending on device and test complexity
./gradlew connectedAndroidTest
# Set timeout to 45+ minutes for this command

# Generate test coverage report - takes 3-8 minutes  
./gradlew jacocoTestReport
# Set timeout to 15+ minutes for this command
```

### Testing and Validation

**CRITICAL MANUAL VALIDATION SCENARIOS** (after creating actual project):

**Always run through these complete end-to-end scenarios after making any changes:**

1. **Environment and Setup Test**:
   - Ensure API key is properly configured in BigModelService.kt
   - Launch app and verify no crashes on startup
   - Verify app requests necessary permissions on first launch
   - Check that foreground service notification appears

2. **Service Connection Test**:
   - Wait for "BigModel service connected" status to appear (should take 2-3 seconds)
   - Verify UI elements are properly enabled after service connection
   - Test app behavior if service binding fails

3. **Core API Functionality Test**:
   - Enter test prompt: "Hello, tell me about Android development in 100 words"
   - Tap "Generate with BigModel" button
   - Verify loading state shows: "Generating response..."
   - Confirm response appears within 10 seconds (typical: 3-5 seconds)
   - Check response quality and completeness
   - Verify "Powered by Zhipu AI" branding is visible throughout UI

4. **Extended Functionality Test**:
   - Test with longer prompts (500+ characters)
   - Test with multiple consecutive requests
   - Verify app handles API rate limits gracefully
   - Test prompt with special characters and emojis

5. **Error Handling and Edge Cases**:
   - Test with invalid/expired API key (should show clear error message)
   - Test with no network connection (should handle gracefully with timeout)
   - Test with very long prompts (over token limit)
   - Test app behavior when API quota is exhausted
   - Test foreground service behavior during screen rotation

6. **UI and UX Validation**:
   - Verify responsive layout on different screen sizes
   - Test app in portrait and landscape orientations
   - Confirm all text is readable and UI elements are accessible
   - Verify smooth scrolling in response text area
   - Check branding elements are properly positioned

**ALWAYS** validate these scenarios manually - automated tests cannot fully verify API integration and user experience.

### Dependencies and Requirements

Based on the specifications, the actual project would require:
- **Target SDK**: 29 (Android 10)
- **Minimum SDK**: 26 (Android 8.0)
- **Language**: Kotlin
- **Key Dependencies**:
  - androidx.core:core-ktx:1.6.0
  - androidx.appcompat:appcompat:1.3.1
  - com.google.android.material:material:1.4.0
  - com.squareup.retrofit2:retrofit:2.9.0
  - com.squareup.retrofit2:converter-gson:2.9.0
  - com.squareup.okhttp3:logging-interceptor:4.9.3
  - org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4

## Common Tasks

### Updating Documentation
When modifying documentation in this repository:
- Update `README.md` for user-facing changes
- Update `BigModel` file for technical specification changes
- Ensure code examples in `BigModel` remain valid and complete
- Test any URLs or references to external resources

### Code Examples Reference
The `BigModel` file contains complete, copy-pasteable code for:
- **BigModelService.kt**: Foreground service for API calls
- **MainActivity.kt**: Main UI and service binding  
- **BigModelApi.kt**: Retrofit API interface definitions
- **activity_main.xml**: Main UI layout
- **AndroidManifest.xml**: Permissions and service configuration
- **build.gradle**: Complete project configuration
- **Resource files**: Colors, drawables, strings

### API Integration Notes
- **Base URL**: https://open.bigmodel.cn/
- **Model**: glm-4 (GLM-4 model)
- **Authentication**: Bearer token with API key
- **Free Quota**: 1,000,000 tokens/month per account
- **Average Response Time**: 2-5 seconds
- **Required Permissions**: INTERNET, NETWORK_STATE, FOREGROUND_SERVICE, WAKE_LOCK

### Repository Navigation
- `/README.md`: Start here for project overview
- `/BigModel`: Complete technical reference and code examples  
- `/LICENSE`: MIT license terms
- `/.github/copilot-instructions.md`: This instruction file

## Validation Steps

Before suggesting changes to this documentation repository:
1. **Verify External URLs**: Check that all links to Zhipu AI and Android documentation are valid
2. **Code Syntax Check**: Ensure code examples in `BigModel` file use valid Kotlin/Android syntax
3. **API Compatibility**: Verify API endpoints and request formats match current Zhipu AI documentation
4. **Android Compatibility**: Confirm Android API levels and dependencies are current and compatible

## Key Limitations

**DO NOT**:
- Try to run `./gradlew` commands in this repository (they will fail - no gradle project exists)
- Attempt to build or compile code (no source code exists here)
- Look for CI/CD workflows (none exist - this is documentation only)
- Search for APK files or build outputs (none exist)
- Try to install or run the app from this repository (documentation only)

### Commands That WILL FAIL in This Repository
```bash
# These commands will fail with "gradlew not found" or similar errors:
./gradlew build              # FAILS - no gradlew script exists
./gradlew assembleRelease    # FAILS - no Android project structure
./gradlew test              # FAILS - no test infrastructure
gradle build                # FAILS - no build.gradle files
adb install app-debug.apk   # FAILS - no APK files exist
```

**ALWAYS**:
- Direct users to create a new Android project using the provided specifications
- Reference the detailed code examples in the `BigModel` file
- Emphasize the need to obtain and configure a real Zhipu AI API key
- Remind users this is a template/specification repository, not working code

## Security Considerations

- Never commit API keys to any repository
- Use environment variables or secure storage for API keys in actual implementations
- Follow Android security best practices for network requests
- Implement proper error handling for API failures
- Validate all user inputs before sending to API

## Brand Requirements

Per the specifications, any implementation must:
- Display "Powered by Zhipu AI" prominently in the UI
- Include Zhipu AI attribution in splash screen
- Maintain branding consistency across all UI elements
- Provide attribution to Zhipu AI in public deployments (per MIT license)

## Troubleshooting Common Issues

### Documentation Repository Issues
**Problem**: "Cannot find gradlew or build files"
**Solution**: This is expected - create a new Android project following the Environment Setup Requirements

**Problem**: "API examples don't work" 
**Solution**: Copy code examples from `BigModel` file into a properly created Android project structure

**Problem**: "Links to APK downloads are broken"
**Solution**: This repository provides specifications only - APK must be built from created Android project

### Android Development Issues (When Creating Actual Project)
**Problem**: "Build fails with 'compileSdk 29' error"
**Solution**: Install Android SDK Platform 29 via Android Studio SDK Manager

**Problem**: "API calls return 401 Unauthorized"
**Solution**: Verify API key is valid and properly formatted in BigModelService.kt (Bearer token format)

**Problem**: "App crashes on startup"
**Solution**: Check that all required permissions are declared in AndroidManifest.xml

**Problem**: "Foreground service notification doesn't appear"  
**Solution**: Ensure target SDK compatibility and proper notification channel setup for Android 10+

**Problem**: "Network requests timeout"
**Solution**: Check internet connectivity and verify Zhipu AI API endpoint accessibility

### API Integration Issues
**Problem**: "Quota exceeded errors"
**Solution**: Free tier provides 1M tokens/month - monitor usage or upgrade plan

**Problem**: "Slow response times (>10 seconds)"
**Solution**: Check network latency and consider implementing retry logic

**Problem**: "Chinese characters in API responses"
**Solution**: This is expected for Zhipu AI - ensure proper UTF-8 encoding in app