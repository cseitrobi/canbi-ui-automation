package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class CongratulationsPage extends BasePage {

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected CongratulationsPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        String dashboardButtonLocator = locators.get("congratulation-dashboard-button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(dashboardButtonLocator)));
    }

    /**
     * Re-finds the dashboard button before clicking to avoid StaleElementReferenceException
     * when this is called from a later test (e.g. test 7 after test 6 completed).
     */
    public DashboardPage clickDashboardButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String dashboardButtonLocator = locators.get("congratulation-dashboard-button");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(dashboardButtonLocator)));
        button.click();
        return new DashboardPage(driver, locators);
    }
}
