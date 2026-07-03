package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class AccountSolutionProfilePage extends BasePage {
    private WebElement selectStrategyButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected AccountSolutionProfilePage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        String selectStrategyButtonLocator = locators.get("product-selection-strategy-button");
        selectStrategyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectStrategyButtonLocator)));
    }
    public ConclusionPage clickSelectStrategy() {
        selectStrategyButton.click();
        return new ConclusionPage(driver, locators);
    }
    public TransferingYourCapitalPage clickSelectStrategyFz() {
        selectStrategyButton.click();
        return new TransferingYourCapitalPage(driver, locators);
    }
}
