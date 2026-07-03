package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class DashboardPage extends BasePage {

    private WebElement totalAssets;
    private WebElement text;
    private WebElement totalAssetsFz;
    private WebElement textFz;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected DashboardPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));

        String textLocator = locators.get("dashboard-text");
        text = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                textLocator)));

    }
}
