package com.pilla.automation.util;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtil {
    private AppiumDriver driver;

    public ScreenshotUtil(AppiumDriver driver) {
        this.driver = driver;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
