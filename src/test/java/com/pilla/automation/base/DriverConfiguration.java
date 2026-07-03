package com.pilla.automation.base;

import com.pilla.automation.AppiumServerManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.testng.ITestContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Represents a configuration for initializing and managing an Appium driver instance.
 * The class provides methods for loading platform-specific locators,
 * configuring driver capabilities, and initializing the driver based on the runtime
 * environment and platform.
 */
@Slf4j
public class DriverConfiguration {

    private final String runtime;
    private final String platform;
    private final Map<String, String> testParameters;
    private final Map<String, String> locators;

    private String deviceName;

    /**
     * Constructs a DriverConfiguration object.
     *
     * @param runtime  The runtime environment to use (e.g., local or browser-stack).
     * @param platform The platform to use (e.g., android or ios).
     * @param context  The test context, providing information and utilities for the test run.
     */
    public DriverConfiguration(String runtime, String platform, ITestContext context) {
        this.runtime = runtime;
        this.platform = platform;
        this.testParameters = context.getCurrentXmlTest().getAllParameters();
        this.locators = loadLocatorsFromProperties(platform);
    }

    /**
     * Initializes and returns an instance of the AppiumDriver based on the provided runtime and platform.
     * The method configures the driver capabilities and determines the appropriate server URL based on the runtime.
     *
     * @return The initialized AppiumDriver instance configured for the specified runtime and platform.
     * @throws RuntimeException if an unsupported or unknown runtime is provided.
     */
    public AppiumDriver initializeDriver() {
        log.info("Setting up driver for runtime: {} and platform: {}", runtime, platform);

        Capabilities options = switch (runtime) {
            case AppiumServerManager.LOCAL -> configureLocalDriver(platform);
            case AppiumServerManager.BROWSER_STACK -> configureBrowserStackDriver(platform);
            default -> throw new RuntimeException("Unknown runtime: " + runtime);
        };

        URL serverUrl = AppiumServerManager.getInstance().getServerUrl(runtime);

        return new AppiumDriver(serverUrl, options);
    }

    /**
     * Loads locator properties from a file specific to the provided platform,
     * and converts them into a map of key-value pairs.
     *
     * @param platform The platform name (e.g., "android" or "ios") used to determine which properties file to load.
     * @return A map containing locator properties as key-value pairs.
     * @throws RuntimeException If the properties file cannot be loaded or does not exist.
     */
    private Map<String, String> loadLocatorsFromProperties(String platform) {
        String filename = platform.toLowerCase() + ".properties"; // android.properties or ios.properties
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new FileNotFoundException("Cannot find properties file: " + filename);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filename, e);
        }

        // Convert Properties to Map<String, String>
        Map<String, String> map = new HashMap<>();
        for (String name : props.stringPropertyNames()) {
            map.put(name, props.getProperty(name));
        }
        return map;
    }

    /**
     * Configure the driver for local runtime
     *
     * @param platform The platform to use (android or ios)
     * @return The configured capabilities
     */
    private Capabilities configureLocalDriver(String platform) {
        String udid = testParameters.getOrDefault("device-udid", "unknown");
        String appPackageOrBundleId = testParameters.getOrDefault("app-package", "unknown");

        if ("android".equalsIgnoreCase(platform)) {
            this.deviceName = "Local device: " + udid;

            UiAutomator2Options options = new UiAutomator2Options()
                    .setUdid(udid)
                    //.setApp("C:\\Users\\Mohsin Farabi\\Downloads\\additiv automation_2\\additiv automation_2\\project-canb-testing-ui-main\\project-canb-testing-ui-main\\119.apk")
                    .setAppPackage(appPackageOrBundleId)
                    .setAppActivity(appPackageOrBundleId + ".MainActivity")
                    .setUiautomator2ServerInstallTimeout(Duration.of(5, ChronoUnit.MINUTES))
                    .setPlatformName("Android");
            // Allow long idle while waiting for activation; default 60s would kill session
            options.setCapability("appium:newCommandTimeout", 120);
            return options;
        } else if ("ios".equalsIgnoreCase(platform)) {
            this.deviceName = "Local iOS device: " + deviceName;
            String platformVersion = testParameters.getOrDefault("platform-version", "17.0");

            XCUITestOptions options = new XCUITestOptions()
                    .setUdid(udid)
                    .setPlatformName("iOS")
                    .setPlatformVersion(platformVersion)
                    .setBundleId(appPackageOrBundleId);
            options.setCapability("appium:newCommandTimeout", 120);
            return options;
        } else {
            throw new RuntimeException("Unsupported platform for local runtime: " + platform);
        }
    }

    /**
     * Configure the driver for BrowserStack runtime
     *
     * @param platform The platform to use (android or ios)
     * @return The configured capabilities
     */
    private Capabilities configureBrowserStackDriver(String platform) {
        String device = testParameters.get("browserstack.device");
        String osVersion = testParameters.get("browserstack.os-version");
        String app = testParameters.get("browserstack.app");
        String buildVersion = testParameters.get("browserstack.build-version");
        String buildName = device + "-" + buildVersion;

        this.deviceName = "Browserstack: " + device;

        if ("android".equals(platform)) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setCapability("appium:app", app);
            options.setCapability("appium:deviceName", device);
            options.setCapability("appium:platformVersion", osVersion);
            options.setCapability("appium:buildName", buildName);
            options.setCapability("appium:newCommandTimeout", 120);
            return options;
        } else if ("ios".equals(platform)) {
            XCUITestOptions options = new XCUITestOptions();
            options.setCapability("appium:app", app);
            options.setCapability("appium:deviceName", device);
            options.setCapability("appium:platformVersion", osVersion);
            options.setCapability("appium:buildName", buildName);
            return options;
        } else {
            throw new RuntimeException("Unsupported platform for BrowserStack runtime: " + platform);
        }
    }

    public Map<String, String> getLocators() {
        return locators;
    }

    public String getDeviceName() {
        return deviceName;
    }
}
