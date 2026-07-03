package com.pilla.automation.base;

import io.appium.java_client.AppiumDriver;

public class WebDriverHolder {
    private static final ThreadLocal<AppiumDriver> threadLocalDriver = new ThreadLocal<>();

    public static void setDriver(AppiumDriver driver) {
        threadLocalDriver.set(driver);
    }

    public static AppiumDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void removeDriver() {
        threadLocalDriver.remove();
    }
}
