# Mobile Automation

This repository contains automated tests for the Pilla mobile application using Appium, Java, and TestNG. The tests can be run locally or on BrowserStack.
node version - v22.17.1
java version - 21.0.8
appium version - 3.2.2
- uiautomator2@3.9.5 [installed (npm)]
- flutter@3.6.0 [installed (npm)]
- xcuitest@11.0.0 [installed (npm)]


Pixel 8 Pro API 35
## Prerequisites

### Required Tools

1. **Java Development Kit (JDK) 21**
   - Download and install from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
   - Set JAVA_HOME environment variable to the JDK installation directory

2. **Maven 3.9.9+**
   - Download from [Maven's official website](https://maven.apache.org/download.cgi)
   - Add Maven's bin directory to your PATH environment variable

3. **Node.js and npm**
   - Download and install from [Node.js official website](https://nodejs.org/)
   - Required for Appium installation

4. **Appium 2.x**
   - Install using npm: `npm install -g appium@next`
   - Install required drivers:
     - For Android: `appium driver install uiautomator2`
     - For iOS: `appium driver install xcuitest`

5. **Android SDK (for Android testing)**
   - Install Android Studio from [Android Developer website](https://developer.android.com/studio)
   - Set ANDROID_HOME environment variable to the Android SDK location
   - Add platform-tools to your PATH environment variable

6. **Xcode (for iOS testing, macOS only)**
   - Install from the Mac App Store
   - Install Xcode Command Line Tools: `xcode-select --install`


### BrowserStack Configuration

If you want to run tests on BrowserStack, you need to:

1. Create a BrowserStack account at [BrowserStack](https://www.browserstack.com/)
2. Set up environment variables:
   - `BROWSERSTACK_USERNAME`: Your BrowserStack username
   - `BROWSERSTACK_ACCESS_KEY`: Your BrowserStack access key

## Project Setup

1. Clone the repository:
   ```
   git clone <repository-url>
   cd pilla-mobile-automation
   ```

2. Install Maven dependencies:
   ```
   mvn clean install -DskipTests
   ```

## Running Tests

### Local Testing

1. Connect your Android or iOS device to your computer via USB
2. Enable USB debugging on your Android device or trust your computer on your iOS device
3. Make sure your device is recognized by running:
   - For Android: `adb devices`
   - For iOS: `xcrun xctrace list devices`

4. Update the `testng.xml` file with your device information:
   - Update the `device-udid` parameter with your device's UDID
   - Update the `app-package` parameter with your app's package name

5. Start the Appium server:
   ```
   appium
   ```

6. Run the tests:
   ```
   mvn clean test
   ```

You can also let the tests reuse an already running local Appium server instead of starting one internally. This is useful when IntelliJ's Maven runner cannot see your Node.js installation.

- Start Appium manually in a terminal: `appium`
- Then run Maven with one of these options:
  - shell: `mvn test -Dappium.server.url=http://127.0.0.1:4723`
  - IntelliJ Maven Runner VM options: `-Dappium.server.url=http://127.0.0.1:4723`

If you want the test code to start Appium itself, provide explicit paths when IntelliJ does not inherit your shell `PATH`:

- VM option: `-Dappium.node.path=/absolute/path/to/node`
- VM option: `-Dappium.js.path=/absolute/path/to/appium.js`
- Or environment variables: `APPIUM_NODE_BINARY`, `APPIUM_JS_PATH`

### BrowserStack Testing

1. Upload your app to BrowserStack and get the app URL
2. Update the `testng.xml` file:
   - Uncomment the BrowserStack test configuration
   - Update the `browserstack.device` parameter with your target device
   - Update the `browserstack.os-version` parameter with your target OS version
   - Update the `browserstack.app` parameter with your app URL from BrowserStack
   - Update the `browserstack.build-version` parameter with your build version

3. Run the tests:
   ```
   mvn clean test
   ```
   
## Test Reports

After running the tests, Allure reports are generated in the `target/allure-results` directory. To view the reports:

1. Install Allure command-line tool:
   ```
   npm install -g allure-commandline
   ```

2. Generate and open the report:
   ```
   allure serve target/allure-results
   ```

## Project Structure

- `src/main/java/com/pilla/automation/pages`: Page objects representing app screens
- `src/main/java/com/pilla/automation/AppiumServerManager.java`: Manages Appium server instances
- `src/test/java/com/pilla/automation/base`: Base test classes and configuration
- `src/test/java/com/pilla/automation`: Test classes
- `src/test/resources`: Configuration files and test data
  - `android.properties`: Android-specific locators
  - `ios.properties`: iOS-specific locators
- `testng.xml`: TestNG configuration file

## Email / activation

Registration flows use **MailTmService**: temporary email accounts are created via the [Mail.tm](https://mail.tm) REST API. The activation link is obtained by polling for incoming messages (no Mercure/real-time stream). No configuration is required.

## Troubleshooting

1. **Appium server fails to start**:
   - Make sure no other instance of Appium is running
   - Check if the port is already in use
   - If IntelliJ Maven says it cannot connect to Node, configure `appium.server.url` to an already running Appium server or pass `appium.node.path`

2. **"Activation email not received"** (registration test):
   - Mail.tm may be slow; the test polls for up to 2 minutes. Ensure the app actually sends the activation email to the address shown in the form.

3. **Device not detected**:
   - Ensure USB debugging is enabled on Android devices
   - Make sure your device is trusted on your computer
   - Verify the device is listed in `adb devices` or `xcrun xctrace list devices`

4. **Test fails to find elements**:
   - Check if the locators in the properties files match the actual elements in your app
   - Use Appium Inspector to verify element locators
   - Optional dialogs such as language selection and biometrics are now handled with short waits, but if another failure remains, capture the exact failing locator from the stack trace and compare it with `src/test/resources/android.properties`

5. **BrowserStack tests fail**:
   - Verify your environment variables are set correctly
   - Check if your app is uploaded correctly to BrowserStack
   - Verify the device and OS version are available on BrowserStack
