package com.pilla.automation.base;

import com.github.javafaker.Faker;
import com.pilla.automation.AppiumServerManager;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchSessionException;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.util.Map;

/**
 * Base class for all test classes.
 * This class provides common functionality for setting up and tearing down the Appium driver.
 * It uses the DriverConfiguration class to initialize the driver, which in turn uses the
 * AppiumServerManager singleton to ensure only one Appium server instance is created.
 */
@Slf4j
@Listeners(AllureTestWatcher.class)
public abstract class BaseTest {

    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected Faker faker = new Faker();

    private DriverConfiguration driverConfiguration;

    /**
     * Sets up the environment and driver configuration for testing.
     * This method initializes the driver based on the specified runtime and platform,
     * allowing tests to run in the desired environment.
     *
     * @param runtime  The runtime to use (e.g., local, browser-stack)
     * @param platform The platform to use (e.g., android, ios)
     * @param context  The test context providing additional metadata and test configuration
     */
    @Parameters({"runtime", "platform"})
    @BeforeClass
    public void setUp(String runtime, String platform, ITestContext context) {
        log.info("Setting up driver for runtime: {} and platform: {}", runtime, platform);
        setupDriver(runtime, platform, context);
    }

    /**
     * Tears down the Appium driver after the test class execution is completed.
     * <p>
     * If an Appium driver instance exists, this method performs the following steps:
     * - Logs a message indicating the driver is being terminated for the corresponding device.
     * - Quits the driver instance to release the resources associated with it.
     * - Removes the driver instance from the WebDriverHolder, ensuring proper cleanup of the thread-local storage.
     * <p>
     * This method is annotated with {@code @AfterClass}, ensuring it is executed
     * once after all the test methods in the class have been executed.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null && getDriver() != null) {
            log.info("Tearing down driver for device: {}", getDeviceName());
            try {
                getDriver().quit();
            } catch (NoSuchSessionException e) {
                log.debug("Session already closed: {}", e.getMessage());
            }
            WebDriverHolder.removeDriver();
            AppiumServerManager.getInstance().stopService();
        }
    }

    /**
     * Setup method that allows specifying the runtime and platform
     * This can be used to run tests on multiple devices in parallel
     *
     * @param runtime  The runtime to use (local or browser-stack)
     * @param platform The platform to use (android or ios)
     */
    protected void setupDriver(String runtime, String platform, ITestContext context) {
        this.driverConfiguration = new DriverConfiguration(runtime, platform, context);
        driver.set(driverConfiguration.initializeDriver());
        WebDriverHolder.setDriver(getDriver());
    }

    protected AppiumDriver getDriver() {
        return driver.get();
    }

    protected String getDeviceName() {
        return driverConfiguration.getDeviceName();
    }

    protected Map<String, String> getLocators() {
        return driverConfiguration.getLocators();
    }
}
